package com.example.nviller.projetm2psav.dao;

import com.example.nviller.projetm2psav.model.User;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mouna on 25/01/2018.
 */

public class DAOUser {

    private DAOFirebaseUser database;
    private static DAOUser instance = null;

    private DAOUser() {
        database = DAOFirebaseUser.getInstance();
    }

    public static DAOUser getInstance() {
        if (instance == null)
            instance = new DAOUser();
        return instance;
    }

    public boolean isLoggedIn() {
        return database.isLoggedIn();
    }

    public void signOut() {
        database.signOut();
    }

    public boolean validateUser(User user, String password) {
        return database.validateUser(user, password);
    }

    public boolean validateUser(String email, String password, String firstName, String lastName) {
        return database.validateUser(email, password, firstName, lastName);
    }

    public boolean validateUser(final String email, final String password) {
        return database.validateUser(email, password);
    }

    public void createUser(String uid, String email, String firstName, String lastName) {
        database.createUser(uid, email, firstName, lastName);
    }

    public String getCurrentUserId() {
        return database.getCurrentUserId();
    }

    public FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }
}
