package com.database;

import com.panels.LoginWindow;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class Database {

    public static Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/pippoteca", "root", "");
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, sqlEx.getMessage());
        }
    }

    //Ora come ora non serve, è solo per un futuro se si volessero implementare connessioni ad altri database
    public Database(String connectionString) {
        try {
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, sqlEx.getMessage());
        }
    }

    public void insert(String autore, String titolo, int numeroPagine, String genere) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO libri VALUES(default, ?, ?, ? ,?)");
            statement.setString(1, titolo);
            statement.setString(2, autore);
            statement.setInt(3, numeroPagine);
            statement.setString(4, genere);
            statement.executeUpdate();
            statement.close();
            JOptionPane.showMessageDialog(null, "Inserimento completato!", "", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, sqlEx.getMessage());
        }
    }

    public Vector<Book> select(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        Vector<Book> results = new Vector<>();
        while (result.next()) {
            results.add(new Book(result.getInt("id"),
                    result.getString("autore"),
                    result.getString("titolo"),
                    result.getInt("numero_pagine"),
                    result.getString("genere")));
        }
        statement.close();
        return results;
    }

    public void update(String id, String autore, String titolo, String numeroPagine, String genere) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE libri SET autore = ?, titolo = ?, numero_pagine = ?, genere = ? WHERE id = ?");
            statement.setString(1, autore);
            statement.setString(2, titolo);
            statement.setInt(3, Integer.parseInt(numeroPagine));
            statement.setString(4, genere);
            statement.setInt(5, Integer.parseInt(id));
            statement.executeUpdate();
            statement.close();
            JOptionPane.showMessageDialog(null, "Modifica completata!", "", JOptionPane.PLAIN_MESSAGE);

        } catch (SQLException sqlex) {
            JOptionPane.showMessageDialog(null, sqlex.getMessage());
        }
    }

    public void delete(String id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM libri WHERE id = ?");
            statement.setInt(1, Integer.parseInt(id));
            statement.executeUpdate();
            statement.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public int selectNum(String query) throws SQLException {
        int res;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        result.next();
        res = result.getInt(1);

        statement.close();
        return res;
    }

    public boolean selectLogin(String username, String password) throws SQLException {
        String query = "SELECT * FROM login WHERE username ='" + username + "'AND password = '" + password + "'";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(query);

        if (rs.next()) {
            return true;
        }
        return false;
    }

    public static void CheckTable() throws SQLException{

        Database db = new Database();

        DatabaseMetaData md = connection.getMetaData(); ResultSet rs = md.getTables(null, null, "login", null);

        if (!rs.next()){
            JOptionPane.showMessageDialog(null, "La tabella degli utenti non esiste, ora la creo", "", JOptionPane.PLAIN_MESSAGE);
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE login (" +
                    "ID int(11) not null auto_increment," +
                    "nominativo varchar(255)," +
                    "username varchar(255)," +
                    "password varchar(255)" +
                    "PRIMARY KEY(ID))");
            statement.executeUpdate();
            statement.close();
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO login (" +
                    "ID, nominativo, username, password) " +
                    "VALUES (NULL, 'admin', 'admin', 'admin') ");
            statement1.executeUpdate();
            statement1.close();
            JOptionPane.showMessageDialog(null, "Tabella creata, loggati con admin, admin", "", JOptionPane.PLAIN_MESSAGE);
        }

        DatabaseMetaData md1 = connection.getMetaData(); ResultSet rs1 = md1.getTables(null, null, "libri", null);

        if (!rs1.next()){
            JOptionPane.showMessageDialog(null, "La tabella dei libri non esiste, ora la creo", "", JOptionPane.PLAIN_MESSAGE);
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE libri (" +
                    "ID int(11) not null auto_increment," +
                    "titolo varchar(255)," +
                    "autore varchar(255)," +
                    "num_pagine int(11)," +
                    "genere varchar(255)," +
                    "PRIMARY KEY(ID))");
            statement.executeUpdate();
            statement.close();
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO libri (" +
                    "ID, titolo, autore, num_pagine, genere) " +
                    "VALUES (NULL, 'titolo', 'autore', 115, 'genere') ");
            statement1.executeUpdate();
            statement1.close();
            JOptionPane.showMessageDialog(null, "Tabella creata e libro aggiunto", "", JOptionPane.PLAIN_MESSAGE);
        }

        new LoginWindow().setVisible(true);
        Database.connection.close();
    }
}