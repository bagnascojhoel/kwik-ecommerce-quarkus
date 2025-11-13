---
description: 'Create an ADR for the given story or task'
agent: Plan
tools: ['edit', 'runNotebooks', 'search', 'new', 'runCommands', 'runTasks', 'makenotion/notion-mcp-server/*', 'usages', 'vscodeAPI', 'problems', 'changes', 'testFailure', 'openSimpleBrowser', 'fetch', 'githubRepo', 'extensions', 'todos', 'runTests']
---

# Architectural Decision Record (ADR) Creation Prompt

## Goal

Write a clear and comprehensive technical ADR that serves as the architectural foundation for the given requirement description or the JIRA story with ID ${input:jiraId:ID for the story or task}.

This document will serve three main purposes:
1. Firstly, it will be the architectural source of truth for implementation plans and AI agents implementing features. The ADR should provide sufficient architectural guidance that implementation plans can be created consistently.
2. Secondly, human developers and architects will review the ADR during peer-review to evaluate the proposed design, trade-offs, and architectural fit within the existing system.
3. Thirdly, the document will serve as a knowledge base for future architectural decisions, helping teams understand why certain patterns were chosen and what alternatives were considered.

### Guidelines

1. Write in markdown, in a clear and concise manner, avoiding unnecessary jargon.
2. Sort all lists alphabetically unless explicitly stated otherwise.
3. Adhere to the instructions defined on `/docs` and `.github/instructions`.
4. Use `adp-jira-api` to fetch story details if available.
5. Use `adp-mysql-db/*` to check for existing database tables and columns.
6. Use consistent terminology throughout the document.
7. Use code blocks for code snippets, SQL queries, and configuration files.
8. Confirm that proposed classes or tables do not exist in the project before suggesting them.
9. Do not propose new dependencies without asking first.
10. Use mermaid diagrams to illustrate complex flows, domain models, and database schemas.
11. Be specific about trade-offs and include quantitative reasoning where possible.
12. Consider the long-term maintainability and scalability implications of decisions.

### Constraints

1. Keep ADRs focused on significant architectural decisions that have lasting impact.
2. Consider the document complete when there are 0 clarification questions needed.
3. Ensure all proposed patterns align with existing architectural principles in the codebase.
4. Ensure the proposed database changes align with existing schema design patterns.

## Execution Framework

### Refine Requirements

1. **Fetch Feature Information**: If Jira MCP is available, use it to retrieve detailed information about the feature or story associated with the provided story ID. If Jira MCP is not available, prompt the user for clarification on the feature details (e.g., provide a summary of the story, acceptance criteria, and any attachments).

2. **Create ADR File**: Create a new file following the naming convention `[story-id].adr.md` in the `docs/features/<ADR-ID>` directory. If the user didn't provide the story-id, STOP and output: `CLARIFICATION REQUIRED Blocked on: Story ID Cannot proceed without human input on: Please provide the story ID for the ADR filename.`.
    - **From here onwards, this newly created file will be referred to as "the document".**

3. **Documentation Scan:**
  - Use keywords to search for content related to the feature implementation inside `/docs` folder.
  - Adhere to the instructions described on `.github.instructions`

4. **Repository Scan**: Execute a workspace search to understand the current architecture:
   - Search for similar features or patterns already implemented
   - Identify the affected layers (domain, application, infrastructure, presentation)
   - Check for existing classes, tables, and patterns that relate to the proposed change
   - Document findings in an "Existing Architecture Analysis" section

5. **Requirements Analysis**: Read the fetched or provided story or task and write a short summary of:
   - The business problem being solved
   - The technical requirements
   - The architectural impact and scope
   - Any constraints or dependencies

6. **Clarification Check**: If the requirements are ambiguous or missing important information, write the assumptions in a dedicated section. After 3 rounds of assumptions, STOP and output: `CLARIFICATION REQUIRED Blocked on: [list specific requirement ambiguities] Cannot proceed without human input on: [specific questions]`. Do not propose an ADR until clarifications are resolved.

## Document Output

The ADR must contain the following sections, in the given order, respecting their rules:
---
#### 1. Title and Metadata
- **Story**: Story ID prompted by the user
- **Title**: Brief, descriptive title (e.g., "Implement Caching Layer for Policy Lookups")

#### 2. Context
- **Technical Context**: Current state of the system and why change is needed
- **Constraints**: Any limitations, requirements, or dependencies (budget, timeline, technology, compliance)
- **Assumptions**: Document any assumptions made (after validation in Refine Requirements phase)
- **Existing Architecture Analysis**: Summary of repository scan findings

Content Rules:
- Be specific about the problem being solved
- Include relevant background information for future readers
- Reference related ADRs or documentation
- Include metrics or data that motivated this decision

#### 3. Decision
Under one paragraph, explain: what was the decision, why you took that decision, and how you plan to implement it.

Content Rules:
- Be specific and actionable
- Explain the reasoning with evidence
- Address how this decision aligns with existing architecture
- Include architecture compliance considerations:
  - Layer boundaries and dependencies
  - Domain model purity (no framework dependencies in domain)
  - Separation of concerns
  - Dependency injection patterns

#### 4. Architectural Design

##### 4.1 Layer Architecture
- **Content**: Identify which layers will be affected and how they interact
- **Diagram**: Include a mermaid diagram showing layer dependencies
- **Rules**: Verify compliance with allowed patterns from `coding.instructions.md`:
  - Domain layer: No Spring/framework dependencies
  - Application layer: Orchestration and use cases
  - Infrastructure layer: Technical implementations
  - Presentation layer: API contracts and controllers

##### 4.2 Domain Model Changes
- **Optional**: If no domain model changes, include "None required for this decision"
- **Content**: Describe new or modified domain entities, value objects, aggregates, services
- **Diagram**: Include mermaid class diagram for domain objects and their relationships
- **Rules**: Follow DDD principles and existing domain patterns

##### 4.3 Database Schema Changes
- **Optional**: If no database changes, include "None required for this decision"
- **Content**: Describe new tables, columns, indexes, or schema modifications
- **Diagram**: Include mermaid entity-relationship diagram
- **Tools**: Use `adp-mysql-db/*` to check for existing tables and columns
- **Rules**: Consider:
  - Data normalization and denormalization trade-offs
  - Index strategy for performance
  - Migration strategy and backwards compatibility
  - Data retention and archival policies

##### 4.4 API Changes
- **Optional**: If no API changes, include "None required for this decision"
- **Content**: Describe new or modified endpoints with their request/response formats. Add the schema for each endpoint. Use the `/docs/ai-seed/static/rest-schemas.md` file as a reference for existing schemas.
- **Rules**: Consider:
  - RESTful design principles
  - Versioning strategy
  - Backwards compatibility
  - API contract testing approach
- **Output Format**:
  ```markdown
  ### Endpoint: `POST /api/v1/resource/{id}`
  **Description**: Brief description of the endpoint's purpose
  
  **Request**
  - **Headers**:
    - `Authorization`: `Bearer <token>`
    - `Content-Type`: `application/json`
  - **Path Parameters**:
    - `id`: `string` (required) - The ID of the resource
  - **Query Parameters**:
    - `param1`: `string` (optional) - Description
  - **Body**:
    ```json
    {
      "field1": "string",
      "field2": "integer"
    }
    ```
  **Response**:
  - **Successful Status Code**: `200 Ok`
  - **Headers**:
    - `Content-Type`: `application/json`
  - **Body**:
    ```json
    {
      "id": "string",
      "field1": "string",
      "field2": "integer",
      "createdAt": "string (ISO 8601 date-time)"
    }
    ```
  ```

##### 4.5 External API Integrations
- **Optional**: If no external integrations, include "None required for this decision"
- **Content**: Describe interactions with external systems, message queues, or third-party services
- **Diagram**: Include sequence diagram for complex interactions
- **Rules**: Consider:
  - Error handling and retry strategies
  - Circuit breakers and timeouts
  - Data consistency patterns

##### 4.6 Flow Diagrams
- **Content**: Use mermaid diagrams to illustrate:
  - Request/response flows
  - Business process workflows
  - State transitions
  - Decision trees

#### 5. Consequences

##### 5.1 Positive Consequences
- List benefits and improvements
- Include measurable outcomes where possible
- Consider: maintainability, performance, scalability, developer experience

##### 5.2 Negative Consequences
- List drawbacks, risks, or trade-offs
- Be honest about technical debt introduced
- Consider: complexity, performance overhead, learning curve

##### 5.3 Neutral Consequences
- List changes that are neither clearly positive nor negative
- Consider: different patterns to learn, shifted responsibilities

##### 5.4 Risks and Mitigations
- **Content**: For each identified risk, document:
  - Risk description
  - Impact (High/Medium/Low)
  - Probability (High/Medium/Low)
  - Mitigation strategy
  - Contingency plan

#### 6. References

- Links to related ADRs
- External documentation or resources
- Relevant standards or best practices
- Research papers or blog posts that influenced the decision

---

### Validate the Document

Review the document following the rules below:
1. **Completeness Check**: Ensure all required sections are present and thoroughly documented
2. **Clarity Check**: Verify the document is clear enough that:
   - An implementation plan can be derived without ambiguity
   - Future developers can understand the reasoning
   - The decision can be evaluated objectively
3. **Architecture Compliance**: Confirm all proposed patterns align with `coding.instructions.md`
4. **Consistency Check**: Verify terminology is consistent throughout
5. **Diagram Validation**: Ensure all mermaid diagrams are syntactically correct and meaningful
6. **Alphabetical Ordering**: Verify all lists are sorted alphabetically (except where order is significant)
7. **Dependency Graph**: Ensure no circular dependencies in proposed architecture
8. **Zero Ambiguity**: There should be 0 clarification questions needed after validation

If any validation rules are not met, refine the document. If fundamental ambiguities remain after 3 refinement iterations, STOP and output: `CLARIFICATION REQUIRED Blocked on: [list specific ambiguities] Cannot proceed without human input on: [specific questions]`.

---

**Remember your goal:** Write a clear, comprehensive, and industry-standard ADR that serves as the architectural foundation for implementation. This document will guide AI agents and developers, assist in peer-review, and serve as a knowledge base for future architectural decisions.