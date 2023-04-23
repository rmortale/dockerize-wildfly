
call mvn clean package
copy target\demo.war docker\
docker compose -f docker\docker-compose27.yaml up 
