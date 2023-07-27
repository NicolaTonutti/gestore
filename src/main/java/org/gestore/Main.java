package org.gestore;

import com.formdev.flatlaf.FlatDarkLaf;
import org.gestore.controller.ControllerGestore;
import org.gestore.model.ModelloGestore;
import org.gestore.view.gestore.FrameGestore;
import org.gestore.view.login.FrameLogin;
import org.gestore.model.UserManager;

import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
        //impostazioni flatlaf L&F per Swing
        Map<String, String> globalExtraDefaults = new HashMap<>();
        globalExtraDefaults.put("@accentColor", "#aeff00");
        FlatDarkLaf.setGlobalExtraDefaults(globalExtraDefaults);
        FlatDarkLaf.setup();


        ModelloGestore modelloGestore = new ModelloGestore();
        UserManager userManager = new UserManager(modelloGestore);

        userManager.setOnLogin(() -> {
            ControllerGestore controllerGestore = new ControllerGestore(modelloGestore);
            new FrameGestore(controllerGestore);
            controllerGestore.init();
        });

        new FrameLogin(userManager);
    }
}