package com.epam.webapp.dao.impl;

import com.epam.webapp.dao.UserDao;
import com.epam.webapp.connectionpool.ConnectionPool;
import com.epam.webapp.connectionpool.ConnectionPoolException;
import com.epam.webapp.dao.DaoException;
import com.epam.webapp.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.webapp.constant.ConstSqlRequest.*;
import static com.epam.webapp.constant.ConstSqlName.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

  private static Logger logger = LogManager.getLogger(UserDaoImpl.class);

  /**
   * check if user is in database - take information about him
   *
   * @param user
   * @return user
   */

  @Override
  public User authorization(User user) throws DaoException {
    ConnectionPool pool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = pool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_GET_USER);
      preparedStatement.setString(1, user.getLogin());
      preparedStatement.setString(2, user.getPassword());
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        user.setId(resultSet.getInt(SQL_USER_ID));
        user.setName(resultSet.getString(SQL_USER_NAME));
        user.setSurname(resultSet.getString(SQL_USER_SURNAME));
        user.setEmail(resultSet.getString(SQL_USER_EMAIL));
        user.setStatus(UserStatus.getUserType(resultSet.getString(SQL_USER_STATUS)));
        user.setType(UserType.getUserType(resultSet.getString(SQL_USER_TYPE)));
        return user;
      }
      return null;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        pool.closeConnection(connection, preparedStatement, resultSet);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public boolean checkLogin(String login) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String checkIsExist = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_CHECK_LOGIN);
      preparedStatement.setString(1, login);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        checkIsExist = resultSet.getString(SQL_USER_LOGIN);
      }
      return checkIsExist != null;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement, resultSet);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public boolean checkEmail(String email) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String checkIsExistEmail = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_CHECK_EMAIL);
      preparedStatement.setString(1, email);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        checkIsExistEmail = resultSet.getString(SQL_USER_EMAIL);
      }
      return checkIsExistEmail != null;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement, resultSet);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public void deleteUser(int userId) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
      preparedStatement.setInt(1, userId);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      e.printStackTrace();
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  /**
   * check is user in database and if no - then insert him in database
   *
   * @param user
   * @return user
   */

  @Override
  public User registration(User user) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_NEW_USER);
      preparedStatement.setString(1, user.getName());
      preparedStatement.setString(2, user.getSurname());
      preparedStatement.setString(3, user.getEmail());
      preparedStatement.setString(4, user.getLogin());
      preparedStatement.setString(5, user.getPassword());
      preparedStatement.executeUpdate();
      return user;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public void grade(int assessment, int userId, int trainingId) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_GRADE);
      preparedStatement.setInt(1, assessment);
      preparedStatement.setInt(2, userId);
      preparedStatement.setInt(3, trainingId);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public void addTrainingToStudent(int userId, int trainingId) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_ADD_TRAINING_TO_STUDENT);
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, trainingId);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public boolean checkEnrolled(int userId, int trainingId) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_CHECK_ENROLLED);
      System.out.println(userId + "userId in DAO");
      System.out.println(trainingId + "trainingId in DAO");
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, trainingId);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        return resultSet.getInt(1) > 0;
      }
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
    return false;
  }

  @Override
  public Map<Task, User> findAllMentors() throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Map<Task, User> mentorsTraining = new HashMap<>();
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_ALL_MENTORS);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        User user = new User();
        Task task = new Task();
        user.setId(resultSet.getInt(SQL_USER_ID));
        user.setName(resultSet.getString(SQL_USER_USER_NAME));
        user.setSurname(resultSet.getString(SQL_USER_SURNAME));
        task.setId(resultSet.getInt(SQL_TRAINING_ID));
        task.setName(resultSet.getString(SQL_TRAINING_NAME));
        mentorsTraining.put(task, user);
      }
      return mentorsTraining;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement, resultSet);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public List<User> findAllUser() throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<User> users = new ArrayList<>();
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_ALL_USERS);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        User user = new User();
        user.setName(resultSet.getString(SQL_USER_NAME));
        user.setSurname(resultSet.getString(SQL_USER_SURNAME));
        user.setLogin(resultSet.getString(SQL_USER_LOGIN));
        user.setEmail(resultSet.getString(SQL_USER_EMAIL));
        user.setType(UserType.getUserType(resultSet.getString(SQL_USER_TYPE)));
        user.setId(resultSet.getInt(SQL_USER_ID));
        user.setStatus(UserStatus.getUserType(resultSet.getString(SQL_USER_STATUS)));
        users.add(user);
      }
      return users;
    } catch (SQLException e) {
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement, resultSet);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public boolean updateUserType(int userId, UserType type, UserStatus status) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_TYPE);
      preparedStatement.setString(1, type.name());
      preparedStatement.setString(2, status.name());
      preparedStatement.setInt(3, userId);
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public List<Student> findStudentsForTraining(int trainingId) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet;
    List<Student> students = new ArrayList<>();
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_STUDENT_FOR_TRAINING);
      preparedStatement.setInt(1, trainingId);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Student student = new Student();
        student.setId(resultSet.getInt(SQL_USER_ID));
        student.setName(resultSet.getString(SQL_USER_NAME));
        student.setSurname(resultSet.getString(SQL_USER_SURNAME));
        student.setEmail(resultSet.getString(SQL_USER_EMAIL));
        student.setLogin(resultSet.getString(SQL_USER_LOGIN));
        student.setGrade(resultSet.getInt(SQL_GRADE_FOR_TRAINING));
        students.add(student);
      }
      return students;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public List<Task> findStudentsMarkForTrainingsTask(int studentId, int trainingId) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Task> tasks = new ArrayList<>();
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_ALL_MARKS_FOR_TRAINING);
      preparedStatement.setInt(1, studentId);
      preparedStatement.setInt(2, trainingId);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Task task = new Task();
        task.setId(resultSet.getInt(SQL_TASK_ID));
        task.setName(resultSet.getString(SQL_TASK_NAME));
        task.setTask(resultSet.getString(SQL_TASK));
        task.setMark(resultSet.getInt(SQL_TASK_MARK));
        task.setAnswer(resultSet.getString(SQL_TASK_ANSWER));
        tasks.add(task);
      }
      return tasks;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement, resultSet);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public boolean sendOfferConsultations(int trainingId, Date date, BigDecimal price) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    System.out.println(trainingId + "1" + date + "2" + price + "3");
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_OFFER_CONSULTATION);
      preparedStatement.setInt(1, trainingId);
      preparedStatement.setString(2, date.toString());
      preparedStatement.setBigDecimal(3, price);
      preparedStatement.setInt(4, trainingId);
      preparedStatement.setString(5, date.toString());
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public Map<Training, Date> findConsultationsOffer(int mentorId) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Map<Training, Date> consultations = new HashMap<>();
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_CONSULTATION_OFFER);
      preparedStatement.setInt(1, mentorId);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Training training = new Training();
        training.setId(resultSet.getInt(SQL_TRAINING_ID));
        training.setName(resultSet.getString(SQL_TRAINING_NAME_AS));
        Date date = resultSet.getDate(SQL_DATE);
        consultations.put(training, date);
      }
      return consultations;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement, resultSet);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }

  @Override
  public boolean sendAgreement(int trainingId, Date date, boolean mark) throws DaoException {
    ConnectionPool connectionPool = ConnectionPool.getInstance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = connectionPool.takeConnection();
      preparedStatement = connection.prepareStatement(SQL_SEND_AGREEMENT);
      preparedStatement.setBoolean(1, mark);
      preparedStatement.setInt(2, trainingId);
      preparedStatement.setString(3, date.toString());
      preparedStatement.executeUpdate();
      return true;
    } catch (SQLException e) {
      logger.error(e);
      throw new DaoException(e);
    } catch (ConnectionPoolException e) {
      logger.error(e);
      throw new DaoException(e);
    } finally {
      try {
        connectionPool.closeConnection(connection, preparedStatement);
      } catch (ConnectionPoolException e) {
        logger.error(e);
        throw new DaoException(e);
      }
    }
  }
}