package br.com.bagnascojhoel.kwik.ecommerce.common.infra_driven;

import br.com.bagnascojhoel.kwik.ecommerce.common.domain.Event;
import br.com.bagnascojhoel.kwik.ecommerce.common.domain.EventPublisher;
import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class VertxEventPublisher implements EventPublisher {

  @Inject
  EventBus eventBus;

  @Override
  public void publish(Event event) {
    eventBus.publish(event.destination(), event.payload());
  }
}
