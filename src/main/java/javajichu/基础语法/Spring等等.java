/* 
 * IoC简介:控制反转，Spring反向控制应用程序所需要使用的外部资源
 * 耦合（Coupling）：代码书写过程中所使用技术的结合紧密度，用于衡量软件中各个模块之间的互联程度
 * 内聚（Cohesion）：代码书写过程中单个模块内部各组成部分间的联系，用于衡量软件中各个功能模块内部的功能联系
 * 高内聚，低耦合 就是同一个模块内的各个元素之间要高度紧密，但是各个模块之间的相互依存度却不要那么紧密
 * DI:依赖注入，应用程序运行依赖的资源由Spring为其提供，资源进入应用程序的方式称为注入
 * AOP:面向切面编程，一种编程**范式**，隶属于软工范畴，指导开发者如何组织程序结构
 * 事务特征（ACID）: 原子性（Atomicity）指事务是一个不可分割的整体，其中的操作要么全执行或全不执行
                    一致性（Consistency）事务前后数据的完整性必须保持一致
                    隔离性（Isolation）事务的隔离性是多个用户并发访问数据库时，数据库为每一个用户开启的事务，不能被其他事务的操作数据所干扰，多个并发事务之间要相互隔离
                    持久性（Durability）持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来即使数据库发生故障也不应该对其有任何影响
 * 
 * Spring MVC :  @RequestMapping("/requestParam3")
 * web.xml中配置SpringMVC核心控制器，用于将请求转发到对应的具体业务处理器Controller中（等同于Servlet配置）
 * @ResponseBody :将异步提交数据组织成标准请求参数格式，并赋值给形参,自动封装数据成json对象数据
 * SSM（Spring+SpringMVC+MyBatis）:
 * Spring环境配置,Spring环境配置(开启bean注解扫描)applicationContext.xml
 *               Mybatis配置事务映射(增删改查标签)
 *               Mybatis核心配置(配置账号密码扫描事务)
 * 整合MVC:       web.xml配置  spring-mvc.xml
 * 
 * Spring boot:     **自动配置****起步依赖****辅助功能**
 * **profile是用来完成不同环境下，配置动态切换功能的**。在yml中使用  --- 分隔不同配置
 *  application-dev.properties/yml 开发环境
    application-test.properties/yml 测试环境
    application-pro.properties/yml 生产环境
 *  - 配置文件： 再配置文件中配置：spring.profiles.active=dev
    - 虚拟机参数：在VM options 指定：-Dspring.profiles.active=dev
    - 命令行参数：java –jar xxx.jar  --spring.profiles.active=dev
 * Spring Cloud :
 *          并没有重复制造轮子，它只是将目前各家公司开发的比较成熟、经得起实际考验的服务框架组合起来。

 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class Spring等等 {
    
}
