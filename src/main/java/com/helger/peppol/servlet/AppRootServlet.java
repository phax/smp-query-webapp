package com.helger.peppol.servlet;

import com.helger.commons.http.EHttpMethod;
import com.helger.photon.core.servlet.AbstractPublicApplicationServlet;
import com.helger.photon.core.servlet.RootXServletHandler;
import com.helger.xservlet.AbstractXServlet;

public class AppRootServlet extends AbstractXServlet
{
  public AppRootServlet ()
  {
    handlerRegistry ().registerHandler (EHttpMethod.GET, new RootXServletHandler (AbstractPublicApplicationServlet.SERVLET_DEFAULT_PATH));
    handlerRegistry ().copyHandlerToAll (EHttpMethod.GET);
  }
}
