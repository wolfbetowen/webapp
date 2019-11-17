package com.epam.webapp.command.impl;

import com.epam.webapp.command.Command;
import com.epam.webapp.command.CommandConst;
import com.epam.webapp.command.exception.CommandException;
import com.epam.webapp.manager.ConfigurationManager;
import com.epam.webapp.manager.MessageManager;
import com.epam.webapp.service.impl.TrainingsServiceImpl;
import com.epam.webapp.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class SendSolutionCommand implements Command {

  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    TrainingsServiceImpl trainingsService = new TrainingsServiceImpl();
    int userId = Integer.parseInt(request.getParameter(CommandConst.USER_ID));
    int taskId = Integer.parseInt(request.getParameter(CommandConst.TASK_ID));
    String solution = request.getParameter(CommandConst.SOLUTION);
    boolean done;
    try {
      done = trainingsService.sendSolution(userId, taskId, solution);
    } catch (ServiceException e) {
      throw new CommandException("Error access service", e);
    }
    if (done) {
      request.getSession().setAttribute(CommandConst.SEND_SOLUTION_MESSAGE,
              MessageManager.getProperty(CommandConst.MESSAGE_SEND_SOLUTION));
      return ConfigurationManager.getProperty(CommandConst.TASK_PAGE);
    }
    request.getSession().setAttribute(CommandConst.SEND_SOLUTION_MESSAGE,
            MessageManager.getProperty(CommandConst.MESSAGE_SEND_SOLUTION_ERROR));
    return ConfigurationManager.getProperty(CommandConst.TASK_PAGE);
  }
}
