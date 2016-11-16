/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import entities.Utilizador;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Miguel
 */
@Named(value = "userLoginView")
@SessionScoped
public class UserLoginView implements Serializable {

    private String username;
    private String password;
    
    MainView mainView;
    
    public UserLoginView() {
        mainView = new MainView();
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
   
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
        
        if(mainView.validUser(username, password)) {
                loggedIn = true;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
                
                FacesContext facesContext = FacesContext.getCurrentInstance();
                
                if(facesContext.getExternalContext().getSessionMap().get("user") != null)
                    facesContext.getExternalContext().invalidateSession();
                 
                Utilizador current = mainView.getUserByName(username);
                facesContext.getExternalContext().getSessionMap().put("user", current);
                
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }   
    
}
