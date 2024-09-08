--前后函数:
lag()  p1:记录salary列当前行前一行的数值,500为默认值
lag()  p2:记录salary列当前行前两行的数值,没有设定默认值
select * ,
       lag(salary,1,500) over( PARTITION by mgr_id ORDER BY salary )as p1,
       lag(salary,2) over( PARTITION by mgr_id ORDER BY salary)as p2
from employee
lead()  p1:记录salary列当前行后一行的数值,500为默认值
lead()  p1:记录salary列当前行后两行的数值,没有设定默认值
select * ,
       lead(salary,1,500) over( PARTITION by mgr_id ORDER BY salary )as p1,
       lead(salary,2) over( PARTITION by mgr_id ORDER BY salary)as p2
from employee
