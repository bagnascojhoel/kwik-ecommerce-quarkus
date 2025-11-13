package br.com.bagnascojhoel.kwik.ecommerce.common.domain;

public class Event {

  private final String destination;

  private final Object payload;

  public Event(String destination, Object payload) {
    this.destination = destination;
    this.payload = payload;
  }

  public Object payload() {
    return payload;
  }

  public String destination() {
    return destination;
  }
}
