package org.gestore.model;

import org.gestore.view.login.FrameLogin;

import javax.swing.*;

public class UserManager
{
    private static UserManager istanza = null;

    public User loggedUser;
    private final ModelloGestore modelloGestore;
    private Runnable onLoginCallback;

    public UserManager(ModelloGestore modelloGestore)
    {
        istanza = this;

        loggedUser = null;
        this.modelloGestore = modelloGestore;
    }

    public static UserManager get()
    {
        return istanza;
    }

    public void setOnLogin(Runnable callback)
    {
        onLoginCallback = callback;
    }

    public void logInUser(FrameLogin frame, String username, char[] charsPassword)
    {
        String password = String.valueOf(charsPassword);

        if(username.equals("admin") &&
            password.equals("admin"))
        {
            loggedUser = new AdminUser();
            frame.dispose();
            onLoginCallback.run();
            return;
        }

        for(User user : modelloGestore.getGiocatori())
        {
            if(username.equals(user.getUsername()))
            {
                if(!password.equals(user.getPassword()))
                {
                    JOptionPane.showMessageDialog(frame,
                        "Username/Password errati",
                        "User Manager",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                loggedUser = user;
                frame.dispose();
                onLoginCallback.run();
                return;
            }
        }

        JOptionPane.showMessageDialog(frame,
            "Utente non registrato.",
            "User Manager",
            JOptionPane.WARNING_MESSAGE
        );
    }

    public static boolean isAdmin()
    {
        if(istanza.loggedUser == null)
            return false;

        return istanza.loggedUser.isAdmin();
    }

    public static String getLoggedUserInfo()
    {
        if(istanza.loggedUser == null)
            return "";

        return istanza.loggedUser.getUsername();
    }
}