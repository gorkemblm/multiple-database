package com.gorkem.demoPLPostgres.core.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomLogger {

    private int httpStatusCode;
    private String statusMessage;
    private String exceptionMessage;
    private Object object;

    //with exception
    public CustomLogger(int httpStatusCode, String statusMessage, String exceptionMessage) {
        this.statusMessage = statusMessage;
        this.httpStatusCode = httpStatusCode;
        this.exceptionMessage = exceptionMessage;
    }
    public CustomLogger(int httpStatusCode, String statusMessage, Object object) {
        this.statusMessage = statusMessage;
        this.httpStatusCode = httpStatusCode;
        this.object = object;
    }
}
