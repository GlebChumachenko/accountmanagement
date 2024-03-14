package com.accountmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationResponse {

    public enum Status{
        SUCCESS, FAILED
    }

    Status status;

    String message;

}
