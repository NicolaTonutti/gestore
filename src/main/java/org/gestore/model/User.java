package org.gestore.model;

public interface User
{
    String getUsername();
    String getPassword();
    boolean isAdmin();


    void acquista(Gioco gioco);
    void desidera(Gioco gioco);
}