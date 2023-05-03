package com.helger.peppol.servlet;

import com.helger.peppol.pub.LayoutAreaContentProviderPublic;
import com.helger.peppol.ui.AppLayoutHTMLProvider;
import com.helger.photon.app.html.IHTMLProvider;
import com.helger.photon.core.servlet.AbstractApplicationXServletHandler;
import com.helger.photon.core.servlet.AbstractPublicApplicationServlet;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

public class PublicApplicationServlet extends AbstractPublicApplicationServlet
{
  public PublicApplicationServlet ()
  {
    super (new AbstractApplicationXServletHandler ()
    {
      @Override
      protected IHTMLProvider createHTMLProvider (final IRequestWebScopeWithoutResponse aRequestScope)
      {
        return new AppLayoutHTMLProvider (LayoutAreaContentProviderPublic::getContent);
      }
    });
  }
}
