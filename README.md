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
10. Utilizar um banco de dados em memória ou qualquer outro banco relacional subindo via docker-compose.
11. Criar uma branch a partir desse projeto e ao finalizar submeter um PR para main onde será avaliado.
12. O arquivo para extrair o dados está na raiz desse projeto.

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

Incluir um `Dockerfile` para a aplicação e um `docker-compose.yml` (opcional) se necessário para subir a aplicação e o banco de dados em memória.

