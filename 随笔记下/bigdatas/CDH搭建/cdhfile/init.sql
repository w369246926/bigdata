-- 卸载密码验证组件 (可选，根据需求)
UNINSTALL COMPONENT 'file://component_validate_password';

-- 创建root用户并设置远程访问
CREATE USER 'root'@'%' IDENTIFIED BY 'Liuxiao2@';
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'Liuxiao2@';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;

-- 创建scm用户并设置权限
CREATE USER 'scm'@'%' IDENTIFIED BY 'scm';
ALTER USER 'scm'@'%' IDENTIFIED WITH mysql_native_password BY 'scm';
GRANT ALL PRIVILEGES ON *.* TO 'scm'@'%';

-- 刷新权限
FLUSH PRIVILEGES;
