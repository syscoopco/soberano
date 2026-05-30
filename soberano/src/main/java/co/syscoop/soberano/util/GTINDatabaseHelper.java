package co.syscoop.soberano.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GTINDatabaseHelper {
    
    /**
     * Returns a connection to the database, or null if the database file does not exist.
     */
    public static Connection getConnection(String absolutePath) {
        
    	File dbFile = new File(absolutePath);
        
        if (!dbFile.exists()) {
            // Silent ignore – no database, just return null
            return null;
        }
        
        try {
            // Read-only mode prevents any attempts to create WAL files
            String url = "jdbc:sqlite:file:" + absolutePath + "?mode=ro";
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
        	e.printStackTrace();
            return null;
        }
    }
}