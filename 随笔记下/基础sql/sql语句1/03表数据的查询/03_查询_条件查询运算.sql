--算数运算
select 6+2;
select 6-2;
select 6*2;
select 6/2;
select 6%2;
select least(20 ,30) as small_num; --求最小值,不可为null
select greatest(20,30) as big_num; --求最大值,不可为null

/*
	条件查询
	标准语法：
		SELECT 列名列表 FROM 表名 WHERE 条件;
*/

--某 int double 列上涨10%
SELECT stock * 1.1 as new_stock FROM product;
--比较运算符
-- 查询库存大于20的商品信息
SELECT * FROM product WHERE stock > 20;
-- 查询品牌为华为的商品信息
SELECT * FROM product WHERE brand='华为';
--不等于
SELECT * FROM product WHERE brand != '华为';
SELECT * FROM product WHERE not (brand = '华为');
SELECT * FROM product WHERE brand <> '华为';
-- 查询金额在4000 ~ 6000之间的商品信息
SELECT * FROM product WHERE price >= 4000 AND price <= 6000;
SELECT * FROM product WHERE price >= 4000 && price <= 6000;  --逻辑与
SELECT * FROM product WHERE price BETWEEN 4000 AND 6000;
-- 查询库存为14、30、23的商品信息
--逻辑运算符
SELECT * FROM product WHERE stock=14 OR stock=30 OR stock=23;
SELECT * FROM product WHERE stock=14 OR stock=30 || stock=23;  --逻辑或
SELECT * FROM product WHERE stock IN(14,30,23);

-- 查询库存为null的商品信息
SELECT * FROM product WHERE stock IS NULL;

-- 查询库存不为null的商品信息
SELECT * FROM product WHERE stock IS NOT NULL;

--匹配运算符
-- 查询名称以小米为开头的商品信息
SELECT * FROM product WHERE NAME LIKE '小米%';

-- 查询名称第二个字是为的商品信息
SELECT * FROM product WHERE NAME LIKE '_为%';

-- 查询名称为四个字符的商品信息
SELECT * FROM product WHERE NAME LIKE '____';

-- 查询名称中包含电脑的商品信息
SELECT * FROM product WHERE NAME LIKE '%电脑%';

优化like语句
模糊查询，程序员最喜欢的就是使用like，但是like很可能让你的索引失效。
首先尽量避免模糊查询，如果必须使用，不采用全模糊查询，也应尽量采用右模糊查询， 即like ‘…%’，是会使用索引的；
左模糊like ‘%...’无法直接使用索引，但可以利用reverse + function index的形式，变化成 like ‘…%’；
全模糊查询是无法优化的，一定要使用的话建议使用搜索引擎。

使用合理的分页方式以提高分页的效率
select id,name from user limit 100000, 20
使用上述SQL语句做分页的时候，随着表数据量的增加，直接使用limit语句会越来越慢。
此时，可以通过取前一页的最大ID，以此为起点，再进行limit操作，效率提升显著。
select id,name from user where id> 100000 limit 20