# Install Using GitHub Actions In AWS

This guide provides step-by-step instructions for installing Works using GitHub Actions in an AWS environment.

## **Pre-requisites**

* Github account - [signup](https://docs.github.com/en/get-started/start-your-journey/creating-an-account-on-github)
* Kubectl installed in the system - [installation guide](https://kubernetes.io/docs/tasks/tools/)
* AWS account - [signup](https://aws.amazon.com/free/?gclid=Cj0KCQjwn7mwBhCiARIsAGoxjaLfY6V0mjFrYwFv5BNgVmeyZOkZ-zwIHDVgMxNW7oIyVsOzWY04WYoaAv8GEALw_wcB\&trk=09863622-0e2a-4080-9bba-12d378e294ba\&sc_channel=ps\&ef_id=Cj0KCQjwn7mwBhCiARIsAGoxjaLfY6V0mjFrYwFv5BNgVmeyZOkZ-zwIHDVgMxNW7oIyVsOzWY04WYoaAv8GEALw_wcB:G:s\&s_kwcid=AL!4422!3!453325185022!e!!g!!aws%20sign%20up!10712784862!111477280531\&all-free-tier.sort-by=item.additionalFields.SortRank\&all-free-tier.sort-order=asc\&awsf.Free%20Tier%20Types=*all\&awsf.Free%20Tier%20Categories=*all)
* Install AWS CLI locally - [installation guide](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
* Postman - [installation guide](https://learning.postman.com/docs/getting-started/installation/installation-and-updates/) and [import data guide](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-data/)
* A domain host - (example: GoDaddy to configure your server to a domain)

### **Install** <a href="#install" id="install"></a>

* Prepare AWS IAM User
* Create an IAM User in your AWS account - [official document](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html)
* Generate ACCESS\_KEY and SECRET\_KEY for the IAM user - [AWS document](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_access-keys.html)
* Assign administrator access to the IAM user for necessary permissions.
* **Set up the AWS profile locally by running the following commands:**
  * aws configure --profile {profilename}
  * fill in the key values as they are prompted
    * AWS\_ACCESS\_KEY\_ID: \<GENERATED\_ACCESS\_KEY>
    * AWS\_SECRET\_ACCESS\_KEY: \<GENERATED\_SECRET\_KEY>
    * AWS\_DEFAULT\_REGION: ap-south-1
  * export AWS\_PROFILE={profilename}

{% hint style="info" %}
**Note:** AWS Account should have S3 Bucket access to make Filestore service work
{% endhint %}

## **Fork the GitHub Repositories**

**Fork** the following repositories with all the branches into your organisation account on [GitHub](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/working-with-forks/fork-a-repo):

* [Works-DevOps](https://github.com/egovernments/DIGIT-DevOps/tree/e2365954e125f2757738888b609f7bd89b325a74/deploy-as-code/helm)
* [Master-Data](https://github.com/egovernments/egov-mdms-data/tree/f9ac05161950757fa61a421deb1f2dcf49b50229/data/pg) (We do not need the master data repo since we are using the mdms-v2 by default with data seeded)
* [configs](https://github.com/egovernments/configs/tree/000b71f8ea9af378058f6f612af83d58c12898f1)&#x20;

## Adding AWS Keys to the Repository

**Go to the forked works-devops repository:**

* Navigate to the repository settings.
* Go to Secrets and Variables.
* Click on the actions options below secrets and variables.
* On the new page, choose the new repository secret option in repository secrets and add the following keys mentioned below:
  * AWS\_ACCESS\_KEY\_ID: \<GENERATED\_ACCESS\_KEY>
  * AWS\_SECRET\_ACCESS\_KEY: \<GENERATED\_SECRET\_KEY>
  * AWS\_DEFAULT\_REGION: ap-south-1
  * AWS\_REGION: ap-south-1

## Changes to be Made in the Repository

* Navigate to the **release-githubactions** branch in the forked DevOps repository.
* **Enable GitHub Actions.**
  * Click on **Actions,** then click on "**I understand my workflows, go ahead and enable them":**

### How to Edit the GitHub Files

* The following steps can be done either directly in the browser or the local system if you are familiar with Git usage.
* Before following any of the steps switch to the **release-githubactions** branch.

1. Steps to edit the git repository in the browser - [Git guide](https://docs.github.com/en/codespaces/the-githubdev-web-based-editor#opening-the-githubdev-editor)
2. Steps to edit in the local system if you are familiar with Git basics:
   1. Git clone {forked DevOps repolink}
   2. Follow the below steps and make changes
   3. Then commit and push to the **release-githubactions** branch
   4. **NOTE:** Complete all changes at once then commit and push the code to remote to trigger the installation.

### Replace the Master and Configuration Repositories

* **Note:** Make these repository/branch changes before installation; making changes to the configuration repository link in the DevOps repository after installation without understanding what impact they may have will lead to failure in the application functionality.
* Navigate to egov-demo.yaml (**config-as-code/environments/egov-demo.yaml**).
* Under the **egov-persister:** change the **gitsync** link of the **works-config** repository to the forked config repository and the branch to **DEMO.**
* Under the **egov-indexer:** change the gitsync link of the **works-config** repository to the forked config repository and the branch to **DEMO.**

## Configure infrastructure-as-code <a href="#configure-infrastructure-as-code" id="configure-infrastructure-as-code"></a>

* Navigate to infra-as-code/terraform/sample-aws.
* Open input.yaml and enter details such as **domain\_name**, **cluster\_name**, **bucket\_name**, and **db\_name**.

### Configure Works Chart Version

* Navigate to file `deploy-as-code/deployer/digit_installer.go`
* Search for `works-demo` in the file and check for health-demo-vX.X
* Change the version to v1.1-> `works-demo-v1.1`

## Configure Application Secrets

* Generate SSH key pair.
* How to Generate SSH Key Pair - choose one of the following methods to generate an SSH key pair:
  * **Method a:** Use an online website. (Note: This is not recommended for production setups, only for demo purposes): [https://8gwifi.org/sshfunctions.jsp](https://8gwifi.org/sshfunctions.jsp)
  * **Method b:** Use OpenSSL commands:
    * `openssl genpkey -algorithm RSA -out private_key.pem`
    * `ssh-keygen -y -f private_key.pem > ssh_public_key`
    * To view the key run the commands or use any text editor to open the files
      * `vi private_key.pem`
      * `vi ssh_public_key`
* Once generated Navigate to config-as-code/environments
* Open egov-demo-secrets.yaml
* Search for `PRIVATE KEY` and replace from `-----BEGIN RSA PRIVATE KEY-----` to `-----BEGIN RSA PRIVATE KEY-----` with private\_key generated (**note**: please make sure the private key is indented as given)
* Add the public\_key to your GitHub account - [Git guide](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account)

## Finalise Installation <a href="#finalise-installation" id="finalise-installation"></a>

* Once all details are entered, push these changes to the remote GitHub repository. Open the Actions tab in your GitHub account to view the workflow. You should see that the workflow has started, and the pipelines are completed successfully.

## Configure domain name <a href="#configure-domain-name" id="configure-domain-name"></a>

* Connect to the Kubernetes cluster, from your local machine by using the following command:

```
aws eks update-kubeconfig --region ap-south-1 --name $CLUSTER_NAME
```

* Get the CNAME of the _nginx-ingress-controller_

```
kubectl get svc nginx-ingress-controller -n egov -o jsonpath='{.status.loadBalancer.ingress[0].hostname}'
```

*   The output of this will be something like this:

    [ae210873da6ff4c03bde2ad22e18fe04-233d3411.ap-south-1.elb.amazonaws.com](http://ae210873da6ff4c03bde2ad22e18fe04-233d3411.ap-south-1.elb.amazonaws.com/)
* Add the displayed CNAME to your domain provider against your domain name. e.g. GoDaddy domain provider - [https://www.godaddy.com/en-in/help/add-a-cname-record-19236](https://www.godaddy.com/en-in/help/add-a-cname-record-19236)

### To Enable Filestore Service <a href="#to-enable-filestore-service" id="to-enable-filestore-service"></a>

After connecting to Kubernetes cluster, edit the deployment of FileStore service using the following command:

```
export KUBE_EDITOR='code --wait'
kubectl edit deployment egov-filestore -n egov
```

The deployment.yaml for Filestore Service will open in VS Code , add the aws key and secret key provided to you in the way shown below:

<figure><img src="../../.gitbook/assets/filestore.png" alt=""><figcaption><p>Filestore secret</p></figcaption></figure>

Close the deployment.yaml file opened in your VS Code editor and the deployment will be updated.
