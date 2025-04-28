package org.hauthfx.secureauthfx;

import java.sql.*;

public class Store {
    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    static ResultSet resultSet = null;
    public static boolean signUpStore(String name, String email, String password)
    {
        boolean store = false;


        String user = "root";
        String passcode = "Harsh$1000Pande";
        String url = "jdbc:mysql://localhost:3307/users";


        try {
            connection = DriverManager.getConnection(url,user,passcode);
            String query = "INSERT INTO information (user_name,email,password) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);

            int i = preparedStatement.executeUpdate();
            if(i > 0)
            {
                store = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }
    public static boolean checkEmail(String email)
    {
        boolean store = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String user = "root";
        String passcode = "Harsh$1000Pande";
        String url = "jdbc:mysql://localhost:3307/users";


        try {
            connection = DriverManager.getConnection(url,user,passcode);
            String query = "SELECT * FROM information WHERE email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                store = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }
    public static boolean checkLogin(String email, String password)
    {
        boolean store = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String user = "root";
        String passcode = "Harsh$1000Pande";
        String url = "jdbc:mysql://localhost:3307/users";


        try {
            connection = DriverManager.getConnection(url,user,passcode);
            String query = "SELECT * FROM information WHERE email = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                store = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    public static void main(String[] args) {
    }
}
