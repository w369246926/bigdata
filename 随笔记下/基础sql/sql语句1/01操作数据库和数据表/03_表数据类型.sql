-- 使用mysql数据库
USE mysql;

/*
	表数据类型
	日期时间类型：
	DATA :年月日  YYYY-MM-DD
*/


/*
表数据类型
日期时间类型：
TIME :时分秒  HH:MM:SS
*/
-- 查询user表结构
DESC USER;

/*
表数据类型
日期时间类型：
YEAR :年  YYYY
*/

/*
表数据类型
日期时间类型：
DATATIME :年月日时分秒  YYYY-MM-DD HH:MM:SS
*/


/*
表数据类型
日期时间类型：
TIMESTAMP  :时间戳  15020320380
*/


/*
非精准浮点：float,double
精准浮点：decimal
Decimal类型为精准浮点数，在计算时不会丢失精度；
占用空间由定义的宽度决定，每4个字节可以存储9位数字，并且小数点要占用一个字节；
可用于存储比bigint更大的整型数据；
*/


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