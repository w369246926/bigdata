/* 
 * Servlet翻译成中文是服务端脚本，它是SUN公司推出的一套规范，称为Servlet规范。
 * 通过点击[JavaEE8官方文档](https://javaee.github.io/javaee-spec/javadocs/)
 * 
 * 编写Servlet:
 * 第一步：编写一个普通类实现Servlet接口或者继承GenericServlet类或者继承HttpServlet**
 * 第二步：重写service方法
 * 第三步：在web.xml配置Servlet**
 * 第四步：启动tomcat服务器测试**
 * 
 * 通过web.xml 中配置 
 * <servlet>
        <servlet-name>servletDemo9</servlet-name>
        <servlet-class>com.itheima.web.servlet.ServletDemo9</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>servletDemo9</servlet-name>
        <url-pattern>/servletDemo9</url-pattern>
    </servlet-mapping>
    <welcome-file-list>
    <welcome-file>/login/login.html</welcome-file>
  </welcome-file-list>
 * 
 * 跳转到相应的类文件,继承extends HttpServlet ,实现 doget dopost 方法
 * 后期被@webservlet("/demo1") 取代
 * 
 * 请求响应 :Request&Response
 * Cookie&SessionJsp:
 * Cookie:它是客户端浏览器的缓存文件，里面记录了客户浏览器访问网站的一些内容。同时，也是HTTP协议请求和响应消息头的一部分
 * Cookie有大小，个数限制。每个网站最多只能存20个cookie，且大小不能超过4kb。同时，所有网站的cookie总数不超过300个。
 * HttpSession:服务端会话对象，用于存储用户的会话数据。
 * JSP:本质其实就是一个Servlet
 * EL表达式和JSTL:
 * 
 * 
 * 
 * 
 * 
 */



public class Javaweb {
    
}
