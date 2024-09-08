/*
	创建视图
	标准语法
		CREATE VIEW 视图名称 [(列名列表)] AS 查询语句;
*/
-- 创建city_country视图，保存城市和国家的信息(使用指定列名)
CREATE VIEW city_country (city_id,city_name,country_name) AS
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
	查询视图
	标准语法
		SELECT * FROM 视图名称;
*/
-- 查询视图
SELECT * FROM city_country;