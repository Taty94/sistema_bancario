package com.pichincha.dm.crms.banking.infrastructure.exception;

import com.pichincha.common.domain.ProblemDetail;
import com.pichincha.dm.crms.banking.domain.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.pichincha.common.config.ConfigPropertiesErrorCatalog;
import com.pichincha.common.domain.ErrorDetail;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.pichincha.common.domain.MessageDefault.*;
import static com.pichincha.common.service.UtilServices.getAllErrorDetailByKey;
import static com.pichincha.common.service.UtilServices.getProblemDetail;
import static com.pichincha.dm.crms.banking.domain.util.Constants.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
        // This constructor is intentionally empty.
        // It is provided for potential future use or for framework requirements.
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e) {
        log.error(Constants.CODIGO_ERROR_DESCRIPCION_GENERICA, e);
        ProblemDetail problemDetail = null;
        problemDetail = processException(null, EXCEPCION_DEFECTO, false);
        problemDetail.getErrors().get(0).setMessage(e.getMessage());
        problemDetail.setResource(e.getClass().getName());
        return new ResponseEntity<>(problemDetail, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(BddSistemaBancarioException.class)
    public ResponseEntity<?> handleOtpException(WebRequest req, BddSistemaBancarioException ex) {
        log.error(Constants.CODIGO_ERROR_DESCRIPCION_GENERICA, ex);
        HttpStatus status = HttpStatus.NOT_FOUND;
        String error = NO_ENCONTRADO_EXCEPCION;
        ProblemDetail problemDetail = processException(req, error, false);
        problemDetail.getErrors().get(0).setMessage(ex.getMensaje());
        problemDetail.setType("ERROR");
        problemDetail.setResource(ex.getClass().getName());
        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleOtpException(WebRequest req, DataIntegrityViolationException ex) {
        log.error(Constants.CODIGO_ERROR_DESCRIPCION_GENERICA, ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = MALA_PETICION_EXCEPCION;
        ProblemDetail problemDetail = processException(req, error, false);
        problemDetail.getErrors().get(0).setMessage(ex.getMessage());
        problemDetail.setType("ERROR");
        problemDetail.setResource(ex.getClass().getName());
        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleOtpException(WebRequest req, IllegalArgumentException ex) {
        log.error(Constants.CODIGO_ERROR_DESCRIPCION_GENERICA, ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String error = MALA_PETICION_EXCEPCION;
        ProblemDetail problemDetail = processException(req, error, false);
        problemDetail.getErrors().get(0).setMessage(ex.getMessage());
        problemDetail.setType("ERROR");
        problemDetail.setResource(ex.getClass().getName());
        return new ResponseEntity<>(problemDetail, status);
    }


    public ProblemDetail processException(WebRequest req, String keyExceptionName, Boolean loadDefaultErrorList) {
        ConfigPropertiesErrorCatalog configProperties = new ConfigPropertiesErrorCatalog();

        Map<String, Object> data = configProperties.getValuesErrorCatalogByKey(keyExceptionName);
        ProblemDetail problemDetail = loadDefaultProblem(data);
        List<ErrorDetail> allErrorDetailByKey = getAllErrorDetailByKey(data);
        problemDetail.setErrors(allErrorDetailByKey);

        if (req != null) {
            ServletWebRequest servletWebRequest = (ServletWebRequest) req;
            problemDetail.setInstance(URI.create(servletWebRequest.getRequest().getRequestURI()).toString());
            problemDetail.setType(URI.create(servletWebRequest.getRequest().getRequestURL().toString()).toString());
        }

        if (Boolean.TRUE.equals(loadDefaultErrorList)) {
            problemDetail.setErrors(allErrorDetailByKey);
        }

        return problemDetail;
    }

    private ProblemDetail loadDefaultProblem(Map<String, Object> data) {
        return getProblemDetail(
                data.get(TITLE).toString(),
                data.get(DETAIL).toString(),
                data.get(INSTANCE).toString(),
                data.get(TYPE).toString(),
                data.get(RESOURCE).toString(),
                data.get(COMPONENT).toString(),
                data.get(BACKEND).toString());
    }
}
