# uyaki-pxc

## usage
参考 [mybatis-plus](https://mp.baomidou.com/guide/dynamic-datasource.html)

### 核心依赖
```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
    <version>3.1.1</version>
</dependency>
<!-- MyBatis-Plus -->
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>${mybatis-plus.version}</version>
</dependency>
<!-- MYSQL -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```
### application.yml配置

```yaml
host: xxx.xxx.xxx.xxx

spring:
  datasource:
    dynamic:
      primary: master #设置默认的数据源或者数据源组,默认值即为master
      strict: false #设置严格模式,默认false不启动. 启动后在未匹配到指定数据源时候回抛出异常,不启动会使用默认数据源.
      datasource:
        master:
          url: jdbc:mysql://${host}:13306/pxc_db?useUnicode=true&characterEncoding=UTF-8&createDatabaseIfNotExist=true&serverTimezone=GMT%2b8
          username: root
          password: 123456
          driver-class-name: com.mysql.jdbc.Driver
        slave:
          url: jdbc:mysql://${host}:13307/pxc_db?useUnicode=true&characterEncoding=UTF-8&createDatabaseIfNotExist=true&serverTimezone=GMT%2b8
          username: root
          password: 123456
          driver-class-name: com.mysql.jdbc.Driver
      hikari:
        # 最大连接数，小于等于0会被重置为默认值10；大于零小于1会被重置为min-idle的值
        max-pool-size: 30
        # 连接超时时间:毫秒，小于250毫秒，否则被重置为默认值30秒
        connection-timeout: 30000
        # 最小空闲连接，默认值10，小于0或大于maximum-pool-size，都会重置为maximum-pool-size
        min-idle: 5
        # 空闲连接超时时间，默认值600000（10分钟），大于等于max-lifetime且max-lifetime>0，会被重置为0；不等于0且小于10秒，会被重置为10秒。
        # 只有空闲连接数大于最大连接数且空闲时间超过该值，才会被释放
        idle-timeout: 30000
        # 连接最大存活时间.不等于0且小于30秒，会被重置为默认值30分钟.设置应该比mysql设置的超时时间短
        max-lifetime: 30000
        #连接测试查询
        connection-test-query: SELECT 1
        
logging:
  level:
    root: DEBUG
```

### 开启读写分离自动切换配置

> 前提：yml配置文件中，节点名必须为master、slave，如果需要换名字或者增加节点，可以自己实现MasterSlaveAutoRoutingPlugin

```java
@Configuration
public class MasterSlaveAutoRoutingConfig {
    /**
     * Master slave auto routing plugin master slave auto routing plugin.
     *
     * @return the master slave auto routing plugin
     */
    @Bean
    public MasterSlaveAutoRoutingPlugin masterSlaveAutoRoutingPlugin() {
        return new MasterSlaveAutoRoutingPlugin();
    }
}
```

> 配置完成后，select语句会走slave节点，其余语句走master（全自动）；
> 如需手动指定执行DB，参考下面@DS注解的使用；

### 指定数据源（按需） 

1. 使用 @DS 切换数据源
2. @DS 可以注解在方法上和类上，同时存在方法注解优先于类上注解
3. 强烈建议只注解在 service 实现类上

|注解|结果|
|:----:|:----:|
|没有 @DS|默认数据源|
|@DS("dsName")|dsName 可以为组名也可以为具体某个库的名称|

```java
@Service
@DS("slave")
public class UserServiceImpl implements UserService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<Map<String, Object>> selectAll() {
    return  jdbcTemplate.queryForList("select * from user");
  }
  
  @Override
  @DS("slave")
  public List<Map<String, Object>> selectByCondition() {
    return  jdbcTemplate.queryForList("select * from user where age >10");
  }
}
```

## 创建pxc

### docker

```bash
$ mkdir pxc
$ cd pxc
$ mkdir data master follower
# 创建数据卷
$ cd data
$ mkdir v1 v2 v3
# 设置权限
$ chmod 777 v1 v2 v3
```

```bash
$ cd ../../pxc/master
$ vim docker-compose.yml
```

```yaml
version: '3'
services:
  pxc01:
    restart: always
    image: percona/percona-xtradb-cluster:5.7
    container_name: pxc01
    privileged: true
    ports:
      - 13306:3306
    environment:
      - MYSQL_ROOT_PASSWORD=123456
      - CLUSTER_NAME=pxc
    volumes:
      - ../data/v1:/var/lib/mysql

networks:
  default:
    external:
      name: mysql_network
```

```bash
$ cd /usr/local/docker/pxc/follower
$ vim docker-compose.yml
```

```yaml
version: '3'
services:
  pxc02:
    restart: always
    image: percona/percona-xtradb-cluster:5.7
    container_name: pxc02
    privileged: true
    ports:
      - 13307:3306
    environment:
      - MYSQL_ROOT_PASSWORD=123456
      - CLUSTER_NAME=pxc
      - CLUSTER_JOIN=pxc01
    volumes:
      - ../data/v2:/var/lib/mysql

  pxc03:
    restart: always
    image: percona/percona-xtradb-cluster:5.7
    container_name: pxc03
    privileged: true
    ports:
      - 13308:3306
    environment:
      - MYSQL_ROOT_PASSWORD=123456
      - CLUSTER_NAME=pxc
      - CLUSTER_JOIN=pxc01
    volumes:
      - ../data/v3:/var/lib/mysql

networks:
  default:
    external:
      name: mysql_network
```

> 一定要等到 master 节点起来，在进行启动 follower 节点不然会出现各个节点之间不能相互注册


###  查看 PXC 集群是否相互注册成功
```mysql
show status like 'wsrep_cluster%'
```
