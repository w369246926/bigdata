/*
	给指定列添加数据
	标准语法：
		INSERT INTO 表名(列名1,列名2,...) VALUES (值1,值2,...);
*/
-- 向product表添加一条数据
INSERT INTO product (id,NAME,price,stock,insert_time) VALUES (1,'手机',1999.99,25,'2020-02-02');


-- 向product表添加指定列数据
INSERT INTO product (id,NAME,price) VALUES (2,'电脑',3999.99);

/*
	给全部列添加数据
	标准语法：
		INSERT INTO 表名 VALUES (值1,值2,值3,...);
*/
-- 默认给全部列添加数据
INSERT INTO product VALUES (3,'冰箱',1500,35,'2030-03-03');

/*
	批量添加所有列数据
	标准语法：
		INSERT INTO 表名 VALUES (值1,值2,值3,...),(值1,值2,值3,...),(值1,值2,值3,...);
*/
-- 批量添加数据
INSERT INTO product VALUES (4,'洗衣机',800,15,'2030-05-05'),(5,'微波炉',300,45,'2030-06-06');