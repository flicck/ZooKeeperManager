# ZooKeeperManager
### 由于ZooKeeper查看、删除、修改节点比较麻烦，写了一个war包提供weiui方便的管理ZooKeeper节点  
#### 一个命令使用ZooKeeperManager:下载war包后，上传能连接ZooKeeper的集群节点上，输入  
>> nohup java -jar ZKManager.war xxx.xxx.xxx.xxx:2181
#### 即可，xxx.xxx.xxx.xxx为你连接ZooKeeper的URL。随后访问该机10240端口就可以访问webui
