# KPIs: Actions Items

### Projects Delayed

1. Count of Projects where WO start date is in the selected date range and WO end date + X days is less than todays date. Let X be configurable.
2. For ex. Selected time period is Jan 1 to Jan 15. WO start date is Jan 7th, end date is Jan 31, X is 7. From 8Feb onwards if project closure is not done, this project is considered delayed.
3. On Hover show “ Contract created in the selected time period but not closed till date”

### **Pending Bills**

1. Count of Bills that are created (Automatically or manually) in the selected date range and but current status(as of today) is not Paid/Payment done (Whichever is final state of Bill)
2. Considers all types of Bills
3. Show Amount as well of these bills as designed in mockup
4. On Hover show “ Bills created in the selected time period but payment is not done as of today”

### **Work Orders Not Issued**

1. Number of Projects that are created in the selected time period and Work Orders are not yet created. (PS: Both are create dates only though text says issued.)
2. On Hover show text “ Projects created but Work Orders not created”

### **Muster Rolls Not Created**

1. Count of weeks across all work orders that are in progress (Accepted by SHG) but Muster Roll is not created.
2. If Contract start date is Jan1st (Mon) and user hasn't created muster rolls until Jan 31, Number of Muster rolls not created are 4 for this project. If User Submits any 2 of them on Feb 1st, then remaining 2 should be shown.
3. On hover show “Work weeks completed but muster rolls not submitted to ULBs”
