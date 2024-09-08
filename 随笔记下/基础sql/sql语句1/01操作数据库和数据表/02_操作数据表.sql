-- 使用mysql数据库
USE mysql;

/*
	查询所有数据表
	标准语法：
		SHOW TABLES;
*/
-- 查询库中所有的表
SHOW TABLES;

/*
	查询表结构
	标准语法：
		DESC 表名;
*/
-- 查询user表结构
DESC USER;

-- 查询user表创建语句
SHOW CREATE TABLE USER ;

/*
	查询数据表的字符集
	标准语法：
		SHOW TABLE STATUS FROM 数据库名称 LIKE '表名';
*/
-- 查看mysql数据库中user表字符集
SHOW TABLE STATUS FROM mysql LIKE 'user';






/*
	创建数据表
	标准语法：
		CREATE TABLE 表名(
			列名 数据类型 约束,
			列名 数据类型 约束,
			...
			列名 数据类型 约束
		);
*/
-- 创建一个product商品表(商品编号、商品名称、商品价格、商品库存、上架时间)
CREATE TABLE product(
	id INT,
	NAME VARCHAR(20),
	price DOUBLE,
	stock INT,
	insert_time DATE
);

-- 查看product表详细结构
DESC product;



/*
	修改表名
	标准语法：
		ALTER TABLE 旧表名 RENAME TO 新表名;
*/
-- 修改product表名为product2
ALTER TABLE product RENAME TO product2;
RENAME TABLE  product2 TO product3



/*
	修改表的字符集
	标准语法：
		ALTER TABLE 表名 CHARACTER SET 字符集名称;
*/
-- 查看db3数据库中product2数据表字符集
SHOW TABLE STATUS FROM db3 LIKE 'product2';

-- 修改product2数据表字符集为gbk
ALTER TABLE product2 CHARACTER SET gbk;


/*
	给表添加列
	标准语法：
		ALTER TABLE 表名 ADD 列名 数据类型;
*/
-- 给product2表添加一列color
ALTER TABLE product2 ADD color VARCHAR(10);


/*
	修改表中列的数据类型
	标准语法：
		ALTER TABLE 表名 MODIFY 列名 数据类型;
*/
-- 将color数据类型修改为int
ALTER TABLE product2 MODIFY color INT;

-- 查看product2表详细信息
DESC product2;

/*
	修改表中列的名称和数据类型
	标准语法：
		ALTER TABLE 表名 CHANGE 旧列名 新列名 数据类型;
*/
-- 将color修改为address
ALTER TABLE product2 CHANGE color address VARCHAR(200);

-- 查看product2表详细信息


/*
	删除表中的列
	标准语法：
		ALTER TABLE 表名 DROP 列名;
*/
-- 删除address列
ALTER TABLE product2 DROP address;





/*
	删除表
	标准语法：
		DROP TABLE 表名;
*/
-- 删除product2表
DROP TABLE product2;

/*
	删除表，判断、如果存在则删除
	标准语法：
		DROP TABLE IF EXISTS 表名;
*/
-- 删除product2表，如果存在则删除
DROP TABLE IF EXISTS product2;

一般情况下，不同的表甚至不同的列都可能需要不同的字符集，可以在创建表的时候指定

CREATE TABLE table_name
(
	col_name_1	type,
	col_name_2	type CHARACTER SET character_name COLLATE collate_name
)DEFAULT CHARACTER SET character_name
 COLLATE collate_name;
