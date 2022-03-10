package com.panels;

import com.database.Database;
import com.panels.components.LabelTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.*;

public class LoginWindow extends JFrame implements ActionListener {

    LabelTextField[] labelTextFields = new LabelTextField[]{
            new LabelTextField(new JLabel("Username"), new JTextField()),
            new LabelTextField(new JLabel("Password"), new JPasswordField(), '*')
    };

    JButton loginButton = new JButton("Login");

    public LoginWindow() {
        super("Login");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        loginButton.addActionListener(this);

        for (LabelTextField text : labelTextFields) add(text);
        add(loginButton);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        Database database = new Database();
        try {
            if (database.selectLogin(labelTextFields[0].Text.getText(), labelTextFields[1].Text.getText())) {
                new MainWindow().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Ritenta, sarai più fortunato", "", JOptionPane.WARNING_MESSAGE);
                new LoginWindow().setVisible(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}