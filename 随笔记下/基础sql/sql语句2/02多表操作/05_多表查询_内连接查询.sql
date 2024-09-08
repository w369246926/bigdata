inner join 、left join、right join，优先使用inner join
三种连接如果结果相同，优先使用inner join，如果使用left join左边表尽量小。
inner join 内连接，只保留两张表中完全匹配的结果集；
left join会返回左表所有的行，即使在右表中没有匹配的记录；
right join会返回右表所有的行，即使在左表中没有匹配的记录；
如果inner join是等值连接，返回的行数比较少，所以性能相对会好一点；
使用了左连接，左边表数据结果尽量小，条件尽量放到左边处理，意味着返回的行数可能比较少；
这是mysql优化原则，就是小表驱动大表，小的数据集驱动大的数据集，从而让性能更优；

/*
	显示内连接
	标准语法：以下查询语句如需增加筛选条件可以增加 and 关键字 ---and name in (xxx,xxx)  ,and name = xxx or name = xxx
		SELECT 列名 FROM 表名1 [INNER] JOIN 表名2 ON 关联条件;
*/
-- 查询用户信息和对应的订单信息
SELECT * FROM USER INNER JOIN orderlist ON orderlist.uid = user.id;


-- 查询用户信息和对应的订单信息，起别名
SELECT * FROM USER u INNER JOIN orderlist o ON o.uid=u.id;


-- 查询用户姓名，年龄。和订单编号
SELECT
	u.name,		-- 用户姓名
	u.age,		-- 用户年龄
	o.number	-- 订单编号
FROM
	USER u          -- 用户表
INNER JOIN
	orderlist o     -- 订单表
ON
	o.uid=u.id;



/*
	隐式内连接
	标准语法：
		SELECT 列名 FROM 表名1,表名2 WHERE 关联条件;
*/
-- 查询用户姓名，年龄。和订单编号
SELECT
	u.name,		-- 用户姓名
	u.age,		-- 用户年龄
	o.number	-- 订单编号
FROM
	USER u,		-- 用户表
	orderlist o     -- 订单表
WHERE
	o.uid=u.id;
