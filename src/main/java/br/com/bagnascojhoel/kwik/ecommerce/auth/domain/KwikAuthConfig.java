package br.com.bagnascojhoel.kwik.ecommerce.auth.domain;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "kwik.auth")
public interface KwikAuthConfig {

  String adminPassword();

}
