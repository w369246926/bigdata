/*
	排序查询
	标准语法：列名1为主要排序条件,列名2为次要排序条件{order by asc/desc}默认不写asc/desc时为升序
		SELECT 列名 FROM 表名 [WHERE 条件] ORDER BY 列名1 排序方式1,列名2 排序方式2;
*/
-- 按照库存升序排序
SELECT * FROM product ORDER BY stock ASC;

-- 查询名称中包含手机的商品信息。按照金额降序排序
SELECT * FROM product WHERE NAME LIKE '%手机%' ORDER BY price DESC;

-- 按照金额升序排序，如果前者金额相同，才会按后者照库存降序排列
SELECT * FROM product ORDER BY price ASC,stock DESC;

-- (去重)字段,不能查询其他字段,去重后会少于其他字段
SELECT distinct * FROM product ORDER BY price ASC,stock DESC;