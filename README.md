# Projeto para estudos usando como case uma estante virtual - my-bookcase

##Tecnologias e versões:

- Java 11
- Maven 3.8.1
- Spring Boot 2.4.4
- JaCoCo 0.8.3
- Mysql 5.7
- [Testcontainers](https://testcontainers.org) Mysql 1.15.3


## Build da aplicação com testes
Para construir a aplicação e rodar toda a suite de testes usando o __maven__.

`mvn clean install`

## Banco de dados com docker-compose
Para subir o banco de dados:

`docker-compose up -d`

## Rodando a aplicação via IDE
Para executar a aplicação pela sua IDE:

`Clicar em "run" na classe MyBookcaseApplication.java`

## Rodando a aplicação via Terminal
Para executar o comando no terminal:

`java -jar targert/my-bookcase-{current_version}.jar`

Confira o log e com tudo correto a aplicação estará disponível na porta `8080`