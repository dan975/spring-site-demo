# Spring practice web app
## Intro
Practice application for familiarization with spring MVC and microservice architecture, 
thymeleaf and bootstrap front-end concepts, etc. 

App is not representation of production project. QA, pentesting not considered, 
development flows are not optimized: hibernate SQL queries not double checked, 
microservice communication not optimized, etc. CSS, JS, HTML not separated, etc.

## Demo video
A demo video of the application can be found at:
https://www.youtube.com/watch?v=ZXPKctgTUAc

## Docker
### Docker compose
Start:

    docker compose up

Shutdown:

    docker compose down -v

### Docker swarm

Start:

One time: initialize docker swarm mode and set current node to a manager

    docker swarm init

Then:

    docker stack deploy -c docker-compose.yml store
or

    docker stack deploy -c docker-compose.yml -c docker-compose.override.yml store

Shutdown:

    docker stack rm store

After all service containers are down remove all volumes created with `docker volume ls` and then `docker volume rm` 
for each volume or execute the following, which will remove all unused volumes

    docker volume prune -f


## Credits
Project's front-end functionality scope is based on PrestaShop's 
http://automationpractice.com/ website