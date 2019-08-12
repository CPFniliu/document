# rabbitMQ 安装

[TOC]

## linux(cento 6.6)

### info

linux | cento 6.6 |
erlang | 20.3 | otp_src_20.3.tar.gz
simplejson | 3.16.0 | simplejson-3.16.0.tar.gz
rabbitMQ | 3.7.14 | rabbitmq-server-3.7.14-1.el6.noarch.rpm

### 安装 erlang

下载 erlang 安装包 : otp_src_20.3.tar.gz

```shell
# 上传解压
tar -xzvf otp_src_20.3.tar.gz
# 创建erlang安装路径
mkdir /opt/erlang
# 进入加压的文件中
cd otp_src_20.3
# 配置安装路径编译代码：./configure --prefix=/opt/erlang
# 如果报No curses library functions found错，安装curses
./configure --prefix=/opt/erlang
# 编译安装
make && make install
进入安装位置, 验证安装是否成功, 判断是否安装成功
cd /opt/erlang/bin
./erl

# 添加配置文件配置Erlang环境变量,
vi /etc/profile

# 增加
export PATH=$PATH:/opt/erlang/bin

# 使得文件生效
source  /etc/profile
```

### 安装 simplejson

### 安装rabbitMQ

---

## CentOS6.8安装RabbitMQ

1. 安装依赖包：

   `yum install xmlto gcc gcc-c++ kernel-devel m4 ncurses-devel openssl-devel unixODBC-devel wxBase wxGTK wxGTK-gl perl -y`

2. 安装erlang

   `yum install -y erlang-20.3-1.el6.x86_64.rpm`

3. 安装相关证书

   ```shell
      wget --no-cache http://www.convirture.com/repos/definitions/rhel/6.x/convirt.repo -O /etc/yum.repos.d/convirt.repo
      yum install -y socat
      rpm --import http://www.rabbitmq.com/rabbitmq-signing-key-public.asc
   ```

   > 安装socat时老报错 
   > 通过yum安装软件老是报错，打算重新安装yum

   1. 把 /etc/yum.repos.d/ 下面所有的源给删除掉了
   2. 下载镜像

      ```shell
      # CentOS 5
      wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-5.repo
      # CentOS 6
      wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-6.repo
      # CentOS 7
      wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
      ```

      注意如果没有安装wget，可以从下面的网址中先手动下载，然后上传到linux上安装：http://www.rpmfind.net/linux/rpm2html/search.php?query=wget

   3. 执行命令，重新生成cache

      ```shell
      yum clean all
      yum makecache   执行这个需要一段时间
      ```

4. 下载并安装 rabbitMQ

   `yum install -y rabbitmq-server-3.6.8-1.el6.noarch.rpm`

### rabbitMQ 配置

### 添加远程访问用户，并分配权限

   ```shell
   # 1. 添加用户
   rabbitmqctl add_user ity ity
   # 2. 配置用户组
   rabbitmqctl set_user_tags ity administrator
   # 3. 分配权限
   rabbitmqctl set_permissions -p / ity '.*' '.*' '.*'
   # 4. 删除用户
   rabbitmqctl delete_user guest
   ```

### 相关命令

启动
`service rabbitmq-server start`

停止
`service rabbitmq-server stop`

重启
`service rabbitmq-server restart`

#### 开启web管理可视化插件

`rabbitmq-plugins enable rabbitmq_management`

访问  http://ip:15672/#/users
      http://IP:15672/  

## 清空一个队列的数据

`rabbitmqctl -p ${vhost-name} purge_queue ${queue-name}`

## mysql 安装

   centos下彻底删除MYSQL 和重新安装MYSQL

### 删除Mysql

   ```shell
      yum remove  mysql mysql-server mysql-libs mysql-server;
      find / -name mysql 将找到的相关东西delete掉(rm -rf /var/lib/mysql)；
      rpm -qa|grep mysql(查询出来的东东yum remove掉)
      rm /etc/my.cnf
   ```

   查看是否还有mysql软件：
   `rpm -qa|grep mysql`

   如果存在的话，继续删除即可。

   rpm方式安装的mysql

      a）查看系统中是否以rpm包安装的mysql：

      [root@localhost opt]# rpm -qa | grep -i mysql
      MySQL-server-5.6.17-1.el6.i686
      MySQL-client-5.6.17-1.el6.i686

      b)卸载mysql

      [root@localhost local]# rpm -e MySQL-server-5.6.17-1.el6.i686
      [root@localhost local]# rpm -e MySQL-client-5.6.17-1.el6.i686

      c)删除mysql服务

      [root@localhost local]# chkconfig --list | grep -i mysql
      [root@localhost local]# chkconfig --del mysql

      d)删除分散mysql文件夹

      [root@localhost local]# whereis mysql 或者 find / -name mysql

      mysql: /usr/lib/mysql /usr/share/mysql

      清空相关mysql的所有目录以及文件
      rm -rf /usr/lib/mysql
      rm -rf /usr/share/mysql

      rm -rf /usr/my.cnf

      通过以上几步，mysql应该已经完全卸载干净了.

### 安装mysql(可以参考简书里面的收藏)

   > [CentOS 6.6 MySQL 8.0详细安装步骤](https://www.jianshu.com/p/672f316c15eb)

### 注意：

   1. 如果你没有设置认证方式，默认的密码加密方式是：caching_sha2_password，而现在很多客户端工具还不支持这种加密认证方式，连接测试的时候就会报错：client does not support  authentication protocol requested by server; consider upgrading MySQL client，这里的错误信息就是不支持身份认证方式，没关系，去my.ini里面在[mysqld]下面加上这句话即可：

      `default_authentication_plugin=mysql_native_password`

   2. 由于数据库的升级，今天在导入从MySQL 5.6导出来的SQL文件时报错:
      `ERROR 1067 (42000): Invalid default value for 'CREATE_TIME'`
      因为MySQL 5.6以后timestamp设定默认值规则改变，不能为0000 00-00 00:00:00

      解决方法:
      查看sql_mode:

      ```shell
         mysql> show session variables like '%sql_mode%';
         ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION 
         1 row in set (0.01 sec)
         修改sql_mode,去掉NO_ZERO_IN_DATE,NO_ZERO_DATE:
         mysql> set sql_mode="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION";
         Query OK, 0 rows affected, 1 warning (0.00 sec)
      ```

      但这是临时的, 如果想永久, 则去更改配置文件, 添加`sql_mode="ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION"`;

#### linux 下 设置 MySQL8 表名大小写不敏感方法，解决设置后无法启动 MySQL 服务的问题

      在安装完成之后，初始化数据库之前，修改 my.cnf, 打开mysql配置文件
      `vi /etc/my.cnf`
      在尾部追加一行
      `lower_case_table_names=1`
      并保存，然后再初始化数据库。
      重启mysql，
      systemctl restart mysqld.service
      如果这个操作是初始化数据库之后，也就是安装后运行过服务，那就可能会出错。
      错误类似于Job for mysqld.service failed because the control process exited with error...
      然后就无法启动 mysql 服务了,除非打开 vim /etc/my.cnf把追加的lower_case_table_names=1删除掉。

      我在网上找了好久，暂时没找到比较好的解决方法，而 MySQL5 就没有这个问题。

      最后我的解决方法如下：

      如果你不在意数据的话直接删除数据

         ```shell
            # 停止MySQL
            systemctl stop mysqld.service
            # 删除 MySQL的数据 /var/lib/mysql
            rm -rf /var/lib/mysql
         ```

      再按照上面的方法进行一遍操作即可。

