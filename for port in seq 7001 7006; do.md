for port in `seq 7001 7006`; do \
docker run -id --restart always --name redis-${port} \
-p ${port}:${port} -p 1${port}:1${port} \
-v /Users/jianghehe/Downloads/redis_cluster/${port}/conf:/usr/local/etc/redis \
-v /Users/jianghehe/Downloads/redis_cluster/${port}/data:/data \
redis redis-server /usr/local/etc/redis/redis.conf \
--requirepass "sinkiang"; \
done



1. for port in `seq 7010 7015`; do \
   base=7008
   myips=$[port-base]
   docker run -d -ti -p ${port}:${port} -p 1${port}:1${port} \
   --privileged=true -v /Users/jianghehe/Downloads/redis-cluster/${port}/conf/redis.conf:/usr/local/etc/redis/redis.conf \
   --privileged=true -v /Users/jianghehe/Downloads/redis_cluster/${port}/data:/data \
   --restart always --name redis-${port} --net redis-net --ip 172.18.0.${myips} \
   --sysctl net.core.somaxconn=1024 redis redis-server /usr/local/etc/redis/redis.conf; \
   done

redis-cli -a sinkiang --cluster create 172.17.0.2:6379 172.17.0.3:6379 172.17.0.4:6379 172.17.0.5:6379 172.17.0.6:6379 172.17.0.7:6379 --cluster-replicas 1

redis-cli -c -a sinkiang -h 172.17.0.2 -p 6379



redis-cli -c -h 172.18.0.7 -p 7015 -a 123456789



for port in `seq 7001 7006`; do \

docker stop redis-${port};

1.   docker rm redis-${port};
2. done

7001 172.17.0.2

7002 172.17.0.3

7003 172.17.0.4

7004 172.17.0.5

7005 172.17.0.6

7006 172.17.0.7







1. redis-cli --cluster create 172.18.0.2:7010 172.18.0.3:7011 172.18.0.4:7012 172.18.0.5:7013 172.18.0.6:7014 172.18.0.7:7015 --cluster-replicas 1 -a sinkiang





```
version: '2.2'
services:
  cerebro:
    image: lmenezes/cerebro:0.8.3
    container_name: cerebro
    ports:
      - "9000:9000"
    command:
      - -Dhosts.0.host=http://elasticsearch:9200
    networks:
      - es72net
  kibana:
    image: docker.elastic.co/kibana/kibana:7.2.0
    container_name: kibana72
    environment:
      #- I18N_LOCALE=zh-CN
      - XPACK_GRAPH_ENABLED=true
      - TIMELION_ENABLED=true
      - XPACK_MONITORING_COLLECTION_ENABLED="true"
    ports:
      - "5601:5601"
    networks:
      - es72net
  elasticsearch:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.2.0
    container_name: es72_01
    environment:
      - cluster.name=geektime
      - node.name=es72_01
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - discovery.seed_hosts=es72_01,es72_02
      - network.publish_host=elasticsearch
      - cluster.initial_master_nodes=es72_01,es72_02
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - es72data1:/usr/share/elasticsearch/data
    ports:
      - 9200:9200
    networks:
      - es72net
  elasticsearch2:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.2.0
    container_name: es72_02
    environment:
      - cluster.name=geektime
      - node.name=es72_02
      - bootstrap.memory_lock=true
      - "ES_JAVA_OPTS=-Xms512m -Xmx512m"
      - discovery.seed_hosts=es72_01,es72_02
      - network.publish_host=elasticsearch
      - cluster.initial_master_nodes=es72_01,es72_02
    ulimits:
      memlock:
        soft: -1
        hard: -1
    volumes:
      - es72data2:/usr/share/elasticsearch/data
    ports:
      - 9201:9200
    networks:
      - es72net
volumes:
  es72data1:
    driver: local
  es72data2:
    driver: local

networks:
  es72net:
    driver: bridge
```