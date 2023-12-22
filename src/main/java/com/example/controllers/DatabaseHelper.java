package com.example.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String databaseName = "db_project";
    private static final String tableName = "users";
    private static final String tableNameFolloewer = "followers";
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "qwerty13";

    public static String getUrl () {return DATABASE_URL;}
    public static String getUser () {return USER;}
    public static String getPassword () {return PASSWORD;}
    public static String getDBName () {return databaseName;}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

    public static void createDatabase() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "CREATE DATABASE " + databaseName;
            statement.executeUpdate(sql);
        }
    }

    public static boolean databaseExists() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1 FROM pg_database WHERE datname='" + databaseName + "'")) {
            return resultSet.next();
        }
    }

    public static boolean tableExists(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1 FROM information_schema.tables WHERE table_name = '" + tableName + "'")) {
            return resultSet.next();
        }
    }

    public static void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE " + tableName + " ("
                    + "login varchar,"
                    + "email varchar,"
                    + "password varchar)";
            statement.executeUpdate(sql);
        }
    }

    public static boolean isUsernameExists(Connection connection, String userName) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public static String selectPasswordByLogin(Connection connection, String login) {
        String sql = "SELECT password FROM users WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("password");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String selectEmailByLogin(Connection connection, String login) {
        String sql = "SELECT email FROM users WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("email");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //==============================//
    // Follower
    public static List<String> getFollowerEmails(Connection connection) throws SQLException {
        List<String> followerEmails = new ArrayList<>();
        String sql = "SELECT email FROM users WHERE login IN (SELECT login FROM followers)";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                followerEmails.add(resultSet.getString("email"));
            }
        }
        return followerEmails;
    }
    public static boolean tableExistsFollower(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1 FROM information_schema.tables WHERE table_name = '" + tableNameFolloewer + "'")) {
            return resultSet.next();
        }
    }
    public static void createTableFollower(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE " + tableNameFolloewer + " ("
                    + "login varchar)";
            statement.executeUpdate(sql);
        }
    }
    public static boolean isUsernameExistsFollower(Connection connection, String userName) throws SQLException {
        String sql = "SELECT 1 FROM followers WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


}

