# <img src="icon.png" alt="drawing" width="40"/> Gerenciador de Procedimentos API

### 1.0 - Apresentação
O Gerenciador de Procedimento é um microservice (Web Service) no estilo RESTful API. Onde foi desenvolvido para ser consumido por
diferentes clients possíveis através de requisições REST.

Seu funcionamento se dá em ganhos de comissão em porcentagem encima de um determinado procedimento realizado, de acordo
com o tipo de procedimento.

### 2.0 - Todas as tecnologias utilizadas no projeto
:white_check_mark: Spring Boot Web para microservice.<br/>
:white_check_mark:Spring Boot MVC para Controller, View e Model.<br/>
:white_check_mark:Spring Boot JPA com PostgreSQL com criação automática.<br/>
:white_check_mark:Spring Boot Security para requisições HTTPS.<br/>
:white_check_mark:Autenticação com JWT.<br/>
:white_check_mark:Criptografia com Auth0 e BCrypt.<br/>
:white_check_mark:Secret Key hospedado com AWS.<br/>
:white_check_mark:Swagger para documentação da API.<br/>
:white_check_mark:Bilioteca Lombok para geração de getters e setters.<br/>
:white_check_mark:Biblioteca MapStruct para conversão de objetos.<br/>
:white_check_mark:Biblioteca Apache POI para geração de arquivo Excel.<br/>
:white_check_mark:Design Patter com a Strategy Enum.<br/>
:white_check_mark:Testes unitários com JUnit e Mockito seguindo o padrão TDD (Test Driven Development).<br/>
:white_check_mark:Padrão de arquitetura DDD (Domain Driven Design).<br/>
:white_check_mark:Arquitetura Maven com ambiente de produção e desenvolvimento (application.yml e application-dev.yml).<br/>
:white_check_mark:Resources Bundle para região em pt_BR e en_US.<br/>
:white_check_mark:Exceções de forma global com Global Handler.<br/>
:white_check_mark:FlyWay configurado no porjeto para geração de scripts de banco de dados.<br/>
:white_check_mark:Logs de negócio através da biblioteca SLF4J.<br/>
:white_check_mark:Geração de arquivos com Base64.<br/>
:white_check_mark:Collection de requisições do Insomnia completa dentro do projeto.<br/>