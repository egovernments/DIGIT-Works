# Estimate Templates

## Introduction

Estimate templates aid civil engineers in grouping commonly used Schedules of Rates (SORs) for specific project types or construction works, thereby reducing the time required for estimate preparation.

## **Functional Details**

### **Create Template**

An estimate template allows users to group necessary Schedule of Rates (SORs) for a well-defined piece of work, storing them in the system with a designated name and description. This facilitates their use in preparing estimates for similar projects.

**Attributes**

<table><thead><tr><th width="90">S.No.</th><th width="119">Field</th><th width="122">Data Type</th><th width="127">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Project Type</td><td>Drop-down</td><td>Yes</td><td>Project types</td></tr><tr><td>2</td><td>Name</td><td>Text</td><td>Yes</td><td>Name given to template.</td></tr><tr><td>3</td><td>Description</td><td>Text</td><td>Yes</td><td>The description, describing the template in detail.</td></tr><tr><td>4</td><td>Search SOR</td><td>Search</td><td>Yes</td><td>To search an SOR and add to the template.</td></tr><tr><td>5</td><td>SORs</td><td><br></td><td><br></td><td><br></td></tr><tr><td>5.1</td><td>Sr. No.</td><td>Display</td><td>Yes</td><td>Serial number.</td></tr><tr><td>5.2</td><td>Description</td><td>Display</td><td>Yes</td><td>SOR description</td></tr><tr><td>5.3</td><td>Unit</td><td>Display</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>6</td><td>Non SORs</td><td><br></td><td><br></td><td><br></td></tr><tr><td>6.1</td><td>Sr. No.</td><td>Display</td><td>Yes</td><td>Serial number.</td></tr><tr><td>6.2</td><td>Description</td><td>Text</td><td>Yes</td><td>Non SOR description</td></tr><tr><td>6.3</td><td>Unit</td><td>Drop-down</td><td>Yes</td><td>Unit of measurement</td></tr></tbody></table>

**Mockups**

<figure><img src="https://lh7-us.googleusercontent.com/W1AgVZuXINGsVsbiWUOLMFqKRhiyDs8CQohk6G4FKA2L59EO2YequSll9xnuUqDwbQ5Eb8QPCfk-3Xt14nODW5ASwmYB878TOoJMN06CRFL3dvf7AIjnNbq2vZd93FOFpwhrjKbplwjt1ssJtAjpOg" alt=""><figcaption></figcaption></figure>

### **Search Template**

It enables the user to search a template using various parameters and see the result from where the complete detail of template can be seen clicking on the template code.

**Search Criteria**

<table><thead><tr><th width="112">S.No.</th><th width="174">Field Name</th><th width="150">Data Type</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Project Type</td><td>Drop-down </td><td>Project types</td></tr><tr><td>2</td><td>Template Name</td><td>Text</td><td>It is name of template</td></tr><tr><td>3</td><td>Template Code</td><td>Drop-down</td><td>It is system generated unique code to identify the template</td></tr><tr><td>4</td><td>Status</td><td>Drop-down</td><td>Active/ Inactive.</td></tr></tbody></table>

**Search Result**

<table><thead><tr><th width="101">S.No.</th><th width="142">Field Name</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>It is system generated unique code to identify the template</td></tr><tr><td>2</td><td>Name</td><td>Template description.</td></tr><tr><td>3</td><td>Description</td><td>Template description.</td></tr><tr><td>4</td><td>Status</td><td>Active/ Inactive.</td></tr></tbody></table>

**Mockups**

<figure><img src="https://lh7-us.googleusercontent.com/lr94kICUIp8URrWwGIGr2eFyzu1JWt1lGFPiI3fkqXsG_NZ-pIZEp04mWxHSBa6XfmzUGHMYP8LWMYGhEt_gp3MianuxgM2MdRlil7tMOgfoBoeuwdkNbmOiklX_gk9tYLAZCfj45viXpVUH7XEzzQ" alt=""><figcaption></figcaption></figure>

### **View Template**

It allows the user to view the details of the template and make corrections if need be using the action Modify Template.

**Attribute**

<table><thead><tr><th width="114">S.No.</th><th width="149">Field</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>A unique code generated for template.</td></tr><tr><td>2</td><td>Project Type</td><td>Project type for which template is defined.</td></tr><tr><td>3</td><td>Name</td><td>Name given to template.</td></tr><tr><td>4</td><td>Description</td><td>The description, describing the template in detail.</td></tr><tr><td>5</td><td>Is Active</td><td>Status of template, Active/ Inactive.</td></tr><tr><td>5</td><td>SORs</td><td><br></td></tr><tr><td>5.1</td><td>Sr. No.</td><td>Serial number.</td></tr><tr><td>5.2</td><td>Description</td><td>SOR description</td></tr><tr><td>5.3</td><td>Unit</td><td>Unit of measurement</td></tr><tr><td>5.4</td><td>Rate</td><td>Rate of SOR</td></tr><tr><td>6</td><td>Non SORs</td><td><br></td></tr><tr><td>6.1</td><td>Sr. No.</td><td>Serial number.</td></tr><tr><td>6.2</td><td>Description</td><td>Non SOR description</td></tr><tr><td>6.3</td><td>Unit</td><td>Unit of measurement</td></tr><tr><td>6.4</td><td>Rate</td><td>Rate of Non SOR</td></tr></tbody></table>

**Mockups**

<figure><img src="https://lh7-us.googleusercontent.com/fdy5hiYFEK_Us_iy2564rpYP_QNytEUT_f72BD2ZxN5Qpr4zte4z147idBi9cyC8EkA4rqq2xtHekJgbZHN7ZsTDpbFfLK9xqpJrgiQ0hp4OWPR7bWdrWrSohkZcGxQgXsTIt4y20DvlW_k3MS3eUQ" alt=""><figcaption></figcaption></figure>

### **Modify Template**

It is to enable the users to correct the already created template.

**Attributes**

<table><thead><tr><th width="90">S.No.</th><th width="143">Field</th><th width="110">Data Type</th><th width="125">Is Mandatory?</th><th>Description</th></tr></thead><tbody><tr><td>1</td><td>Code</td><td>Display</td><td>Yes</td><td>A unique code generated for template.</td></tr><tr><td>2</td><td>Project Type</td><td>Drop-down</td><td>Yes</td><td>Project types</td></tr><tr><td>3</td><td>Name</td><td>Text</td><td>Yes</td><td>Name given to template.</td></tr><tr><td>4</td><td>Description</td><td>Text</td><td>Yes</td><td>The description, describing the template in detail.</td></tr><tr><td>5</td><td>Is Active</td><td>Drop-down</td><td>Yes</td><td>Active/ Inactive</td></tr><tr><td><br></td><td>Search SOR</td><td>Search</td><td>Yes</td><td>To search an SOR and add to the template.</td></tr><tr><td>6</td><td>SORs</td><td><br></td><td><br></td><td><br></td></tr><tr><td>6.1</td><td>Sr. No.</td><td>Display</td><td>Yes</td><td>Serial number.</td></tr><tr><td>6.2</td><td>Description</td><td>Display</td><td>Yes</td><td>SOR description</td></tr><tr><td>6.3</td><td>Unit</td><td>Display</td><td>Yes</td><td>Unit of measurement</td></tr><tr><td>7</td><td>Non SORs</td><td><br></td><td><br></td><td><br></td></tr><tr><td>7.1</td><td>Sr. No.</td><td>Display</td><td>Yes</td><td>Serial number.</td></tr><tr><td>7.2</td><td>Description</td><td>Text</td><td>Yes</td><td>Non SOR description</td></tr><tr><td>7.3</td><td>Unit</td><td>Drop-down</td><td>Yes</td><td>Unit of measurement</td></tr></tbody></table>

**Mockups**\


<figure><img src="https://lh7-us.googleusercontent.com/TKokSQ91Zz1Lqm0MNbAoiQL_6wUHfon8c4FfiJkJziyxWz37ZH5dOFobzwmcFXWQ1er8TtpqIl4sqQknrQgLmzRhNbwzJjvGqjcZ9I3Ak1YdBxKMLfArI1PvgNuJvm2W_rmaWlYc0fdQLMhWE9r78w" alt=""><figcaption></figcaption></figure>
