package com.epic.demo.advisors;

import com.epic.demo.exception.NotfoundException;
import com.epic.demo.exception.ValidateException;
import com.epic.demo.util.StandradResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@CrossOrigin
public class AppWideExceptionHandle {

    @ExceptionHandler(Exception.class)
    public ResponseEntity HandleException(Exception e){
        return new ResponseEntity(new StandradResponse("500", "Error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotfoundException.class)
    public ResponseEntity HandleNotFoundException(NotfoundException e){
        return new ResponseEntity(new StandradResponse("400", "Error", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity HandleValidationException(ValidateException e){
        return new ResponseEntity(new StandradResponse("404", "Error", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
