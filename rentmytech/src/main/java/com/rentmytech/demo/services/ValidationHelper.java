package com.rentmytech.demo.services;

import com.rentmytech.demo.models.ValidationError;

import java.util.List;

public interface ValidationHelper
{
    List<ValidationError> getConstraintViolation(Throwable cause);

    boolean isAuthorizedToMakeChange(String username);
}
