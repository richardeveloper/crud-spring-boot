package br.com.crud.configs;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.entities.PedidoEntity;
import br.com.crud.entities.ProdutoEntity;
import br.com.crud.repositories.ClienteRepository;
import br.com.crud.repositories.PedidoRepository;
import br.com.crud.repositories.ProdutoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SetUpConfig implements CommandLineRunner {

  private final ClienteRepository clienteRepository;

  private final ProdutoRepository produtoRepository;

  private final PedidoRepository pedidoRepository;

  public SetUpConfig(ClienteRepository clienteRepository, ProdutoRepository produtoRepository,
    PedidoRepository pedidoRepository) {

    this.clienteRepository = clienteRepository;
    this.produtoRepository = produtoRepository;
    this.pedidoRepository = pedidoRepository;
  }

  @Override
  public void run(String... args) {

    List<ClienteEntity> clientes = criarClientes();
    List<ClienteEntity> clienteEntities = clienteRepository.saveAll(clientes);

    List<ProdutoEntity> produtos = criarProdutos();
    List<ProdutoEntity> produtoEntities = produtoRepository.saveAll(produtos);

    produtos = produtoEntities.stream()
      .filter((produto) -> produto.getNome().contains("Mario"))
      .toList();

    PedidoEntity pedido = new PedidoEntity();
    pedido.setCliente(clienteEntities.get(0));
    pedido.setProdutos(produtos);
    pedido.calcularValorTotal();
    pedido.setDataPedido(LocalDateTime.now());

    pedidoRepository.save(pedido);
  }

  private static List<ClienteEntity> criarClientes() {
    ClienteEntity c1 = new ClienteEntity(
      null,
      "Erich Gamma",
      "erich.gamma@gmail.com",
      "69969850474",
      LocalDateTime.now()
    );

    ClienteEntity c2 = new ClienteEntity(
      null,
      "John Vlissides",
      "john.vlissides@gmail.com",
      "62965874151",
      LocalDateTime.now()
    );

    ClienteEntity c3 = new ClienteEntity(
      null,
      "Ralph Jhonson",
      "ralph.jhonson@hotmail.com",
      "75983967494",
      LocalDateTime.now()
    );

    ClienteEntity c4 = new ClienteEntity(null,
      "Richard Helm",
      "richard.helm@yahoo.com.br",
      "18981368286",
      LocalDateTime.now()
    );

    return List.of(c1, c2, c3, c4);
  }

  private static List<ProdutoEntity> criarProdutos() {

    ProdutoEntity p1 = new ProdutoEntity(
      null,
      "Mario Kart 8 Deluxe",
      new BigDecimal("299.99"),
      "https://cdn.awsli.com.br/300x300/1919/1919257/produto/208634606/mariokart8deluxe-001-bgozyl.jpg"
    );

    ProdutoEntity p2 = new ProdutoEntity(
      null,
      "The Legend Of Zelda: Breath Of The Wild",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/81KGsbq8ekL.jpg"
    );

    ProdutoEntity p3 = new ProdutoEntity(
      null,
      "Donkey Kong Country Tropical Freeze",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/913II3nRc0L.jpg"
    );

    ProdutoEntity p4 = new ProdutoEntity(
      null,
      "Legend of Zelda: Links Awakening",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/91z5JYtUZAS.jpg"
    );

    ProdutoEntity p5 = new ProdutoEntity(
      null,
      "Super Mario Odyssey",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/91SF0Tzmv4L.jpg"
    );

    ProdutoEntity p6 = new ProdutoEntity(
      null,
      "Super Smash Bros Ultimate",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/81gTTc2IpCL._AC_UF1000,1000_QL80_.jpg"
    );

    ProdutoEntity p7 = new ProdutoEntity(
      null,
      "Mario Strikers Battle League",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/71Hlbr-w6JL._AC_UF1000,1000_QL80_.jpg"
    );

    ProdutoEntity p8 = new ProdutoEntity(
      null,
      "Pokemon Legend Arceus",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/71bhNf8QiOS.jpg"
    );

    ProdutoEntity p9 = new ProdutoEntity(
      null,
      "Pokemon Let's Go Pikachu",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/71w18E60zzL.jpg"
    );

    ProdutoEntity p10 = new ProdutoEntity(
      null,
      "Mario vs. Donkey Kong",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/81GvdH1DjUL.jpg"
    );

    ProdutoEntity p11 = new ProdutoEntity(
      null,
      "The Legend Of Zelda: Skyward Sword",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/81WmySS+aiL._AC_UF1000,1000_QL80_.jpg"
    );


    ProdutoEntity p12 = new ProdutoEntity(
      null,
      "Pokemon Sword",
      new BigDecimal("299.99"),
      "https://m.media-amazon.com/images/I/81F1eKUToxL.jpg"
    );


    return List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12);
  }
}
