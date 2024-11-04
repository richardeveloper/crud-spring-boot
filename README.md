# ADS1241 - Atividade 09 - Criação de API REST utilizando Spring Boot

<div align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

</div>

## Instruções

<p align="justify">
Implementar um backend utilizando Spring Boot para fornecer serviços de uma API RESTful. O modelo deve ser composto das três entidades e um banco de dados da sua escolha deve ser configurado no projeto para persistência:
</p>

> - Cliente (Long id, String nome, String email, String telefone)
> - Produto (Long id, String nome, Double preco)
> - Pedido (Long id, Cliente cliente, List\<Produto> produtos)

<p align="justify"> 
O backend deve implementar os Repositories JPA para cada classe do modelo. Todos os repositories devem implementar um método adicional de busca: ClienteRepository e ProdutoRepository devem implementar a busca por nome de cliente ou produto, respectivamente. PedidoRepository deve permitir pesquisar por id de Cliente ou id de Produto.
</p>

<p align="justify">
Deve ser implementado um Service para cada classe de modelo. As classes Service devem realizar as operações de cadastro: salvar, listar todos os itens, listar por id, e listar por nome (ou id de cliente ou produto no caso de PedidoService). A classe Service deve fazer algumas validações:
</p>

> - Nomes não devem ser strings vazias
> - Preço não pode ser negativo
> - Pedido deve conter id de cliente e produtos válidos

<p align="justify"> 
Os controllers devem implementar endpoints para cada serviço implementado. O Swagger deve ser incluído no projeto e usado para demonstrar o funcionamento da API.
</p>

> [!TIP]
> 
> ### Rodar aplicação
>
> **Requisitos:**
> - Java 17 ou superior
> - PostgreSQL
>
> **Variáveis para conexão com banco de dados:**
>
> ```
> export DB_URL="jdbc:postgresql://localhost:5432/banco_de_dados"
> export DB_USERNAME="postgres"
> export DB_PASSWORD="postgres"
> ```
>
> **Documentação da API:**
> > <a href="http://localhost:8080/swagger-ui" style="text-decoration: none;">Swagger UI</a>
