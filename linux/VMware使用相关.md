# VMware

## linux 相关

### 安装vmware-tools

1. 如果是最小安装的话首先要安装依赖包
   `[root@localhost ~]# yum -y install perl gcc gcc-c++ make cmake kernel kernel-headers kernel-devel net-tools`
2. VMware workstation 导航栏 --> 虚拟机 --> 安装 VMware tools.
3. 将CD-ROM挂载到指定目录

   ```shell
   # 若存在 /mnt/cdrom, 则该步可省略
   [root@localhost ~]# mkdir -p /mnt/cdrom
   # 挂载目录
   [root@localhost ~]# mount -t auto /dev/cdrom /mnt/cdrom
   mount: /dev/sr0 is write-protected, mounting read-only
   ```

   > 通常情况下都是将设备目录 /dev/crrom 挂载到 /mnt/cdrom 目录, 当然也可能会在 /media/cdrom 目录

4. 拷贝安装包到用户家目录

   ```shell
   # 拷贝文件
   [root@localhost ~]# cp /mnt/cdrom/VMwareTools-10.0.5-3228253.tar.gz ~
   # 解除挂载
   [root@localhost ~]# umount /dev/cdrom
   # 解压安装包
   [root@localhost ~]# tar -zxvf VMwareTools-10.0.5-3228253.tar.gz
   ```

5. 安装VMware Tools

   ```shell
   # 进入文件夹
   [root@localhost ~]# cd vmware-tools-distrib/
   # 运行 `vmware-install.pl` 文件
   [root@localhost vmware-tools-distrib]# perl ./vmware-install.pl
   ```

### 建立window10 家庭版和linux虚拟机共享文件夹

1. 在 window10 下新建一个共享文件夹
2. 使用VMware workstation设置将共享文件夹添加至虚拟机(虚拟机最好关机)
   VMware workstation --> 右键当前虚拟机设置 --> 选项 --> 共享文件夹
3. 打开虚拟机, 使用 `ls /mnt/hgfs` 命令看到共享的文件夹.
