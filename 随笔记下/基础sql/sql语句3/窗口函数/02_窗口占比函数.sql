--窗口占比函数
--p1:小于等于当前 salary 数量占比总体数量的比例
--p1:小于等于当前 salary 分区内数量占比总体数量的比例
select * ,
       cume_dist() over( ORDER BY salary)as p1,
       cume_dist() over( PARTITION by mgr_id ORDER BY salary)as p2
from employee


--排名减去1,(貌似减去自己)除以总人数减去1(貌似减去自己)
--(rank-1)/(rows-1)
select * ,
       rank() over( PARTITION by mgr_id ORDER BY salary )as p1,
       percent_rank() over( PARTITION by mgr_id ORDER BY salary)as p2
from employee