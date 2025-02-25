package com.epam.tc.service.impl;

import com.epam.tc.dao.DaoException;
import com.epam.tc.entity.Topic;
import com.epam.tc.service.ServiceException;
import com.epam.tc.dao.DaoFactory;
import com.epam.tc.dao.TopicDao;
import com.epam.tc.service.TopicService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * this class implements interface methods TopicService {@link TopicService} methods of this class
 * catch DaoException {@link DaoException} and throw ServiceException {@link ServiceException}
 *
 * @author alex raby
 * @version 1.0
 */
public class TopicServiceImpl implements TopicService {

  /**
   * class object Logger {@link Logger}
   * writes important events to a log file
   */
  private static Logger logger = LogManager.getLogger(TopicServiceImpl.class);

  /**
   * {@inheritDoc}
   */
  public List<Topic> findTopicsForTraining(int trainingId) throws ServiceException {
    TopicDao topicDao = DaoFactory.getTopicDao();
    List<Topic> topics;
    try {
      topics = topicDao.findTopicsForTraining(trainingId);
    } catch (DaoException e) {
      logger.error(e);
      throw new ServiceException("Error access database", e);
    }
    return topics;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Topic findTopic(int topicId) throws ServiceException {
    TopicDao topicDao = DaoFactory.getTopicDao();
    try {
      return topicDao.findTopic(topicId);
    } catch (DaoException e) {
      logger.error(e);
      throw new ServiceException("Error access database", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addTopicForTraining(int trainingId, String topicsName,
                                  String topicsText) throws ServiceException {
    TopicDao topicDao = DaoFactory.getTopicDao();
    try {
      topicDao.addTopicForTraining(trainingId, topicsName, topicsText);
    } catch (DaoException e) {
      logger.error(e);
      throw new ServiceException("Error access database", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateTrainingsTopic(int topicId, String topicName, String topic) throws ServiceException {
    TopicDao topicDao = DaoFactory.getTopicDao();
    try {
      topicDao.updateTrainingsTopic(topicId, topicName, topic);
    } catch (DaoException e) {
      logger.error(e);
      throw new ServiceException("Error access database", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean checkTopicStatus(int userId, int topicId) throws ServiceException {
    TopicDao topicDao = DaoFactory.getTopicDao();
    try {
      return topicDao.checkTopicStatus(userId, topicId);
    } catch (DaoException e) {
      logger.error(e);
      throw new ServiceException("Error access database", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void markTopic(int userId, int topicId) throws ServiceException {
    TopicDao topicDao = DaoFactory.getTopicDao();
    try {
      topicDao.markTopic(userId, topicId);
    } catch (DaoException e) {
      logger.error(e);
      throw new ServiceException("Error access database", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Topic> findLearnedTopics(int studentId, int trainingId) throws ServiceException {
    TopicDao topicDao = DaoFactory.getTopicDao();
    try {
      return topicDao.findLearnedTopics(studentId, trainingId);
    } catch (DaoException e) {
      logger.error(e);
      throw new ServiceException("Error access database", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteTopic(int topicId) throws ServiceException {
    TopicDao topicDao = DaoFactory.getTopicDao();
    try {
      topicDao.deleteTopic(topicId);
    } catch (DaoException e) {
      logger.error(e);
      throw new ServiceException(e);
    }
  }
}
