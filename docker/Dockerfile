FROM maven:3.3.9-jdk-7
MAINTAINER zhc aassaadd@qq.com
ENV APP_PATH  pom-parent
COPY . /usr/src/app/${APP_PATH}
WORKDIR /usr/src/app/${APP_PATH}
#RUN mvn clean install 
VOLUME /root/.m2
ENTRYPOINT ["mvn","clean","install","package"]
#ENTRYPOINT ["mvn","clean","install","package","-P","standalone"]
