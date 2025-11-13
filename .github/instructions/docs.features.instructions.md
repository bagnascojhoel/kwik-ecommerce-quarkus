---
applyTo: 'docs/**/features/**/*.md'
---

# Feature Documentation Instructions

All feature documentation serve three distinct purposes with specific success criteria:

1. **AI Agent Source of Truth**: The agent must be capable of executing this plan multiple independent times with ≥95% code similarity (measured by structural equivalence, not exact text matching). The plan must be unambiguous enough to produce consistent class structures, method signatures, database schemas, and test scenarios.

2. **Human Peer Review**: Developers will validate this plan against the ADR to ensure:
   - All ADR requirements are addressed
   - Architectural consistency is maintained
   - No scope creep or missing requirements
   - Technical feasibility is confirmed
   - Design and pseudo implementation are sound

3. **Knowledge Base**: Future agents and humans will reference this document to:
   - Understand implementation decisions and rationale
   - Maintain or extend the feature
   - Learn patterns used in the codebase