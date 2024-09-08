-- 创建学生表(编号、姓名、年龄)  编号设为主键 PRIMARY KEY
CREATE TABLE student(
	id INT PRIMARY KEY,
	NAME VARCHAR(30),
	age INT
);
--联合主键:以两个字段合在一起为主键
CREATE TABLE student(
	id INT,
	NAME VARCHAR(30),
	age INT,
	CONSTRAINT PK1 PRIMARY KEY (id,name)
);

-- 查询学生表的详细信息
DESC student;

-- 添加数据
INSERT INTO student VALUES (1,'张三',23);
INSERT INTO student VALUES (2,'李四',24);

-- 删除主键
ALTER TABLE student DROP PRIMARY KEY;


-- 建表后单独添加主键约束
ALTER TABLE student MODIFY id INT PRIMARY KEY;
ALTER TABLE student add PRIMARY KEY (id,name);