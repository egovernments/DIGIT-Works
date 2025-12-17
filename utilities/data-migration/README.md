WELCOME

# data migration in mukta production
data mismatches can occur between the JIT side and the Mukta side, particularly regarding the status of payments. This is a common issue caused by Kafka lag. The following Python script addresses and resolves these data mismatches.


There can be some data mismatching on jit side and mukta side especially mismatching in status of payment is a very common mismatching due to the kafka lag so 
here's the python script for fix/migrate the data.

# functionality

<!-- Identifying Mismatched Data -->
The first step is to identify the IDs with mismatched data. This is done by executing the following SQL query on the database:

SELECT jp.id 
FROM eg_mukta_ifms_disburse AS md 
INNER JOIN jit_payment_inst_details AS jp ON md.id = jp.id 
WHERE md.status != jp.pistatus;
This query retrieves all the IDs where the status in the Mukta database (eg_mukta_ifms_disburse) does not match the status in the JIT payment details (jit_payment_inst_details)

Fetching and Matching Data

1. IFMS-Adapter PI Search: Perform a PI search using the IFMS adapter to obtain the most up-to-date payment information.

2. Program Disbursement Search on IFIX Side: Conduct a program disbursement search on the IFIX side to retrieve the corresponding disbursement data.

3. Modify Disburse: Use the modify_disburse function to reconcile and modify the statuses of the payment and disbursement data. This function ensures that      the statuses are updated according to the predefined enums and requirements.

<!-- Updating the Pipeline -->

Finally, the modified result is passed into the on-disburse update function on the IFIX side. This function updates the entire pipeline, ensuring consistency and accuracy across the system.
