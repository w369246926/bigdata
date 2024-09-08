-- 创建学生表(编号、姓名、年龄)  编号设为主键自增
CREATE TABLE student(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(30),
	age INT
);

-- 查询学生表的详细信息
DESC student;

-- 添加数据
INSERT INTO student VALUES (NULL,'张三',23),(NULL,'李四',24);

-- 删除自增约束
ALTER TABLE student MODIFY id INT;
INSERT INTO student VALUES (NULL,'张三',23);

-- 建表后单独添加自增约束
ALTER TABLE student MODIFY id INT AUTO_INCREMENT;

--指定自增长值起始
CREATE TABLE student(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(30),
	age INT
)AUTO_INCREMENT=100;

ALTER TABLE student AUTO_INCREMENT=100;

--删除表数据后
delete from student ; --底层是一条一条删除,保留自增字段的值基础+1
truncate student; --底层是删除表的方式删除表数据,并重新建表,自增字段从1开始,即使建表时设置了开始值


