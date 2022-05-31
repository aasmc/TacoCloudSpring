# Taco Cloud
Educational project based on the book Spring In Action 6-th edition by
Craig Walls.

The aim of the project is to get started with Spring Framework. 

### Branch spring_data_cassandra
To run the app in that branch you need to configure cassandra DB.

- First you need to install Docker.
- In your Terminal create Docker network for cassandra: docker network create cassandra-net
- Run Docker: docker run --name my-cassandra --network cassandra-net -p 9042:9042 -d cassandra:latest
- Open CQL cassandra shell: docker run -it --network cassandra-net --rm cassandra cqlsh my-cassandra
- Create cassandra keyspace from the cqlsh: 
```text
create keyspace tacocloud
  ... with replication={'class':'SimpleStrategy', 'replication_factor':1}
  ... and durable_writes=true;
```

### Branch spring_data_mongodb
To run the app in that branch you need to have a MongoDB server running
locally and listening on port 27017. This can be easily achieved with
Docker by running the following command:
- $ docker run -p 27017:27017 -d mongo:latest

But for convenience we can use an embedded Mongo database instead.
To do that we need to use the following dependency:
```xml
<dependency>
  <groupId>de.flapdoodle.embed</groupId>
  <artifactId>de.flapdoodle.embed.mongo</artifactId>
  <!-- <scope>test</scope> -->
</dependency>
```

