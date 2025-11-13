package br.com.bagnascojhoel.kwik.ecommerce.tenant.infra_driving.rest.api;

import br.com.bagnascojhoel.kwik.ecommerce.common.infra_driving.rest.api.JsonApiFeedback;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JsonApiFeedbackImpl implements JsonApiFeedback {

  private String message;

  private int code;

  private Throwable cause;
}
