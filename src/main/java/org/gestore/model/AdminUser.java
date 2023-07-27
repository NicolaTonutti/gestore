package org.gestore.model;

import org.gestore.model.User;

public class AdminUser implements User
{
    @Override
    public String getUsername()
    {
        return "admin";
    }

    @Override
    public String getPassword()
    {
        return "admin";
    }

    @Override
    public boolean isAdmin()
    {
        return true;
    }

    @Override
    public void acquista(Gioco gioco)
    {
    }

    @Override
    public void desidera(Gioco gioco)
    {
    }
}