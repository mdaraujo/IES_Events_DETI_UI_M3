/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hubert
 */
public class RolesResponse implements Serializable {

    private int code = 200;

    private String message = "OK";

    private boolean success = true;

    private List<PermissionResponse> response;

    public RolesResponse() {
        this.response = new ArrayList<PermissionResponse>();
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

    public List<PermissionResponse> getResponse() {
        return response;
    }

    public void setResponse(List<PermissionResponse> response) {
        this.response = response;
    }

}
