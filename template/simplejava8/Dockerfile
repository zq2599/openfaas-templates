# 用maven镜像作为基础镜像，用于编译构建java项目
FROM maven:3.6.3-openjdk-8 as builder

WORKDIR /home/app

# 将整个项目都复制到/home/app目录下
COPY . /home/app/

# 进入pom.xml所在目录执行构建命令，指定m2/settings.xml文件作为配置文件，
# 请在settings.xml中配置好私服，否则构建速度极慢
RUN cd function && mvn clean package -U -DskipTests --settings ./m2/settings.xml 

# of-watchdog里面有二进制文件watchdog，制作镜像时要用到
FROM openfaas/of-watchdog:0.7.6 as watchdog

# openjdk镜像是容器的运行环境
FROM openjdk:8-jre-slim as ship

# 为了安全起见，在生产环境运行容器时不要用指root帐号和群组
RUN addgroup --system app \
    && adduser --system --ingroup app app

# 从of-watchdog镜像中复制二进制文件fwatchdog，这是容器的启动进程
COPY --from=watchdog /fwatchdog /usr/bin/fwatchdog

# 赋予可执行权限
RUN chmod +x /usr/bin/fwatchdog

WORKDIR /home/app

# 前面用maven编译构建完毕后，这里将构建结果复制到镜像中
COPY --from=builder /home/app/function/target/java8maven-1.0-SNAPSHOT-jar-with-dependencies.jar ./java8maven-1.0-SNAPSHOT-jar-with-dependencies.jar
# 指定容器的运行帐号
user app

# 指定容器的工作目录
WORKDIR /home/app/

# fwatchdog收到web请求后的转发地址，java进程监听的就是这个端口
ENV upstream_url="http://127.0.0.1:8082"

# 运行模式是http
ENV mode="http"

# 拉起业务进程的命令，这里就是启动java进程
ENV fprocess="java -jar java8maven-1.0-SNAPSHOT-jar-with-dependencies.jar"

# 容器对外暴露的端口，也就是fwatchdog进程监听的端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=5s CMD [ -e /tmp/.lock ] || exit 1

# 容器启动命令，这里是执行二进制文件fwatchdog
CMD ["fwatchdog"]