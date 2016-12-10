/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import entities.Evento;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Miguel
 */
@Named(value = "editEventView")
@SessionScoped
public class EditEventView implements Serializable {
    
    private Evento event;
    private MainView mv;
    
    @PostConstruct
    public void init() {
        System.out.println("PASSEI AQUI");
        mv = new MainView();
        event = new Evento();
        
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
	String nome = params.get("eventNome");
        
        event = mv.getEventByName(nome);
    }

    public Evento getEvent() {
        return event;
    }

    public void setEvent(Evento event) {
        this.event = event;
    }
    
    public String editEvent() {
        
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage("The Event "  + event.getNome() + " Is Saved Successfully"));
        return "";
    }
}
