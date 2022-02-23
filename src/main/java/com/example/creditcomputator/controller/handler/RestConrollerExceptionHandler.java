package com.example.creditcomputator.controller.handler;

import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.creditcomputator.dto.error.ErrorResponse;


@RestControllerAdvice
public class RestConrollerExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
		return new ErrorResponse("Credit Computator Rest API, Entity has not been found.",
						e.getMessage());
	}

	@ExceptionHandler(EntityExistsException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleEntityExistsException(EntityExistsException e) {
		return new ErrorResponse("Credit Computator Rest API, Entity already exists.",
						e.getMessage());
	}

	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		return new ErrorResponse("Credit Computator Rest API, Data integrity violation.",
				e.getMessage());
	}
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
		String message = e.getConstraintViolations()
		 .stream()
		 .map(ConstraintViolation::getMessage)
		 .collect(Collectors.toList())
		 .toString();
		return new ErrorResponse("Constraint violation.", message);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String message = e.getAllErrors()
				.stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.toList())
				.toString();
		return new ErrorResponse("Argument validation failed.", message);
	}
	
	
//	@ExceptionHandler(RuntimeException.class)
//	@ResponseStatus(HttpStatus.BAD_GATEWAY)
//	public ErrorResponse handleRuntimeException(RuntimeException e) {
//		e.printStackTrace();
//		return new ErrorResponse("Application", e.getMessage());
//	}
	
	
}
