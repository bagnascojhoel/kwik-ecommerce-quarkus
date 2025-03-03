package br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api;

import java.time.LocalDateTime;

public interface JsonMetadata {

  String getCreatedBy();

  LocalDateTime getCreatedAt();

  String getModifiedBy();

  LocalDateTime getModifiedAt();
}
