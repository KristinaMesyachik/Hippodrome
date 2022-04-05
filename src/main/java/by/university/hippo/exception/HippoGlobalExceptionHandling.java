package by.university.hippo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class HippoGlobalExceptionHandling {
    @ExceptionHandler
    public ResponseEntity<HippoIncorrectData> handleException(NoSuchHippoException exception) {
        HippoIncorrectData restaurantIncorrectData= new HippoIncorrectData();
        restaurantIncorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(restaurantIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<HippoIncorrectData> handleException(Exception exception) {
        HippoIncorrectData employeeIncorrectData = new HippoIncorrectData();
        employeeIncorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(employeeIncorrectData, HttpStatus.BAD_REQUEST);
    }
}
