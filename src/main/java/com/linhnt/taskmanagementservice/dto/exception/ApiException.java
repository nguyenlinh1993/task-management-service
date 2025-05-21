package com.linhnt.taskmanagementservice.dto.exception;

import com.linhnt.taskmanagementservice.utils.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@SuppressWarnings("PMD")
public class ApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorObject error;

    private final Throwable cause;

    public ApiException(ErrorObject errorObject) {
        this(errorObject, null);
    }

    public ApiException(ErrorObject errorObject, Throwable cause) {
        super(errorObject == null || errorObject.getErrors() == null ? null : JsonUtil.toJson(errorObject.getErrors()));
        this.error = errorObject;
        this.cause = cause;
    }

    public static class Builder {
        private final Class<? extends ApiException> exceptionClass;
        private String errorCode;
        private ErrorType errorType = ErrorType.INTERNAL;
        private String[] params;
        private Throwable cause;

        public Builder(Class<? extends ApiException> exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        public static Builder badRequest(String errorCode, String... params) {
            return new Builder(ApiBadRequestException.class).errorCode(errorCode).params(params);
        }

        public static Builder unAuthorized(String errorCode, String... params) {
            return new Builder(ApiUnAuthorizedException.class).errorCode(errorCode).params(params);
        }

        public static Builder notFound(String errorCode, String... params) {
            return new Builder(ApiNotFoundException.class).errorCode(errorCode).params(params);
        }

        public static Builder forbidden(String errorCode, String... params) {
            return new Builder(ApiForbiddenException.class).errorCode(errorCode).params(params);
        }

        public static Builder conflict(String errorCode, String... params) {
            return new Builder(ApiConflictException.class).errorCode(errorCode).params(params);
        }

        public static Builder internalServerError(String errorCode, String... params) {
            return new Builder(ApiInternalServerErrorException.class).errorCode(errorCode).params(params);
        }

        public static Builder internalServerError(String errorCode, ErrorType errorType, String... params) {
            return new Builder(ApiInternalServerErrorException.class).errorCode(errorCode).errorType(errorType).params(params);
        }

        private Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        private Builder errorType(ErrorType errorType) {
            this.errorType = errorType;
            return this;
        }

        private Builder params(String... params) {
            this.params = params;
            return this;
        }

        public Builder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public ApiException build() {
            ErrorObject errorObject = new ErrorObject();
            errorObject.addError(errorCode, errorType, params);
            if (exceptionClass == ApiNotFoundException.class) {
                return new ApiNotFoundException(errorObject, cause);
            }
            if (exceptionClass == ApiUnAuthorizedException.class) {
                return new ApiUnAuthorizedException(errorObject, cause);
            }
            if (exceptionClass == ApiForbiddenException.class) {
                return new ApiForbiddenException(errorObject, cause);
            }
            if (exceptionClass == ApiConflictException.class) {
                return new ApiConflictException(errorObject, cause);
            }
            if (exceptionClass == ApiBadRequestException.class) {
                return new ApiBadRequestException(errorObject, cause);
            }
            if (exceptionClass == ApiInternalServerErrorException.class) {
                return new ApiInternalServerErrorException(errorObject, cause);
            }
            return new ApiException(errorObject, cause);
        }
    }
}
