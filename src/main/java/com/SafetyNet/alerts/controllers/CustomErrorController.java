package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SafetyNet.alerts.models.Error;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/error")
@Api(tags = "Error API")
public class CustomErrorController extends AbstractErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    public String getErrorPath() {
        return "/error";
    }

    @GetMapping
    @ApiOperation("Handle custom error")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Success", response = Error.class),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Error> handleCustomError(HttpServletRequest request) {
        HttpStatus status = getResponseStatus(request);
        ErrorProperties errorProperties = getErrorProperties();

        Error error = new Error();
        error.setStatus(status.value());
        error.setError(status.getReasonPhrase());
        error.setMessage(errorProperties.getIncludeMessage());

        return new ResponseEntity<>(error, status);
    }

    private HttpStatus getResponseStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ignored) {
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ErrorProperties getErrorProperties() {
        // Obtain the ErrorProperties bean from the Spring context
        // You can autowire it if it's available as a bean

        // For example:
        // return applicationContext.getBean(ErrorProperties.class);
        return null;
    }

    public ErrorAttributes getErrorAttributes() {
        return errorAttributes;
    }

    public void setErrorAttributes(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }
}
