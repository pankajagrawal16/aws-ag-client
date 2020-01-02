package sts.ag.api.client;

import com.amazonaws.http.HttpMethodName;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class RequestBuilder {
    private HttpMethodName httpMethod;
    private String resourcePath;
    private InputStream body;
    private Map<String, String> headers;
    private Map<String, List<String>> parameters;

    RequestBuilder withHttpMethod(HttpMethodName name) {
        httpMethod = name;
        return this;
    }

    RequestBuilder withResourcePath(String path) {
        resourcePath = path;
        return this;
    }

    public RequestBuilder withBody(InputStream content) {
        this.body = content;
        return this;
    }

    RequestBuilder withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public RequestBuilder withParameters(Map<String, List<String>> parameters) {
        this.parameters = parameters;
        return this;
    }

    Request build() {
        return new Request(httpMethod, resourcePath, body, headers, parameters);
    }
}
