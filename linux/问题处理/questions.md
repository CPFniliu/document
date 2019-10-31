# questions

## centos7可以ping通内网，但ping不通外网

首先检查添加DNS是否正常，如不存在则添加dns：

```shell
[root@cgls]# vi /etc/resolv.conf
nameserver 114.114.114.114
nameserver 8.8.8.8
```

如果还是不行，可以尝试添加路由：（根据自己的网段设置，我的是192.168.11网段的！）

`[root@cgls]# route add default gw 192.168.11.1`

一般情况下，这时候就可以ping通了：

[root@cgls]# ping www.baidu.com

