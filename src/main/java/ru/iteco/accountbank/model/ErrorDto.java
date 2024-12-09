package ru.iteco.accountbank.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    public static String NOT_FOUND = "The bank book isn't present in system";
    public static String PARAMETER_NOT_PASSED = "The parameter haven't passed in the request";

    private String message;
    private int code;

}
