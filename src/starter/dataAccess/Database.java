package dataAccess;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Responsible for creating connections to the database. Connections should be closed after use, either by calling
 * {@link #closeConnection(Connection)} on the Database instance or directly on the connection.
 */
public class Database {

    // FIXME: Change these fields, if necessary, to match your database configuration
    public static final String DB_NAME = "chess";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "idontknow1";

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    static private final LinkedList<Connection> storedConnections = new LinkedList<Connection>();
    /**
     * Gets a connection to the database.
     *
     * @return Connection the connection.
     * @throws DataAccessException if a data access error occurs.
     */
    public Connection getConnection() throws DataAccessException {
        try {
            Connection conn;
            if(storedConnections.isEmpty()){
                conn = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
                conn.setCatalog(DB_NAME);
            }
            else{
                conn = storedConnections.removeFirst();
            }
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Closes the specified connection.
     *
     * @param connection the connection to be closed.
     * @throws DataAccessException if a data access error occurs while closing the connection.
     */
    public void closeConnection(Connection connection) throws DataAccessException {
        storedConnections.add(connection);
    }


}
