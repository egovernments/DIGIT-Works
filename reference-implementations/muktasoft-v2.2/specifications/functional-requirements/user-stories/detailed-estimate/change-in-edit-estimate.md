# Change in edit estimate

## Edit Estimate

1. There is no change in the role-action and edit process.
2. Edit screen will get changed based on changes to have detailed estimate.

### Notification

Not applicable.

### Actions

On Save, Changes in the detail estimate are saved and no workflow changes done, fresh analysis is generated.

On submit, based on logged-in user role, workflow pop-up window is displayed.

<table data-header-hidden><thead><tr><th width="189.5"></th><th></th></tr></thead><tbody><tr><td><strong>Role</strong></td><td><strong>Workflow Window</strong></td></tr><tr><td>Estimate Creator</td><td>Submit pop-up window, estimate gets forwarded to verifier with changes saved and fresh analysis generated.</td></tr><tr><td>Estimate Verifier</td><td>Verify and Forward pop-up window, estimate gets forwarded to technical sanctioner with changes saved and fresh analysis generated.</td></tr><tr><td>Technical Sanctioner</td><td>Technical Sanction pop-up window, estimate gets forwarded to approver with changes saved and fresh analysis generated.</td></tr><tr><td>Approver</td><td>Approval pop-up window, estimate gets approved with changes saved and fresh analysis generated.</td></tr></tbody></table>

## UI

Same as created detailed estimate screen with additional estimate number displayed on top.

## Acceptance Criteria

1. Role based access based on configuration.
2. The estimates which are in workflow can only be edited.
3. Estimate is opened in editable mode.
4. The details given in table can be edited by user.
