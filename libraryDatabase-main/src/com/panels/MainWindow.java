package com.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener {

    JComboBox<String> c = new JComboBox<>(new String[]{"Visualizza database", "Aggingi elemento","Statistiche"});
    JButton[] buttons = new JButton[] {new JButton("Procedi"), new JButton("Chiudi")};

    public MainWindow(){

        super("Database Libreria");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new FlowLayout());

        buttons[0].addActionListener(this);
        buttons[0].setActionCommand("Open");
        buttons[1].addActionListener(this);

        add(c);
        for(JButton button : buttons) add(button);

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        dispose();
        String cmd = e.getActionCommand();

        if (cmd.equals("Open")) {
            try {
                switch(c.getSelectedIndex()){
                    case 0 -> new DatabaseWindow().setVisible(true);
                    case 1 -> new InsertWindow().setVisible(true);
                    case 2 -> new StatWindow().setVisible(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
