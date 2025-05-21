package com.linhnt.taskmanagementservice.utils;

import com.linhnt.taskmanagementservice.dto.core.ErrorDto;
import com.linhnt.taskmanagementservice.dto.exception.ApiException;
import com.linhnt.taskmanagementservice.dto.exception.ErrorDetail;
import com.linhnt.taskmanagementservice.dto.exception.ErrorObject;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.stream.Collectors;

public class ParseUtil {
    public static Set<String> parseToSetCode(MvcResult mvcResult) throws UnsupportedEncodingException {
        ErrorDto errorObject = JsonUtil.parseJson(mvcResult.getResponse().getContentAsString(), ErrorDto.class);
        return errorObject.getErrors()
                .stream()
                .map(ErrorDetail::getErrorCode)
                .collect(Collectors.toSet());
    }

    public static Set<String> parseToSetCode(ApiException miaException) {
        ErrorObject errorObject = miaException.getError();
        return errorObject.getErrors()
                .stream()
                .map(ErrorDetail::getErrorCode)
                .collect(Collectors.toSet());
    }
}
