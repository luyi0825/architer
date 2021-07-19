package com.architecture.ultimate.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

/**
 * 向employees表中存防数据
 */
public class InsertEmployeesData {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    //private static final String URL = "jdbc:mysql://localhost:3306/mydb"
    private static final String URL = "jdbc:mysql://192.168.2.222:3306/test?useUnicode=true&characterEncoding=utf-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException {
        //50个线程，每个线程插入1万数据
        for (int j = 0; j < 50; j++) {
          Runnable runnable=  new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection connection = getConnection();
                        String sql = "insert into employees (name,age,position) values (?,?,?)";
                        for (int i = 1; i < 10000; i++) {
                            String uuid = UUID.randomUUID().toString();
                            String name = uuid.replace("-", "").substring(0, 20);
                            int age = (int) (Math.random() * 100);
                            String position = uuid.substring(10, 15);
                            PreparedStatement ps = connection.prepareStatement(sql);
                            ps.setString(1, name);
                            ps.setInt(2, age);
                            ps.setString(3, position);
                            ps.execute();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
          new Thread(runnable).start();

        }
    }


    public static Connection getConnection() {

        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
