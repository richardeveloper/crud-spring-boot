# ADS1241 - Criação de Backend RESTful utilizando Spring Boot

<div align="center">

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

</div>

## Instruções

<p align="justify">
Implementar um backend utilizando Spring Boot para fornecer serviços de uma API RESTful. O modelo deve ser composto das três entidades e um banco de dados da sua escolha deve ser configurado no projeto para persistência:
</p>
> - Cliente (id, nome, email, contato)
> - Produto (id, nome, preco)
> - Pedido (id, cliente, List<Long> produtos)

<p align="justify"> 
O backend deve implementar os Repositories JPA para cada classe do modelo. Todos os repositories devem implementar um método adicional de busca: ClienteRepository e ProdutoRepository devem implementar a busca por nome de cliente ou produto, respectivamente. PedidoRepository deve permitir pesquisar por id de Cliente ou id de Produto.
</p>

<p align="justify">
Deve ser implementado um Service para cada classe de modelo. As classes Service devem realizar as operações de cadastro: salvar, listar todos os itens, listar por id, e listar por nome (ou id de cliente ou produto no caso de PedidoService). A classe Service deve fazer algumas validações:
</p>

> - Nomes não devem ser strings vazias
> - Preço não pode ser negativo
> - Pedido deve conter id de cliente e produtos válidos
> - Os controllers devem implementar endpoints para cada serviço implementado.
> - O Swagger deve ser incluído no projeto e usado para demonstrar o funcionamento da API.