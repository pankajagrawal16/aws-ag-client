package sts.ag.api.model;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.IOUtils;

import java.io.IOException;

public class Response {
    private final HttpResponse httpResponse;
    private final String body;

    public Response(final HttpResponse httpResponse) throws IOException {
        this.httpResponse = httpResponse;
        if (httpResponse.getContent() != null) {
            this.body = IOUtils.toString(httpResponse.getContent());
        } else {
            this.body = null;
        }
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public String getBody() {
        return body;
    }
}