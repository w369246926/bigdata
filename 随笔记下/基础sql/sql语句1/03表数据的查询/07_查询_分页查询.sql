/*
	分页查询
	标准语法：
		SELECT 列名 FROM 表名 
		[WHERE 条件] 
		[GROUP BY 分组列名]
		[HAVING 分组后条件过滤] 
		[ORDER BY 排序列名 排序方式] 
		LIMIT 当前页数,每页显示的条数;
	
	LIMIT 当前页数,每页显示的条数;
	公式：当前页数 = (当前页数-1) * 每页显示的条数
*/
-- 每页显示3条数据

-- 第1页  当前页数=(1-1) * 3
SELECT * FROM product LIMIT 0,3;

-- 第2页  当前页数=(2-1) * 3
SELECT * FROM product LIMIT 3,3;

-- 第3页  当前页数=(3-1) * 3
SELECT * FROM product LIMIT 6,3;