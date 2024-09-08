/*
	左外连接
	标准语法：
		SELECT 列名 FROM 表名1 LEFT [OUTER] JOIN 表名2 ON 条件;
*/
-- 查询所有用户信息，以及用户对应的订单信息
SELECT
	u.*,
	o.number
FROM
	USER u
LEFT OUTER JOIN
	orderlist o
ON
	o.uid=u.id;
	


/*
	右外连接
	标准语法：
		SELECT 列名 FROM 表名1 RIGHT [OUTER] JOIN 表名2 ON 条件;
*/
-- 查询所有订单信息，以及订单所属的用户信息
SELECT
	o.*,
	u.name
FROM
	USER u
RIGHT OUTER JOIN
	orderlist o
ON
	o.uid=u.id;

/*
并集连接
标准语法：union(去重) 和 union all (不去重)
SELECT 列名 FROM 表名1 RIGHT [OUTER] JOIN 表名2 ON 条件;
*/
