---

description: 'Create an implementation plan for an ADR with files to be changed and references to supporting documentation or existing files'
agent: Architect
----------------

# Write Implementation Plan for ADR

Your goal is to create a reproducable implementation plan for a given ADR. The plan should outline all necessary changes to the codebase, including new files to be created, existing files to be modified, and references to supporting documentation or existing files that can serve as patterns.

Consider that the output document will be used by AI Agents to implement the changes and as support for future implementations. It will also be used by human reviewers to validate the implementation.

## Phase Architecture

- Each phase must have measurable completion criteria
- Tasks within phases must be executable in parallel unless dependencies are specified
- All task descriptions must include specific file paths, function names, and exact implementation details
- No task should require human interpretation or decision-making

## AI-Optimized Implementation Standards

- Use explicit, unambiguous language with zero interpretation required
- Structure all content as machine-parseable formats (tables, lists, structured data)
- Include specific file paths, line numbers, and exact code references where applicable
- Define all variables, constants, and configuration values explicitly
- Provide complete context within each task description
- Use standardized prefixes for all identifiers (REQ-, TASK-, etc.)
- Include validation criteria that can be automatically verified

## BDD Test Guidelines

- There are two types of BDD tests: Behavior Integration Tests and Behavior End-to-End Tests
- Behavior Integration Tests MUST cover application and domain layers while mocking infrastructure and not going through the presentation
- Behavior End-to-End Tests MUST validate happy-path scenarios for all application entrypoints and start the whole application, going through the REST API up to the database
- Both types of BDD tests MUST be written in Gherkin syntax
- They MUST be comprehensive and cover all use cases described in the ADR
- They MUST reference object mothers to be used for test data setup
- They MUST be written using TDD approach, only generate the minimal code to make them pass

## Output

You need to write a new markdown file named `<ADR-ID>.implementation-plan.md`, where `<ADR-ID>` is the ID of the ADR you are creating the implementation plan for. The file should be placed in the same directory as the ADR.

<plan_style_guide>
The below template defined the mandatory structure of the implementation plan file you need to generate.

There are some meta instructions on the template that you must NOT write in the final template, but rather follow their instructions while filling the sections they appear on. The meta instructions are:
- Optional: if a section should be or not included in the final plan
- Content: guidelines you need to follow when filling the section

```
# Implementation Plan for ADR <ADR-ID>

### 1 BDD Changes
**Optional:** If not necessary, write "None required for this section."

#### Content:
- List all new and changed test classes with their full paths.
- For each test file, add references to documentation or existing files that should be used as patterns.
- **Include the whole scenario in BDD Gherkin style (no step implementations).**
- Include atleast one @E2e scenario to validate the happy-path of  each feature. But most tests should be @Integration scenarios.
- For most scenarios you should use @Integration as they will only depend on application and domain layers while mocking infrastructure and not going through the presentation layer.
- Include references to object mothers to be used.

### 2. Object Mother Changes
**Optional:** If not necessary, write "None required for this section."

#### Content:
- List all new and changed fixture or test data files with their full paths.
- For each file, add references to documentation or existing files that should be used as patterns.
- Each change to object mothers MUST build an overall consistent set of data to represent the domain and be used for testing.

### 3. Implementation Phases

#### Content:
- Behavior Integration Tests MUST be implemented using TDD on phase 1
- If behavior end-to-end tests are required, they MUST be implemented using TDD on phase 2
- The other steps in the implementation plan SHOULD be after BDD test phases and before unit tests phases
- Unit Tests MUST NOT use TDD and should be in the final phase

#### Implementation Phase 1

- GOAL-001: [Describe the goal of this phase, e.g., "Implement feature X", "Refactor module Y", etc.]
- Impacted layers;
- Impacted files;

#### Implementation Phase 2

- GOAL-002: [Describe the goal of this phase, e.g., "Implement feature X", "Refactor module Y", etc.]
- Impacted layers;
- Impacted files;

### 4. Database Changes
**Optional:** If not necessary, write "None required for this implementation."

#### Content:
**Description:** 1 sentence describing why database changes are needed, if any.
**Migrations:** 
- List the files to be created with their paths and brief descriptions. Use "Create file:" for new files.
- When its a DDL script, include the columns affected.
- Include any indexes to be created.
- When acting on existing tables or values, describe mitigation and rollback procedures.
- For updates to existing files (e.g., master changelog), use "Update file:" followed by the path and description of changes.

### 5. Library Changes
**Optional:** If not necessary, write "None required for this implementation."

#### Content:
Describe the rationale for any new libraries or upgrades, including why existing libraries cannot fulfill the need.

### 6. Source Code Changes
**Optional:** If not necessary, write "None required for this implementation."

#### Content:
- Describe impacted modules and why they are affected.

#### 6.1 Presentation Layer
**Optional:** If not necessary, write "None required for this layer."

##### Content:
- List all new and changed classes with their full paths.
- For each class, add references to documentation or existing files that should be used as patterns.
- List all endpoints to be created or changed with HTTP methods, paths, request/response schemas.

#### 6.2 Application Layer
**Optional:** If not necessary, write "None required for this layer."

##### Content:
- List all new and changed classes with their full paths.
- For each class, add references to documentation or existing files that should be used as patterns.
- For application layer interfaces, include methods from the use case interfaces.

#### 6.3 Domain Layer
**Optional:** If not necessary, write "None required for this layer."

##### Content:
- List all new and changed classes with their full paths.
- For each class, add references to documentation or existing files that should be used as patterns.
- For domain layer repository interfaces, include methods from the repository interfaces.

#### 6.4 Infrastructure Layer
**Optional:** If not necessary, write "None required for this layer."

##### Content:
- List all new and changed classes with their full paths.
- For each class, add references to documentation or existing files that should be used as patterns.
- Do not include code implementations.

#### 6.5 Resources
**Optional:** If not necessary, write "None required for this layer."

##### Content:
- List all new and changed resource files with their full paths.
- When any file inside `resources` is changed, describe the purpose and impact of the change.

## 7. Documentation Changes
**Optional:** If not necessary, write "None required for this implementation."

#### Content:
- List all new and changed documentation files with their full paths.
- For each documentation file, add references to existing files that should be used as patterns.
- Include references to any external documentation that should be updated.

#### 8. Unit Testing
**Optional:** If not necessary, write "None required for this section."

##### Content:
- List all new and changed test classes with their full paths.
- For each test file, add references to documentation or existing files that should be used as patterns.
- **Include the title of the test scenarios for each file.**
- Unit tests must use JUnit, described with given/when/then and DisplayName, and cover specific edge cases for classes.
- Include references to object mothers to be used.

## 9. Risks & Assumptions

[List any risks or assumptions related to the implementation of the plan.]

- **RISK-001**: Risk 1
- **ASSUMPTION-001**: Assumption 1
```

</plan_style_guide>

## Validation

DO NOT OUTPUT ANY OF THESE VALIDATIONS IN THE FINAL IMPLEMENTATION PLAN.

- Ensure that the implementation plan is clear, concise, and free of ambiguity.
- Verify that all necessary sections are included and properly formatted.
- Confirm that all referenced files and documentation exist and are accessible.
- Check for consistency in terminology and style throughout the document.
- Validate that the plan aligns with the goals and constraints outlined in the ADR.

