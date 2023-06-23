package com.SafetyNet.alerts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SafetyNet.alerts.DTO.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController extends AbstractErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public ResponseEntity<ErrorDTO> handleCustomError(HttpServletRequest request) {
        HttpStatus status = getResponseStatus(request);
        ErrorProperties errorProperties = getErrorProperties();

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(status.value());
        errorDTO.setError(status.getReasonPhrase());
        errorDTO.setMessage(errorProperties.getIncludeMessage());

        return new ResponseEntity<>(errorDTO, status);
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
		return null;
        // Obtain the ErrorProperties bean from the Spring context
        // You can autowire it if it's available as a bean

        // For example:
        // return applicationContext.getBean(ErrorProperties.class);
    }

	public ErrorAttributes getErrorAttributes() {
		return errorAttributes;
	}

	public void setErrorAttributes(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}
}
