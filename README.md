# 欣宸的自定义OpenFaaS模板
### java11extend

在官方java11模板的基础上做了以下两处改动：

1. 增加二方库：jackson-databind:2.11.0和commons-lang3:3.10
2. 修改Handler.java的功能：请求body当做json解析，得到name字段，将name值、当前时间、服务器IP地址(Pod的)、进程ID，这些内容一起做成JSON对象，作为响应body的内容返回

