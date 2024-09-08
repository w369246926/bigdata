-- 创建学生表(编号、姓名、年龄)  默认值约束default
CREATE TABLE student(
	id INT ,
	NAME VARCHAR(30) default '张三', --不给值时为张三
	age INT
);

-- 添加表结构方式给默认值
ALTER TABLE student modify Name VARCHAR(30) default '张三';


-- 查询学生表的详细信息
DESC student;

-- 添加数据
INSERT INTO student VALUES (NULL,'张三',23);
INSERT INTO student VALUES (NULL,'李四',23);



-- 建表后单独添加唯一约束
ALTER TABLE student MODIFY age INT UNIQUE;