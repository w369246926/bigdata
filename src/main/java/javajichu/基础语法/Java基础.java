//public class Java01 {
//    public static void main(String[] args) {
//        System.out.println("HelloWorld");
//
//        int x = 10;
//        int y = x++ + x++ + x++;
//        System.out.println(y); // y的值是多少？
//        /*
//        解析，三个表达式都是++在后，所以每次使用的都是自增前的值，但程序自左至右执行，所以第一次自增时，使用的是10进行计算，但第二次自增时，x的值已经自增到11了，所以第二次使用的是11，然后再次自增。。。
//        所以整个式子应该是：int y = 10 + 11 + 12;
//        输出结果为33。
//        */
//    }
//}
///*
// * 关键字的特点：关键字的字母全部小写。
// * 常量：在程序运行过程中，其值不可以发生改变的量。
// * 1B（字节） = 8bit 1KB = 1024B 1MB = 1024KB 1GB = 1024MB 1TB = 1024GB
// * 整数类型  byte1-128~127
// *          short2-32768~32767
// *          int(默认)4-2的31次方到2的31次方-1
// *          long8-2的63次方到2的63次方-1
// * 浮点类型 float4负数：-3.402823E+38到-1.401298E-45 正数:1.401298E-45到3.402823E+38
// *          double(默认)8负数：-1.797693E+308到-4.9000000E-324 正数：4.9000000E-324   到1.797693E+308
// * 字符类型char20-65535
// * 布尔类型boolean1true，false
// *
// * 类型转化 分为两种情况：自动类型转换和强制类型转换。
// * 整数默认是int类型，byte、short和char类型数据参与运算均会自动转换为int类型。
// * boolean类型不能与其他基本数据类型相互转换。(强制转化!!!)
// *
// * 运算符  等级顺序：byte,short,char --> int --> long --> float --> double
// * /和%的区别：两个数据做除法，/取结果的商，%取结果的余数。
// * char类型:类型参与算术运算，使用的是计算机底层对应的十进制数值
// *  'a'  --  97		a-z是连续的，所以'b'对应的数值是98，'c'是99，依次递加
//    'A'  --  65		A-Z是连续的，所以'B'对应的数值是66，'C'是67，依次递加
//    '0'  --  48		0-9是连续的，所以'1'对应的数值是49，'2'是50，依次递加
// *
// * ++和-- 既可以放在变量的后边，也可以放在变量的前边。
// * 参与操作的时候，如果放在变量的后边，先拿变量参与操作，后拿变量做++或者--。
// *
// * a+=b，将a+b的值给a  |a-=b，将a-b的值给a |a*=b，将a×b的值给a |a/=b，将a÷b的商给a |a%=b，将a÷b的余数给a
// *
// * &逻辑与a&b，a和b都是true，结果为true，否则为false
// * |逻辑或a\|b，a和b都是false，结果为false，否则为true
// * ^逻辑异或a^b，a和b结果不同为true，相同为false
// * !逻辑非!a，结果和a的结果正好相反
// * &&短路与作用和&相同，但是有短路效果  短路与&&，如果左边为真，右边执行；如果左边为假，右边不执行。
// * ||短路或作用和|相同，但是有短路效果  短路或||，如果左边为假，右边执行；如果左边为真，右边不执行。
// *
// * 三元运算符 :  int c = a > b ? a : b; // 判断 a>b 是否为真，如果为真取a的值，如果为假，取b的值
// * 位移运算: 左位移(乘法) <<  右位移(除法) >>
// *   例: 1 << 3 : 1*2^3.        1 >> 3 : 1/2^3.
// *
// * 判断: switch  case  break  default , if  else if else ,
// * 循环 : for  ,  while
// *
// * 数组就是存储数据长度固定的容器，存储多个数据的数据类型要一致。
// * 动态初始化:  int[] arr = new int[3];
// * 静态初始化:  int[] arr = new int[]{11,22,33};
// * 数组长度为3，索引范围是0~2，但是我们却访问了一个3的索引。 程序运行后，将会抛出
// * ArrayIndexOutOfBoundsException  数组越界异常
// * NullPointerException 空指针异常。
// * 堆内存  存储对象或者数组，new来创建的，都存储在堆内存。
// * 方法栈  方法运行时使用的内存，比如main方法运行，进入方法栈中执行。
// *
// * 方法（method）:public static void 方法名 (参数1, 参数2, 参数3...) {
//	方法体;
//    }
// * 方法重载: * 多个方法在同一个类中 多个方法具有相同的方法名 多个方法的参数不相同，类型不同或者数量不同
// *
// * 十进制：Java中，数值默认都是10进制，不需要加任何修饰。
//    二进制：数值前面以0b开头，b大小写都可以。
//    八进制：数值前面以0开头。
//    十六进制：数值前面以0x开头，x大小写都可以。
//    注意: 书写的时候, 虽然加入了进制的标识, 但打印在控制台展示的都是十进制数据.
// *
// * 8421码又称BCD码，是BCD代码中最常用的一种BCD： (Binary-Coded Decimal‎) 二进制码十进制数在这种编码方式中，
// * 每一位二进制值的1都是代表一个固定数值，把每一位的1代表的十进制数加起来得到的结果就是它所代表的十进制数。
// *
// * 原码反码补码:
// * **原码 **:（可直观看出数据大小）就是二进制定点表示法，即最高位为符号位，【0】表示正，【1】表示负，其余位表示数值的大小。
// * **反码 :** 正数的反码与其原码相同；负数的反码是对其原码逐位取反，但符号位除外。
// * **补码 :** （数据以该状态进行运算）正数的补码与其原码相同；负数的补码是在其反码的末位加1。
// * **位运算概述 :**  位运算符指的是二进制位的运算，先将十进制数转成二进制后再进行运算。在二进制位运算中，1表示true，0表示false。
// * 二维数组 :int[][] arr = new int[3][3]; 三个数组中,每个数组中有三个数组
// *
// * **类是对事物的一种描述，对象则为具体存在的事物**
// * 注意：package 、import 、class 三个关键字的摆放位置存在顺序关系
// * static 关键字是静态的意思,是Java中的一个修饰符,可以修饰成员方法,成员变量
// *  被类的所有对象共享 是我们判断是否使用静态关键字的条件 随着类的加载而加载，优先于对象存在 对象需要类被加载后，才能创建
// *  可以通过类名调用 也可以通过对象名调用
// *
// * String类的构造方法  public String(char[] chs) : 根据字符数组的内容，来创建字符串对象
// * == 比较基本数据类型：比较的是具体的值
// * == 比较引用数据类型：比较的是对象地址值
// * equals : 比较字符串内容, 区分大小写  equalsIgnoreCase : 比较字符串内容, 忽略大小写
// * char c = String.charAt(i);  便利字符串中的每个字符
// * 截取字符串前三位  telString.substring(0,3);包含头，不包含尾
// * 截取字符串后四位  telString.substring(7);包含头
// * String.replace("TMD","***");  将TMD替换为****
// * String[] sArr = String.split(",");  根据逗号切割字符串
// * char[] toCharArray()  将字符串拆分为字符数组后返回
// * String s2 = String.valueOf(number); 转换int <-> String
// * StringBuilder append(任意类型)添加数据，并返回对象本身
// *              reverse()返回相反的字符序列
// *
// *
// *
// * git常用命令
// * git init初始化，创建 git 仓库
// * git status查看 git 状态 （文件是否进行了添加、提交操作）
// * git add 文件名添加，将指定文件添加到暂存区
// * git commit -m '提交信息'提交，将暂存区文件提交到历史仓库
// * git log查看日志（ git 提交的历史日志）
// * git reflog ：可以查看所有分支的所有操作记录（包括已经被删除的 commit 记录的操作）
// *  将代码切换到第二次修改的版本 指令：git reset --hard 版本唯一索引值
// * + 创建和切换
//  创建命令：git branch 分支名
//  切换命令：git checkout 分支名
//+ 新分支添加文件
//  查看文件命令：ls
//  总结：不同分支之间的关系是平行的关系，不会相互影响
//+ 合并分支
//  合并命令：git merge 分支名
//+ 删除分支
//  删除命令：git branch -d 分支名
//+ 查看分支列表
//  查看命令：git branch
// * 如何解决冲突  <<<<<<<和>>>>>>>中间的内容,就是冲突部分
//1. 修改冲突行，保存，即可解决冲突。
//2. 重新add冲突文件并commit到本地仓库，重新push到远程
// * idea中的操作
// * 版本切换:方式一: 控制台Version Control->Log->Reset Current Branch...->Reset  这种切换的特点是会抛弃原来的提交记录
// * 方式二:控制台Version Control->Log->Revert Commit->Merge->处理代码->commit   这种切换的特点是会当成一个新的提交记录,之前的提交记录也都保留
// * 创建分支 VCS->Git->Branches->New Branch->给分支起名字->ok
// * 切换分支  idea右下角Git->选择要切换的分支->checkout
// * 合并分支  VCS->Git->Merge changes->选择要合并的分支->merge
// *
// * 常用API:
// * Math :abs(int a)返回参数的绝对值
// *      ceil(double a)返回大于或等于参数的最小double值，等于一个整数
// *      floor(double a)返回小于或等于参数的最大double值，等于一个整数
// *      round(float a)按照四舍五入返回最接近参数的int
// *      max(int a,int b)返回两个int值中的较大值
// *      min(int a,int b)返回两个int值中的较小值
// *      pow (double a,double b)返回a的b次幂的值
// *      random()返回值为double的正值，[0.0,1.0)
// * System:  exit(int status)终止当前运行的   Java   虚拟机，非零表示异常终止
// *          currentTimeMillis()返回当前时间(以毫秒为单位)
// * 递归的思想:----递归求阶乘
// * 二分查找  :取中后 判断  ,再取中判断  每次查找一半的选项 (有一个前提条件，数组内的元素一定要按照大小顺序排列)
// * 冒泡排序  :每次找最大值 ,每次少找一次
// * 快速排序  :比目标数小的放左边,大的放右边
// * Arrays:
// * Arrays.toString(int[] a)返回指定数组的内容的字符串表示形式
// * Arrays.sort(int[] a)按照数字顺序排列指定的数组
// * Arrays.binarySearch(int[] a, int key)利用二分查找返回指定元素的索引
// * 计算机中时间原点:1970年1月1日 00:00:00
// * //格式化：从 Date 到 String  //从 String 到 Date
// * SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
//        String s = sdf.format(d);
// * SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date dd = sdf2.parse(ss);
// * LocalDateTime getYear()获取年 getMonthValue()获取月份（1-12） getDayOfMonth()获取月份中的第几天（1-31）
// *               getDayOfYear()获取一年中的第几天（1-366） DayOfWeek getDayOfWeek()获取星期
// *               getMinute()获取分钟 getHour()获取小时
// * Period between(开始时间,结束时间)计算两个“时间"的间隔 年月日
// * Duration  时分秒
// *
// * 异常: Error(报错,通过代码无法解决)
// *      Exception(编译时异常)  RunTimeException(运行时异常)
// * 处理异常: try cath , throws
// * throw new 异常();NullPointerException(); 为空异常
// * 自定义异常 :1. 写继承关系 2. 提供空参构造 3. 提供带参构造 public class AgeOutOfBoundsException extends RuntimeException
// * Optional获取对象 : get()如果存在值,返回值,否则抛出NoSuchElementException
// *                   isPresent()如果存在值,则返回true,否则为false
// * Optional<Student> optional = Optional.ofNullable(s); if(optional.isPresent()){
// *
// * 集合
// * 数组的长度是不可变的,集合的长度是可变的
// * 数组可以存基本数据类型和引用数据类型
// * 集合只能存引用数据类型,如果要存基本数据类型,需要存对应的包装类
// * Collection集合:
// * Collection<String> c = new ArrayList<>();  Iterator<String> it = c.iterator(); while (it.hasNext()) {String s = it.next(); if("b".equals(s)){  it.remove();
// * iterator():迭代器循环
// * for(String str : list){ :增强for 循环
// * List集合:
// * ArrayList: 底层是数组结构实现，查询快、增删慢
// *            add(E e)将指定的元素追加到此集合的末尾
// *            add(int index,E element)在此集合中的指定位置插入指定的元素
// *            remove(int index)删除指定索引处的元素，返回被删除的元素
// *            set(int index,E element)修改指定索引处的元素，返回被修改的元素
// *            get(int index)返回指定索引处的元素
// *            size()返回集合中的元素的个数
// * LinkedList:底层是链表结构实现，查询慢、增删快
// *            addFirst(E e)在该列表开头插入指定的元素
// *            addLast(E e)将指定的元素追加到此列表的末尾
// *            getFirst()返回此列表中的第一个元素
// *            getLast()返回此列表中的最后一个元素
// *            removeFirst()从此列表中删除并返回第一个元素
// *            removeLast()从此列表中删除并返回最后一个元素
// * Set:       不可以存储重复元素 没有索引,不能使用普通for循环遍历
// * TreeSet:   不可以存储重复元素 没有索引 可以将元素按照规则进行排序
// * 自然排序Comparable:让元素所属的类实现Comparable接口，重写compareTo(T o)方法
// * 比较器排序Comparator:让集合构造方法接收Comparator的实现类对象，重写compare(T o1,T o2)方法
// * HashSet:   底层数据结构是哈希表 存取无序 不可以存储重复元素 没有索引,不能使用普通for循环遍历
// * 哈希值:是JDK根据对象的地址或者字符串或者数字算出来的int类型的数值
// *          Object类中的public int hashCode()：返回对象的哈希码值
// *          同一个对象多次调用hashCode()方法返回的哈希值是相同的
// *          默认情况下，不同对象的哈希值是不同的。而重写hashCode()方法，可以实现让不同对象的哈希值相同
// * 哈希表结构:小于8个 数组加链表  大于8 个  数组 加红黑树
// * map:       双列集合,一个键对应一个值  键不可以重复,值可以重复
// *            put(K key,V   value)添加元素
// *            remove(Object key)根据键删除键值对元素
// *            clear()移除所有的键值对元素
// *            containsKey(Object key)判断集合是否包含指定的键
// *            containsValue(Object value)判断集合是否包含指定的值
// *            isEmpty()判断集合是否为空
// *            size()集合的长度，也就是集合中键值对的个数
// *            get(Object key)根据键获取值
// *            keySet()获取所有键的集合:Set<String> keySet = map.keySet();
// *            values()获取所有值的集合:Collection<String> values = map.values();
// *            entrySet()获取所有键值对对象的集合
// * 遍历思路://获取所有键的集合。用keySet()方法实现
//        Set<String> keySet = map.keySet();
//        //遍历键的集合，获取到每一个键。用增强for实现
//        for (String key : keySet) {
//            //根据键去找值。用get(Object key)方法实现
//            String value = map.get(key);
//            System.out.println(key + "," + value);
// *
// * //获取所有键值对对象的集合
//        Set<Map.Entry<String, String>> entrySet = map.entrySet();
//        //遍历键值对对象的集合，得到每一个键值对对象
//        for (Map.Entry<String, String> me : entrySet) {
//            //根据键值对对象获取键和值
//            String key = me.getKey();
//            String value = me.getValue();
//            System.out.println(key + "," + value);
// *
// * HashMap:     HashMap底层是哈希表结构的
//                依赖hashCode方法和equals方法保证键的唯一
//                如果键要存储的是自定义对象，需要重写hashCode和equals方法
// *
// * TreeMap:     TreeMap底层是红黑树结构
// *              依赖自然排序或者比较器排序,对键进行排序
// *              如果键存储的是自定义对象,需要实现Comparable接口或者在创建TreeMap对象时候给出比较器排序规则
// * Stream流:    获取Stream流 中间方法 终结方法
// *              Collection体系的集合 Map体系的集合 数组可以通过Arrays 同种数据类型的多个数据
// *              filter(Predicate predicate)用于对流中的数据进行过滤
// *              limit(long maxSize)返回此流中的元素组成的流，截取前指定参数个数的数据
// *              skip(long n)跳过指定参数个数的数据，返回由该流的剩余元素组成的流
// *              concat(Stream a, Stream b)合并a和b两个流为一个流
// *              distinct()返回由该流的不同元素
// *              forEach(Consumer action)对此流的每个元素执行操作
// *              count()返回此流中的元素数
// *              collect(Collector collector)把结果收集到集合中
// *              Collector toList()把元素收集到List集合中
// *              Collector toSet()把元素收集到Set集合中
// *              Collector toMap(Function keyMapper,Function valueMapper)把元素收集到Map集合中
// *
// * io流file:    File(String   pathname)通过将给定的路径名字符串转换为抽象路径名来创建新的 File实例
// *              File f1 = new File("E:\\itcast\\java.txt");
// *              File(String parent, String child): 从父路径名字符串和子路径名字符串创建新的 File实例
// *              File f2 = new File("E:\\itcast","java.txt");
// *              createNewFile()当具有该名称的文件不存在时，创建一个由该抽象路径名命名的新空文件
// *              mkdir()创建由此抽象路径名命名的目录
// *              mkdirs()创建由此抽象路径名命名的目录，包括任何必需但不存在的父目录
// *              delete()删除由此抽象路径名表示的文件或目录
// *              isDirectory()测试此抽象路径名表示的File是否为目录
// *              isFile()测试此抽象路径名表示的File是否为文件
// *              exists()测试此抽象路径名表示的File是否存在
// *              getAbsolutePath()返回此抽象路径名的绝对路径名字符串
// *              getPath()将此抽象路径名转换为路径名字符串
// *              getName()返回由此抽象路径名表示的文件或目录的名称
// *              listFiles()返回此抽象路径名表示的目录中的文件和目录的File对象数组
// *
// * InputStream：这个抽象类是表示字节输入流的所有类的超类
// * OutputStream：这个抽象类是表示字节输出流的所有类的超类
// * 字节流写数据: 绝对路径: 从盘符开始 相对路径: 相对当前项目下的路径
// *              子类名特点：子类名称都是以其父类名作为子类名的后缀
// *              FileOutputStream(String name)：创建文件输出流以指定的名称写入文件
// *              创建字节输出流对象(调用系统功能创建了文件,创建字节输出流对象,让字节输出流对象指向文件)
// *              如果文件不存在,会帮我们创建  如果文件存在,会把文件清空
// *              FileOutputStream fos = new FileOutputStream("myByteStream\\fos.txt");
// *              调用字节输出流对象的写数据方法
// *              fos.write(97);
// *              释放资源(关闭此文件输出流并释放与此流相关联的任何系统资源)
// *              fos.close();
// * 字节流写数据实现换行 : - windows:\r\n ,linux:\n ,mac:\r
// * 字节流写数据如何实现追加写入: 第二个参数为true ，则字节将写入文件的末尾而不是开头
// * FileOutputStream fos = new FileOutputStream("myByteStream\\fos.txt",true);
// *
// * 字节流读数据:通过打开与实际文件的连接来创建一个FileInputStream,该文件由文件系统中的路径名name命名
// *              FileInputStream fis = new FileInputStream("myByteStream\\fos.txt");
// *              while ((by=fis.read())!=-1) { System.out.print((char)by); } fis.close();
// * 字节流复制文件:
// *              //根据数据源创建字节输入流对象
//                FileInputStream fis = new FileInputStream("E:\\itcast\\窗里窗外.txt");
//                //根据目的地创建字节输出流对象
//                FileOutputStream fos = new FileOutputStream("myByteStream\\窗里窗外.txt");
// *              int by; while ((by=fis.read())!=-1) { fos.write(by); }   //释放资源
// * 字节流读数据....
// * 字节流复制文件....图片
// * 字节缓冲流    不必为 写入的每个字节 导致 底层系统的调用
// *              //字节缓冲输出流：BufferedOutputStream(OutputStream out)
// *              //字节缓冲输入流：BufferedInputStream(InputStream in)
// *              字节缓冲流复制视频
// * (I/O流2)"字符流":      由于字节流操作中文不方便,所以: 字符流 = 字节流 + 编码表
// * 对象序列化流
// * Properties:  - Properties可以保存到流中或从流中加载  (I/O流2)
// *
// * 多线程:      并发  在同一时刻，有多个指令在多个CPU上同时执行。 并行  在同一时刻，有多个指令在单个CPU上交替执行。
// * 进程和线程:  进程是正在运行的程序  线程是进程中的单个顺序控制流，是一条执行路径
// * 实现多线程
// * 方式一：继承Thread类 run()在线程开启后，此方法将被调用执行
// *                     start()使此线程开始执行，Java虚拟机会调用run方法()
// * 方式二：实现Runnable接口  需要共享资源时使用
// * 方式三: 实现Callable接口  需要有过程和结果时使用
// *
// * 同步代码块解决数据安全问题   synchronized(任意对象) {  多条语句操作共享数据的代码  }
// * 线程死锁是指由于两个或者多个线程互相持有对方所需要的资源，导致这些线程处于等待状态，无法前往执行
// *
// * 线程池:  节省线程资源,一次创建 循环使用 ,(多线程)
// * 网络编程三要素: IP 端口 协议
// * UDP协议: 由于使用UDP协议消耗系统资源小，通信效率高，所以通常都会用于音频、视频和普通数据的传输
// * 三种通讯方式: 单播 组播 广播
// * TCP协议: 三次握手  第一次握手，客户端向服务器端发出连接请求，等待服务器确认
//                     第二次握手，服务器端向客户端回送一个响应，通知客户端收到了连接请求
//                     第三次握手，客户端再次向服务器端发送确认信息，确认连接
//            四次挥手  1:客户端向服务端发送取消
//                     2:服务端回复客户端收到
//                     3:服务端向客户端发送取消
//                     4:客户端发送确认
// * NIO方式: 高并发,非阻塞
// * HTTP协议:超文本传输协议
// *          + 请求行 1方式:get post... 2:URL 3:版本协议
//            + 请求头
//                    + Host: 用来指定请求的服务端地址
//                    + Connection: 取值为keep-alive表示需要持久连接
//                    + User-Agent: 客户端的信息
//                    + Accept: 指定客户端能够接收的内容类型
//                    + Accept-Encoding: 指定浏览器可以支持的服务器返回内容压缩编码类型
//                    + Accept-Language: 浏览器可接受的语言
//            + 请求空行
//            + 请求体
// *  响应信息     + 响应行   http1.0:每次都是新连接  http1.1:支持常连接
// *                    响应状态码:+ 1xx: 指示信息(表示请求已接收，继续处理)
//                                + 2xx: 成功(表示请求已被成功接收、理解、接受)
//                                + 3xx: 请求重定向(要完成请求必须进行更进一步的操作)
//                                + 4xx: 客户端错误(请求有语法错误或请求无法实现)
//                                + 5xx: 服务器端错误(服务器未能实现合法的请求)
//                + 响应头 :Content-Type: 告诉客户端实际返回内容的网络媒体类型
//                        + text/html ----> 文本类型
//                        + image/png ----> png格式文件
//                        + image/jpeg ----> jpg格式文件
//                + 响应空行
//                + 响应体
// *
// * 类加载器:负责将.class文件（存储的物理文件）加载在到内存中
// * 反射  :  是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；
// *          对于任意一个对象，都能够调用它的任意属性和方法；
// *          这种动态获取信息以及动态调用对象方法的功能称为Java语言的反射机制。
// *       获取Class类对象的三种方式: 1:类名.class属性 2:对象名.getClass()方法 3:Class.forName(全类名)方法
// *      全类名:包名 + 类名 :Class clazz = Class.forName("com.itheima.myreflect2.Student");
// *      通过class属性来获取: Class clazz2 = Student.class;
// *      利用对象的getClass方法来获取class对象 : Class clazz3 = s.getClass();
// *
// * xml:通过标签来描述数据的一门语言,可拓展,XML文件是由很多标签组成的,而标签名是可以自定义的
// *      用于进行存储数据和传输数据,配置文件:可读性好,维护性高
// *      Document:整个xml文档
// *      element :所有标签
// *      Attribute: 所有属性
// *      text :所有文本
// *      利用解析器把xml文件加载到内存中,并返回一个文档对象 Document document = saxReader.read(new File("myxml\\xml\\student.xml"));
//        获取到根标签 Element rootElement = document.getRootElement();
// *枚举:是指将变量的值一一列出来,变量的值只限于列举出来的值的范围内
//        public enum s {    枚举项1,枚举项2,枚举项3; }
// *
// * 注解 :对我们的程序进行标注和解释 public @interface 注解名称 { public 属性类型 属性名() default 默认值 ; }
// *
// *
// *
// *
// *
// *
// *
// *
// *
//
// */