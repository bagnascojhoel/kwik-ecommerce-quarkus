package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber;

import br.com.bagnascojhoel.kwik.ecommerce.auth.application.JwtFlowApplicationService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber.mocks.JwtServiceMock;
import br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber.mocks.UserRepositoryMock;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.AuthenticationException;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.GenerateJwtCommand;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.Jwt;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.jwt.JwtService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.PasswordEncryptionService;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.RawSecret;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.User;
import br.com.bagnascojhoel.kwik.ecommerce.auth.domain.user.UserRepository;
import br.com.bagnascojhoel.kwik.ecommerce.auth.object_mother.JwtMother;
import br.com.bagnascojhoel.kwik.ecommerce.auth.object_mother.UserMother;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import lombok.Getter;
import org.mockito.Mockito;

@ApplicationScoped
public class AuthContext {

  @Inject
  private PasswordEncryptionService passwordEncryptionService;

  @Inject
  private JwtFlowApplicationService jwtFlowApplicationService;

  @Inject
  private UserRepository userRepository;

  @Inject
  private JwtService jwtService;

  private Setup setup = new Setup();

  @Getter
  private Result result = new Result();

  public void reset() {
    this.setup = new Setup();
    this.result = new Result();
  }

  public void setupValidJwt() {
    this.setup.currentJwt = JwtMother.validJwt();
    Mockito.when(getJwtServiceMock().isValid(this.setup.currentJwt)).thenReturn(true);
  }

  public void setupExpiredJwt() {
    this.setup.currentJwt = JwtMother.expiredJwt();
    Mockito.when(getJwtServiceMock().isValid(this.setup.currentJwt)).thenReturn(false);
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
    Mockito.when(getUserRepositoryMock().getByUsername(this.setup.user.getUsername()))
        .thenReturn(Optional.ofNullable(this.setup.getUser()));
    Mockito.when(getUserRepositoryMock().getByEmail(this.setup.user.getEmail()))
        .thenReturn(Optional.ofNullable(this.setup.getUser()));

    // Mock JwtService to generate a valid JWT
    Jwt generatedJwt = JwtMother.validJwt();
    Mockito.when(getJwtServiceMock().generate()).thenReturn(generatedJwt);
    Mockito.when(getJwtServiceMock().isValid(generatedJwt)).thenReturn(true);
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

  private JwtServiceMock getJwtServiceMock() {
    return (JwtServiceMock) this.jwtService;
  }

  private UserRepositoryMock getUserRepositoryMock() {
    return (UserRepositoryMock) this.userRepository;
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
