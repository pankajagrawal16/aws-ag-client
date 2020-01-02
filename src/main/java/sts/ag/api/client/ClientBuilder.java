package sts.ag.api.client;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;

import java.net.URI;

class ClientBuilder {
    private URI endpoint;
    private AWSCredentialsProvider credentials;
    private ClientConfiguration clientConfiguration;

    ClientBuilder withEndpoint(URI endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    ClientBuilder withClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
        return this;
    }

    ClientBuilder withCredentials(AWSCredentialsProvider credentials) {
        this.credentials = credentials;
        return this;
    }

    ApiGatewayClient build() {
        return new ApiGatewayClient(clientConfiguration, endpoint, credentials);
    }

}
