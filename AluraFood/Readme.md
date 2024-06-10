


# Rabbit
- filas: 
  - client: 5672
  - admin dashboard: 15672 

comando para subir: 

- RabbitMQ

docker run -d --hostname my-rabbit --name some-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management


- MySQL

docker run --name my-mysql -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_USER=root -e MYSQL_PASSWORD=1234 -p 3306:3306 -d mysql:latest
