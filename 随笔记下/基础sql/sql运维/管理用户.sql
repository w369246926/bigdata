mysql8,运维
--为某个用户创建数据库
CREATE DATABASE sqlquesiton;
--创建sql用户及密码
CREATE USER 'quesiton'@'%' IDENTIFIED BY 'sqlquesiton';
--将某种名称开头的数据库的权限赋予给某个人(数据库名称可以正则表达，匹配多个同样名称开头的数据库)
GRANT ALL PRIVILEGES ON `sqlquesiton%`.* TO 'quesiton'@'%';
--查看权限
SHOW GRANTS FOR 'sqlquesiton'@'%';


















MySQL用户账号和信息存储在名为mysql的MySQL数据库中，当需要获取所有用户账号列表时，可以使用以下代码

USE mysql;
SELECT user FROM user;

创建用户账号
CREATE USER user_name IDENTIFIED BY 'password';
INENTIFIED BY指定的口令为纯文本

重命名一个用户账号
RENAME USEER user_name TO new_name;
删除用户帐号
DROP USER user_name;

查看用户访问权限
SHOW GRANTS FOR user_name;
授予用户权限
GRANT option ON table_name.col_name TO user_name
撤销权限
REVOKE option ON table_nmae.col_name FROM user_name

更改密码
SET PASSWORD FOR user_name = Password('PASSWORD')
-- 更新当前登录用户的口令
SET PASSWORD = Password('password')

