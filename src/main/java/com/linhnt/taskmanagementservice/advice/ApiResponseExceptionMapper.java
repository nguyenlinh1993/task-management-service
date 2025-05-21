package com.linhnt.taskmanagementservice.advice;

import com.linhnt.taskmanagementservice.dto.core.ErrorDto;
import com.linhnt.taskmanagementservice.dto.exception.ApiBadRequestException;
import com.linhnt.taskmanagementservice.dto.exception.ApiConflictException;
import com.linhnt.taskmanagementservice.dto.exception.ApiException;
import com.linhnt.taskmanagementservice.dto.exception.ApiForbiddenException;
import com.linhnt.taskmanagementservice.dto.exception.ApiNotFoundException;
import com.linhnt.taskmanagementservice.dto.exception.ApiUnAuthorizedException;
import com.linhnt.taskmanagementservice.dto.exception.ErrorObject;
import com.linhnt.taskmanagementservice.utils.MessageUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@ControllerAdvice
public class ApiResponseExceptionMapper {
    @Value("${spring.profiles.active}")
    private String profile;

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ErrorDto> handleAppException(ApiException e, HandlerMethod handlerMethod) {
        HttpStatus status;
        if (e instanceof ApiNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof ApiConflictException) {
            status = HttpStatus.CONFLICT;
        } else if (e instanceof ApiBadRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof ApiUnAuthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (e instanceof ApiForbiddenException) {
            status = HttpStatus.FORBIDDEN;
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorObject errorResponse = e.getError();

        logError(handlerMethod, e);
        addDebugInfo(errorResponse, e);

        ErrorDto response = new ErrorDto();
        response.setHasError(true);
        response.setErrors(errorResponse.getErrors());
        response.setDebugInfo(errorResponse.getDebugInfo());
        response.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public final ResponseEntity<ErrorDto> handleValidationException(HandlerMethodValidationException e, HandlerMethod handlerMethod) {
        ErrorObject errorResponse = new ErrorObject();
        e.getAllErrors().forEach(violation -> {
            String errorMessage = violation.getDefaultMessage();
            errorResponse.addError(errorMessage);
        });

        logError(handlerMethod, e);
        addDebugInfo(errorResponse, e);

        ErrorDto response = new ErrorDto();
        response.setHasError(true);
        response.setErrors(errorResponse.getErrors());
        response.setDebugInfo(errorResponse.getDebugInfo());
        response.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorDto> handleValidationException(ConstraintViolationException e, HandlerMethod handlerMethod) {
        ErrorObject errorResponse = new ErrorObject();
        e.getConstraintViolations().forEach(violation -> {
            String errorMessage = violation.getMessage();
            errorResponse.addError(errorMessage);
        });

        logError(handlerMethod, e);
        addDebugInfo(errorResponse, e);

        ErrorDto response = new ErrorDto();
        response.setHasError(true);
        response.setErrors(errorResponse.getErrors());
        response.setDebugInfo(errorResponse.getDebugInfo());
        response.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException e, HandlerMethod handlerMethod) {
        ErrorObject errorResponse = new ErrorObject();
        e.getAllErrors().forEach(objectError -> {
            String msg = objectError.getDefaultMessage();
            if (MessageUtil.containsKey(msg)) {
                errorResponse.addError(msg);
            }
        });

        logError(handlerMethod, e);
        addDebugInfo(errorResponse, e);

        ErrorDto response = new ErrorDto();
        response.setHasError(true);
        response.setErrors(errorResponse.getErrors());
        response.setDebugInfo(errorResponse.getDebugInfo());
        response.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDto> handleOtherException(Exception e, HandlerMethod handlerMethod) {
        ErrorObject errorResponse = new ErrorObject();
        errorResponse.addError("30000001.500_internal_server_error");

        logError(handlerMethod, e);
        addDebugInfo(errorResponse, e);

        ErrorDto response = new ErrorDto();
        response.setHasError(true);
        response.setErrors(errorResponse.getErrors());
        response.setDebugInfo(errorResponse.getDebugInfo());
        response.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void addDebugInfo(ErrorObject errorResponse, Exception e) {
        if (!"prod".equals(profile)) {
            if (e.getCause() != null) {
                errorResponse.setDebugInfo(e.getCause().getStackTrace());
            } else {
                errorResponse.setDebugInfo(e.getStackTrace());
            }
        }
    }

    private void logError(HandlerMethod handlerMethod, Exception e) {
        String apiName = getApiName(handlerMethod);
        if (e instanceof ApiException me) {
            log.error(String.format("FROM API: %s", apiName), me.getCause() == null ? e : me.getCause());
        } else {
            log.error(String.format("FROM API: %s", apiName), e);
        }
    }

    private String getApiName(HandlerMethod handlerMethod) {
        GetMapping getMapping = handlerMethod.getMethodAnnotation(GetMapping.class);
        if (getMapping != null) {
            return getMapping.name();
        }
        PostMapping postMapping = handlerMethod.getMethodAnnotation(PostMapping.class);
        if (postMapping != null) {
            return postMapping.name();
        }
        PutMapping putMapping = handlerMethod.getMethodAnnotation(PutMapping.class);
        if (putMapping != null) {
            return putMapping.name();
        }
        DeleteMapping deleteMapping = handlerMethod.getMethodAnnotation(DeleteMapping.class);
        if (deleteMapping != null) {
            return deleteMapping.name();
        }
        PatchMapping patchMapping = handlerMethod.getMethodAnnotation(PatchMapping.class);
        if (patchMapping != null) {
            return patchMapping.name();
        }
        return "Unknown API";
    }
}
