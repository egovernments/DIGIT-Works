# System Data Setup

Before you run the Works application, you need to set up the basic system data such as boundaries of the geography and the master data. In this document, we will illustrate the steps to load the base data for the server.

1. Create a superuser by using the super\_user\_createion curl given in the collection.

{% file src="../../.gitbook/assets/Works Seed Data Setup.postman_collection.json" %}

2. Add Master data by port-forwading the mdms-v2 service and creating the schema present in the collection. Once all the schema is created add the seed data according to requirements.

{% file src="../../.gitbook/assets/MDMS Data Setup.postman_collection.json" %}

3. Once the master data is set up send the request to the workflow service to create the workflows for the services which require workflows as mentioned in the collection given below.

{% file src="../../.gitbook/assets/Workflow Data Setup.postman_collection.json" %}

#### 4. Enable File Store <a href="#to-enable-file-store" id="to-enable-file-store"></a>

* Encode Aws access key and secret key to base64 encoding

```
echo -n "<access key or secret key>" | base64
```

* Update the secrets in the cluster (\*do not put these in the git DevOps repo files and commit)

```
kubectl edit secrets egov-filestore -n egov
```

* If it is not editable by the VI editor, we can use vs code to edit the file, run the below command and run the above step again

```
export EDITOR='code --wait'
```

#### 5. Redeploy Services at once to read from new data <a href="#redeploy-all-the-services-at-once-to-read-from-new-data" id="redeploy-all-the-services-at-once-to-read-from-new-data"></a>

* Run the below command to delete and restart all the services

```
kubectl delete pods --all -n egov
```

* Run the command below to check if all pods/services are running. If not, wait for some time and check again:

```
kubectl get pods -n egov
```
