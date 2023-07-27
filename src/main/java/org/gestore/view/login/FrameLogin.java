package org.gestore.view.login;

import org.gestore.model.UserManager;
import org.gestore.util.IO;

import javax.swing.*;
import java.awt.*;

public class FrameLogin extends JFrame
{
    private final JTextField username;
    private final JPasswordField password;

    public FrameLogin(UserManager userManager)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login");
        setIconImage(IO.loadImage("/icon.png"));

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 16, 8, 16);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Image logo = IO.loadImage("/logo.png");
        if(logo != null)
        {
            add(new JLabel(new ImageIcon(logo)), gbc);
        }

        username = new JTextField();
        username.setToolTipText("Username");
        password = new JPasswordField();
        password.setToolTipText("Password");

        add(username, gbc);
        add(password, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> userManager.logInUser(this, username.getText(), password.getPassword()));

        add(loginBtn, gbc);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().requestFocusInWindow();
    }
}