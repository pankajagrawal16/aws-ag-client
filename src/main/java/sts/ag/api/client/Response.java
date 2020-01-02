package sts.ag.api.client;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.IOUtils;

import java.io.IOException;

class Response {
    private final HttpResponse httpResponse;
    private final String body;

    Response(final HttpResponse httpResponse) throws IOException {
        this.httpResponse = httpResponse;
        if (httpResponse.getContent() != null) {
            this.body = IOUtils.toString(httpResponse.getContent());
        } else {
            this.body = null;
        }
    }

    HttpResponse getHttpResponse() {
        return httpResponse;
    }

    String getBody() {
        return body;
    }
}