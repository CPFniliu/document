# command

## commmon

### 解压tar文件

   ```cmd
   \$ tar -zxf yasuo.tar.gz
   ```

### 文件和文件夹

#### 移动

   linux实用命令之如何移动文件夹及文件下所有文件

   ```java
   \$ mv [options] 源文件或目录 目标文件或目录
   ```

1. 复制粘贴文件　　cp  [选项]  源文件或目录  目标文件或目录
2. 剪切粘贴文件　　mv [选项]  源文件或目录  目标文件或目录
3. 删除文件　　　　rm 文件　　　　　　慎用 rm -rf  
4. 查找文件 ```find / -name name.txt```

## System config

### 关闭防火墙

1. 重启后生效方法
   `chkconfig iptables on/off`
2. 即时生效, 但关机后失效
   `service iptables start/stop`
3. 开启防火墙后, 做如下设置，开启相关端口，
   修改/etc/sysconfig/iptables 文件，添加以下内容：

   ```linux
   -A RH-Firewall-1-INPUT -m state --state NEW -m tcp -p tcp --dport 80 -j ACCEPT
   -A RH-Firewall-1-INPUT -m state --state NEW -m tcp -p tcp --dport 22 -j ACCEPT
   ```

### 切换至root

1. \$  sudo
   这样输入当前管理员用户密码就可以得到超级用户的权限。但默认的情况下5分钟root权限就失效了。
2. sudo -i
   通过这种方法输入当前管理员用户的密码就可以进到root用户。
3. 如果想一直使用root权限，要通过su切换到root用户。
   那我们首先要重设置root用户的密码：
   $  sudo passwd root
   这样就可以设置root用户的密码了。
   之后就可以自由的切换到root用户了
   $  su
   输入root用户的密码即可。
   su "king" 或者 exit回到用户权限

##