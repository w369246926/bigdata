/*
	分组查询
	标准语法：GROUP BY,一般将分组和聚合同时使用,比如某某分组,每个组的求和或平均或累计等等,
	from 后接 WHERE 后接 group by  后接 having 后接 ORDER BY
	查询表    条件筛选      分组           筛选        排序
		SELECT 列名 FROM 表名 [WHERE 条件] GROUP BY 分组列名 [HAVING 分组后条件过滤] [ORDER BY 排序列名 排序方式];
*/
-- 按照品牌分组，获取每组商品的总金额
SELECT brand,SUM(price) FROM product GROUP BY brand;

-- 对金额大于4000元的商品，按照品牌分组,获取每组商品的总金额
SELECT brand,SUM(price) FROM product WHERE price > 4000 GROUP BY brand;

-- 对金额大于4000元的商品，按照品牌分组，获取每组商品的总金额，只显示总金额大于7000元的
SELECT brand,SUM(price) getSum FROM product WHERE price > 4000 GROUP BY brand HAVING getSum > 7000;

-- 对金额大于4000元的商品，按照品牌分组，获取每组商品的总金额，只显示总金额大于7000元的、并按照总金额的降序排列
SELECT brand,SUM(price) getSum FROM product 
WHERE price > 4000 
GROUP BY brand 
HAVING getSum > 7000 
ORDER BY getSum DESC;

提高group by语句的效率
1、反例:先分组，再过滤
select job, avg（salary） from employee
group by job
having job ='develop' or job = 'test';
2、正例:先过滤，后分组
select job，avg（salary） from employee
where job ='develop' or job = 'test'
group by job;
理由:可以在执行到该语句前，把不需要的记录过滤掉