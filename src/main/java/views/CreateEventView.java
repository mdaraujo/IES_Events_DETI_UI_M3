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
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Miguel
 */
@Named(value = "createView")
@RequestScoped
public class CreateEventView implements Serializable {

    private Evento event;

    @PostConstruct
    public void init() {
        this.event = new Evento();
    }

    public Evento getEvent() {
        return event;
    }

    public void setEvent(Evento event) {
        this.event = event;
    }

    public String createEvent() {

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("The Event " + event.getNome() + " Is Registered Successfully"));
        return "";
    }
}
