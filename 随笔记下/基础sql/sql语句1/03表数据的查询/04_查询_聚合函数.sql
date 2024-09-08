/*
	聚合函数
	标准语法：
		SELECT 函数名(列名) FROM 表名 [WHERE 条件];
*/
-- 计算product表中总记录条数
SELECT COUNT(*) FROM product;
--查询 pirce 大于200的数据有多少条
SELECT COUNT(pid) FROM product where pirce > 200;

-- 获取最高价格
SELECT MAX(price) FROM product;


-- 获取最低库存
SELECT MIN(stock) FROM product;

-- 获取平均值
SELECT avg(stock) FROM product;


-- 获取总库存数量
SELECT SUM(stock) FROM product;
查询creat_id = 苹果 的数据 stock 的值 累加等于多少
SELECT SUM(stock) FROM product where creat_id = '苹果';


-- 获取品牌为苹果的总库存数量
SELECT SUM(stock) FROM product WHERE brand='苹果';

-- 获取品牌为小米的平均商品价格
SELECT AVG(price) FROM product WHERE brand='小米';
