# EquityTradingPlatform-Backend

# Run with docker commands
###Make sure to clear all docker images and containers
* docker system prune
* docker image prune --all
	* To remove removable images and containers.
* docker ps
* docker rm -f <first 4 letters of ps seperated by a space>
* docker images
* docker rmi -f <first 4 letters of images seperated by a space>
* mvn package -DskipTest

###To run
* docker build --file  Dockerfile-mysql -t cd/mysql .
* docker build --file Dockerfile-cd-serve -t cd/cd-server .
* docker run -p 3306:3306 --name mysql -d cd/mysql
* docker run -p 8081:8080 --link mysql:mysql -d cd/cd-server
