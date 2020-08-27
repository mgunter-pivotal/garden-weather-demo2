mkdir rmq 
mkdir comms-broker #poller or listener
mkdir weather-data
mkdir gps-data
mkdir weather-app #rest endpoint


touch rmq/Dockerfile
touch rmq/garden.yml

touch comms-broker/pom.xml
touch comms-broker/garden.yml

touch weather-data/Dockerfile
touch weather-data/garden.yml

touch gps-data/Dockerfile
touch gps-data/garden.yml

touch weather-app/pom.xml
touch weather-app/garden.yml