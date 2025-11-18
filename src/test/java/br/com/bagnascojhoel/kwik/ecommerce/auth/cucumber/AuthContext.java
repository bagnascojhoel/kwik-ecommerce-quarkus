package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import br.com.bagnascojhoel.kwik.ecommerce.auth.application.JwtFlowApplicationService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.AuthenticationException;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.GenerateJwtCommand;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.Jwt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.PasswordEncryptionService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.RawSecret;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import br.com.bagnascojhoel.kwik.ecommerce.auth.infra_driven.library.PasswordEncryptionServiceAdapter;
import br.com.bagnascojhoel.kwik.ecommerce.auth.object_mother.JwtMother;
import br.com.bagnascojhoel.kwik.ecommerce.auth.object_mother.UserMother;
import io.quarkiverse.cucumber.ScenarioScope;
import jakarta.inject.Inject;
import java.util.Optional;
import lombok.Getter;
import org.mockito.Mockito;

@ScenarioScope
public class AuthContext {

  private final PasswordEncryptionService passwordEncryptionService =
      new PasswordEncryptionServiceAdapter();

  @Inject
  private JwtFlowApplicationService jwtFlowApplicationService;

  @Inject
  private UserRepository userRepository;

  private Setup setup = new Setup();

  @Getter
  private Result result = new Result();

  public void reset() {
    this.setup = new Setup();
    this.result = new Result();
  }

  public void setupValidJwt() {
    this.setup.currentJwt = JwtMother.validJwt();
  }

  public void setupExpiredJwt() {
    this.setup.currentJwt = JwtMother.expiredJwt();
  }

  public void setupKwikAdmin() {
    this.setupUser(
        UserMother.kwikAdmin(passwordEncryptionService), UserMother.kwikAdminRawPassword());
  }

  public void setupUser() {
    this.setupUser(
        UserMother.douglasFonseca(passwordEncryptionService),
        UserMother.douglasFonsecaRawPassword());
  }

  public void setupUser(User user, RawSecret password) {
    user.changePassword(passwordEncryptionService, password);
    this.setup.user = user;
    this.setup.rawPassword = password;
    Mockito.when(userRepository.getByUsername(this.setup.user.getUsername()))
        .thenReturn(Optional.ofNullable(this.setup.getUser()));
    Mockito.when(userRepository.getByEmail(this.setup.user.getEmail()))
        .thenReturn(Optional.ofNullable(this.setup.getUser()));
  }

  public void login() {
    this.login(this.setup.user.getUsername(), this.setup.rawPassword);
  }

  private void login(String username, RawSecret password) {
    try {
      GenerateJwtCommand command = new GenerateJwtCommand(username, password.getValue());
      this.result.newJwt = jwtFlowApplicationService.login(command);
    } catch (Exception exception) {
      this.result.exception = exception;
    }
  }

  public void refresh() {
    this.refresh(this.setup.currentJwt);
  }

  private void refresh(Jwt token) {
    try {
      this.result.newJwt = jwtFlowApplicationService.refresh(token);
    } catch (AuthenticationException authenticationException) {
      this.result.exception = authenticationException;
    }
  }

  public void checkState() {
    this.checkState(this.setup.currentJwt);
  }

  private void checkState(Jwt token) {
    this.result.isValidJwt = jwtFlowApplicationService.checkState(token);
  }

  public void loginWithUsername() {
    this.login(this.setup.user.getUsername(), this.setup.rawPassword);
  }

  public void loginWithEmail() {
    this.login(this.setup.user.getEmail(), this.setup.rawPassword);
  }

  public void loginWrongPassword() {
    this.login(this.setup.user.getUsername(), RawSecret.of("wrong-password"));
  }

  @Getter
  protected static final class Setup {

    private User user;

    private RawSecret rawPassword;

    private Jwt currentJwt;
  }

  @Getter
  protected static final class Result {

    private Exception exception;

    private boolean isValidJwt = false;

    private Jwt newJwt;
  }
}
