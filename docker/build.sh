#docker build -t xxxx:1.0 $WORKSPACE/xxxx
REGISTRY_URL=10.0.0.21:5000
APP_MAVEN_NAME=maven-snake3
APP_MAVEN_V=3.0.0
APP_V=3.0.0
APP_NAME=snake3
APP_PATH=snake3
docker build -t $APP_MAVEN_NAME:$APP_MAVEN_V $WORKSPACE/$APP_PATH/maven
if docker ps -a |grep -i $APP_MAVEN_NAME; then
    docker rm -f $APP_MAVEN_NAME
fi
docker  create --name $APP_MAVEN_NAME $APP_MAVEN_NAME:$APP_MAVEN_V
docker cp $APP_MAVEN_NAME:/usr/src/app/$APP_PATH/target/$APP_NAME.war $WORKSPACE/$APP_NAME
docker -t $REGISTRY_URL/$APP_NAME:$APP_V $WORKSPACE/$APP_NAME
docker push $REGISTRY_URL/$APP_NAME:$APP_V
if docker ps -a |grep -i $APP_NAME; then 
    docker rm -f $APP_NAME
fi
docker run -d -p 80:8080  --name hello $REGISTRY_URL/$APP_NAME:$APP_V
############
docker build -t pom-parent-maven:1.0.0 .
if docker ps -a |grep -i pom-parent-maven; then
    docker rm -f pom-parent-maven
fi
docker  run -it --name pom-parent-maven  -v /Users/zhaohaochen/Documents/workspace/snake/maven:/root/.m2 pom-parent-maven:1.0.0
docker cp pom-parent-maven:/usr/src/app/pom-parent/target/pom-parent.jar ./maven
docker -t $REGISTRY_URL/pom-parent:1.0.0 ./maven
docker push $REGISTRY_URL/pom-parent:1.0.0
if docker ps -a |grep -i pom-parent; then 
    docker rm -f pom-parent
fi
docker run -d -p 80:8080  --name pom-parent $REGISTRY_URL/pom-parent:1.0.0

########jenkins--不需要发布的
REGISTRY_URL=10.0.0.21:5000
APP_MAVEN_NAME=pom-parent-maven
APP_MAVEN_V=1.0.0
docker build -t $APP_MAVEN_NAME:$APP_MAVEN_V .
if docker ps -a |grep -i $APP_MAVEN_NAME; then
    docker rm -f $APP_MAVEN_NAME
fi
docker  run  --name $APP_MAVEN_NAME  -v /docker/maven:/root/.m2  $APP_MAVEN_NAME:$APP_MAVEN_V
#"-P","standalone"
########jenkins--需要发布的service
REGISTRY_URL=10.0.0.21:5000
DOCKER_URL=10.0.0.23:2376
APP_MAVEN_NAME=agent-service-maven
APP_NAME=snake-service
APP_PATH=agent-service
APP_V=1.0.0
docker cp $APP_MAVEN_NAME:/usr/src/app/$APP_PATH/target/$APP_NAME.jar $WORKSPACE/build
docker cp $APP_MAVEN_NAME:/usr/src/app/$APP_PATH/target/lib $WORKSPACE/build
docker  -H $DOCKER_URL build -t $REGISTRY_URL/$APP_NAME:$APP_V $WORKSPACE/build
docker -H $DOCKER_URL push $REGISTRY_URL/$APP_NAME:$APP_V
if docker -H $DOCKER_URL ps -a |grep -i $APP_NAME; then 
    docker -H $DOCKER_URL rm -f $APP_NAME
fi
docker -H $DOCKER_URL run -d -p 20883:20883  --name $APP_NAME  --add-host zookeeper:10.0.0.21 --add-host db:10.0.0.24 --add-host redis:10.0.0.21 $REGISTRY_URL/$APP_NAME:$APP_V

##docker images|grep none|awk '{print $3}'|xargs docker rmi