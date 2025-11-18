package br.com.bagnascojhoel.kwik.ecommerce.auth.cucumber.mocks;

public abstract class Mocked<T> {

  protected T mockedInstance;

  public Mocked() {
    initMock();
  }

  protected abstract void initMock();

  public T mock() {
    return this.mockedInstance;
  }
}
