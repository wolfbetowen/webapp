package com.epam.webapp.service.impl;

import com.epam.webapp.dao.DaoException;
import com.epam.webapp.dao.DaoFactory;
import com.epam.webapp.dao.PaymentCardDao;
import com.epam.webapp.entity.PaymentCard;
import com.epam.webapp.service.PaymentCardService;
import com.epam.webapp.service.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public class PaymentCardServiceImpl implements PaymentCardService {

  @Override
  public List<PaymentCard> findUsersCard(int userId) throws ServiceException {
    PaymentCardDao paymentCardDao = DaoFactory.getPaymentCardDao();
    try {
      return paymentCardDao.findUsersCard(userId);
    } catch (DaoException e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public void replenishCard(int cardId, BigDecimal sum) throws ServiceException {
    PaymentCardDao paymentCardDao = DaoFactory.getPaymentCardDao();
    try {
      paymentCardDao.replenishCard(cardId, sum);
    } catch (DaoException e) {
      throw new ServiceException(e); //TODO logger
    }
  }

  @Override
  public boolean transferMoneyCardToCard(int cardDonor, int cardRecipient, BigDecimal sum) throws ServiceException {
    PaymentCardDao paymentCardDao = DaoFactory.getPaymentCardDao();
    System.out.println("it is service");
    try {
      return paymentCardDao.transferMoneyCardToCard(cardDonor, cardRecipient, sum);
    } catch (DaoException e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public boolean paymentConsultation(int cardId, int consultationId, int userId) throws ServiceException {
    PaymentCardDao paymentCardDao = DaoFactory.getPaymentCardDao();
    try {
      return paymentCardDao.paymentConsultation(cardId, consultationId, userId);
    } catch (DaoException e) {
      throw new ServiceException(e);
    }
  }
}
