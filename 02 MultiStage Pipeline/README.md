# Multistage Pipeline

- This is simple pipeline to understand how we can execute different different block of code.

- This is simple yet great way to understand how the gitlab stages are useful for Building, Testing and Developing CICD Pipeline.

- We can define multiple stages under the `stages` section and name of the stages can be anything.

- If you defined 3 stages in pipeline than you must have to utilize all those three stages, otherwise it will show errors.

- Also we can define infinite number of jobs under one single stage.

- Here i have defined two stages.

![Stages defined in Pipeline](https://github.com/imdhruv99/GitLab-CICD/blob/main/02%20MultiStage%20Pipeline/Images/01.png)

- First stage is `build` which will simply create one text file and add simple words to it.

- At the end of the stage i have used artifacts. Artifacts works as bridge between two different stages.

- Using Artifacts we can store our result of the stage into directory. Job artifacts created by GitLab Runner are uploaded to GitLab and are downloadable as a single archive using the GitLab UI or the GitLab API. Through the artifacts we can use first stage result into second stage result, if we do not use artifacts here than it will show no such file type of errors in second stage.

![First Stage output](https://github.com/imdhruv99/GitLab-CICD/blob/main/02%20MultiStage%20Pipeline/Images/02.png)

![Job artifacts](https://github.com/imdhruv99/GitLab-CICD/blob/main/02%20MultiStage%20Pipeline/Images/04.png)

- We can download generated artifacts from job artifacts panel which is located righthand side as you can see in above image

- Second stage is `test` which will cat the file and give us the output.

![Second stage output](https://github.com/imdhruv99/GitLab-CICD/blob/main/02%20MultiStage%20Pipeline/Images/03.png)