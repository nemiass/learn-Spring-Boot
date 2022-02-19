package com.nemias.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// Clase handler de Excepciones, este interceptará las excepciones que lanzemos, le indicamos que es un
// @RestController ya que este retorna un ResponseEntity, y sobre todo le anteponemos el decorador anotación
// @ControllerAdvice, nos sirve para indicar que si sucede una excepcion en todo el proyecto, esta clase
// lo va interceptar y retornará nuestras excepciones que programemos
@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    // decorador el cual le indicamos la clase de excepcion que va interceptar
    @ExceptionHandler(ModelNotFoundException.class)
    // metodo que interceptará las excepciones ModelNotFoundException
    public final ResponseEntity<ExceptionResponse> manejarModeloExcepciones(ModelNotFoundException ex,
            WebRequest request)
    {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    // Si ocurre otro error que nosotros no manejemos, entonces tambien manejaremos a esos errores, que puede
    // ser cualquier Exception
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> manejarTodasExcepciones(ModelNotFoundException ex,
            WebRequest request)
    {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    // sobreescribiendo un metodo de nuestro padre, sobreescribiremos un error, mas que todo cuando
    // enviamos argumentos no validos en los requests
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        // si queremos jalar todos lo errores, nos devolverá una lista con todos los errroes
        // ex.getBindingResult().getAllErrors();
        String msgException = "validacion fallida";
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), msgException, request.getDescription(false));
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
