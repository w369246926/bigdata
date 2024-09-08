package com.flink.netty.test;

/**
 * <P>
 * 调用Http的处理器
 * </P>
 * 
 * @作者 hayashi
 */
public class HttpCallHandler extends SimpleChannelHandler {

    /** K区分（结束）的字节数 */
    private static final int K_DIVISION_END_BYTE_SIZE = 3;

    /** 用于Http调用流量控制的信号量（按业务编号哈希） */
    private static HashMap<String, Semaphore> semaphoreMap;

    /** 代表业务编号 */
    private String repBusinessNo;

    /**
     * 构建用于Http调用流量控制的信号量<br>
     * <br>
     * ・从ConfigManager获取包含所有业务设置的MapIterator
     * ・对从MapIterator获取的每个元素，使用JNDI名称创建信号量，并将其注册到HashMap中<br>
     * 信号量的许可数可通过从配置获取的getFlowHttp()方法获得
     */
    public static void initSemaphore() {
        semaphoreMap = new HashMap<String, Semaphore>();

        // 获取BusinessConfigData的列表
        MapIterator mapIterator = ConfigManagerEx.getBusinessConfigMapIterator();

        while (mapIterator.hasNext()) {
            mapIterator.next();
            BusinessConfigDataEx config = (BusinessConfigDataEx) mapIterator.getValue();

            semaphoreMap.put(config.getBusinessNo(), new Semaphore(config.getHttpFlow(), true));
        }
    }

    /**
     * 返回用于Http调用流量控制的信号量
     * 
     * @param businessNo 业务编号
     * @return
     */
    private Semaphore getSemaphore(String businessNo) {
        BusinessConfigDataEx businessConfig = ConfigManagerEx.getBusinessConfigData(this.repBusinessNo, businessNo);

        return semaphoreMap.get(businessConfig.getBusinessNo());
    }

    /**
     * 返回T6计时器值
     * 
     * @param businessNo 业务编号
     * @return T4计时器值
     */
    private int getT4Timer(String businessNo) {
        BusinessConfigDataEx businessConfig = ConfigManagerEx.getBusinessConfigData(this.repBusinessNo, businessNo);

        return businessConfig.getT4Timer();
    }

    /**
     * 构造函数
     * 
     * @param repBusinessNo 代表业务编号
     */
    public HttpCallHandler(String repBusinessNo) {
        this.repBusinessNo = repBusinessNo;
    }

    /**
     * 获取连接时的时间
     */
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {

        LogManagerEx.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);

        // 设置连接时的时间
        ctx.setAttachment(new Date());

        super.channelOpen(ctx, e);
    }

    /**
     * 调用Http并发送接收到的响应电文.
     * <p>
     * </blockquote></blockquote> ・执行Http流量控制的许可获取。<br>
     * ・如果许可获取失败，则判断为T4超时。<br>
     * <blockquote> [T4超时 - 会话日志输出内容]<blockquote> ・结束代码：8<br>
     * ・结束代码详细信息："FLOWTOUT"<br>
     * ・错误检测位置："P"<br>
     * </blockquote></blockquote> ・调用Http。<br>
     * ・如果Http调用失败（超时/其他错误），则进行SNQT日志/SNQT详细日志输出，并返回错误电文。（处理结果：0x58） <br>
     * <blockquote> [T5超时 - 会话日志输出内容]<blockquote> ・结束代码：8<br>
     * ・结束代码详细信息："HttpTOUT "<br>
     * ・错误检测位置："P"<br>
     * </blockquote></blockquote> <blockquote> [T4超时 -
     * 会话日志输出内容]<blockquote> ・结束代码：8<br>
     * ・结束代码详细信息："TRANTOUT"<br>
     * ・错误检测位置："P"<br>
     * </blockquote></blockquote> <blockquote> [其他错误 -
     * 会话日志输出内容]<blockquote> ・结束代码：Z<br>
     * ・结束代码详细信息："HttpERR "<br>
     * </blockquote> </blockquote> ・如果是响应，则返回响应电文。<br>
     * </p>
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {

        LogManagerEx.outputTraceLog(e.getChannel(), Thread.currentThread().getStackTrace(), this.repBusinessNo);

        @SuppressWarnings("unchecked")
        TelegramItem<Telegram> item = (TelegramItem<Telegram>) e.getMessage();

        // 用于存放来自Http的响应的引用
        TelegramItem<Telegram> retItem = null;

        // 信号量许可是否获取成功
        boolean isGetSemaphorePermit = false;

        String businessNo = SessionData.getBusinessNo(e.getChannel());

        BusinessConfigDataEx businessConfig = ConfigManagerEx.getBusinessConfigData(this.repBusinessNo, businessNo);
        // 默认错误
        ErrorResponseTypeEx errorType = ErrorResponseTypeEx.HTTP_OTHER_ERROR;
        SessionLogEndCodeDetailEx endCode = SessionLogEndCodeDetailEx.HTTPERR;
        // 获取Http调用流量控制用的信号量
        Semaphore semaphore = getSemaphore(businessNo);
        // 获取-释放许可的try块
        try {
            isGetSemaphorePermit = semaphore.tryAcquire(getT4Timer(businessNo), TimeUnit.SECONDS);

            if (isGetSemaphorePermit) {
                // 电文接收时刻
                Date sessionStartDate = (Date) ctx.getAttachment();
                // 输出Http服务前的时间
                LogManagerEx.outputSnqtLog(ctx.getChannel(), SnqtLogIdEx.SNQT9001);
                int telegramMaxSize = ConfigManagerEx.getConfigData(this.repBusinessNo).getTelegramMaxSize();

                Client client = new SnetHttpClient();
                // 设置日志
                client.setHttpJournal(new SnetHttpJournal(e.getChannel()));
                InetSocketAddress remoteAddr = (InetSocketAddress) e.getChannel().getRemoteAddress();
                InetSocketAddress localAddr = (InetSocketAddress) e.getChannel().getLocalAddress();
                // 输出源端口号日志
                LogManagerEx.outputSnqtLog(
                        ctx.getChannel(), SnqtLogIdEx.SNQT0021, 
                        String.valueOf(remoteAddr.getPort()), String.valueOf(localAddr.getPort()));
                // 调用Http
                retItem = client.execute(e.getChannel(), item, businessConfig, telegramMaxSize, sessionStartDate);
                LogManagerEx.outputSnqtLog(ctx.getChannel(), SnqtLogIdEx.SNQT9002);
            }
        } catch (SnetHttpException ex) {
            LogManagerEx.outputSnqtLog(ctx.getChannel(), ex.getLogId(), ex.getMessage(), ex);
            errorType = ex.getErrorResponseType();
            // 如果是T5超时
            if (errorType == ErrorResponseTypeEx.T5_TIMEOUT) {
                endCode = SessionLogEndCodeDetailEx.HTTPCONNTOUT;
            }
            // 如果是T6超时
            else if (errorType == ErrorResponseTypeEx.T6_TIMEOUT) {
                endCode = SessionLogEndCodeDetailEx.HTTPSOCKETTOUT;
            }
            // 其他情况
            else {
                endCode = SessionLogEndCodeDetailEx.HTTPCONNERR;
            }
        } catch (Exception ex) {
            LogManagerEx.outputSnqtLog(ctx.getChannel(), SnqtLogIdEx.SNQT9007, ex);
            LogManagerEx.outputSnqtDetailLog(e.getChannel(), LogLevel.ERROR, "", ex, this.repBusinessNo);
            endCode = SessionLogEndCodeDetailEx.HTTPERR;
        } finally {
            if (isGetSemaphorePermit) {
                // 如果许可获取成功，则释放它
                semaphore.release();
            } else {
                // T4超时
                // 进行错误处理
                LogManagerEx.outputSnqtLog(e.getChannel(), SnqtLogIdEx.SNQT2008);
                endCode = SessionLogEndCodeDetailEx.FLOWTOUT;
                errorType = ErrorResponseTypeEx.T4_TIMEOUT;
            }
        }
        // 如果存在响应电文
        if (retItem != null) {
            if (checkResponseTelegram(retItem, e.getChannel())) {
                // 没有检查错误的情况下
                Channels.write(e.getChannel(), retItem);
            } else {
                doHttpErrorHandling(e.getChannel(), SessionLogEndCodeDetailEx.DENCHKER, ErrorResponseTypeEx.OTHER_ERROR);                
            }
        } else {// 如果是错误的情况
            doHttpErrorHandling(e.getChannel(), endCode, errorType);
        }
    }

    /**
     * 处理发生错误时的情况
     * 
     * @param channel 通道
     * @param endCodeDetail 会话日志结束代码详细信息
     * @param errorResponseType 错误响应类型
     */
    private void doHttpErrorHandling(Channel channel, SessionLogEndCodeDetailEx endCodeDetail,
            ErrorResponseTypeEx errorResponseType) {

        // 设置结束代码、结束代码详细信息、错误检测位置到会话日志信息中
        SessionLogData sessionLogData = SessionData.getSessionLogData(channel);
        sessionLogData.setEndcode(endCodeDetail.getEndCodeValue());
        sessionLogData.setEndcodedetail(endCodeDetail.name());
        sessionLogData.setErrorplace(endCodeDetail.getErrorPlaceValue());

        // 返回错误响应
        Channels.write(channel, errorResponseType);
    }

    /**
     * 进行响应电文的检查<br>
     * 被检查的项目有电文记录长度、电文区分、k区分（开始）的值
     * 
     * @param item 响应电文
     * @param channel 通道
     * @return 检查结果（true: 正常，false: 异常）
     */
    private boolean checkResponseTelegram(TelegramItem<Telegram> item, Channel channel) {

        Telegram header = item.getHeader();

        int telegramLength = 0;
        int kDivisionStart = 0;
        String telegramSegment = null;

        // 获取编码后电文的长度
        int headerByteSize = SNetProxyUtil.getTotalByteSize(header);
        int bodyByteSize = 0;

        if (item.getBody() != null) {
            bodyByteSize = item.getBody().length;
        }
        int totalByteSize = headerByteSize + bodyByteSize;

        LogManagerEx.outputSnqtDetailLog(channel, LogLevel.DEBUG, "headerByteSize = " + headerByteSize,
                this.repBusinessNo);
        LogManagerEx.outputSnqtDetailLog(channel, LogLevel.DEBUG, "item.getBody().length = " + bodyByteSize,
                this.repBusinessNo);

        // K区分夹住的项目的大小
        int kkSize = 0;

        if (header instanceof SNetExtensionTelegramHeader) {
            telegramLength = ((SNetExtensionTelegramHeader) header).getTelegramLength().intValue();
            telegramSegment = ((SNetExtensionTelegramHeader) header).getTelegramSegment();
            kDivisionStart = ((SNetExtensionTelegramHeader) header).getkDivisionStart().intValue();
            // 获取Boady部分（K区分夹住的项目的大小）
            // 企业ID(2) + 店号(6) + 电文字节大小 - k区分（结束）(3)
            kkSize = SNetExtensionTelegramHeaderInfo.BODY_BYTE_LENGTH + bodyByteSize - K_DIVISION_END_BYTE_SIZE;
        } else if (header instanceof SNetTelegramHeader) {
            telegramLength = ((SNetTelegramHeader) header).getTelegramLength().intValue();
            telegramSegment = ((SNetTelegramHeader) header).getTelegramSegment();
        } else {
            LogManagerEx.outputSnqtLog(channel, SnqtLogIdEx.SNQT1013, header.getClass().toString());
            return false;
        }

        // 检查结果
        boolean result = true;

        if (totalByteSize != telegramLength) {
            // 如果电文记录长度错误
            // 输出错误日志
            LogManagerEx.outputSnqtLog(channel, SnqtLogIdEx.SNQT1008, telegramLength, totalByteSize);
            result = false;
        } else if (!TelegramSegment.RESPONSE.getValue().equals(telegramSegment)) {
            // 如果电文区分错误
            // 输出错误日志
            LogManagerEx.outputSnqtLog(channel, SnqtLogIdEx.SNQT1009, telegramSegment);
            result = false;
        } else if (kkSize != kDivisionStart) {
            // 如果K区分（开始）错误
            // 输出错误日志
        	LogManagerEx.outputSnqtLog(channel, SnqtLogIdEx.SNQT1010, kkSize, kDivisionStart);
            result = false;
        }

        // 如果有检查错误
        if (!result) {
            // 头部以文本形式输出，体部以HexDump形式输出
            StringBuffer buffer = new StringBuffer();
            buffer.append(header.toString());
            buffer.append(", ");
            buffer.append(SNetProxyUtil.getHexDump(item.getBody()));

            LogManagerEx.outputSnqtDetailLog(channel, LogLevel.ERROR, buffer.toString(), this.repBusinessNo);

            // 将结束代码、结束代码详细信息、错误检测位置设置到会话日志信息中
            SessionLogData sessionLogData = SessionData.getSessionLogData(channel);
            sessionLogData.setEndcode(SessionLogEndCodeDetailEx.DENCHKER.getEndCodeValue());
            sessionLogData.setEndcodedetail(SessionLogEndCodeDetailEx.DENCHKER.name());
            sessionLogData.setErrorplace(SessionLogEndCodeDetailEx.DENCHKER.getErrorPlaceValue());
        }

        return result;
        
        
//        这段代码主要涉及以下功能模块、调用时机和数据传递：
//
//        1. **功能模块：**
//
//           - **HttpCallHandler 类：** 该类是一个处理 HTTP 请求的处理器，继承自 Netty 的 `SimpleChannelHandler`。
//          
//           - **initSemaphore() 方法：** 该方法用于初始化 HTTP 请求流量控制的信号量。遍历配置管理器中的业务配置数据，为每个业务创建一个信号量，并将其存储在 `semaphoreMap` 中。
//
//           - **getSemaphore(String businessNo) 方法：** 该方法根据业务号从 `semaphoreMap` 中获取相应的信号量。
//
//           - **getT4Timer(String businessNo) 方法：** 该方法用于获取 T4 定时器的数值，即超时时间。
//
//           - **channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) 方法：** 该方法在建立连接时被调用，记录连接建立的时间。
//
//           - **messageReceived(ChannelHandlerContext ctx, MessageEvent e) 方法：** 该方法在接收到消息时被调用，处理 HTTP 请求的发送和接收。包括流量控制、HTTP 请求的发起、处理 HTTP 响应、日志记录等。
//
//           - **doHttpErrorHandling(Channel channel, SessionLogEndCodeDetailEx endCodeDetail, ErrorResponseTypeEx errorResponseType) 方法：** 该方法用于处理 HTTP 请求过程中发生的错误，设置会话日志信息的结束代码、结束代码详细信息和错误检测位置，并返回相应的错误响应。
//
//           - **checkResponseTelegram(TelegramItem<Telegram> item, Channel channel) 方法：** 该方法用于检查 HTTP 响应电文的有效性，包括零件长度、电文区分、K 区分等。如果存在检查错误，会记录错误日志，并设置会话日志信息的结束代码、结束代码详细信息和错误检测位置。
//
//        2. **调用时机：**
//
//           - **initSemaphore() 方法：** 在程序初始化阶段调用，用于初始化信号量。
//
//           - **channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) 方法：** 在建立连接时调用。
//
//           - **messageReceived(ChannelHandlerContext ctx, MessageEvent e) 方法：** 在接收到消息时调用，处理 HTTP 请求的整个生命周期。
//
//        3. **数据传递：**
//
//           - **通过参数传递：** 方法参数和类成员变量被用于在不同方法之间传递数据。例如，`repBusinessNo` 在构造函数中设置，然后在各个方法中使用。
//
//           - **通过类成员变量：** 一些状态信息被存储在类的成员变量中，以在不同的方法调用之间保持状态。
//
//           - **通过返回值传递：** 一些方法返回布尔值或其他结果，以便在调用方判断操作是否成功。
//
//           - **通过方法调用传递：** 一些方法直接调用其他方法，并通过参数传递数据。
//
//        以上是对代码的简要分析，具体的功能和调用时机可能还需要结合整个系统的架构和上下文来更全面地理解。
//           

