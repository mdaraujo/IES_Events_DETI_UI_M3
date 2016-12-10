/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import login.request.ChangePassRequest;
import login.request.CurrentUserResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Miguel
 */
@Named(value = "accountBean")
@SessionScoped
public class accountBean implements Serializable {

    private static final String BASE_URL = "http://deti-ies-6.ua.pt:8080/ies-2016-2017-g207/rest";

    private CurrentUserResponse currentUser;

    private ChangePassRequest passRequest;

    public String getCurrentUser(String token) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String url = BASE_URL + "/info";
            HttpGet request = new HttpGet(url);
            request.addHeader("Auth-token", "52b5f98e-761e-42e7-9311-c5fc257f4f64");
            request.addHeader("token", token);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            return json;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @PostConstruct
    public void init() {

        passRequest = new ChangePassRequest();

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext.getExternalContext().getSessionMap().get("token") != null) {
            String token = facesContext.getExternalContext().getSessionMap().get("token").toString();

            String responseJSON = getCurrentUser(token);
            JSONObject responseObj = new JSONObject(responseJSON);
            JSONObject currentUserJSON = (JSONObject) responseObj.get("response");

            System.out.println("Current User: " + currentUserJSON.toString());

            currentUser = new CurrentUserResponse(currentUserJSON.toString());
        } else {
            try {
                facesContext.getExternalContext().redirect("/IES_Events_DETI_UI_M3/");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void saveBtnClick(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;
        boolean saveSuccess;

        String token = facesContext.getExternalContext().getSessionMap().get("token").toString();
        String url = BASE_URL + "/user/edit/" + currentUser.getUserId();
        String jsonResponse = sendPUT(url, currentUser.toEditUserJSONRequset(), token);

        System.out.println("Save: " + jsonResponse);

        JSONObject obj = new JSONObject(jsonResponse);
        int code = obj.getInt("code");
        String requestMessage = obj.getString("message");

        if (code == 200) {
            saveSuccess = true;

            message = new FacesMessage("Edit Successfuly");
        } else {
            saveSuccess = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Edit Error", requestMessage);
        }

        facesContext.addMessage(null, message);
        context.addCallbackParam("saveSuccess", saveSuccess);
    }

    public void deleteBtnClick(ActionEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;

        String token = facesContext.getExternalContext().getSessionMap().get("token").toString();
        String url = BASE_URL + "/user/delete/" + currentUser.getUserId();
        String jsonResponse = sendDELETE(url, token);

        System.out.println("Delete Response: " + jsonResponse);

        JSONObject obj = new JSONObject(jsonResponse);
        int code = obj.getInt("code");
        String requestMessage = obj.getString("message");

        if (code == 200) {
            message = new FacesMessage("Delete Successfuly");
            facesContext.addMessage(null, message);

            try {
                facesContext.getExternalContext().redirect("/IES_Events_DETI_UI_M3/");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Delete Error", requestMessage);
            facesContext.addMessage(null, message);
        }
    }

    public void passBtnClick(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;
        boolean success;

        String token = facesContext.getExternalContext().getSessionMap().get("token").toString();
        String url = BASE_URL + "/user/edit/" + currentUser.getUserId();
        String jsonResponse = sendPUT(url, passRequest.toJSON(), token);

        System.out.println("Change Pass Response: " + jsonResponse);

        JSONObject obj = new JSONObject(jsonResponse);
        int code = obj.getInt("code");
        String requestMessage = obj.getString("message");

        if (code == 200) {
            success = true;

            message = new FacesMessage("Changed Password Successfuly");
        } else {
            success = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Change Password Error", requestMessage);
        }

        facesContext.addMessage(null, message);
        context.addCallbackParam("success", success);
    }

    public String sendPUT(String url, String body, String token) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPut request = new HttpPut(url);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.addHeader("Auth-token", "52b5f98e-761e-42e7-9311-c5fc257f4f64");
            request.addHeader("token", token);
            request.setEntity(params);
            System.out.println("PUT Request: " + body);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            return json;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String sendDELETE(String url, String token) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpDelete request = new HttpDelete(url);
            request.addHeader("Auth-token", "52b5f98e-761e-42e7-9311-c5fc257f4f64");
            request.addHeader("token", token);
            HttpResponse result = httpClient.execute(request);
            System.out.println("DELETE Request URL: " + url);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            return json;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return "Exception found";
        }
    }

    public CurrentUserResponse getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CurrentUserResponse currentUser) {
        this.currentUser = currentUser;
    }

    public ChangePassRequest getPassRequest() {
        return passRequest;
    }

    public void setPassRequest(ChangePassRequest passRequest) {
        this.passRequest = passRequest;
    }
}
