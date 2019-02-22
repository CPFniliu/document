# SecureCRT

## install

## 使用

### 上传文件

方案一 : 直接输入 `rz` 弹出文件选择对话框, 添加相应的文件进去, 确认上传.
方案二 : 使用 sftp 进行上传.

   1. 打开SecureCRT导航栏的File下选择Connect SFTP Session(一般为Alt + P).
   2. 使用 cd 命令定位到远程系统, 使用 lcd 定位到本地系统路径.
   3. 使用 put 命令将本地文件上传至远程服务器

      ```shell
      # 使用 cd 命令定位到远程系统
      sftp> cd /opt/ity/ity/docs
      sftp> pwd
      /opt/ity/ity/docs

      # 使用 lcd 定位到本地系统路径
      sftp> lcd p:/kmerit/itongye/web_main/membercenter/target/
      sftp> lpwd
      p:\kmerit\itongye\web_main\membercenter\target

      # 使用 put 命令将本地文件上传至远程服务器
      sftp> put membercenter.war
      Uploading membercenter.war to /opt/ity/ity/docs/membercenter.war
      100% 28251KB   1130KB/s 00:00:25
      P:\kmerit\itongye\web_main\membercenter\target\membercenter.war: 28929312 bytes transferred in 25 seconds (1130 KB/s)
      ```