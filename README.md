# Recipes-Management-Search-System
Please do not use any VPN as it might not connect to hosted kafka.

This is a combination of three microservices.

Recipes-Authoring- Command-Service→1 Handling all CRUD APIs and sending data to Kaka Queue.

Recipes-Search-Consumer-Service→2 Consuming Kafka queue and indexing in Elastic search.

Recipes-Search-Service→3 Searching in Elastic Search.

For Fast searching, I used Elastic Search.
For async processing→ Kafka.

It is production ready→ as we can do the scaling of any of the MS based on the traffic received.

Run Instruction:
Elastic Search:
It requires running an elastic search on the local host.
Download Elasticsearch from here. https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.4.0-
windows-x86_64.zip
Extract the zip file
Disable the security, inside config/elasticsearch.yaml. please change all fields to false.
Now Run bin/elasticsearch.bat

Kafka: No need to install Kafka, it uses my already hosted Kafka(free version for personal training).


