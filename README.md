# Gerenciador de Procedimentos API

### :white_check_mark: 1.0 - Apresentação
O Gerenciador de Procedimento é um microservice (Web Service) no estilo RESTful API. Onde foi desenvolvido para ser consumido por
diferentes clients possíveis através de requisições REST.

Seu funcionamento se dá em ganhos de comissão em porcentagem encima de um determinado procedimento realizado, de acordo
com o tipo de procedimento.

### 2.0 - Todas as tecnologias utilizadas no projeto
- Spring Boot Web para microservice.
- Spring Boot MVC para Controller, View e Model.
- Spring Boot JPA com PostgreSQL com criação automática.
- Spring Boot Security para requisições HTTPS.
  - Autenticação com JWT.
  - Criptografia com Auth0 e BCrypt.
  - Secret Key hospedado com AWS.
- Swagger para documentação da API.
- Bilioteca Lombok para geração de getters e setters.
- Biblioteca MapStruct para conversão de objetos.
- Biblioteca Apache POI para geração de arquivo Excel.
- Design Patter com a Strategy Enum.
- Testes unitários com JUnit e Mockito seguindo o padrão TDD (Test Driven Development).
- Padrão de arquitetura DDD (Domain Driven Design).
- Arquitetura Maven com ambiente de produção e desenvolvimento (application.yml e application-dev.yml).
- Resources Bundle para região em pt_BR e en_US.
- Exceções de forma global com Global Handler.
- FlyWay configurado no porjeto para geração de scripts de banco de dados.
- Logs de negócio através da biblioteca SLF4J.
- Geração de arquivos com Base64.
- Collection de requisições do Insomnia completa dentro do projeto.