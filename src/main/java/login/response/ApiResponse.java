/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.response;

import java.io.Serializable;

/**
 *
 * @author Hubert
 */
public class ApiResponse {

    private int code;

    private String message;

    private boolean success;

    public ApiResponse() {
        this.code = 200;
        this.message = "OK";
        this.success = true;
    }

    public ApiResponse(int code) {
        this.code = code;
        this.success = code == 200;
        switch (code) {
            case 200:
            case 201:
            case 202:
            case 203:
                this.message = "OK";
                break;
            case 400:
                this.message = "Bad request";
                break;
            case 401:
                this.message = "Unauthorized request. Access token is invalid.";
                break;
            case 403:
                this.message = "Access forbidden.";
                break;
            case 404:
                this.message = "Resource not found.";
                break;
            case 405:
                this.message = "Bad method request";
                break;
            case 500:
                this.message = "Internal error occured.";
                break;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
