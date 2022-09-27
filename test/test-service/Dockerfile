FROM openjdk:11
WORKDIR /
# 构建产物路径
# ADD app-shangbao/target/app-shangbao.jar app.jar
ADD target/test-service.jar app.jar

# 应用端口
EXPOSE 8888
# JMX
EXPOSE 8888
##执行命令
CMD ["java","-jar","/app.jar"]
