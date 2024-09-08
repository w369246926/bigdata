/*
	结果是单行单列的
	标准语法：
		SELECT 列名 FROM 表名 WHERE 列名=(SELECT 列名 FROM 表名 [WHERE 条件]);
*/
-- 查询年龄最高的用户姓名
SELECT MAX(age) FROM USER;
SELECT NAME,age FROM USER WHERE age=(SELECT MAX(age) FROM USER);


/*
	结果是多行单列的
	标准语法：
		SELECT 列名 FROM 表名 WHERE 列名 [NOT] IN (SELECT 列名 FROM 表名 [WHERE 条件]); 
*/
-- 查询张三和李四的订单信息
SELECT * FROM orderlist WHERE uid IN (1,2);
SELECT id FROM USER WHERE NAME IN ('张三','李四');
SELECT * FROM orderlist WHERE uid IN (SELECT id FROM USER WHERE NAME IN ('张三','李四'));


/*
	结果是多行多列的
	标准语法：
		SELECT 列名 FROM 表名 [别名],(SELECT 列名 FROM 表名 [WHERE 条件]) [别名] [WHERE 条件];
*/
-- 查询订单表中id大于4的订单信息和所属用户信息
SELECT * FROM orderlist WHERE id > 4;
SELECT
	u.name,
	o.number
FROM
	USER u,
	(SELECT * FROM orderlist WHERE id > 4) o
WHERE
	o.uid=u.id;

--not in ( 不在其中)
--all(比较全部,结果全部大于)与
--in (在其中)
--any & some (比较全部,结果部分大于)或
--exists (where 后接 exists 结果为true 则执行外部sql,提升效率用节省资源)
select id from dept where id = 1;

select age from emp where dept_id = (select id from dept where id = 1)

select * from emp where age > (select max(age) from emp where dept_id = (select id from dept where id = 1)
)

select * from emp where age > all (select age from emp where dept_id = (select id from dept where id = 1)
)

select id from dept

select * from emp where dept_id != all (select id from dept)

select * from emp where dept_id not in (select id from dept)

select * from emp where dept_id in (select id from dept)