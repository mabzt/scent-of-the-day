package com.maison.mabs.sotd.infrastructure.adapter.in.web.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders httpHeaders, HttpStatusCode httpStatusCode, WebRequest webRequest) {
		ProblemDetail problemDetail = exception.getBody();
		problemDetail.setProperty("errors",
				exception.getBindingResult()
					.getFieldErrors()
					.stream()
					.map(fieldError -> Map.of("field", fieldError.getField(), "message",
							Objects.requireNonNullElse(fieldError.getDefaultMessage(), "invalid")))
					.toList());
		return ResponseEntity.of(problemDetail).build();
	}

	@ExceptionHandler(SotdConflictException.class)
	public ResponseEntity<ProblemDetail> handleConflict(SotdConflictException exception) {
		var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
	}

}
