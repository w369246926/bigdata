package com.flink.sockettest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;

/**
 * FileSender is a file send Server. it recive the parameter from client, and accroding
 * to the parameter to send the matched file to client. 
 * this class extends Timer class, because of it will start the interval timer to 
 * keep the server is active. the server import JDK concurrent thread pool to resolve
 * the concurrent access stress.
 * at present, it supports 15 members to concurrent access Server in one cup.
 * the client is FileReceiver.
 * @author Ding Peng
 * @version 1.0 27/07/2011
 * @see DataClientHandler
 */

public class DataServerHandler extends Timer{

	/**
	 * thread pool hendler.
	 */
	private ExecutorService executorService;
	
	/**
	 * thread pool size.
	 */
	private static final int POOL_SIZE = 15;
	
	/**
	 * server processing port.
	 */
	private static int SERVER_PORT = 8979;
	
	/**
	 * the data transfer buffer size.
	 */
	private static final int BUFFER_SIZE = 1024*1024;
	
	public DataServerHandler() {}
	
	/**
	 * handle client socket and some business dispose in this method.
	 * if you want to use you must override the class TaskHandler's method
	 * handleService. 
	 */
	public void handleAccept(){
			try {
				ServerSocket ssc = new ServerSocket(SERVER_PORT);
				System.out.println("waitting...");
				while(true) {
					executorService = Executors.newFixedThreadPool(
							Runtime.getRuntime().availableProcessors() * POOL_SIZE);
					executorService.execute(new TaskHandler(ssc.accept()) {
						@Override
						protected void handleService() {
							//此处读取客户端传过来的交易码，交易码存放在HandlerTag.java这个文件中，若有新增交易的需求需现在此定义交易码
							//tag是从客户端读取进来的交易码，通过switch判断客户端传过来的交易码，来选择执行对应的service handler,service方法定义在
							//此方法之下，今后如果需增加新的service方法，需定义在此方法之下。另外在switch方法中要增加对应的case。
							int tag = readIntegerData();
							switch(tag) {
								case HandlerTag.FILE_HANDLE_TAG : 
									handleFileService(); 
									break;
								case HandlerTag.LITERAL_HANDLE_TAG :
									handleLiteralService();
									break;
								default :
									close();
							}
						}
						
						//此处是从客户端接收参数并发送文件给客户端
						protected void handleFileService() {
							String parameter = getAcceptedParameter("file");
							if("1".equals(parameter)) {	    		
								File f = new File("E:/Xunlei/电影/隐形的翅膀 钟恩淇.flv");
								List<File> fileList = new ArrayList<File>();
								fileList.add(f);
								handleFileTransfer(fileList);
							}
						}
						
						//此处是从客户端接收参数和回写字符串给客户端
						protected void handleLiteralService() {
							Map<String, String> paramMap = getAcceptedParameterMap();
							System.out.println(paramMap);
							handleLiteralTransfer("adasdfasdf");
						}
					});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	public static void main(String[] args) throws IOException {
		new DataServerHandler().startMonitor();
	}
	
	public static int getSocketPort() {
		String port = parseProperties().getProperty("SERVER_PORT");
		return Integer.valueOf(port);
	}
	
	public static Properties parseProperties() {
		Properties socketProperties = new Properties();
		try {
			socketProperties.load(DataServerHandler.class.getResourceAsStream("socket_cfg.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socketProperties;
	}
	
	/**
	 * start the server processing monitor, it keeps the server alive.
	 * @throws IOException 
	 */
	protected void startMonitor() throws IOException {
		super.schedule(new TimerTask(){
			@Override
			public void run() {
				System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				Socket s = new Socket();
				InetSocketAddress socketAddr = null;
				try {
					socketAddr = new InetSocketAddress(InetAddress.getLocalHost(), SERVER_PORT);
					s.connect(socketAddr);
					//close the test client.
					if(!s.isClosed())s.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					System.err.println("server " + socketAddr.getPort() + " is closed.");
					System.err.println("server restart .");
					DataServerHandler server = new DataServerHandler();
					server.handleAccept();
					System.err.println("server restart success.");
				}
			}
		}, 0, 1000);
	}
	
	/**
	 * this is the task handler class,
	 * it implements Runable interface to resolve mutil thread access.
	 */
	abstract class  TaskHandler implements Runnable{
		
		/**
		 * the accepted client socket
		 */
		Socket socketClient;
		
		/**
		 * the outstream from socket
		 */
		DataOutputStream socketOutputStream;
		
		/**
		 * the inputstream from socket
		 */
		DataInputStream socketInputStream;
		
		/**
		 * handle the task, the constructor will init the Socket client.
		 * and init the socket outputstream and socket inputstream. 
		 * @param socketClient
		 */
		public TaskHandler(Socket socketClient) {
			this.socketClient = socketClient;
			System.out.println(socketClient.getRemoteSocketAddress().toString());
			this.socketOutputStream = getSocketDataOuputStream();
			this.socketInputStream = getSocketDataInputStream();
			System.out.println("one client connect in.......");
		}
		
		/**
		 * get accepted parameter by param key.
		 * @param name param key
		 * @return value
		 */
		public String getAcceptedParameter(String name) {
			Map<String, String> params = getAcceptedParameterMap();
			return params.get(name);
		}
		
		/**
		 * get accepted parameter map from client.
		 * @return parameter map
		 */
		public Map<String, String> getAcceptedParameterMap() {
			Map<String, String> paramMap = new HashMap<String, String>();
			
			String result = readStringData();
			StringTokenizer tokenizer = new StringTokenizer(result, "&");
			paramMap = new HashMap<String, String>(tokenizer.countTokens());
			
			while(tokenizer.hasMoreTokens()) {
				String pair = tokenizer.nextToken();
				String[] pairArr = pair.split("=");
				paramMap.put(pairArr[0], pairArr[1]);
			}
			
			return paramMap;
		}
		
		protected int readIntegerData() {
			int result = 0;
			try {
				result = socketInputStream.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		/**
		 * read string data from client.
		 * @return data
		 */
		protected String readStringData() {
			String result = null;
			try {
				result = socketInputStream.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		/**
		 * write string data to client.
		 * @param data
		 */
		protected void writeStringData(String data) {
			try {
				socketOutputStream.writeUTF(data);
				socketOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * obtain the outputstream from socket.
		 * @return dataoutputstream.
		 */
		protected DataOutputStream getSocketDataOuputStream() {
			DataOutputStream dos = null;
			try {
				dos = new DataOutputStream(
							new BufferedOutputStream(
									socketClient.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return dos;
		}
		
		/**
		 * obtain the inputstream from socket.
		 * @return datainputstream
		 */
		protected DataInputStream getSocketDataInputStream() {
			DataInputStream dis = null;
			try {
				dis = new DataInputStream(
							new BufferedInputStream(
									socketClient.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return dis;
		}
		
		/**
		 * file transfer handler, it's in charge of file read and write.
		 * @param fileList files.
		 */
		protected void handleFileTransfer(List<File> fileList) {
			try {
				writeStringData(FileTokenizer.nameJoinedWithComma(fileList));
				writeStringData(FileTokenizer.lengthJoinedWithComma(fileList));
				byte[] readBytes = new byte[BUFFER_SIZE];
				
				for (Iterator iter = fileList.iterator(); iter
						.hasNext();) {
					File sfile = (File) iter.next();
					DataInputStream fdis = new DataInputStream(
							new BufferedInputStream(new FileInputStream(sfile)));
					
					int readNum = 0;
					while((readNum = fdis.read(readBytes)) != -1){
						socketOutputStream.write(readBytes, 0, readNum);
					}
					
					fdis.close();
				}
				
				socketOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				close();
			}
		}
		
		/**
		 * literal transfer handler, it's in charge of data write.
		 * @param dataMap literal pair map.
		 */
		protected void handleLiteralTransfer(Map<String, String> dataMap) {
			writePairParameterMap(dataMap);
			close();
		}
		
		/**
		 * literal transfer handler, it's in charge of data write.
		 * @param key data key.
		 * @param value data.
		 */
		protected void handleLiteralTransfer(String key, String value) {
			writePairParameter(key, value);
			close();
		}
		
		protected void handleLiteralTransfer(String data) {
			writeStringData(data);
			close();
		}
		
		/**
		 * close the socket inputstream and socket outputstream
		 */
		protected void close() {
			try {
				socketOutputStream.close();
				socketInputStream.close();
				socketClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * write parameters map to server, the parameter fromat pattern is pair.
		 * one pair joined with '=', mutil pair joind with '&' 
		 * @param paramMap
		 */
		@SuppressWarnings("unchecked")
		protected void writePairParameterMap(Map<String, String> paramMap) {
			StringBuffer sbf = new StringBuffer();
			for (Iterator iter = paramMap.entrySet().iterator(); iter.hasNext();) {
				Entry<String, String> entry = (Entry<String, String>) iter.next();
				sbf.append(entry.getKey())
				   .append("=")
				   .append(entry.getValue())
				   .append("&");
			}
			sbf.deleteCharAt(sbf.lastIndexOf("&"));
			try {
				socketOutputStream.writeUTF(sbf.toString());
				socketOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * write one pair to server. the pair pattern is key = value.
		 * @param key param name
		 * @param value param value
		 */
		protected void writePairParameter(String key, String value) {
			try {
				socketOutputStream.writeUTF(
							StringUtils.join(new String[]{key, value}, "="));
				socketOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * handle the file list transfer. the implmentation class must be override this abstract method.
		 */
		protected abstract void handleService();

		/**
		 * task executor this method override the father Runnable interface.
		 * if you wanna start a Thread you will implements Runnable and override its run method.
		 */
		public void run() {
			handleService();
		}
	}
}

/**
 * this is a file helper class, the class provide us file name and file length split joint,
 * if you need more helper method for files, you can add your own methods in this class.
 */
class FileTokenizer {
	
	/**
	 * file name decorator flag
	 */
	final static int NAME_FLAG = 1;
	
	/**
	 * file length decorator flag
	 */
	final static int LENGTH_FLAG = 2;
	
	/**
	 * file names will joined with comma.
	 * @param fileList files
	 * @return the joined name string.
	 */
	public static String nameJoinedWithComma(
							List<File> fileList) {
		return  delimiterHandler(fileList, ",", NAME_FLAG);
	} 
	
	/**
	 * file lengths will joined with comma.
	 * @param fileList files
	 * @return the joined length string.
	 */
	public static String lengthJoinedWithComma(
								List<File> fileList) {
		return  delimiterHandler(fileList, ",", LENGTH_FLAG);
	}
	
	/**
	 * delimiter joined handler, accroding to your delim, file attribute flag.
	 * @param fileList files
	 * @param delim joined delimiter
	 * @param handleFlag file attribute flag.
	 * @return the joined attributes string.
	 */
	public static String delimiterHandler(List<File> fileList, 
					String delim, int handleFlag) {
		
		if (StringUtils.isEmpty(delim))
			throw new IllegalArgumentException("the argument delim must not be null.");
		
		StringBuffer buffer = new StringBuffer();
		
		for (Iterator iter = fileList.iterator(); 
									iter.hasNext();) {
			File file = (File) iter.next();
			
			if (handleFlag == NAME_FLAG) 
				buffer.append(file.getName())
						.append(delim);
			else if (handleFlag == LENGTH_FLAG) 
				buffer.append(file.length())
						.append(delim);
		}
		//delete the last delimiter char
		return buffer.deleteCharAt(
				buffer.lastIndexOf(delim)).toString();
	}
	
}



