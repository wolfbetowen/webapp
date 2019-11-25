package com.epam.tc.command.impl;

import com.epam.tc.command.Command;
import com.epam.tc.command.PageName;
import com.epam.tc.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alex raby
 * @version 1.0
 * log out command, closing session
 */
public class LogOutCommand implements Command {

  @Override
  public String execute(HttpServletRequest request) {
    request.getSession().invalidate();
    return ConfigurationManager.getProperty(PageName.INDEX_PAGE);
  }
}