package sts.ag.api.client;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.http.*;
import com.amazonaws.internal.auth.DefaultSignerProvider;
import com.amazonaws.protocol.json.JsonOperationMetadata;
import com.amazonaws.protocol.json.SdkStructuredPlainJsonFactory;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.fasterxml.jackson.databind.JsonNode;
import sts.ag.api.model.Request;
import sts.ag.api.model.Response;

import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ApiGatewayClient extends AmazonWebServiceClient {

    private static final String API_GATEWAY_SERVICE_NAME = "execute-api";

    private final JsonResponseHandler<Response> responseHandler;
    private final HttpResponseHandler<AmazonServiceException> errorResponseHandler;
    private final AWSCredentialsProvider credentials;
    private final AWS4Signer signer;

    public ApiGatewayClient(final ClientConfiguration clientConfiguration,
                     final URI endpoint,
                     final AWSCredentialsProvider credentials) {
        super(clientConfiguration);
        this.credentials = credentials;
        this.signer = new AWS4Signer();
        this.signer.setServiceName(API_GATEWAY_SERVICE_NAME);
        this.endpoint = endpoint;

        final JsonOperationMetadata metadata = new JsonOperationMetadata()
                .withHasStreamingSuccessResponse(false)
                .withPayloadJson(false);

        final Unmarshaller<Response, JsonUnmarshallerContext> responseUnmarshaller = in -> new Response(in.getHttpResponse());

        this.responseHandler = SdkStructuredPlainJsonFactory.SDK_JSON_FACTORY.createResponseHandler(metadata, responseUnmarshaller);

        JsonErrorUnmarshaller defaultErrorUnmarshaller = new JsonErrorUnmarshaller(ApiGatewayException.class, null) {
            @Override
            public AmazonServiceException unmarshall(JsonNode jsonContent) {
                return new ApiGatewayException(jsonContent.toString());
            }
        };

        this.errorResponseHandler = SdkStructuredPlainJsonFactory.SDK_JSON_FACTORY.createErrorResponseHandler(
                Collections.singletonList(defaultErrorUnmarshaller), null);
    }

    public Response execute(final Request request) {
        return execute(request.getHttpMethod(), request.getResourcePath(), request.getHeaders(), request.getParameters(), request.getBody());
    }

    private Response execute(final HttpMethodName method,
                             final String resourcePath,
                             final Map<String, String> headers,
                             final Map<String, List<String>> parameters,
                             final InputStream content) {
        final ExecutionContext executionContext = buildExecutionContext();

        DefaultRequest request = new DefaultRequest(API_GATEWAY_SERVICE_NAME);
        request.setHttpMethod(method);
        request.setContent(content);
        request.setEndpoint(this.endpoint);
        request.setResourcePath(resourcePath);
        request.setHeaders(headers);

        if (parameters != null) {
            request.setParameters(parameters);
        }

        AmazonHttpClient.RequestExecutionBuilder builder = this.client.requestExecutionBuilder();

        return builder.errorResponseHandler(errorResponseHandler)
                .executionContext(executionContext)
                .request(request)
                .execute(responseHandler)
                .getAwsResponse()
                .getResult();
    }

    private ExecutionContext buildExecutionContext() {
        final ExecutionContext executionContext = ExecutionContext.builder()
                .withSignerProvider(new DefaultSignerProvider(this, signer))
                .build();

        executionContext.setCredentialsProvider(credentials);
        return executionContext;
    }

    @Override
    protected String getServiceNameIntern() {
        return API_GATEWAY_SERVICE_NAME;
    }
}
