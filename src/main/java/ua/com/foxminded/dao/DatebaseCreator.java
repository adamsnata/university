package ua.com.foxminded.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.foxminded.util.FileParser;

public class DatebaseCreator {

    private String urlDataBase;
    private String urlSchema ;
    private String username ;
    private String password ;
    private String driver ;

    Logger logger = LoggerFactory.getLogger("SampleLogger");
    
    public DatebaseCreator() {
       
        Properties property = new Properties();

        try {
            FileInputStream fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);

            urlDataBase = property.getProperty("db.url");
            urlSchema = property.getProperty("spring.datasource.url");
            username = property.getProperty("spring.datasource.username");
            password = property.getProperty("spring.datasource.password");        
            driver = property.getProperty("spring.datasource.driver-class-name"); 

        } catch (IOException e) {
            logger.error("Error. It is miss application.properties");
        }
    }

    public void createDatabase() {
        FileParser file = new FileParser();
        logger.info("Remove and create DB University");
        Arrays.stream(file.readFileToLines("sql_db.script")).forEach(sql -> doQuery(sql, urlDataBase));
        logger.info("Remove and create DB University successfully");
        logger.info("Remove and create Schema");
        Arrays.stream(file.readFileToLines("schema.script")).forEach(sql -> doQuery(sql, urlSchema));
        logger.info("Remove and create Schema successfully");
    }
    
    public void doQuery(String sql, String url) {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.info(e.getMessage());
                }
            }
        }
    }
}
