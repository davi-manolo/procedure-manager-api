# <img src="icon.png" alt="drawing" width="28"/> Gerenciador de Procedimentos API [![version](https://img.shields.io/badge/version-1.2.0-green.svg)](https://semver.org)

### 1.0 - Apresentação :ledger:
O Gerenciador de Procedimento é um microservice (Web Service) no estilo RESTful API. Onde foi desenvolvido para ser consumido por
diferentes clients possíveis através de requisições REST.

Seu funcionamento se dá em ganhos de comissão em porcentagem encima de um determinado procedimento realizado, de acordo
com o tipo de procedimento.

### 2.0 - Todas as tecnologias utilizadas no projeto :package:
:white_check_mark: Spring Boot Web para microservice.<br/>
:white_check_mark:Spring Boot MVC para Controller, View e Model.<br/>
:white_check_mark:Spring Boot JPA com PostgreSQL com criação automática.<br/>
:white_check_mark:Spring Boot Security para requisições HTTPS.<br/>
:white_check_mark:Spring Boot Validation para validar objetos de requisição.<br/>
:white_check_mark:Spring Scheduled para agendamentos de tarefas agendadas com Cron (limpeza de banco de dados automática).<br/>
:white_check_mark:Autenticação com JWT.<br/>
:white_check_mark:Criptografia com Auth0 e BCrypt.<br/>
:white_check_mark:Secret Key hospedado com AWS.<br/>
:white_check_mark:Swagger para documentação da API.<br/>
:white_check_mark:Bilioteca Lombok para geração de getters e setters.<br/>
:white_check_mark:Biblioteca MapStruct para conversão de objetos.<br/>
:white_check_mark:Biblioteca Apache POI para geração de arquivo Excel.<br/>
:white_check_mark:Geração de arquivo PDF com Thymeleaf com layout HTML e CSS.<br/>
:white_check_mark:Design Patter com a Strategy Enum.<br/>
:white_check_mark:Testes unitários com JUnit e Mockito seguindo o padrão TDD (Test Driven Development).<br/>
:white_check_mark:Padrão de arquitetura DDD (Domain Driven Design).<br/>
:white_check_mark:Arquitetura Maven com ambiente de produção e desenvolvimento (application.yml e application-dev.yml).<br/>
:white_check_mark:Resources Bundle para região em pt_BR e en_US.<br/>
:white_check_mark:Exceções de forma global com Global Handler.<br/>
:white_check_mark:FlyWay Migration configurado no projeto para versionamento de scripts de banco de dados.<br/>
:white_check_mark:Logs de negócio através da biblioteca SLF4J.<br/>
:white_check_mark:Geração de arquivos com Base64.<br/>
:white_check_mark:Collection de requisições do Insomnia completa dentro do projeto.<br/>

### 3.0 - Documentação do Swagger :clipboard:
Para acessar a documentação da API, é necessário subir a aplicação com o profile de desenvolvimento.<br/>
Basta definir nas variáveis de ambiente um argumento na JVM:
```
spring.profiles.active=dev
```
Após levantar o serviço, basta acessar a documentação em seu navegador:
```
http://localhost:8080/swagger-ui/#/
```

### 4.0 - Subir o projeto :hammer_and_wrench:
É importante que você tenha um banco de dados rodando em sua máquina local, recomendo que seja configurado o banco de 
dados PostgreSQL (inicialmente implementado neste projeto).<br/>
Após o banco de dados estar rodando crie o um banco chamado `procedure_manager_db`.

Com o projeto clonado em sua máquina, dentro da sua IDE crie as variáveis de ambiente conforme foi criado o seu banco de dados.
| Nome Variável          | Valor Variável                                        |
|------------------------|-------------------------------------------------------|
| DATASOURCE_URL         | *jdbc:postgresql://localhost:5432/procedure_manager_db* |
| DATASOURCE_USERNAME    | *nome_usuario*                                          |
| DATASOURCE_PASSWORD    | *senha_usuario*                                         |
| spring.profiles.active | *dev*                                                   |
>
> OBS: É importante que rode a aplicação em modo de desenvolvimento (profile de "dev").
> 

### 5.0 - Documentações do projeto :card_index_dividers:
Caso queira a _Collection_ de requisições pronta para ser utilizada, está presente na pasta _raiz_ dentro da pasta _**externalfiles**_.<br/>
O script de criação das tabelas do banco de dados está presente em _**src/main/resources/db/migration**_.

_**Desenvolvido com :hearts: por Davi Manolo.**_