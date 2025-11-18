package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber.mocks;

import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.Jwt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.JwtService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.SignedJwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import org.mockito.Mockito;

@ApplicationScoped
@Alternative
public class JwtServiceMock extends Mocked<JwtService> implements JwtService {

  @Override
  public boolean isValid(Jwt token) {
    return this.mockedInstance.isValid(token);
  }

  @Override
  public Jwt generate() {
    return this.mockedInstance.generate();
  }

  @Override
  public Jwt unwrap(SignedJwt signedToken) {
    return this.mockedInstance.unwrap(signedToken);
  }

  @Override
  protected void initMock() {
    this.mockedInstance = Mockito.mock(JwtService.class);
  }

  @Override
  public JwtService mock() {
    return this.mockedInstance;
  }
}
