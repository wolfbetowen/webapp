package com.epam.tc.command.impl;

import com.epam.tc.entity.Task;
import com.epam.tc.manager.ConfigurationManager;
import com.epam.tc.manager.MessageManager;
import com.epam.tc.service.ServiceException;
import com.epam.tc.service.ServiceFactory;
import com.epam.tc.service.TaskService;
import com.epam.tc.command.Command;
import com.epam.tc.command.CommandException;
import com.epam.tc.command.MessageName;
import com.epam.tc.command.VariableName;
import com.epam.tc.command.PageName;
import com.epam.tc.validator.InputDataValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Send solution command.
 *
 * @author alex raby
 * @version 1.0 submitting a solution for a Task {@link Task}
 */
public class SendSolutionCommand implements Command {

  /**
   * class object Logger {@link Logger}
   * writes important events to a log file
   */
  private static final Logger logger = LogManager.getLogger(SendSolutionCommand.class);

  /** {@inheritDoc} */
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    TaskService taskService = ServiceFactory.getTaskService();
    int userId = Integer.parseInt(request.getParameter(VariableName.USER_ID));
    int taskId = Integer.parseInt(request.getParameter(VariableName.TASK_ID));
    InputDataValidation validation = new InputDataValidation();
    String solution = request.getParameter(VariableName.SOLUTION);
    solution = validation.stripXSS(solution);
    solution = validation.deleteExcessiveSpace(solution);
    boolean checkSolutionSize = validation.checkSizeTextArea(solution, 10, 1000);
    if (!checkSolutionSize) {
      request.getSession().setAttribute(MessageName.SEND_SOLUTION_MESSAGE,
              MessageManager.getProperty(MessageName.MESSAGE_TEXTAREA_SIZE));
      return ConfigurationManager.getProperty(PageName.TASK_PAGE);
    }
    try {
       taskService.sendSolution(userId, taskId, solution);
    } catch (ServiceException e) {
      logger.error(e);
      throw new CommandException("Error access service", e);
    }
      request.getSession().setAttribute(MessageName.SEND_SOLUTION_MESSAGE,
              MessageManager.getProperty(MessageName.MESSAGE_SEND_SOLUTION));
      return ConfigurationManager.getProperty(PageName.TASK_PAGE);
  }
}
