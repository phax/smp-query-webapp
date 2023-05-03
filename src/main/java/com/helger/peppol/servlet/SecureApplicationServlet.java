package com.helger.peppol.servlet;

import com.helger.peppol.secure.LayoutAreaContentProviderSecure;
import com.helger.peppol.ui.AppLayoutHTMLProvider;
import com.helger.photon.app.html.IHTMLProvider;
import com.helger.photon.core.servlet.AbstractApplicationXServletHandler;
import com.helger.photon.core.servlet.AbstractSecureApplicationServlet;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

/**
 * The servlet to show the secure application
 *
 * @author Philip Helger
 */
public class SecureApplicationServlet extends AbstractSecureApplicationServlet
{
  public SecureApplicationServlet ()
  {
    super (new AbstractApplicationXServletHandler ()
    {
      @Override
      protected IHTMLProvider createHTMLProvider (final IRequestWebScopeWithoutResponse aRequestScope)
      {
        return new AppLayoutHTMLProvider (LayoutAreaContentProviderSecure::getContent);
      }
    });
  }
}
