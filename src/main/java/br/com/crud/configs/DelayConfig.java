package br.com.crud.configs;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class DelayConfig implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
    try {
      Thread.sleep(500);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    chain.doFilter(request, response);
  }

}
