package com.epam.tc.dao;

/**
 * Dao Exception extends Exception {@link Exception}
 *
 * @author alex raby
 * @version 1.0
 */
public class DaoException extends Exception {

  /**
   * Instantiates a new Dao exception.
   */
  public DaoException() {
    super();
  }

  /**
   * Instantiates a new Dao exception.
   *
   * @param message the message
   */
  public DaoException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Dao exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public DaoException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new Dao exception.
   *
   * @param cause the cause
   */
  public DaoException(Throwable cause) {
    super(cause);
  }
}
