package com.app.trans.exceptions.advice;//package com.unibuc.assig.FinalProject.exceptions.advice;
//
//import com.unibuc.assig.FinalProject.exceptions.notFoundExceptions.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.time.LocalDateTime;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler({AnObjectNotFound.class})
//    public ModelAndView handle(AnObjectNotFound e)
//    {
//        String error_msg = e.getMessage();
//        String error_time = LocalDateTime.now().toString();
//        ModelAndView model = new ModelAndView();
//        model.getModel().put("error_msg", error_msg);
//        model.getModel().put("error_time",error_time);
//        model.setViewName("error");
//        return model;
//    }
//
//
////    @ExceptionHandler({AnObjectNotFound.class})
////    public ResponseEntity<String> handle(AnObjectNotFound e)
////    {
////        return ResponseEntity
////                .status(HttpStatus.NOT_FOUND)
////                .body(e.getMessage() + " at " + LocalDateTime.now());
////    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> handle(MethodArgumentNotValidException e)
//    {
//        return ResponseEntity.badRequest()
//                .body("Invalid value: " + e.getFieldError().getRejectedValue() +
//                " for field " + e.getFieldError().getField() +
//                " with message "+ e.getFieldError().getDefaultMessage());
//
//    }
//
//}
