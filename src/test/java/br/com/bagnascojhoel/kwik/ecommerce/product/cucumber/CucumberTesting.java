package br.com.bagnascojhoel.kwik.ecommerce.product.cucumber;

import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;

@CucumberOptions(
    features = "src/test/resources/user-acceptance-testing",
    glue = "br.com.bagnascojhoel.kwik.ecommerce.product.cucumber")
public class CucumberTesting extends CucumberQuarkusTest {}
