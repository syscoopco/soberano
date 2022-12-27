package co.syscoop.soberano.database.relational;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sql.RowSet;

public class DatabaseUtil {

	public static void close(RowSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        }
        catch (Exception ignore) {}
    }
	
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        }
        catch (Exception ignore) {}
    }

    public static void close(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception ignore) {}
    }

    public static void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        }
        catch (Exception ignore) {}
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        }
        catch (Exception ignore) {}
    }
    
    public static String arrayToString(Object array) {
    	String arrayStr = null;
    	if (array instanceof ArrayList) {
    		arrayStr = array.toString().replace("[", "");
      	} else if (array instanceof Long[] || array instanceof int[]) {
      		arrayStr = Arrays.toString((Long[]) array).replace("[", "");
    	} else if(array instanceof Double[]) {
    		arrayStr = Arrays.toString((Double[]) array).replace("[", "");
    	} else if (array instanceof String[]) {
    		arrayStr = Arrays.toString((String[]) array).replace("[", "");
    	}
    	if (arrayStr != null) arrayStr = arrayStr.replace("]", "");
    	return arrayStr.replace(" ", "");
    }
}
