# 深澜校园网自动登录保持器Java版

### 如何使用

编辑工程目录或jar包所在目录下的`config.properties`里的`username`、`password`和`domain`的值，运行工程即可。

| 参数         | 说明      | 示例           |
|------------|---------|--------------|
| `username` | 登录用户名   | 20241234567  |
| `password` | 登录密码    | 123456       |
| `domain`   | 校园网登录网址 | 210.43.112.9 |

### 相关说明

检测到已登录将再5分钟后重新检测，连续登陆失败超过5次将在10分钟后重新检测登录。

### 参考

实现原理: [深澜认证协议分析,python模拟登录-CSDN](https://blog.csdn.net/qq_41797946/article/details/89417722)

哈希加密: [Java实现哈希加密(HmacSHA1、HmacMD5、HmacSHA256、HmacSHA512)-CSDN](https://blog.csdn.net/skh2015java/article/details/121705023)