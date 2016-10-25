FROM java:7-jre
MAINTAINER zhc aassaadd@qq.com
ENV APP_PATH  snake3
ENV APP_NAME  snake3
ENV PORT 20883
COPY ${APP_NAME}.jar /usr/src/app/${APP_NAME}.jar
COPY lib /usr/src/app/lib
# RUN echo "Asia/shanghai" > /etc/timezone;
WORKDIR /usr/src/app/
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar","snake3.jar"]
