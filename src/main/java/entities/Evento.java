/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Miguel
 */
public class Evento implements Serializable{
    
    private String nome;
    private String descricao;
    private Date data;
    private String local;
    private Utilizador admin;
    private String tipo;

    public Evento(String nome, String descricao, Date data, String local, String tipo, Utilizador admin) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.tipo = tipo;
        this.admin = admin;
    }

    public Evento() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Utilizador getAdmin() {
        return admin;
    }

    public void setAdmin(Utilizador admin) {
        this.admin = admin;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
