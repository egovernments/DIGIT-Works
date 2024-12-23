# Solution Design

## Works Management

A Works Management System (WMS) is typically used by various government departments to track a project's end-to-end lifecycle (scope and finances).

Click on the links below to learn more about the features and capabilities supported by Works.

1. [Estimation of project costs and proposals](solution-design.md#id-7lxspous6vj4)
2. [Tendering for bids and contracts](solution-design.md#irv9nx28q2r3)
3. [Contracting with work order](solution-design.md#yxkefd3kpmer)
4. [Measuring project milestones](solution-design.md#q6w3dto1rpzh)
5. [Invoicing and payments](solution-design.md#t8dle02vzju0)
6. [Closing the project](solution-design.md#pnzn1sroolt)
7. [Reporting with dashboards](solution-design.md#y5y46t164a4t)

Refer to the process illustration below to explore the Works Management solution design approach.&#x20;

<figure><img src="../.gitbook/assets/image (190).png" alt=""><figcaption></figcaption></figure>

### Estimation <a href="#id-7lxspous6vj4" id="id-7lxspous6vj4"></a>

* [x] Once the project is decided, the first step is to estimate the cost of the project.
* [x] The estimation includes multiple steps.
  1. **Estimate Proposal:**
     * The Estimate proposal is for getting the in-principal approval for the project. If approved, the concerned engineer can start to make detailed estimates for the project.
     * The estimate proposal contains high-level details like the name of the project, a brief description, a few line items that constitute the project and the multiple budget heads to finance the project.
  2. **Detail Estimate:**
     * A detailed Estimate is prepared mostly after the estimate proposal is approved. (In most cases, these start simultaneously, as the concerned engineer would already know the chances for approvals).
     * A detailed Estimate is generally prepared using offline tools supporting advanced functionalities and methods of doing scope estimation. For example, in the case of Civil Projects, an estimation tool/process already has a Plinth Area Estimate, Service Unit Method, Floor Area Method, Carpet Area Method, Typical Bay Method, and Cost Comparison Method. It is possible to do a very detailed analysis using these inputs.
  3. **Abstract Estimate:**
     * An abstract estimate is a grouping and summary of the bill of quantities that evolved from a detailed estimation process.
     * The concerned engineer enters the abstract estimate details into WMS. This goes through an approval process with necessary stakeholders and departments depending on the nature of the work and the estimated cost.
  4. **Sub Estimate:**
     * An Estimate is sometimes divided into sub-estimates (For large or multi-location projects etc) for better management purposes.
     * Sub-estimates are later grouped into work packages for an easy tendering process.
  5. **Spill Over Estimate:**
     * For a project that spreads into multiple financial years, a spillover estimate is created for that year. Hence no new request for assetisation is needed.
  6. **Revised Estimate:**
     * Under circumstances where the scope & finances of the project go beyond the estimated amount and the set buffer, a revised estimate needs to be created and moved through the approval process.

### Tendering <a href="#irv9nx28q2r3" id="irv9nx28q2r3"></a>

1. **Work Packages** created from Estimates/Sub-estimates, essentially comprise the scope & bill of quantities that provide contractors with enough information to bid for the contract.
   * Authorities Draft Tender Papers (DTP) using Work Packages.
2. Bids are invited from contractors between set dates. There is also a negotiation process that happens on the bid amount. The contractor with the lowest bid is selected.
3. The Authority issues a Letter of Intent (LOI) to enter into a contract with the contractor.
   * The contractor issues a Letter of Acceptance in response to the LOI.

### Contracting <a href="#yxkefd3kpmer" id="yxkefd3kpmer"></a>

1. A Work Order is then created and shared with the contractor.
   * A work order is a detailed document that contains Scope, Bill of Quantities, Timelines, Terms and Conditions, Details of Contractor, Liability Periods, Other Documents etc.
2. A Work order also goes through the approval process.

### Measurement <a href="#q6w3dto1rpzh" id="q6w3dto1rpzh"></a>

1. Before the measurement starts, there are certain offline checks required. For example, acceptance letter issued to date, letter acknowledgement date, work order acknowledgement, signed site handover date, work commenced date etc.
2. Measurement is essentially of two types.
   * **Tracking Milestones:**
     * Milestones are set up during the contracting phase and before the project starts. These milestones describe the timeline for each phase and the percentage of work that will be completed in various stages.
     * As a milestone is reached, the completion status can be tracked on the WMS.
     * All milestones should be in the completed stage to process the final contractor bill.
   * **Tracking Measurement Book:**
     * MBook is also set up for detailed project tracking. MBook measurements are derived from abstract estimates and track the day-to-day progress of completed work.
     * MBook measurements can be entered by the vendor and verified by employees or can be entered by ground inspectors/ field staff regularly.

### Contractor Bill Payments <a href="#t8dle02vzju0" id="t8dle02vzju0"></a>

1. As the project progresses, the contractor raises the invoice for which bills are created by the employee in the system under specific budget heads and sent for approval
2. Approved bills are sent to the finance department for disbursement.
   * **Advance Bill:**
     * Bill that is raised before the commencement of work. For example, to buy construction materials or to procure labour.
   * **Part Bill:**
     * Bills that are raised during work.
     * In an ideal scenario, these bills are tightly coupled with the amount of work that is done (MBook measurements)
   * Final Bill:
     * The last bill that is raised before completing the project.

### Project Closure <a href="#pnzn1sroolt" id="pnzn1sroolt"></a>

Closing the project is a set of activities/checklists (prospective list given below) that are run to ensure all requirements are fulfilled.

1. Assetisation request raised
2. Final bill approved
3. Site inspection done
4. Site handover done
5. Contractor feedback submitted etc

### Reports & Dashboards <a href="#y5y46t164a4t" id="y5y46t164a4t"></a>

Reports and Dashboards give employees views and ways to analyze project performance within their jurisdiction. This also includes timelines, delays, risks, projections etc.

1. Some of the reports are -
   * Work progress register
   * Estimate appropriation register
   * Estimate abstract report by the department
   * Contractor bill report
   * Works utilisation report
   * Retention money recovery register
2. Each report is available for download in PDF or Excel.

DSS dashboard is also included.

