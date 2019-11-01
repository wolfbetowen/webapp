package com.epam.webapp.connectionpool;

import com.epam.webapp.connectionpool.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * Class creating Connection pool
 */
public class ConnectionPool {

  private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
  private static final String DRIVER = "driver";
  private static final String URL = "url";
  private static final String USER = "user";
  private static final String PASSWORD = "password";
  private static final String POOL_SIZE = "poolsize";
  private BlockingQueue<Connection> connectionQueue;
  private BlockingQueue<Connection> givenAwayConQueue;

  private String driverName;
  private String url;
  private String user;
  private String password;
  private int sizePool;
  private static ConnectionPool instance;

  static {
    try {
      instance = new ConnectionPool();
    } catch (ConnectionPoolException e) {
      e.printStackTrace();
    }
  }

  public static ConnectionPool getInstance() {
    return instance;
  }

  private ConnectionPool() throws ConnectionPoolException {

    DBResourceManager resourceManager = DBResourceManager.getInstance();
    this.driverName = resourceManager.getValue(DRIVER);
    this.url = "jdbc:mysql://localhost:3306/training?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    this.url = resourceManager.getValue(URL);
    this.user = resourceManager.getValue(USER);
    this.password = resourceManager.getValue(PASSWORD);

    try {
      this.sizePool = Integer.parseInt(resourceManager.getValue(POOL_SIZE));
    } catch (NumberFormatException e) {
      logger.error(e);
      throw new ConnectionPoolException("invalid integer", e);
    }
  }

  /**
   * initialization connecting
   */
  public void initPool() throws ConnectionPoolException {
    try {
      Class.forName(driverName);
      givenAwayConQueue = new ArrayBlockingQueue<>(sizePool);
      connectionQueue = new ArrayBlockingQueue<>(sizePool);
      for (int i = 0; i < sizePool; i++) {
        Connection connection = DriverManager.getConnection(url, user, password);
        PooledConnection pooledConnection = new PooledConnection(connection);
        connectionQueue.add(pooledConnection);
      }
    } catch (ClassNotFoundException e) {
      logger.error(e);
      throw new ConnectionPoolException(e);
    } catch (SQLException e) {
      logger.error(e);
      throw new ConnectionPoolException(e);
    }
  }


  public Connection takeConnection() throws ConnectionPoolException {
    Connection connection;
    try {
      connection = connectionQueue.take();
      givenAwayConQueue.add(connection);
    } catch (InterruptedException e) {
      logger.error(e);
      throw new ConnectionPoolException(e);
    }
    return connection;
  }

  public void dispose() {
    clearConnectionQueue();
  }

  /**
   * closing connecting
   * @param con
   * @param st
   * @param rs
   */
  public void closeConnection(Connection con, Statement st, ResultSet rs) throws ConnectionPoolException {
    try {
      con.close();
    } catch (SQLException e) {
      logger.error("Error closing connection.", e);
      throw new ConnectionPoolException(e);
    }
    try {
      rs.close();
    } catch (SQLException e) {
      logger.error("Error closing connection.", e);
      throw new ConnectionPoolException(e);
    }

    try {
      st.close();
    } catch (SQLException e) {
      logger.error("Error closing connection.", e);
      throw new ConnectionPoolException(e);
    }
  }

  /**
   * closing connecting
   * @param con
   * @param st
   */
  public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {
    try {
      con.close();
    } catch (SQLException e) {
      logger.error("Error closing connection.", e);
      throw new ConnectionPoolException(e);
    }

    try {
      st.close();
    } catch (SQLException e) {
      logger.error("Error closing connection.", e);

    }
  }

  /**
   * clear connection queue
   */
  private void clearConnectionQueue() {
    try {
      closeConnectionsQueue(givenAwayConQueue);
      closeConnectionsQueue(connectionQueue);
    } catch (SQLException e) {
      logger.error("Error clearing connection queue.", e);
    }
  }

  /**
   * close connections queue
   * @param queue
   * @throws SQLException
   */
  private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {
    Connection connection;
    while ((connection = queue.poll()) != null) {
      if (!connection.getAutoCommit()) {
        connection.commit();
      }
      ((PooledConnection) connection).reallyClose();
    }
  }

  /**
   * private inner class for creating connection
   */
  private class PooledConnection implements Connection {

    private Connection connection;

    public PooledConnection(Connection connection) throws SQLException {
      this.connection = connection;
      this.connection.setAutoCommit(true);

    }


    public void reallyClose() throws SQLException {
      connection.close();
    }

    @Override
    public void close() throws SQLException {
      if (connection.isClosed()) {
        throw new SQLDataException("Attempting to close closed connection.");
      }
      if (connection.isReadOnly()) {
        connection.setReadOnly(false);
      }
      if (!givenAwayConQueue.remove(this)) {
        throw new SQLDataException("Error deleting connection from the given away connections pool.");
      }

      if (!connectionQueue.offer(this)) {
        throw new SQLException("Error allocating connection in the pool.");
      }
    }

    @Override
    public Statement createStatement() throws SQLException {
      return connection.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String s) throws SQLException {
      return connection.prepareStatement(s);
    }

    @Override
    public CallableStatement prepareCall(String s) throws SQLException {
      return connection.prepareCall(s);
    }

    @Override
    public String nativeSQL(String s) throws SQLException {
      return connection.nativeSQL(s);
    }

    @Override
    public void setAutoCommit(boolean b) throws SQLException {
      connection.setAutoCommit(b);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
      return connection.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
      connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
      connection.rollback();
    }

    @Override
    public boolean isClosed() throws SQLException {
      return connection.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
      return connection.getMetaData();
    }

    @Override
    public void setReadOnly(boolean b) throws SQLException {
      connection.setReadOnly(b);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
      return connection.isReadOnly();
    }

    @Override
    public void setCatalog(String s) throws SQLException {
      connection.setCatalog(s);
    }

    @Override
    public String getCatalog() throws SQLException {
      return connection.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int i) throws SQLException {
      connection.setTransactionIsolation(i);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
      return connection.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
      return connection.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
      connection.clearWarnings();
    }

    @Override
    public Statement createStatement(int i, int i1) throws SQLException {
      return connection.createStatement(i, i1);
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i, int i1) throws SQLException {
      return connection.prepareStatement(s, i, i1);
    }

    @Override
    public CallableStatement prepareCall(String s, int i, int i1) throws SQLException {
      return connection.prepareCall(s, i, i1);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
      return connection.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
      connection.setTypeMap(map);
    }

    @Override
    public void setHoldability(int i) throws SQLException {
      connection.setHoldability(i);
    }

    @Override
    public int getHoldability() throws SQLException {
      return connection.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
      return connection.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String s) throws SQLException {
      return connection.setSavepoint(s);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
      connection.rollback();
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
      connection.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int i, int i1, int i2) throws SQLException {
      return connection.createStatement(i, i1, i2);
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i, int i1, int i2) throws SQLException {
      return connection.prepareStatement(s, i, i1, i2);
    }

    @Override
    public CallableStatement prepareCall(String s, int i, int i1, int i2) throws SQLException {
      return connection.prepareCall(s, i, i1, i2);
    }

    @Override
    public PreparedStatement prepareStatement(String s, int i) throws SQLException {
      return connection.prepareStatement(s, i);
    }

    @Override
    public PreparedStatement prepareStatement(String s, int[] ints) throws SQLException {
      return connection.prepareStatement(s, ints);
    }

    @Override
    public PreparedStatement prepareStatement(String s, String[] strings) throws SQLException {
      return connection.prepareStatement(s, strings);
    }

    @Override
    public Clob createClob() throws SQLException {
      return connection.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
      return connection.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
      return connection.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
      return createSQLXML();
    }

    @Override
    public boolean isValid(int i) throws SQLException {
      return connection.isValid(i);
    }

    @Override
    public void setClientInfo(String s, String s1) throws SQLClientInfoException {
      connection.setClientInfo(s, s1);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
      connection.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String s) throws SQLException {
      return connection.getClientInfo(s);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
      return connection.getClientInfo();
    }

    @Override
    public Array createArrayOf(String s, Object[] objects) throws SQLException {
return connection.createArrayOf(s, objects);    }

    @Override
    public Struct createStruct(String s, Object[] objects) throws SQLException {
      return connection.createStruct(s, objects);
    }

    @Override
    public void setSchema(String s) throws SQLException {
    connection.setSchema(s);
    }

    @Override
    public String getSchema() throws SQLException {
      return connection.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
  connection.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int i) throws SQLException {
connection.setNetworkTimeout(executor, i);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
      return connection.getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
      return connection.unwrap(aClass);
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
      return connection.isWrapperFor(aClass);
    }
  }
}
