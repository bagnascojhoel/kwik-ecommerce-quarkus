package br.com.bagnascojhoel.kwik.ecommerce.common.infra_shared;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import java.time.Clock;
import java.time.ZoneId;

@Dependent
public class ClockConfig {

  @Produces
  public Clock provideClock() {
    return Clock.system(ZoneId.of("-3"));
  }
}
