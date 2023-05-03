package com.helger.peppol.ui;

import com.helger.peppol.app.AppHelper;
import com.helger.peppol.app.CPPApp;
import com.helger.photon.bootstrap4.uictrls.ext.BootstrapLoginManager;

public final class PPLoginManager extends BootstrapLoginManager
{
  public PPLoginManager ()
  {
    super (AppHelper.getApplicationTitle () + " Administration - Login");
    setRequiredRoleIDs (CPPApp.REQUIRED_ROLE_IDS_CONFIG);
  }
}
