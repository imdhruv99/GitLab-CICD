# Final Project

- This project is build using below things...

- The project is developed using programming language [Java](https://docs.oracle.com/en/java/javase/16/docs/api/index.html) and web framework is [SpringBoot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/).

- The project is build using Gradle. Gradle is General purpose build automation tool. You can learn more about gradle from [here](https://docs.gradle.org/current/userguide/what_is_gradle.html).

- I have used [PMD](https://pmd.github.io/pmd/) to anylize source code at time of developing and later in CICD as well.

- To test API i have used [Postman](https://learning.postman.com/docs/getting-started/introduction/) which is standard API testing tool in industry and in CICD i have used [newman](https://github.com/postmanlabs/newman) which is command line collection runner for postman. It is based on NPM.

- I have used AWS as a cloud provider. Specifically i have used [AWS S3](https://docs.aws.amazon.com/s3/index.html) to store the build of our project and [AWS Elastic BeanStalk](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/Welcome.html) is used to expose our application.

- To create AWS ElasticBeanStalk Application i have used `AWS CLI`. Learn AWS CLI commands for Beanstalk from [here](https://docs.aws.amazon.com/cli/latest/reference/elasticbeanstalk/index.html).

- That's it, Now let's start with CICD of our project. The soul purpose of this project is to understand GitLab CICD in Depth so i am not going to explain how this Java & SpringBoot based project works.

### Configuraing CICD for Deploying on Cloud

- Before running this project we need to setup some variables.

- To setup variables go to `GitLab Repository > Settings > CICD > Variables`

- Create AWS S3 Bucket and save that `S3 Bucket Name` as a variable in above path.

- Create IAM user with `Administrator Access` and save `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` as variable.

- Create Environment named as `production` in `AWS Elastic BeanStalk` because creating env using CICD is not a best practice. Follow process given [here](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/environments-create-wizard.html) to create env. Choose `Upload your code` under the Application code section in AWS Console and upload Jar File from `build/libs/`. Later this jar file will be updated by automation with latest jar file.

### Stages

- Our CICD pipeline is divided in 6 stages. Below are the list of stages with simple explaination.


-   #### pre-build test 
    
    - This stage will perform test before building our project, I have used code quality testing job and unit testing job under this stage.
    
    - **Code Quality Job**
        
        - To perform this stage i have used `openjdk:12-alpine` docker image as base image to run commands in shell.
        - The Code Quality Job will perform `PMD Code Quality Test` and generates the reports.
        - All the reports stored under `/build/reports/pmd` using artifacts.
    
    - **Unit Testing Job**
        
        - To perform this stage i have used `openjdk:12-alpine` docker image as base image to run commands in shell.
        - To perform unit test i have simply used `Gradle` automation tool.
        - Test Reports will be stored in `/build/reports/tests` and Test Result will be stored under `build/test-results/test/*.xml` in XML format.

-   #### build 
    
    - This stage will actually build our project and store the build at the end of stage.

    - **Build Job**

        - To perform this stage i have used `openjdk:12-alpine` docker image as base image to run commands in shell.
        - First of all i have update app version, commit id and brach in `application.yaml` using sed commands and GitLab provided built-in CICD Variables. Through this we can track latest build version, commit number and commit branch.
        - `./gradlew build` will build the project and generate the Jar file. I have used Variable as a reference of jar file name.
        - Artifacts will store the jar file for next stages. 

-   #### test

    - Test stage contains smoke test which will perform health specific checks on project.

    - **Smoke Test Job**

        - To perform this stage i have used `openjdk:12-alpine` docker image as base image to run commands in shell.
        - To run smoke test i have installed the `curl command` using `before_script` gitlab cicd module. It will execute before main script module.
        - After that job will run the jar file and hit the `health api` which will gives us status about our applications health.

-   #### deploy

    - Deploy stage deploy our web application to the AWS Cloud.
    
    - **Deploy Job**
        
        - To perform this stage i have used `banst/awscli` docker image as base image to run aws cli commands in shell.
        - `entrypoint` is used to define entrypoint for gitlab runner to enter the docker container and attach runner to container, so the runner can run commands.
        - ``before_script` will install `jq` json parser and `curl`.
        - Then i have setup the default AWS Region using AWS CLI command and copied latest jar file from gitlab to s3 bucket. Here i have used variable which is pointing to the S3 Bucket name.
        - Now I have used AWS CLI command to create Elastic BeanStalk Application and update the ENV with latest version of application.
        - CNAME will give us the `LoadBalancer DNS ENDPOINT` for communicating with our application.
        - After 45 sec of deploying it will check health of our application and information such as version, branch and commit id of our application.

-   #### post-deploy test

    - Post-Deploy Test Stage will perform API Testing on our Application. I have created the `POSTMAN API Collection` in json file.
    
    - **API Testing Job**

        - To perform this stage i have used `vdespa/newman` docker image as base image to run postman collections in shell.
        - `entrypoint` is used to define entrypoint for gitlab runner to enter the docker container and attach runner to container, so the runner can run commands.
        - `newman run` command will execute all the API listed under `Cars API.postman_collection.json`. 
        - `Production.postman_environment.json` is env setup for our Postman.
        - The output will be stored in XML and HTML as well. XML reports will be stored in `/newman/report.xml`.

-   #### publish

    - Publish page is used to publish GitLab Pages.

    - **Pages Job**

    - This job will create public directory and copy the postman `report.html` under that directory as `index.html`.