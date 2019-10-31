# docker 安装

## CentOs 6.x 安装

从前当CentOS6.5 安装Docker报错No package docker available时

我们只需要做一步

yum install epel-release
操作就行

但是现在这种操作无效，运行

yum install docker-io
仍然报错No package docker available

我们只需要运行下面两行命令

cd /etc/yum.repos.d
sudo wget http://www.hop5.in/yum/el6/hop5.repo
然后运行 即可

yum install docker-io

