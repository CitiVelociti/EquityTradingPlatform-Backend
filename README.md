# EquityTradingPlatform-Backend

## Run spring boot on terminal
* Make sure you are in the backend directory
	* mvn spring-boot:run

## Run with docker commands
###Make sure to clear all docker images and containers
* To remove removable images and containers.
	* docker system prune
	* docker image prune --all
* Check containers and if there are any, remove them
	* docker ps
	* docker rm -f <first 4 letters of ps separated by a space>
* Check images and if there are any, remove them
	* docker images
	* docker rmi -f <first 4 letters of images separated by a space>
* mvn package -DskipTest

###To run -- database must run before the server
* docker build --file  Dockerfile-mysql -t cd/mysql .
* docker build --file Dockerfile-backend -t cd/backend .
* docker run -p 3306:3306 --name mysql -d cd/mysql
* docker run -p 8080:8080 --link mysql:mysql -d cd/backend
