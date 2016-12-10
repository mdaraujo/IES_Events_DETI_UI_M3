/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.google.common.hash.Hashing;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import login.request.LoginRequest;
import login.request.RegisterRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
@Named(value = "userLoginView")
@SessionScoped
public class UserLoginView implements Serializable {

    private LoginRequest loginRequest;
    
    private RegisterRequest registerRequest;

    private static final String BASE_URL = "http://deti-ies-6.ua.pt:8080/ies-2016-2017-g207/rest";

    public void loginBtnClick(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;
        boolean loggedIn;
        
        String url = BASE_URL + "/login";
        
        String jsonResponse = sendPOST(url, loginRequest.toJSON());

        System.out.println("Login: " + jsonResponse);

        JSONObject obj = new JSONObject(jsonResponse);
        int code = obj.getInt("code");
        String requestMessage = obj.getString("message");

        if (code == 200) {
            loggedIn = true;

            JSONObject jsonUser = (JSONObject) obj.get("response");
            String token = jsonUser.getString("token");
            String firstName = jsonUser.getString("firstname");
            String lastName = jsonUser.getString("lastname");

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", firstName + " " + lastName);

            if (facesContext.getExternalContext().getSessionMap().get("token") != null) {
                String oldToken = facesContext.getExternalContext().getSessionMap().get("token").toString();

                String logoutResponse = logout(oldToken);

                System.out.println("Logout: " + logoutResponse);

                facesContext.getExternalContext().invalidateSession();
            }

            facesContext.getExternalContext().getSessionMap().put("token", token);
            facesContext.getExternalContext().getSessionMap().put("user", loginRequest.getUsername()); // save current user id

        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", requestMessage);
        }

        facesContext.addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }
    
    public void logoutBtnClick() {
        FacesMessage message;
        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext.getExternalContext().getSessionMap().get("token") != null) {
            String oldToken = facesContext.getExternalContext().getSessionMap().get("token").toString();

            String logoutResponse = logout(oldToken);

            System.out.println("Logout: " + logoutResponse + " Token: " + oldToken);
            
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Goodbye", "See ya");

            facesContext.getExternalContext().invalidateSession();
        }
        else
        {
            System.out.println("Already logged out");
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Already logged out", "No active user session");
        }
        
        facesContext.addMessage(null, message);
        
        try {
            facesContext.getExternalContext().redirect("/IES_Events_DETI_UI_M3/");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void registerBtnClick(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message;
        boolean regSuccess;
         
        String url = BASE_URL + "/user/register";
        String jsonResponse = sendPOST(url, registerRequest.toJSON());

        System.out.println("Register: " + jsonResponse);

        JSONObject obj = new JSONObject(jsonResponse);
        int code = obj.getInt("code");
        String requestMessage = obj.getString("message");

        if (code == 200) {
            regSuccess = true;

            JSONObject jsonUser = (JSONObject) obj.get("user");
            int id = jsonUser.getInt("userId"); // Criar pessoa/user com este id na eventsDB, com o resto dos paramentros
            String firstName = jsonUser.getString("firstname");
            String lastName = jsonUser.getString("lastname");

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", firstName + " " + lastName);
            facesContext.addMessage(null, new FacesMessage("You can Log In now"));
        } else {
            regSuccess = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Register Error", requestMessage);
        }

        facesContext.addMessage(null, message);
        context.addCallbackParam("regSuccess", regSuccess);
    }

    public String sendPOST(String url, String body) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.addHeader("Auth-token", "52b5f98e-761e-42e7-9311-c5fc257f4f64");
            request.setEntity(params);
            
            System.out.println("Post Requset: " + body);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            return json;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public String logout(String token) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String url = BASE_URL + "/logout";
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

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    @PostConstruct
    public void init() {
        loginRequest = new LoginRequest();
        registerRequest = new RegisterRequest();
    }

    public RegisterRequest getRegisterRequest() {
        return registerRequest;
    }

    public void setRegisterRequest(RegisterRequest registerRequest) {
        this.registerRequest = registerRequest;
    }
}
