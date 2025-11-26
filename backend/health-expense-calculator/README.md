# Health Expense Calculator

The health expense calculator works with the expense service to generate bills based on business rules. For Payments V2 it also orchestrates billing periods, validations, and reporting.

### Service Dependencies

- DIGIT backbone services
- Persister
- MDMS
- IDgen
- Expense
- Attendance
- Muster-roll
- Redis (for period-status fallback caching)

### Service Details

- Calculate bill amounts according to business logic and post bills to the expense service.
- Payments V2 enhancements:
  - Billing configuration lifecycle (create/search/update) with automatic billing period generation.
  - Intermediate billing engine that:
    - Validates sequential billing periods.
    - Fetches attendance registers and muster-roll statuses (prefers persisted periodStatuses; fallback via Redis/API when missing).
    - Ensures all musters are APPROVED before bill generation.
    - Generates period-level bills and tracks bill-generation status.
  - Bill status check API `/v1/_checkBillStatus` used by muster-roll to enforce post-billing locks.
  - Period-aware report generation (PDF/Excel) reflecting Payments V2 fields.

### Key configuration (Payments V2)

- `billing.config.v2.enabled`: Enable billing config + period generation APIs.
- `billing.period.generation.enabled`: Toggle automatic period creation.
- `billing.muster.roll.period.aware.enabled`: Enforce period-aware muster validations.
- `billing.v2.batch.processing.enabled`: Controls batch billing (kept false for explicit period selection).
- Redis cache properties for muster status fallback and `works.attendance.register.update.endpoint` for attendance patching.

### API Specs

- Payments V2 contract: `health-expense-calculator/health-expense-calculator-v2-api-swagger-2.0.0.yaml`

### Postman Collection

- Payments V2: `health-expense-calculator/health-expense-calculator-2.0.0.postman_collection.json`
