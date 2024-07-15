# Desafio Técnico Backend Java com Spring Boot + Jpa + H2
# Como Executar a aplicação.
## 1-Executar JAVA.
    *Run JAVA > DesafioApplication.java
    Padrão MVC - Model View Controler - Cliente<>Controlador<>Serviço<>Repositorio<>Banco de Dados

## 2-Authenticação.
    *Efetuar registro no endpoint "/auth/register"
    -> POST
    informando(Body): login:String,password:String,role:"ADMIN" or "USER"
    retorno: Created

    *Efetuar login no endpoint "/auth/login"
    ->POST
    informando(Body): login:String,password:String
    retorno: Token

## 3-Endereço - Ações.
    *Efetuar cadastro no endpoint "/enderecos"
    ->POST ("role"Requisito:ADMIN)
    informando(Authorization): Token(item 2)
    informando(Body): logradouro:String,numero:String,complemento:String,bairro:String,cidade:String,estado:String,cep:String
    Retorno: Created + (validação cep)

    *Efetuar atualização por id no endpoint "/enderecos/id"
    ->PUT ("role"Requisito:USER)
    informando(Authorization): Token(item 2)
    informando(Body NOVO): logradouro:String,numero:String,complemento:String,bairro:String,cidade:String,estado:String,cep:String
    Retorno: Created + (validação cep)

    *Efetuar consulta no endpoint "/enderecos"
    ->GET ("role"Requisito:USER)
    informando(Authorization): Token(item 2)
    Retorno: Array objs

    *Efetuar consulta por id no endpoint "/enderecos/id"
    ->GET ("role"Requisito:USER)
    informando(Authorization): Token(item 2)
    Retorno: obj

    *Efetuar delete por id no endpoint "/enderecos/id"
    ->DELETE ("role"Requisito:USER)
    informando(Authorization): Token(item 2)

## 4-Cliente - Ações.
    *Efetuar cadastro no endpoint "/clientes"
    ->POST ("role"Requisito:ADMIN)
    informando(Authorization): Token(item 2)
    informando(Body): nome:String,cpf:String,dataNascimento:String,email:String
    Retorno: Created

    *Efetuar atualização por id no endpoint "/clientes/id"
    ->PUT ("role"Requisito:USER)
    informando(Authorization): Token(item 2)
    informando(Body NOVO): nome:String,cpf:String,dataNascimento:String,email:String
    Retorno: Created + (validação cep)

    *Efetuar consulta no endpoint "/clientes"
    ->GET ("role"Requisito:USER)
    informando(Authorization): Token(item 2)
    Retorno: Array objs

    *Efetuar consulta por cpf no endpoint "/clientes/cpf"
    ->GET ("role"Requisito:USER)
    informando(Authorization): Token(item 2)
    Retorno: obj

    *Efetuar delete por id no endpoint "/clientes/id"
    ->DELETE ("role"Requisito:USER)
    informando(Authorization): Token(item 2)

## 5-CSV - Ações.
    *Efetuar importação no endpoint "/csv/import"
    ->POST ("role"Requisito:ADMIN)
    informando(Authorization): Token(item 2)
    Retorno: Created + (validação cep)

## 6-H2-Console - Ações.
    *Efetuar acesso ao banco de dados em memoria no endpoint "/h2-console"
    url=jdbc:h2:mem:testdb
    username=sa
    password=

1. Executar os testes.
### Testes Unitários
- Testes para verificar a extração e inserção correta dos dados.
- Testes para as rotas de consulta e cadastro de clientes.
- Testes para verificar a flag `is_idoso`.
- Testes para validar a regra de verificação do CEP.

2. Executar a aplicação utilizando Docker.