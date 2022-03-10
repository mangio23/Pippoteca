package com.panels;

import com.database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StatWindow extends JFrame implements ActionListener {

    public Connection connection;

    public StatWindow() throws SQLException{
        super("Statistiche");
        int numLibri;
        int mediaPag;




        Database db = new Database();
                numLibri = (int)(db.selectNum("SELECT COUNT(ID) FROM libri"));
                mediaPag = (int)(db.selectNum("SELECT COUNT(ID) FROM libri WHERE numero_pagine>100"));

        JLabel[] JLabel = new JLabel[]{
                new JLabel("Numero libri nel database: " + numLibri),
                new JLabel("Quanti con più di 100 pagine: " + mediaPag),
        };
        JPanel[] panels = new JPanel[]{new JPanel(new GridLayout(2,1)), new JPanel(new FlowLayout())};
        for(JLabel lable : JLabel) panels[0].add(lable);
        setLayout(new BorderLayout());
        add(panels[0], BorderLayout.NORTH); add(panels[1], BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
