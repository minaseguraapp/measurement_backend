## Measurement Java Lambdas

## Description

## Requirements

- [AWS CLI](https://aws.amazon.com/cli/)
- [AWS SAM](https://aws.amazon.com/serverless/sam/)
- Java:
    - v17
- Maven

## Deployment

Deploy the application to your AWS account using [AWS SAM](https://aws.amazon.com/serverless/sam/).

```bash
mvn clean package
sam deploy -g
```
SAM will create an output of the API Gateway endpoint URL for future use in our load tests.
Make sure the app name used here matches with the `STACK_NAME` present under `load-test/run-load-test.sh`
