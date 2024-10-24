

FROM  openjdk:21-jdk


# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

#RUN cd..
RUN mvn clean install
#RUN cd /home/java/MyLuckAppProject
#COPY  token_file.json token_file.json
COPY  target/shared_database_api-0.0.1-SNAPSHOT.jar shared_database_api-0.0.1-SNAPSHOT.jar
#EXPOSE 8089:8089
#RUN java -jar demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -Xms1024M -Xmx4096M  -jar shared_database_api-0.0.1-SNAPSHOT.jar

#  docker build -t vocabulary_builder_image .
#  docker run -i -d -e TZ=Asia/Kolkata -p 8020:8020 --name vocabulary_builder_container --network mylucknetwork vocabulary_builder_image
#  docker stop vocabulary_builder_container-container
#  docker rm vocabulary_builder_container-container
#docker run -it -e MYSQL_ROOT_PASSWORD=my-secret-pw mysql:5.7 --max-allowed-packet=67108864
