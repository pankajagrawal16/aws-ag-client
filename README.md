# aws-ag-client

This Java repo demostrates how to make IAM authenticated call to AWS api gateway endpoint. It is setup as a maven project currently and use AWS java SDK 
under the hood to provide a simple warpper classes to make these calls. 

# How to use?

- Configure Application gateway client as shown below in code snippet:

  <pre>
  
  
       AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(<b><i>ACCESS_KEY</i></b>, <b><i>SECRET_KEY</b></i>)))
                    .withRegion(US_EAST_1)
                    .build();

       ApiGatewayClient client = new ClientBuilder()
                  .withClientConfiguration(new ClientConfiguration())
                  .withCredentials(new STSAssumeRoleSessionCredentialsProvider.Builder(<b><i>ROLE_ARN</b></i>, "readable-session-name")
                          .withStsClient(stsClient)
                          .withRoleSessionDurationSeconds(3600)
                          .build())
                  .withEndpoint(<b><i>ENDPOINT</b></i>)
                  .build();
                
   </pre>
   
  - ACCESS_KEY: Access key of user account on which IAM role will be assumed.
  - SECRET_KEY: Secret key of user account on which IAM role will be assumed.
  - ROLE_ARN: ARN of role that is set up to access API gateway. Ex: arn:aws:iam::<accountnumber>:role/roleName
  - ENDPOINT: Api gateway endpoint. Ex: https://hash.execute-api.us-east-1.amazonaws.com
  
  
- Configure Request object for API call

  <pre>
  
      Request request = new RequestBuilder()
                      .withHttpMethod(HttpMethodName.GET)
                      .withBody("")
                      .withHeaders(Map.of("Content-Type", "application/json"))
                      .withResourcePath(<b><i>PATH</b></i>)
                      .build();

  </pre>
  
  - PATH: Path to the api call request. This will be path of API after the specified root ENDPOINT.
  
  
- Execute configured request with API gateway client object as shown below:

  <pre>
  
        try {

            Response response = client.execute(request);

        } catch (ApiGatewayException e) {
            // Handle exception as needed!
        }
      
  </pre>
 
   - In case of non 2XX response from the API call `ApiGatewayException` is thrown. Clients should handle this exception as per the needed use case.
   
   
- Sample code as described above also resides in main file [here](https://github.com/pankajagrawal16/aws-ag-client/blob/master/src/main/java/sts/ag/api/ApiGateWayMain.java)
