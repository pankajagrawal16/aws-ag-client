package sts.ag.api.model;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import sts.ag.api.client.ApiGatewayClient;

import java.net.URI;

public class ClientBuilder {
    private URI endpoint;
    private Regions region;
    private AWSCredentialsProvider credentials;
    private ClientConfiguration clientConfiguration;

    public ClientBuilder withEndpoint(URI endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public ClientBuilder withRegion(Regions region) {
        this.region = region;
        return this;
    }

    public ClientBuilder withClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
        return this;
    }

    public ClientBuilder withCredentials(AWSCredentialsProvider credentials) {
        this.credentials = credentials;
        return this;
    }

    public ApiGatewayClient build() {
        return new ApiGatewayClient(clientConfiguration, endpoint, credentials, region);
    }

}
