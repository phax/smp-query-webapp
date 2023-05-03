package com.helger.peppol.app;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.email.EmailAddress;
import com.helger.photon.core.interror.InternalErrorBuilder;
import com.helger.photon.core.interror.InternalErrorSettings;
import com.helger.photon.core.interror.callback.AbstractErrorCallback;
import com.helger.photon.core.mgr.PhotonCoreManager;
import com.helger.photon.core.smtp.CNamedSMTPSettings;
import com.helger.photon.core.smtp.NamedSMTPSettings;
import com.helger.smtp.settings.ISMTPSettings;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

public final class AppInternalErrorHandler extends AbstractErrorCallback
{
  @Override
  protected void onError (@Nonnull final Throwable t,
                          @Nullable final IRequestWebScopeWithoutResponse aRequestScope,
                          @Nonnull @Nonempty final String sErrorCode,
                          @Nullable final Map <String, String> aCustomAttrs)
  {
    new InternalErrorBuilder ().setThrowable (t)
                               .setRequestScope (aRequestScope)
                               .addErrorMessage (sErrorCode)
                               .addCustomData (aCustomAttrs)
                               .handle ();
  }

  public static void doSetup ()
  {
    // Set global internal error handlers
    new AppInternalErrorHandler ().install ();

    final NamedSMTPSettings aNamedSettings = PhotonCoreManager.getSMTPSettingsMgr ()
                                                              .getSettings (CNamedSMTPSettings.NAMED_SMTP_SETTINGS_DEFAULT_ID);
    final ISMTPSettings aSMTPSettings = aNamedSettings == null ? null : aNamedSettings.getSMTPSettings ();
    InternalErrorSettings.setSMTPSenderAddress (new EmailAddress ("peppol@helger.com", "peppol.helger.com application"));
    InternalErrorSettings.setSMTPReceiverAddress (new EmailAddress ("philip@helger.com", "Philip"));
    InternalErrorSettings.setSMTPSettings (aSMTPSettings);
    InternalErrorSettings.setFallbackLocale (CPPApp.DEFAULT_LOCALE);
  }
}
