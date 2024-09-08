/*
	修改视图数据
	标准语法
		UPDATE 视图名称 SET 列名=值 WHERE 条件;
	
	修改视图结构
	标准语法
		ALTER VIEW 视图名称 (列名列表) AS 查询语句; 
*/
-- 修改视图数据,将北京修改为深圳。(注意：修改视图数据后，源表中的数据也会随之修改)
SELECT * FROM city_country;
UPDATE city_country SET city_name='深圳' WHERE city_name='北京';


-- 将视图中的country_name修改为name
ALTER VIEW city_country (city_id,city_name,NAME) AS
SELECT
	c1.id,
	c1.name,
	c2.name
FROM
	city c1,
	country c2
WHERE
	c1.cid=c2.id;


/*
	删除视图
	标准语法
		DROP VIEW [IF EXISTS] 视图名称;
*/
-- 删除city_country视图
DROP VIEW IF EXISTS city_country;