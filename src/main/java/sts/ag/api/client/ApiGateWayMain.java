package sts.ag.api.client;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;

import java.net.URI;
import java.util.Map;

import static com.amazonaws.regions.Regions.US_EAST_1;

public class ApiGateWayMain {

    private static final String ACCESS_KEY = "";
    private static final String SECRET_KEY = "";
    private static final String ENDPOINT = "https://yc9n5wd8hf.execute-api.us-east-1.amazonaws.com";
    private static final String ROLE_ARN = "arn:aws:iam::906126917321:role/accessApiGateway";

    public static void main(String[] args) {
        ApiGatewayClient client = apiGatewayClient(URI.create(ENDPOINT));

        try {

            Request request = new RequestBuilder()
                    .withHttpMethod(HttpMethodName.GET)
                    .withHeaders(Map.of("Content-Type", "application/json"))
                    .withResourcePath("/production/search/abv")
                    .build();

            Response response = client.execute(request);

            System.out.println("Response: " + response.getBody());
            System.out.println("Status: " + response.getHttpResponse().getStatusCode());

        } catch (ApiGatewayException e) {
            System.out.println(String.format("Exception with message %s and status code %s",
                    e.getMessage(), e.getStatusCode()));
        }
    }

    private static ApiGatewayClient apiGatewayClient(final URI endpoint) {
        return new ClientBuilder()
                .withClientConfiguration(new ClientConfiguration())
                .withCredentials(new STSAssumeRoleSessionCredentialsProvider.Builder(ROLE_ARN, "readable-session-name")
                        .withStsClient(stsClient())
                        .withRoleSessionDurationSeconds(3600)
                        .build())
                .withEndpoint(endpoint)
                .build();
    }

    private static AWSSecurityTokenService stsClient() {
        return AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
                .withRegion(US_EAST_1)
                .build();
    }
}
