cat /export/onekey/slave | while read line
do
{
 echo $line
 ssh $line "source /home/dcyw/.bash_profile;jps |grep Kafka |cut -d' ' -f1 |xargs kill -s 9"
}&
wait
done

