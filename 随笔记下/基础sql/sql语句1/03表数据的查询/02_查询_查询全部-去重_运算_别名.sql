/*
	查询全部数据
	标准语法：
		SELECT * FROM 表名;
*/
-- 查询product表所有数据
SELECT * FROM product;


/*
	查询指定列
	标准语法：
		SELECT 列名1,列名2,... FROM 表名;
*/
-- 查询名称、价格、品牌
SELECT NAME,price,brand FROM product;


/*
	去除重复查询
	标准语法：
		SELECT DISTINCT 列名1,列名2,... FROM 表名;
*/
-- 查询品牌
SELECT brand FROM product;
-- 查询品牌，去除重复
SELECT DISTINCT brand FROM product;



/*
	计算列的值
	标准语法：
		SELECT 列名1 运算符(+ - * /) 列名2 FROM 表名;
		
	如果某一列为null，可以进行替换
	ifnull(表达式1,表达式2)
	表达式1：想替换的列
	表达式2：想替换的值
*/
-- 查询商品名称和库存，库存数量在原有基础上加10
SELECT NAME,stock+10 FROM product;

-- 查询商品名称和库存，库存数量在原有基础上加10。进行null值判断
SELECT NAME,IFNULL(stock,0)+10 FROM product;



/*
	起别名
	标准语法：

		SELECT 列名1,列名2,... AS 别名 FROM 表名; --as可以省略
*/
-- 查询商品名称和库存，库存数量在原有基础上加10。进行null值判断。起别名为getSum
SELECT NAME,IFNULL(stock,0)+10 AS getSum FROM product;
SELECT NAME,IFNULL(stock,0)+10 getSum FROM product;
SELECT NAME as '姓名',IFNULL(stock,0)+10 as getSum FROM product;


--	表起别名
SELECT 列名1,列名2,... FROM 表名 as A; --as可以省略

SELECT NAME,IFNULL(stock,0)+10 FROM product as A;
SELECT A.NAME, B.name FROM product as A user as U;
SELECT A.NAME,IFNULL(A.stock,0)+10 FROM product A;

UNION操作符
UNION在进行表链接后会筛选掉重复的记录，所以在表链接后会对所产生的结果集进行排序运算，删除重复的记录再返回结果。
实际大部分应用中是不会产生重复的记录，最常见的是过程表与历史表UNION。
如：
select username,tel from user
union
select departmentname from department
这个SQL在运行时先取出两个表的结果，再用排序空间进行排序删除重复的记录，最后返回结果集，如果表数据量大的话可能会导致用磁盘进行排序。
推荐方案：采用UNION ALL操作符替代UNION，因为UNION ALL操作只是简单的将两个结果合并后就返回
