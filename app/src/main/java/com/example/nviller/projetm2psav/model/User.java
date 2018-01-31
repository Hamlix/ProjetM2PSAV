package com.example.nviller.projetm2psav.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nviller on 19/01/2018.
 */

public class User {
    public String idUser;
    public String emailUser;
    public String mdpUser;
    public String firstnameUser;
    public String lastnameUser;
    public String pseudoUser;

    public User(){

    }

    public User (String idUser, String emailUser, String mdpUser, String pseudoUser, String firstnameUser, String lastnameUser){
        this.idUser = idUser;
        this.emailUser = emailUser;
        this.mdpUser = mdpUser;
        this.pseudoUser = pseudoUser;
        this.firstnameUser = firstnameUser;
        this.lastnameUser = lastnameUser;
    }

    public User ( String emailUser,String firstnameUser, String lastnameUser){
        this.emailUser = emailUser;
        this.firstnameUser = firstnameUser;
        this.lastnameUser = lastnameUser;
    }

    //A voir et se décider
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firstname", firstnameUser);
        result.put("lastname", lastnameUser);
        result.put("email", emailUser);
        result.put("nickname", pseudoUser);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User : ");
        sb.append("firstnameUser= ").append(firstnameUser).append("\n");
        sb.append("lastnameUser= ").append(lastnameUser).append("\n");
        sb.append("emailUser= ").append(emailUser).append("\n");
        return sb.toString();
    }
/*
    public String getIdUser() {
        return idUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getMdpUser() {
        return mdpUser;
    }

    public void setMdpUser(String mdpUser) {
        this.mdpUser = mdpUser;
    }

    public String getPseudoUser() {
        return pseudoUser;
    }

    public void setPseudoUser(String loginUser) {
        this.pseudoUser = loginUser;
    }

    public String getFirstnameUser() {
        return firstnameUser;
    }

    public void setFirstnameUser(String firstnameUser) {
        this.firstnameUser = firstnameUser;
    }

    public String getLastnameUser() {
        return lastnameUser;
    }

    public void setLastnameUser(String lastnameUser) {
        this.lastnameUser = lastnameUser;
    }
*/
}
