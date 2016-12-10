/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import entities.Evento;
import entities.Utilizador;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Miguel
 */
@Named(value = "mainView")
@SessionScoped
public class MainView implements Serializable {

    private List<Evento> events;
    private List<Utilizador> users;
    
    private Utilizador currentUser;
    
    private Evento selectedEvent;
    private boolean selectedEventEditable;
    
    private String searchName;
    
    public MainView()
    {
        events = new ArrayList<>();
        users = new ArrayList<>();
        
        users.add(new Utilizador("eventsAdmin"));
        users.add(new Utilizador("miguel"));
        users.add(new Utilizador("fabio"));
        users.add(new Utilizador("joao"));
        events.add(new Evento("Teste ASD", "Descriçao Teste da cadeira de ASD", Date.valueOf("2014-10-10"), "Sala 123", "Exam", users.get(0)));
        events.add(new Evento("Teste UIO", "Descriçao Teste da cadeira de UIO", Date.valueOf("2013-10-10"), "Sala 153", "Exam", users.get(1)));
        events.add(new Evento("Palestra sobre Hacking", "Palestra sobre Hacking", Date.valueOf("2012-10-10"), "Sala 497", "Presentation", users.get(3)));
        events.add(new Evento("Teste TYU", "Descriçao Teste da cadeira de TYU", Date.valueOf("2017-10-10"), "Sala 123", "Exam", users.get(2)));
        events.add(new Evento("Apresentação XPTO", "Apresentaçao sobre XPTO", Date.valueOf("2017-10-10"), "Sala 123", "Presentation", users.get(2)));
        events.add(new Evento("Teste ERT", "Descriçao Teste da cadeira de ERT", Date.valueOf("2015-10-10"), "Sala 385", "Exam", users.get(0)));
        events.add(new Evento("Teste SDF", "Descriçao Teste da cadeira de SDF", Date.valueOf("2017-10-10"), "Sala 274", "Exam", users.get(3)));
        events.add(new Evento("Teste BNM", "Descriçao Teste da cadeira de BNM", Date.valueOf("2012-10-10"), "Sala 497", "Exam", users.get(1)));
        events.add(new Evento("Palestra sobre Data Mining", "Palestra sobre Data Mining", Date.valueOf("2012-10-10"), "Sala 497", "Presentation", users.get(1)));
        events.add(new Evento("Teste CVB", "Descriçao Teste da cadeira de CVB", Date.valueOf("2019-10-10"), "Sala 186", "Exam", users.get(1)));
        events.add(new Evento("Teste FGH", "Descriçao Teste da cadeira de FGH", Date.valueOf("2018-10-10"), "Sala 936", "Exam", users.get(0)));
        events.add(new Evento("Teste FGH", "Descriçao Teste da cadeira de FGH", Date.valueOf("2017-10-10"), "Sala 637", "Exam", users.get(2)));
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(facesContext.getExternalContext().getSessionMap().get("user") != null)
        {
             String currentUserName = facesContext.getExternalContext().getSessionMap().get("user").toString();
             currentUser = getUserByName(currentUserName);
        }
        
        selectedEventEditable = true;
        searchName="";
    }
    
    @PostConstruct
    public void init() {
        events = new ArrayList<>();
        users = new ArrayList<>();
        
        users.add(new Utilizador("eventsAdmin"));
        users.add(new Utilizador("miguel"));
        users.add(new Utilizador("fabio"));
        users.add(new Utilizador("joao"));
        events.add(new Evento("Teste ASD", "Descriçao Teste da cadeira de ASD", Date.valueOf("2014-10-10"), "Sala 123", "Exam", users.get(0)));
        events.add(new Evento("Teste UIO", "Descriçao Teste da cadeira de UIO", Date.valueOf("2013-10-10"), "Sala 153", "Exam", users.get(1)));
        events.add(new Evento("Palestra sobre Hacking", "Palestra sobre Hacking", Date.valueOf("2012-10-10"), "Sala 497", "Presentation", users.get(3)));
        events.add(new Evento("Teste TYU", "Descriçao Teste da cadeira de TYU", Date.valueOf("2017-10-10"), "Sala 123", "Exam", users.get(2)));
        events.add(new Evento("Apresentação XPTO", "Apresentaçao sobre XPTO", Date.valueOf("2017-10-10"), "Sala 123", "Presentation", users.get(2)));
        events.add(new Evento("Teste ERT", "Descriçao Teste da cadeira de ERT", Date.valueOf("2015-10-10"), "Sala 385", "Exam", users.get(0)));
        events.add(new Evento("Teste SDF", "Descriçao Teste da cadeira de SDF", Date.valueOf("2017-10-10"), "Sala 274", "Exam", users.get(3)));
        events.add(new Evento("Teste BNM", "Descriçao Teste da cadeira de BNM", Date.valueOf("2012-10-10"), "Sala 497", "Exam", users.get(1)));
        events.add(new Evento("Palestra sobre Data Mining", "Palestra sobre Data Mining", Date.valueOf("2012-10-10"), "Sala 497", "Presentation", users.get(1)));
        events.add(new Evento("Teste CVB", "Descriçao Teste da cadeira de CVB", Date.valueOf("2019-10-10"), "Sala 186", "Exam", users.get(1)));
        events.add(new Evento("Teste FGH", "Descriçao Teste da cadeira de FGH", Date.valueOf("2018-10-10"), "Sala 936", "Exam", users.get(0)));
        events.add(new Evento("Teste FGH", "Descriçao Teste da cadeira de FGH", Date.valueOf("2017-10-10"), "Sala 637", "Exam", users.get(2)));
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if(facesContext.getExternalContext().getSessionMap().get("user") != null)
        {
             String currentUserName = facesContext.getExternalContext().getSessionMap().get("user").toString();
             currentUser = getUserByName(currentUserName);
        }
        
        selectedEventEditable = true;
        searchName="";
    }

    public List<Evento> getAllEvents() {
        return events;
    }
    
    public int getNumberOfAllEvents()
    {
        return events.size();
    }
    
//    public boolean validUser(String nome, String pass)
//    {
//        for (int i = 0; i < users.size(); i++) {
//            if  (users.get(i).getNome().equals(nome) && users.get(i).getPassword().equals(pass))
//                return true;
//        }
//        return false;
//    }
    
    public Utilizador getUserByName(String nome)
    {
        for (int i = 0; i < users.size(); i++) {
            if  (users.get(i).getNome().equals(nome))
                return users.get(i);
        }
        return null;
    }
    
    public Evento getEventByName(String nome)
    {
        for (int i = 0; i < events.size(); i++) {
            if  (events.get(i).getNome().equals(nome))
                return events.get(i);
        }
        return null;
    }
    
    public List<Evento> getMyEvents()
    {
        List<Evento> myEvents = new ArrayList<>();
        
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getAdmin().equals(currentUser)) {
                myEvents.add(events.get(i));
            }
        }
        
        return myEvents;
    }
    
    public List<Evento> getsearchEventsByName()
    {
        if (searchName.isEmpty()) {
            return getAllEvents();
        }
        
        List<Evento> myEvents = new ArrayList<>();
        
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getNome().toLowerCase().contains(searchName.toLowerCase())) {
                myEvents.add(events.get(i));
            }
        }
        
        return myEvents;
    }
    
    public boolean isSelectedEventEditable()
    {
        return selectedEventEditable;
    }

    public void setSelectedEventEditable() {
        this.selectedEventEditable = selectedEvent.getAdmin().getNome().equals(currentUser.getNome());
    }
    
    public int getNumberOfMyEvents()
    {
        return getMyEvents().size();
    }

    public Evento getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Evento selectedEvent) {
        this.selectedEvent = selectedEvent;
        setSelectedEventEditable();
        RequestContext.getCurrentInstance().update("form:btns");
    }

    public Utilizador getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Utilizador currentUser) {
        this.currentUser = currentUser;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
    
}
