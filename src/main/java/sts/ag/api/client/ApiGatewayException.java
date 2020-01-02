package sts.ag.api.client;

import com.amazonaws.AmazonServiceException;

class ApiGatewayException extends AmazonServiceException {
    ApiGatewayException(String errorMessage) {
        super(errorMessage);
    }
}