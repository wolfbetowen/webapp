package com.epam.tc.command.impl;

import com.epam.tc.command.Command;
import com.epam.tc.command.CommandException;
import com.epam.tc.command.PageName;
import com.epam.tc.command.VariableName;
import com.epam.tc.manager.ConfigurationManager;
import com.epam.tc.manager.MessageManager;
import com.epam.tc.service.PaymentCardService;
import com.epam.tc.service.ServiceException;
import com.epam.tc.service.ServiceFactory;
import com.epam.tc.command.MessageName;
import com.epam.tc.validator.InputDataValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * The type Replenish card command.
 *
 * @author alex raby
 * @version 1.0 card account replenishment
 */
public class ReplenishCardCommand implements Command {

  /**
   * class object Logger {@link Logger}
   * writes important events to a log file
   */
  private static Logger logger = LogManager.getLogger(ReplenishCardCommand.class);

  /** {@inheritDoc} */
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String tempSum = request.getParameter(VariableName.SUM);
    InputDataValidation validation = new InputDataValidation();
    boolean checkSum = validation.checkMoneyField(tempSum);
    if (!checkSum) {
      request.getSession().setAttribute(MessageName.MESSAGE_OPERATION,
              MessageManager.getProperty(MessageName.MESSAGE_INVALID_SUM));
      return ConfigurationManager.getProperty(PageName.CARD_MANAGEMENT);
    }
    BigDecimal sum = new BigDecimal(tempSum);
    int cardId = Integer.parseInt(request.getParameter(VariableName.CARD_ID));
    PaymentCardService paymentCardService = ServiceFactory.getPaymentCardService();
    request.getSession().setAttribute(MessageName.MESSAGE_OPERATION,
            MessageManager.getProperty(MessageName.MESSAGE_OPERATION_DONE));
    request.getSession().setAttribute(VariableName.EDITOR,
            request.getParameter(VariableName.EDITOR));
    try {
      paymentCardService.replenishCard(cardId, sum);
    } catch (ServiceException e) {
      logger.error(e);
      throw new CommandException(e);
    }
    return ConfigurationManager.getProperty(PageName.CARD_MANAGEMENT);
  }
}
