# 常用git命令


```shell
#windows 查看用户名
git config user.name
#查看邮箱
git config user.email
#查看密钥是否存在
cd ~/.ssh
#生成密钥
ssh-keygen -t rsa -b 4096 -C "fx369246926@gmail.com"
#查看密钥
cat ~/.ssh/id_rsa.pub
#pin github
ssh -T git@github.com


#
# 下载远程仓库代码
git clone https://git

# 添加create_dws.sh文件到git中进行管理
git add create_dws.sh

# 选中所有文件
git add *

# 提交文件到本地缓冲区
git commit -a -m "注释"

# 推送/上传到远程仓库
git push
```

