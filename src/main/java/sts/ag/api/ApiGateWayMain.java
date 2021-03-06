package sts.ag.api;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import sts.ag.api.client.ApiGatewayClient;
import sts.ag.api.client.ApiGatewayException;
import sts.ag.api.model.ClientBuilder;
import sts.ag.api.model.Request;
import sts.ag.api.model.RequestBuilder;
import sts.ag.api.model.Response;

import java.net.URI;
import java.util.Map;

import static com.amazonaws.regions.Regions.US_EAST_1;

public class ApiGateWayMain {

    // NOTE: In production-like environments, the ACCESS_KEY and SECRET_KEY should be read from
    // a secure storage utility, and not be hardcoded in-line like this:
    private static final String ACCESS_KEY = ""; // e.g. "AKIAXNIETWOCQIIPDGEZ"
    private static final String SECRET_KEY = ""; // e.g. "6GNNft5N3+f2xIZ/dqPFbo7PxlWJ2+izaUPE4C1b"
    private static final String ENDPOINT = ""; // e.g. "https://8axkz5pcpe.execute-api.eu-west-1.amazonaws.com"
    private static final String ROLE_ARN = ""; // e.g. "arn:aws:iam::1234567890:role/your-role
    private static final String PATH = "";; // e.g. "/prod/api/v2/web/authenticate"
    private static final String SESSION_NAME = ""; // e.g. "User ABC"
    private static final String REGION = "eu-west-1"; // AWS region, e.g. "eu-west-1"

    public static void main(String[] args) {
        ApiGatewayClient client = apiGatewayClient(URI.create(ENDPOINT));

        try {

            Request request = new RequestBuilder()
                    .withHttpMethod(HttpMethodName.GET)
                    .withHeaders(Map.of("Content-Type", "application/json"))
                    .withResourcePath(PATH)
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
        Regions region = Regions.fromName(REGION);

        return new ClientBuilder()
                .withClientConfiguration(new ClientConfiguration())
                .withCredentials(new STSAssumeRoleSessionCredentialsProvider.Builder(ROLE_ARN, SESSION_NAME)
                        .withStsClient(stsClient(region))
                        .withRoleSessionDurationSeconds(3600)
                        .build())
                .withEndpoint(endpoint)
                .withRegion(region)
                .build();
    }

    private static AWSSecurityTokenService stsClient(Regions region) {
        return AWSSecurityTokenServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
                .withRegion(region)
                .build();
    }
}
