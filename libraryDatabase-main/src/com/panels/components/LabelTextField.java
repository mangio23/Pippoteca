package com.panels.components;

import javax.swing.*;

public class LabelTextField extends JPanel {

    public JLabel Label;
    public JTextField Text;
    public JPasswordField Psw;

    public LabelTextField(JLabel label, JTextField text){
        Label = label;
        Text = text;
        Text.setColumns(20);
        add(Label); add(Text);
    }
    public LabelTextField(JLabel label, JPasswordField psw, char c){
        Label = label;
        Psw = psw;
        Psw.setColumns(20);
        Psw.setEchoChar(c);
        add(Label); add(Psw);
    }
}