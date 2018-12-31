# command

## commmon
# command

### tar

tar在Linux上是常用的打包、压缩、加压缩工具，他的参数很多，折里仅仅列举常用的压缩与解压缩参数

参数：
-c ：create 建立压缩档案的参数；
-x ： 解压缩压缩档案的参数；
-z ： 是否需要用gzip压缩；
-v： 压缩的过程中显示档案；
-f： 置顶文档名，在f后面立即接文件名，不能再加参数

> 举例： 一，将整个/home/www/images 目录下的文件全部打包为 /home/www/images.tar
> `tar -cvf /home/www/images.tar /home/www/images` ← 仅打包，不压缩
> `tar -zcvf /home/www/images.tar.gz /home/www/images` ← 打包后，以gzip压缩
> 在参数f后面的压缩文件名是自己取的，习惯上用tar来做，如果加z参数，则以tar.gz 或tgz来代表gzip压缩过的tar file文件

1 将tgz文件解压到指定目录
`tar zxvf test.tgz -C` 指定目录
比如将/source/kernel.tgz解压到 /source/linux-2.6.29 目录

`tar zxvf /source/kernel.tgz -C /source/ linux-2.6.29`

2 将指定目录压缩到指定文件
比如将linux-2.6.29 目录压缩到 kernel.tgz

`tar czvf kernel.tgz linux-2.6.29`

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

进入超级用户模式。也就是输入"su -",系统会让你输入超级用户密码，输入密码后就进入了超级用户模式。（当然，你也可以直接用root用）
(注意有- ，这和su是不同的，在用命令”su”的时候只是切换到root，但没有把root的环境变量传过去，还是当前用户的环境变量，用”su -”命令将环境变量也一起带过去，就象和root登录一样)

## CentOS 7 修改IP地址和主机名

### shutdown & halt & poweroff & reboot

   type |command | means
   -|-|-
   shutdown | shutdown -p now   | 关闭机器
   ^        | shutdown -H now   | 停止机器
   ^        | shutdown -r 09:35 | 在 09:35am 重启机器
   ^        | shutdown -c       | 要取消即将进行的关机
   halt     | halt              | 停止机器
   ^        | halt -p           | 关闭机器
   ^        | halt --reboot     | 重启机器
   ^        | halt              | 停止机器
   poweroff | poweroff          | 关闭机器
   ^        | poweroff --halt   | 停止机器
   ^        | poweroff --reboot | 重启机器
   reboot   | reboot            | 重启机器
   ^        | reboot --halt     | 停止机器
   ^        | reboot -p         | 关闭机器

1. shutdown 会给系统计划一个时间关机。它可以被用于停止、关机、重启机器。shutdown 会给系统计划一个时间关机。它可以被用于停止、关机、重启机器。
2. halt 命令通知硬件来停止所有的 CPU 功能，但是仍然保持通电。你可以用它使系统处于低层维护状态。注意在有些情况会它会完全关闭系统。
3. poweroff 会发送一个 ACPI 信号来通知系统关机。
4. reboot 命令reboot 通知系统重启。