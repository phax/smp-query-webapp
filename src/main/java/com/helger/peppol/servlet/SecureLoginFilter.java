package com.helger.peppol.servlet;

import javax.annotation.Nonnull;

import com.helger.commons.state.EContinue;
import com.helger.peppol.app.CPPApp;
import com.helger.peppol.ui.PPLoginManager;
import com.helger.photon.core.servlet.AbstractUnifiedResponseFilter;
import com.helger.photon.security.login.LoggedInUserManager;
import com.helger.photon.security.util.SecurityHelper;
import com.helger.servlet.response.UnifiedResponse;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A special servlet filter that checks that a user can only access the config
 * application after authenticating.
 *
 * @author Philip Helger
 */
public final class SecureLoginFilter extends AbstractUnifiedResponseFilter
{
  private PPLoginManager m_aLogin;

  @Override
  public void init () throws ServletException
  {
    super.init ();
    // Make the application login configurable if you like
    m_aLogin = new PPLoginManager ();
  }

  @Override
  @Nonnull
  protected EContinue handleRequest (@Nonnull final IRequestWebScopeWithoutResponse aRequestScope,
                                     @Nonnull final UnifiedResponse aUnifiedResponse) throws ServletException
  {
    if (m_aLogin.checkUserAndShowLogin (aRequestScope, aUnifiedResponse).isBreak ())
    {
      // Show login screen
      return EContinue.BREAK;
    }

    // Check if the currently logged in user has the required roles
    final String sCurrentUserID = LoggedInUserManager.getInstance ().getCurrentUserID ();
    if (!SecurityHelper.hasUserAllRoles (sCurrentUserID, CPPApp.REQUIRED_ROLE_IDS_CONFIG))
    {
      aUnifiedResponse.setStatus (HttpServletResponse.SC_FORBIDDEN);
      return EContinue.BREAK;
    }

    return EContinue.CONTINUE;
  }
}
