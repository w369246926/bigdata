# 一、基于string类型操作
# 设置获取Key
SET hello world
GET hello

# MSET（Multi）支持批量设置key、MGET支持批量获取key
MSET hello1 world1 hello2 world2
MGET hello1 hello2

# 设置一个key，并指定过期时间
SETEX hello3 5 world

# 使用INCR来进行累加操作
SET PV 1
INCR PV

# 二、基于hash类型操作
# 记住：大key、小key
# 设置Hash的指定字段
HSET userinfo userid 1
HSET userinfo username zhangsan

# 将Hash的字段获取
HGET userinfo userid

# 获取Hash中所有的key
HKEYS userinfo

# 获取Hash中所有的key和value
HGETALL userinfo

# 删除userinfo下的userid
HDEL userinfo userid

# 三、操作list类型
# push往列表的头部插入数据
node1.itcast.cn:6379> LPUSH list 1 2 3 4
(integer) 4
# range表示取指定范围的元素（0--1表示获取数据的元素）
node1.itcast.cn:6379> LRANGE list 0 -1

# 四、操作SET类型
# 4.1 添加元素
SADD set_test 1
SADD set_test 1 2 3 4

# SMEMBERS key 
# 返回集合中的所有成员
# SCARD key 
# 获取集合的成员数

# 4.2 获取所有的元素
SMEMBERS set_test

# 4.3 获取元素的个数
SCARD set_test

# 要使用SET结构来保存网站的UV
SADD uv:2020-01-01 001 002 003
SCARD uv:2020-01-01

# 五、针对key的操作
# 5.1 删除一个key，对应的数据结构
DEL list

# 5.2 判断set_test这个key是否存在
EXISTS set_test

# 返回1表示存在，返回0表示不存在
# node1.itcast.cn:6379> EXISTS set_test
# (integer) 1
# node1.itcast.cn:6379> EXISTS set_test1
# (integer) 0

# 六、针对ZSET（有序SET）的操作
# 6.1 向ZSet中添加页面的PV值
ZADD pv 100 page1.html 200 page2.html 300 page3.html

# 6.2 获取一共有几个页面
# ZCARD key 
ZCARD pv

# 6.3 要给page1.html页面增加pv值
# ZINCRBY key increment member 
ZINCRBY pv 10 page1.html

# 6.4 创建两个保存PV的ZSET：
ZADD pv_zset1 10 page1.html 20 page2.html
ZADD pv_zset2 5 page1.html 10 page2.html
ZINTERSTORE pv_zset_result 2 pv_zset1 pv_zset2

# 6.7 获取ZSET中的所有成员
# ZRANGE key start stop [WITHSCORES] 
ZRANGE pv_zset_result 0 -1 WITHSCORES

# 6.8 求page1.html在页面PV中的排名(最小)
# 默认是按照升序统计 0, 1, 2,3 ...，从小到大排列
# ZRANK key member 
ZRANK pv_zset_result page1.html

# 6.9 求page1.html在页面PV中的排名(最大)
# ZREVRANK key member 
# 注意：这个操作效率很高，并不是重新排序，只是把ZSET反转（revert）即可
ZREVRANK pv_zset_result page1.html

# 注意：
# 1. 排名是ZRANK是基于从小到大排列的，ZREVRANK是基于从大到小排列
# 2. 排名是从0开始，0代表第一名


# 七、Bitmap操作
# 6.10 uid=0，5，11，15，19
# 使用Bitmap来保存用户是否访问过网站
SETBIT unique:users:2020-01-01 0 1
SETBIT unique:users:2020-01-01 5 1
SETBIT unique:users:2020-01-01 11 1
SETBIT unique:users:2020-01-01 15 1
SETBIT unique:users:2020-01-01 19 1

#SETBIT unique:users:2020-01-01 1000000 1

# 6.11 获取指定用户是否访问过网站
# GETBIT key offset
GETBIT unique:users:2020-01-01 0

# 6.12 统计2020-01-01这一天一共有多少用户访问了网站
# BITCOUNT key [start end]
BITCOUNT unique:users:2020-01-01 0 -1

# 6.13 计算2020-01-01与2020-01-02的所有访问网站的用户
# BITOP operation destkey key [key, …]
SETBIT unique:users:2020-01-02 0 1
SETBIT unique:users:2020-01-02 6 1
SETBIT unique:users:2020-01-02 12 1

# 取or操作
BITOP or unique:users:or:2020-01-01_02 unique:users:2020-01-01 unique:users:2020-01-02
# 取数量
BITCOUNT unique:users:or:2020-01-01_02 0 -1

# 八、HyperLogLog操作
# pfadd key userid, userid...
# pfcount key
# 需求：
# 求某个网站的UV值
pfadd taobao:uv:2020-01-01 1
pfadd taobao:uv:2020-01-01 2
pfadd taobao:uv:2020-01-01 1

# 获取UV的值（去重后的）
pfcount taobao:uv:2020-01-01