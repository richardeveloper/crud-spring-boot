package br.com.crud.configs;

import br.com.crud.entities.ClienteEntity;
import br.com.crud.entities.ProdutoEntity;
import br.com.crud.repositories.ClienteRepository;
import br.com.crud.repositories.ProdutoRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SetUpConfig implements CommandLineRunner {

  @Autowired
  private ClienteRepository clienteRepository;

  @Autowired
  private ProdutoRepository produtoRepository;

  @Override
  public void run(String... args) throws Exception {
    List<ClienteEntity> clientes = criarClientes();
    clienteRepository.saveAll(clientes);

    List<ProdutoEntity> produtos = criarProdutos();
    produtoRepository.saveAll(produtos);
  }

  private static List<ClienteEntity> criarClientes() {
    ClienteEntity u1 = new ClienteEntity(null, "Franz Kafka", "franz.kafka@gmail.com", "62965874151");
    ClienteEntity u2 = new ClienteEntity(null, "Richard Helm", "richard.helm@yahoo.com.br", "18981368286");
    ClienteEntity u3 = new ClienteEntity(null, "Ralph Jhonson", "ralph.jhonson@hotmail.com", "75983967494");
    ClienteEntity u4 = new ClienteEntity(null, "John Vlissides", "jhon.vlissides@gmail.com", "86983390870");
    ClienteEntity u5 = new ClienteEntity(null, "Erich Gamma", "erich.gamma@gmail.com", "69969850474");
    ClienteEntity u6 = new ClienteEntity(null, "Alan Turing", "alan.turing@yahoo.com.br", "67986042733");
    ClienteEntity u7 = new ClienteEntity(null, "Grace Hopper", "grace.hopper@outlook.com", "79988230510");
    ClienteEntity u8 = new ClienteEntity(null, "Dennis Ritchie", "dennis.ritchie@gmail.com", "47989259284");
    ClienteEntity u9 = new ClienteEntity(null, "Linus Torvalds", "linus.torvalds@outlook.com", "83983304263");
    ClienteEntity u10 = new ClienteEntity(null, "Margaret Hamilton", "margaret.hamilton@hotmail.com", "15979843505");

    return List.of(u1, u2, u3, u4, u5, u6, u7, u8, u9, u10);
  }

  private static List<ProdutoEntity> criarProdutos() {
    ProdutoEntity p1 = new ProdutoEntity(null, "iPhone 16 Pro", new BigDecimal("10499.00"));
    ProdutoEntity p2 = new ProdutoEntity(null, "iPhone 16", new BigDecimal("7799.00"));
    ProdutoEntity p3 = new ProdutoEntity(null, "iPhone 15 Plus", new BigDecimal("6499.00"));
    ProdutoEntity p4 = new ProdutoEntity(null, "iPhone 14", new BigDecimal("5799.00"));
    ProdutoEntity p5 = new ProdutoEntity(null, "iPhone SE", new BigDecimal("4299.00"));
    ProdutoEntity p6 = new ProdutoEntity(null, "Apple Watch Series 10", new BigDecimal("5499.00"));
    ProdutoEntity p7 = new ProdutoEntity(null, "Apple Watch Ultra 2", new BigDecimal("8499.00"));
    ProdutoEntity p8 = new ProdutoEntity(null, "Apple Watch SE", new BigDecimal("3299.00"));
    ProdutoEntity p9 = new ProdutoEntity(null, "AirPods 4", new BigDecimal("1499.00"));
    ProdutoEntity p10 = new ProdutoEntity(null, "AirPods Pro 2", new BigDecimal("2599.00"));
    ProdutoEntity p11 = new ProdutoEntity(null, "AirPods Pro Max", new BigDecimal("6590.00"));
    ProdutoEntity p12 = new ProdutoEntity(null, "MacBook Air", new BigDecimal("12499.00"));
    ProdutoEntity p13 = new ProdutoEntity(null, "MacBook Pro", new BigDecimal("19990.00"));
    ProdutoEntity p14 = new ProdutoEntity(null, "iMac", new BigDecimal("15499.00"));
    ProdutoEntity p15 = new ProdutoEntity(null, "Mac Mini", new BigDecimal("7499.00"));

    return List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15);
  }
}
