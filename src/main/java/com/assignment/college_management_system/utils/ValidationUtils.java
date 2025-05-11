package com.assignment.college_management_system.utils;

import com.assignment.college_management_system.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    public void checkIsResourceExistOrThrow(Boolean isRecordExist, String message) {
        if (!isRecordExist) {
            throw new ResourceNotFoundException(message);
        }
    }
}
