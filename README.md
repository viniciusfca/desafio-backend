# Desafio Técnico Backend Java com Spring Boot

## Descrição do Desafio

Este desafio consiste em criar uma aplicação backend em Java que realiza as seguintes tarefas:

1. Extrair dados de clientes e endereços de um arquivo CSV.
2. Inserir os dados extraídos em duas tabelas: uma tabela de clientes e outra de endereços.
3. Implementar rotas para:
   - Consultar cliente por CPF.
   - Consultar todos os clientes.
   - Cadastrar novos clientes.
4. Adicionar uma coluna adicional na tabela de clientes para identificar se o cliente é idoso (idade maior que 60 anos).
5. Validar o CEP dos endereços utilizando uma API de CEP.
6. Incluir testes unitários.
7. Incluir algum tipo de autenticação nas rotas.
8. Fornecer um README explicando como executar a aplicação e os testes.
9. Incluir um arquivo Docker para subir a aplicação.
10. Utilizar um banco de dados em memória.

## Requisitos

### Tabelas

- **Cliente**
  - `id` (UUID)
  - `nome` (String)
  - `cpf` (String)
  - `endereco_id` (UUID)
  - `data_nascimento` (LocalDate)
  - `email` (String)
  - `is_idoso` (Boolean)

- **Endereço**
  - `id` (UUID)
  - `logradouro` (String)
  - `numero` (String)
  - `complemento` (String)
  - `bairro` (String)
  - `cidade` (String)
  - `estado` (String)
  - `cep` (String)

### Rotas

- `GET /clientes/{cpf}`: Consultar cliente por CPF.
- `GET /clientes`: Consultar todos os clientes.
- `POST /clientes`: Cadastrar novo cliente.

### Testes Unitários

- Testes para verificar a extração e inserção correta dos dados.
- Testes para as rotas de consulta e cadastro de clientes.
- Testes para verificar a flag `is_idoso`.
- Testes para validar a regra de verificação do CEP.

### README

O README deve conter instruções para:

1. Executar a aplicação.
2. Executar os testes.
3. Executar a aplicação utilizando Docker.

### Docker

Incluir um `Dockerfile` para subir a aplicação.

# Como executar a aplicação

## Pré-requisitos

- Java 21
- Maven
- Docker

## 1. **Executar a aplicação**

```bash
    cd clientes

    mvn clean package
    
    java -jar target/clientes-0.0.1-SNAPSHOT.jar
```

- obs: É preciso estar com a porta 8080 livre

## 2. **Executar os testes**

### Teste 1 : Login

- URL: http://localhost:8080/Login

- Método: `POST`

- Json enviado

```json
  {
    "login": "admin",
    "password": "admin"
  }
```

- Exemplo de retorno

```json
  {
      "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6IkFQSSBDbGllbnRlIiwiZXhwIjoxNzE5ODg2NTUwfQ.EGW25RBLa4lMoCpGeOib8BWAm9bUYpYctYEVjl-7_MY"
  }
```

### Teste 2 : Cadastro

- URL: http://localhost:8080/Clientes

- Método: `POST`

- Authorization: Bearer `[token_do_login]`

- Json enviado

```json
  {
    "nome": "Davi Cavalcanti",
    "cpf": "12345678900",
    "data_nascimento": "1987-01-01",
    "email": "joao.silva@gmail.com",
    "endereco_id": {
        "logradouro": "Rua Exemplo",
        "numero": "123",
        "complemento": "Apt 1",
        "bairro": "Centro",
        "cidade": "São Paulo",
        "estado": "SP",
        "cep": "05024000"
    }
  }
```

- Exemplo de retorno

```json
  {
    "id": "6f97727d-f5c4-4153-b395-843651b7b351",
    "cpf": "12345678900",
    "nome": "Davi Cavalcanti",
    "data_nascimento": "1987-01-01",
    "email": "davi.cavalcanti@gmail.com",
    "is_idoso": false,
    "endereco_id": {
        "id": "a8356b79-e738-4762-abcd-878e872c4194",
        "logradouro": "Rua Exemplo",
        "numero": "123",
        "complemento": "Apt 1",
        "bairro": "Centro",
        "cidade": "São Paulo",
        "estado": "SP",
        "cep": "05024000"
    }
  }
```

### Teste 3 : Consulta por CPF

- URL: http://localhost:8080/Clientes

- Método: `GET`

- Authorization: Bearer `[token_do_login]`

- Exemplo de retorno

```json
[
  {
    "id": "bbce6c7a-2f86-4652-a60d-db1650b38d81",
    "cpf": "304.862.795-29",
    "nome": "Maria Sophia da Luz",
    "data_nascimento": "1930-06-18",
    "email": "ecampos@nunes.com",
    "is_idoso": true,
    "endereco_id": {
        "id": "2487a9c2-fb74-440f-8b2e-5587b879f823",
        "logradouro": "Rodovia Agatha Sales",
        "numero": "96",
        "complemento": "",
        "bairro": "Jatobá",
        "cidade": "da Costa",
        "estado": "PI",
        "cep": "24101284"
    }
  },
  {
    "id": "89a591bb-2bba-46e9-ac2d-b43e078da788",
    "cpf": "315.624.987-46",
    "nome": "Sr. Gustavo Henrique Carvalho",
    "data_nascimento": "1947-06-14",
    "email": "fcastro@cunha.com",
    "is_idoso": true,
    "endereco_id": {
        "id": "ef467bef-ca63-4a59-ba5c-c50021375993",
        "logradouro": "Rua Correia",
        "numero": "3",
        "complemento": "",
        "bairro": "Miramar",
        "cidade": "Rocha",
        "estado": "MG",
        "cep": "41086373"
    }
  }
]
```

## 3. **Executar a aplicação utilizando Docker**

```bash
    cd clientes
    
    docker build -t api-clientes .

    docker run --name api-clientes-container -p 8080:8080 api-clientes
```

- obs: É preciso estar com a porta 8080 livre