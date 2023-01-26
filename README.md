# Gerenciador de Procedimentos API

### 1.0 - Apresentação
O Gerenciador de Procedimento é um microservice (Web Service) no estilo RESTful API. Onde foi desenvolvido para ser consumido por
diferentes clients possíveis através de requisições REST.

Seu funcionamento se dá em ganhos de comissão em porcentagem encima de um determinado procedimento realizado, de acordo
com o tipo de procedimento.

### 2.0 - Todas as tecnologias utilizadas no projeto
:white_check_mark: Spring Boot Web para microservice.
:white_check_mark:Spring Boot MVC para Controller, View e Model.
:white_check_mark:Spring Boot JPA com PostgreSQL com criação automática.
:white_check_mark:Spring Boot Security para requisições HTTPS.
:white_check_mark:Autenticação com JWT.
:white_check_mark:Criptografia com Auth0 e BCrypt.
:white_check_mark:Secret Key hospedado com AWS.
:white_check_mark:Swagger para documentação da API.
:white_check_mark:Bilioteca Lombok para geração de getters e setters.
:white_check_mark:Biblioteca MapStruct para conversão de objetos.
:white_check_mark:Biblioteca Apache POI para geração de arquivo Excel.
:white_check_mark:Design Patter com a Strategy Enum.
:white_check_mark:Testes unitários com JUnit e Mockito seguindo o padrão TDD (Test Driven Development).
:white_check_mark:Padrão de arquitetura DDD (Domain Driven Design).
:white_check_mark:Arquitetura Maven com ambiente de produção e desenvolvimento (application.yml e application-dev.yml).
:white_check_mark:Resources Bundle para região em pt_BR e en_US.
:white_check_mark:Exceções de forma global com Global Handler.
:white_check_mark:FlyWay configurado no porjeto para geração de scripts de banco de dados.
:white_check_mark:Logs de negócio através da biblioteca SLF4J.
:white_check_mark:Geração de arquivos com Base64.
:white_check_mark:Collection de requisições do Insomnia completa dentro do projeto.