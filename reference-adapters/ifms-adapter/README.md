# IFMS Adapter

Integration with Odisha IFMS is via the Just-in-Time (JIT) APIs provided by the Odisha treasury.

### Service Dependencies

- DIGIT backbone services
- IFMS-JIT
- Expense service
- Expense-calculator
- Individual
- Organisation
- IDGen
- Persister

## Service Details

- Fetches Bills and creates payment for approved bills
- Posts the bills to JIT System and updates the bill details according to response.

### API Specs

reference-adapters/ifms-adapter/ifms-adapter-v1.0.0.yaml
