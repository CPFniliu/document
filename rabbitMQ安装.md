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

## 配置

### 添加远程访问用户，并分配权限

```shell
# 1. 添加用户
rabbitmqctl add_user ity ity
# 2. 配置用户组
rabbitmqctl set_user_tags ity administrator
# 3. 分配权限
rabbitmqctl set_permissions -p / ity '.*' '.*' '.*'
```

### 相关命令

启动
`service rabbitmq-server start`

停止
`service rabbitmq-server stop`

重启
`service rabbitmq-server restart`

#### 开启可视化插件

`rabbitmq-plugins enable rabbitmq_management`

访问 http://ip:15672/#/users  