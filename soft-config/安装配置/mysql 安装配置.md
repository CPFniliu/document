# mysql 安装配置

## install

> https://blog.csdn.net/chrisjingu/article/details/90291445

## config

my.cnf

```conf
[mysqld]
sql_mode = "STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION"

lower_case_table_names=1

# 默认使用“mysql_native_password”插件认证
default_authentication_plugin=mysql_native_password

default-time-zone ='+8:00' #服务器时区
# 服务端使用的字符集默认为UTF8
character-set-server=utf8mb4
default-storage-engine = InnoDB #默认存储

datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock

log-error=/var/log/mysqld.log
pid-file=/var/run/mysqld/mysqld.pid

# 允许最大连接数
max_connections=200
# 允许连接失败的次数。这是为了防止有人从该主机试图攻击数据库系统
max_connect_errors=10
```
