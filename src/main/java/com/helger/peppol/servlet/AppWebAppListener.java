/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppol.servlet;

import javax.annotation.Nonnull;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.helger.commons.vendor.VendorInfo;
import com.helger.dns.config.DNSConfig;
import com.helger.httpclient.HttpDebugger;
import com.helger.peppol.app.AppConfig;
import com.helger.peppol.app.AppInternalErrorHandler;
import com.helger.peppol.app.AppSecurity;
import com.helger.peppol.app.CPPApp;
import com.helger.peppol.app.ajax.CAjax;
import com.helger.peppol.app.mgr.PPMetaManager;
import com.helger.peppol.pub.MenuPublic;
import com.helger.peppol.rest.PPAPI;
import com.helger.peppol.secure.MenuSecure;
import com.helger.peppol.ui.AppCommonUI;
import com.helger.photon.ajax.IAjaxRegistry;
import com.helger.photon.api.IAPIRegistry;
import com.helger.photon.bootstrap4.servlet.WebAppListenerBootstrap;
import com.helger.photon.core.appid.CApplicationID;
import com.helger.photon.core.appid.PhotonGlobalState;
import com.helger.photon.core.locale.ILocaleManager;
import com.helger.photon.core.menu.MenuTree;

import jakarta.servlet.ServletContext;

/**
 * This listener is invoked during the servlet initialization. This is basically
 * a ServletContextListener.
 *
 * @author Philip Helger
 */
public final class AppWebAppListener extends WebAppListenerBootstrap
{
  @Override
  protected String getInitParameterDebug (@Nonnull final ServletContext aSC)
  {
    return AppConfig.getGlobalDebug ();
  }

  @Override
  protected String getInitParameterProduction (@Nonnull final ServletContext aSC)
  {
    return AppConfig.getGlobalProduction ();
  }

  @Override
  protected String getDataPath (@Nonnull final ServletContext aSC)
  {
    return AppConfig.getDataPath ();
  }

  @Override
  protected boolean shouldCheckFileAccess (@Nonnull final ServletContext aSC)
  {
    return AppConfig.isCheckFileAccess ();
  }

  @Override
  protected void initGlobalSettings ()
  {
    // Disable DNS caching
    DNSConfig.setDNSCacheTime (0);
    HttpDebugger.setEnabled (false);

    // JUL to SLF4J
    SLF4JBridgeHandler.removeHandlersForRootLogger ();
    SLF4JBridgeHandler.install ();

    VendorInfo.setVendorName ("Philip Helger");
    VendorInfo.setVendorURL ("http://www.helger.com");
    VendorInfo.setVendorEmail ("philip@helger.com");
    VendorInfo.setVendorLocation ("Vienna, Austria");
    VendorInfo.setInceptionYear (2023);
  }

  @Override
  protected void initLocales (@Nonnull final ILocaleManager aLocaleMgr)
  {
    aLocaleMgr.registerLocale (CPPApp.LOCALE_EN);
    aLocaleMgr.setDefaultLocale (CPPApp.DEFAULT_LOCALE);
  }

  @Override
  protected void initAjax (@Nonnull final IAjaxRegistry aAjaxRegistry)
  {
    aAjaxRegistry.registerFunction (CAjax.DATATABLES);
    aAjaxRegistry.registerFunction (CAjax.DATATABLES_I18N);
  }

  @Override
  protected void initAPI (@Nonnull final IAPIRegistry aAPIRegistry)
  {
    PPAPI.init (aAPIRegistry);
  }

  @Override
  protected void initMenu ()
  {
    // Create all menu items
    {
      final MenuTree aMenuTree = new MenuTree ();
      MenuPublic.init (aMenuTree);
      PhotonGlobalState.state (CApplicationID.APP_ID_PUBLIC).setMenuTree (aMenuTree);
    }
    {
      final MenuTree aMenuTree = new MenuTree ();
      MenuSecure.init (aMenuTree);
      PhotonGlobalState.state (CApplicationID.APP_ID_SECURE).setMenuTree (aMenuTree);
    }
  }

  @Override
  protected void initSecurity ()
  {
    // Set all security related stuff
    AppSecurity.init ();
  }

  @Override
  protected void initUI ()
  {
    // UI stuff
    AppCommonUI.init ();
  }

  @Override
  protected void initManagers ()
  {
    // Load managers
    PPMetaManager.getInstance ();

    // Setup error handler
    AppInternalErrorHandler.doSetup ();
  }
}
