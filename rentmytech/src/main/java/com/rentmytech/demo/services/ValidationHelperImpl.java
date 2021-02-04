package com.rentmytech.demo.services;

import com.rentmytech.demo.exceptions.ResourceNotFoundException;
import com.rentmytech.demo.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service(value = "validationHelper")
public class ValidationHelperImpl implements ValidationHelper
{
    public List<ValidationError> getConstraintViolation(Throwable cause)
    {
        while((cause != null) && !(cause instanceof ConstraintViolationException || cause instanceof MethodArgumentNotValidException))
        {
            System.out.println(cause.getClass().toString());
            cause = cause.getCause();
        }
        List<ValidationError> listValidationErrors = new ArrayList<>();
        {
            if (cause != null)
            {
                if(cause instanceof ConstraintViolationException)
                {
                    ConstraintViolationException exception = (ConstraintViolationException) cause;
                    ValidationError newError = new ValidationError();
                    newError.setCode(exception.getMessage());
                    newError.setCode(exception.getConstraintName());
                    listValidationErrors.add(newError);
                } else
                {
                    if (cause instanceof MethodArgumentNotValidException)
                    {
                        MethodArgumentNotValidException exception = (MethodArgumentNotValidException) cause;
                        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
                        for(FieldError error : fieldErrors)
                        {
                            ValidationError newError = new ValidationError();
                            newError.setCode(error.getField());
                            newError.setMessage(error.getDefaultMessage());
                            listValidationErrors.add(newError);
                        }
                    } else
                    {
                        System.out.println("Error in producing constraint violations exceptions. " +
                                "If we see this in the console a major logic error has occurred in the " +
                                "helperfunction.getConstraintViolation method that we should investigate. " +
                                "Note the application will keep running as this only affects exception reporting!");
                    }
                }
            }
            return listValidationErrors;
        }
    }
    @Override
    public boolean isAuthorizedToMakeChange(String username)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(username.equalsIgnoreCase(authentication.getName().toLowerCase()) || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
        {
            return true;
        } else
        {
            throw new ResourceNotFoundException(authentication.getName() + " you are not authorized to make changes");
        }
    }
}
