We use automated testing for all features. We know that e2e tests are the most powerful ones, but also the slower and harder to develop and maintaing. So we use behavior integraiton testing to cover all features validating the application and domain layers; use behavior end-to-end testing to validate happy path scenarios to also be used once the graalvm image is built; and unit tests to validate other not critic scenarios.

The types of tests used are:
1. Behavior Integration Tests: prefered type of testing; the expected functionality is written on Given-When-Then using Gherking style; these focus on testing the application and domain layer and mocks everything outside it.
2. Behavior End-to-End Tests: cover only happy path of each feature; the expected functionality is written on Given-When-Then using Gherking style; nothing is mocked; they are ran against the native application, so no mocks;
3. Unit Tests: used for non-critic scenarios; should run fast.

We know that creating a powerful and concise set of fixtures is important, so we use the concept of object mothers (https://martinfowler.com/bliki/ObjectMother.html) for all tests. The fixtures must be built incrementally and be meaningful to the domain.

Test scenarios should be organized by FEATURE under .feature files, regardless if they are @E2e or @Integration. So a .feature file should contain happy-path scenarios using @E2e and other business rules covered by @Integration scenarios.

On .feature files behavior integration tests must be annotated with @Integration. While end-to-end scenarios, must use @E2e annotation. 

The test scenarios using @Integration will not start the whole application, the must be light-weight.
On the other hand, the @E2e scenarios will start the whole application and must be executable from outside the application. They will cover happy path scenarios and will be used to validate any generated native image of the project.