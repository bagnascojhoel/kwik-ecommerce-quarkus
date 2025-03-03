package br.com.bagnascojhoel.kwik.ecommerce.common.domain;

public interface EventPublisher {

  void publish(Event event);
}
