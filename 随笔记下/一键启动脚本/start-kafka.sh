cat /home/dcyw/export/onekey/slave | while read line
do
{
 echo $line
 ssh $line "source /home/dcyw/.bash_profile;nohup ${KAFKA_HOME}/bin/kafka-server-start.sh -daemon ${KAFKA_HOME}/config/server.properties >/dev/nul* 2>&1 & "
}&
wait
done

