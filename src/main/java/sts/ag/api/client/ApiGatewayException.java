package sts.ag.api.client;

import com.amazonaws.AmazonServiceException;

public class ApiGatewayException extends AmazonServiceException {
    ApiGatewayException(String errorMessage) {
        super(errorMessage);
    }
}