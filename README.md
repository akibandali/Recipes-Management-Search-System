# Recipes-Management-Search-System
Please do not use any VPN as it might not connect to hosted kafka.

This is a combination of three microservices.

Recipes-Authoring- Command-Service→ Handling all CRUD APIs and sending data to Kafka Queue.

Recipes-Search-Consumer-Service→ Consuming Kafka queue and indexing in Elastic search.

Recipes-Search-Service→ Searching in Elastic Search.

For Fast searching-> I used Elastic Search.
For async processing→ Kafka.
For database-> For simplicity and testing purpose I used in memory db(H2). If I am given some time I would like to replace it with couchbase or MongoDB.

It is production ready→ as we can do the scaling of the MS based on the traffic received.
Multiple instances of search service can be deployed.

Run Instruction:
Elastic Search:
It requires running an elastic search on the local host or hosted.
Download Elasticsearch from here. https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.4.0-
windows-x86_64.zip
Extract the zip file
Disable the security, inside config/elasticsearch.yaml. please change all fields to false.
Now Run bin/elasticsearch.bat

Please follow the provided document for further instructions.


In IT tests, I did not mock H2 and Kafka.
Defiantly there is room for writing IT tests for search service considering all search use cases.

Kafka: No need to install Kafka, it uses my already hosted Kafka(free version for personal training).
All the MSs can be built as docker image.
