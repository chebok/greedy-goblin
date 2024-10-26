# Базовый образ Ubuntu 22.04
FROM ubuntu:22.04

# Установка Nginx
RUN apt-get -y update && apt-get -y install nginx

# Открываем порт для доступа
EXPOSE 8080

# Запускаем Nginx
ENTRYPOINT ["/usr/sbin/nginx", "-g", "daemon off;"]

