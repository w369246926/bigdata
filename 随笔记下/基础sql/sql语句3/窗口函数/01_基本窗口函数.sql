--窗口函数
--排名函数
partition by  分区   如去除则按全表排序
order by      分组   asc/desc  排序字段

select * ,
       row_number() over (partition by mgr_id order by salary desc) as r1, --顺序排名  1 2 3
       rank() over (partition by mgr_id order by salary desc) as r1,       --并列排名  1 1 3
       dense_rank() over (partition by mgr_id order by salary desc) as r1  --并列顺序排名 1 1 2
from employee;

--聚合函数sum() avg() max() min() count()
--将选中列进行   分区    分组   聚合sum  排序
select * ,
       sum(salary) over(PARTITION by mgr_id ORDER BY eid asc
       )as psum
from employee

select * ,
       sum(salary) over(PARTITION by mgr_id ORDER BY eid desc
       )as psum
from employee

select * ,
       sum(salary) over( ORDER BY eid
       )as psum
from employee

select * ,
       sum(salary) over(PARTITION by mgr_id
       )as psum
from employee

--滑动窗口unbounded(首行)  current(当前行)
select * ,
       sum(salary) over(
       PARTITION by mgr_id
       ORDER BY eid
       rows between unbounded preceding and current row
       )as psum
from employee

--滑动窗口从前两行到当前行
select * ,
       sum(salary) over(
       PARTITION by mgr_id
       ORDER BY eid
       rows between 2 preceding and current row
       )as psum
from employee
--滑动窗口从前两行到当前行的下一行
select * ,
       sum(salary) over(
       PARTITION by mgr_id
       ORDER BY eid
       rows between 2 preceding and 1 following
       )as psum
from employee

--滑动窗口从首行到最后一行
select * ,
       sum(salary) over(
       PARTITION by mgr_id
       ORDER BY eid
       rows between current row and unbounded following
       )as psum
from employee