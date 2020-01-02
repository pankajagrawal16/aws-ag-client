package sts.ag.api.client;

import com.amazonaws.http.HttpMethodName;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

class Request {

    private final HttpMethodName httpMethod;
    private final String resourcePath;
    private final InputStream body;
    private final Map<String, String> headers;
    private final Map<String, List<String>> parameters;

    Request(HttpMethodName httpMethod,
            String resourcePath,
            InputStream body,
            Map<String, String> headers,
            Map<String, List<String>> parameters) {
        this.httpMethod = httpMethod;
        this.resourcePath = resourcePath;
        this.body = body;
        this.headers = headers;
        this.parameters = parameters;
    }

    HttpMethodName getHttpMethod() {
        return httpMethod;
    }

    String getResourcePath() {
        return resourcePath;
    }

    InputStream getBody() {
        return body;
    }

    Map<String, String> getHeaders() {
        return headers;
    }

    Map<String, List<String>> getParameters() {
        return parameters;
    }
}
