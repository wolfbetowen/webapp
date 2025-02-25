package com.epam.tc.command.impl;

import com.epam.tc.command.Command;
import com.epam.tc.command.CommandException;
import com.epam.tc.command.MessageName;
import com.epam.tc.command.VariableName;
import com.epam.tc.command.PageName;
import com.epam.tc.entity.Training;
import com.epam.tc.manager.ConfigurationManager;
import com.epam.tc.manager.MessageManager;
import com.epam.tc.service.ServiceFactory;
import com.epam.tc.service.TrainingService;
import com.epam.tc.service.ServiceException;
import com.epam.tc.validator.InputDataValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Create training command.
 *
 * @author alex raby
 * @version 1.0 creating a new Training {@link Training}
 */
public class CreateTrainingCommand implements Command {

  /**
   * class object Logger {@link Logger}
   * writes important events to a log file
   */
  private static final Logger logger = LogManager.getLogger(CreateTrainingCommand.class);

  /** {@inheritDoc} */
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    TrainingService trainingsService = ServiceFactory.getTrainingService();
    String trainingName = request.getParameter(VariableName.TRAINING_NAME);
    int mentorId = Integer.parseInt(request.getParameter(VariableName.MENTOR_ID));
    String trainingDescription = request.getParameter(VariableName.DESCRIPTION);
    InputDataValidation validation = new InputDataValidation();
    trainingDescription = validation.stripXSS(trainingDescription);
    trainingDescription = validation.deleteExcessiveSpace(trainingDescription);
    trainingName = validation.stripXSS(trainingName);
    trainingName = validation.deleteExcessiveSpace(trainingName);
    boolean checkName = validation.checkSizeTextArea(trainingName, 2, 70);
    boolean checkDescription = validation.checkSizeTextArea(trainingDescription, 50, 1000);
    if (!checkName) {
      request.getSession().setAttribute(VariableName.NAME, trainingName);
      request.getSession().setAttribute(VariableName.TEXT, trainingDescription);
      request.getSession().setAttribute(MessageName.MESSAGE_ABOUT_CHANGES,
              MessageManager.getProperty(MessageName.MESSAGE_TEXTAREA_NAME_SIZE));
      return ConfigurationManager.getProperty(PageName.CREATE_TEXT_PAGE);
    }
    if (!checkDescription) {
      request.getSession().setAttribute(VariableName.NAME, trainingName);
      request.getSession().setAttribute(VariableName.TEXT, trainingDescription);
      request.getSession().setAttribute(MessageName.MESSAGE_ABOUT_CHANGES,
              MessageManager.getProperty(MessageName.MESSAGE_TEXTAREA_SIZE));
      return ConfigurationManager.getProperty(PageName.CREATE_TEXT_PAGE);
    }
    try {
       trainingsService.createTraining(trainingName, mentorId, trainingDescription);
    } catch (ServiceException e) {
      logger.error(e);
      throw new CommandException("Error access service", e);
    }
    request.getSession().setAttribute(VariableName.REDIRECT_TO_PAGE, VariableName.TRAINING_INFORMATION);
      request.getSession().setAttribute(MessageName.MESSAGE_ABOUT_CHANGES,
              MessageManager.getProperty(MessageName.MESSAGE_CHANGES_SAVED));
      return ConfigurationManager.getProperty(PageName.TRAININGS_PAGE);
  }
}
