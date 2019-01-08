# nginx install and config

## linux CentOs x64

### nginx install

   ```shell
   # 安装c++编译环境，如已安装可略过, 期间会有确认提示输入y回车
   [root@localhost src]# yum install gcc-c++

   # openssl安装
   [root@localhost src]# wget http://www.openssl.org/source/openssl-fips-2.0.10.tar.gz
   [root@localhost src]# tar zxvf openssl-fips-2.0.10.tar.gz
   [root@localhost src]# cd openssl-fips-2.0.10
   [root@localhost openssl-fips-2.0.10]# ./config && make && make install

   # pcre安装
   [root@localhost src]# wget ftp://ftp.csx.cam.ac.uk/pub/software/programming/pcre/pcre-8.40.tar.gz
   [root@localhost src]# tar zxvf pcre-8.40.tar.gz
   [root@localhost src]# cd pcre-8.40
   [root@localhost pcre-8.40]# ./configure && make && make install

   # zlib安装
   [root@localhost src]# wget http://zlib.net/zlib-1.2.11.tar.gz
   [root@localhost src]# tar zxvf zlib-1.2.11.tar.gz
   [root@localhost src]# cd zlib-1.2.11
   [root@localhost zlib-1.2.11]# ./configure && make && make install

   # 下载nginx
   [root@localhost src]# wget http://nginx.org/download/nginx-1.10.2.tar.gz
   [root@localhost src]# tar zxvf nginx-1.10.2.tar.gz
   [root@localhost src]# cd nginx-1.10.2
   [root@localhost nginx-1.10.2]# ./configure && make && make install

   ```

### nginx start

1. 先找一下nginx安装到什么位置上了, 进入nginx目录并启动, 可以用用whereis libpcre.so.1命令找到libpcre.so.1在哪里
2. 用ln -s /usr/local/lib/libpcre.so.1 /lib64命令做个软连接就可以了
3. 用sbin/nginx启动Nginx
4. 用ps -aux | grep nginx查看状态

   ```shell
   [root@localhost nginx]# whereis libpcre.so.1
   [root@localhost nginx]# ln -s /usr/local/lib/libpcre.so.1 /lib64
   [root@localhost nginx]# sbin/nginx
   [root@localhost nginx]# ps -aux | grep nginx 
   ```

5. 进入Linux系统的图形界面，打开浏览器输入localhost会看到下图，说明nginx启动成功

### nginx的基本操作

   ```shell
   启动
   [root@localhost ~]# /usr/local/nginx/sbin/nginx
   停止/重启
   [root@localhost ~]# /usr/local/nginx/sbin/nginx -s stop(quit、reload)
   命令帮助
   [root@localhost ~]# /usr/local/nginx/sbin/nginx -h
   验证配置文件
   [root@localhost ~]# /usr/local/nginx/sbin/nginx -t
   配置文件
   [root@localhost ~]# vim /usr/local/nginx/conf/nginx.conf
   ```

### 简单配置Nginx

打开nginx配置文件位于nginx目录下的conf文件夹下

6、Nginx负载均衡配置

 Nginx集反向代理和负载均衡于一身，在配置文件中修改配就可以实现

首先我们打开配置文件

[root@localhost nginx]# vim conf/nginx.conf
 每一个server就是一个虚拟主机，我们有一个当作web服务器来使用

listen 80;代表监听80端口
server_name xxx.com;代表外网访问的域名
location / {};代表一个过滤器，/匹配所有请求，我们还可以根据自己的情况定义不同的过滤，比如对静态文件js、css、image制定专属过滤
root html;代表站点根目录
index index.html;代表默认主页

这样配置完毕我们输入域名就可以访问到该站点了。

负载均衡功能往往在接收到某个请求后分配到后端的多台服务器上，那我们就需要upstream{}块来配合使用

复制代码
upstream xxx{};upstream模块是命名一个后端服务器组，组名必须为后端服务器站点域名，内部可以写多台服务器ip和port，还可以设置跳转规则及权重等等
ip_hash;代表使用ip地址方式分配跳转后端服务器，同一ip请求每次都会访问同一台后端服务器
server;代表后端服务器地址

server{};server模块依然是接收外部请求的部分
server_name;代表外网访问域名
location / {};同样代表过滤器，用于制定不同请求的不同操作
proxy_pass;代表后端服务器组名，此组名必须为后端服务器站点域名

server_name和upstream{}的组名可以不一致，server_name是外网访问接收请求的域名，upstream{}的组名是跳转后端服务器时站点访问的域名
复制代码
 

配置一下Windows的host将我们要访问的域名aaa.test.com指向Linux



因为硬件有限，我是将Windows中的IIS作为Nginx的后端服务器，所以配置一下IIS的站点域名



打开cmd再ping一下aaa.test.com确实指向Linux系统了，再打开浏览器输入aaa.test.com会显示bbb这个站点就代表负载成功了。



Nginx的负载功能就配置完成了，这只是简单设置了一下，生产环境中还有很多详细的调整，后续再逐渐增加，本人水平有限，如有不对之处还望指导谢谢。

  