package Core.Support.General;

import Core.Utils.Decoder;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SQLServer {
    private static Connection con;
    private static Statement stm;
    private static List<Map<String, Object>> lastQueryResult;
    private static final String dbUrl = PropertyBuilder.getEnvProperty().getProperty("db.url");
    private static final String user = PropertyBuilder.getEnvProperty().getProperty("db.user");
    private static final String password = Decoder.Base64Decode(PropertyBuilder.getEnvProperty().getProperty("db.password"));

    protected static void connect(String dbName) {
        String connectionString = String.format("jdbc:sqlserver://%s;DatabaseName=%s",dbUrl, dbName);
        try {
            System.out.printf("Open connection to [%s] database%n", dbName);
            con = DriverManager.getConnection(connectionString, user, password);
            System.out.println("Connected to database successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection FAIL ...");
        }
    }

    protected static int update(String dbName, String query) throws SQLException, ClassNotFoundException {
        connect(dbName);
        PreparedStatement pstmt = con.prepareStatement(query);
        int result = 0;
        try{
            System.out.printf("Query [%s]%n", query);
            result = pstmt.executeUpdate();
            System.out.println("Query Success");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            if (pstmt != null) {
                pstmt.close();
                con.close();
                System.out.println("Connection is closed ...");
            }
        }
        return result;
    }

    protected static void select(String dbName, String query) throws SQLException, ClassNotFoundException {
        connect(dbName);
        try{
            System.out.printf("Query [%s]%n", query);
            stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);
            lastQueryResult = resultSetToList(rs);
            System.out.println("Query Success");
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (stm != null) {
                stm.close();
                con.close();
                System.out.println("Connection is closed ...");

            }
        }
    }

    private static List<Map<String, Object>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        while (rs.next()){
            Map<String, Object> row = new HashMap<String, Object>(columns);
            for(int i = 1; i <= columns; ++i){
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }

    public static List<Map<String, Object>> getLastQueryResult(){
        return lastQueryResult;
    }
}
