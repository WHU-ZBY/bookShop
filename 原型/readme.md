### 说明

后台管理网站和服务器基本完成
不过还有不少可以改进的，页面外观啥的没考虑，基本功能都具有了
服务器涉及到前端的一些接口可能需要配合你前端更改

#### bookShop项目地址：

https://github.com/WHU-ZBY/bookShop
（邀请你为合作者了）
已经部署到服务器上

#### 后台管理页面网址：

http://106.15.182.82:8081/manageForBookShop/index.html#/login

#### 给你的账号：

UID：100001
Password：123
Authority：1（管理员）



### 接口

前端可能需要的接口，如果有需要调整的就跟我说（什么新增接口、返回对象需要什么样的等等）

在压缩包里面，解压后打开index.html文件可以看到

![1583032699434](C:\Users\zby\AppData\Roaming\Typora\typora-user-images\1583032699434.png)

##### controller里面都是接口

pojo里面是对象（有些对象没有用，是使用mybatis时候自动生成的）

一些使用示例：

![1583032765623](C:\Users\zby\AppData\Roaming\Typora\typora-user-images\1583032765623.png)

接口的地址格式

http://106.15.182.82:8080/接口名

### 技术说明

#### JWT

后端使用了jwt验证技术，就是token进行一个安全验证

在前端登陆的时候（调用login或者register）后，服务器会返回一个token，前端储存token，并在每次和请求的时候需要将token放在请求中。服务器会对请求中的token和对应user的token进行比对，如果两个token一致就通过，不一致就需要重新登陆（token具有时效性，暂定为一天）

所以前端需要将获取的token储存起来，在调用接口的时候自动加上

