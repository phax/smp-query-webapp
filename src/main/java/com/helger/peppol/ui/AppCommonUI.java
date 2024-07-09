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
package com.helger.peppol.ui;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.string.StringHelper;
import com.helger.html.css.DefaultCSSClassProvider;
import com.helger.html.css.ICSSClassProvider;
import com.helger.html.jquery.JQueryAjaxBuilder;
import com.helger.html.jscode.JSAssocArray;
import com.helger.peppol.app.ajax.CAjax;
import com.helger.peppol.domain.NiceNameEntry;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.photon.bootstrap4.ext.BootstrapSystemMessage;
import com.helger.photon.bootstrap4.uictrls.datatables.BootstrapDataTables;
import com.helger.photon.core.requestparam.RequestParameterHandlerURLPathNamed;
import com.helger.photon.core.requestparam.RequestParameterManager;
import com.helger.photon.uictrls.datatables.DataTablesLengthMenu;
import com.helger.photon.uictrls.datatables.EDataTablesFilterType;
import com.helger.photon.uictrls.datatables.ajax.AjaxExecutorDataTables;
import com.helger.photon.uictrls.datatables.ajax.AjaxExecutorDataTablesI18N;
import com.helger.photon.uictrls.datatables.plugins.DataTablesPluginSearchHighlight;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

@Immutable
public final class AppCommonUI
{
  public static final ICSSClassProvider CSS_CLASS_LOGO1 = DefaultCSSClassProvider.create ("logo1");
  public static final ICSSClassProvider CSS_CLASS_LOGO2 = DefaultCSSClassProvider.create ("logo2");

  private static final DataTablesLengthMenu LENGTH_MENU = new DataTablesLengthMenu ().addItem (25)
                                                                                     .addItem (50)
                                                                                     .addItem (100)
                                                                                     .addItemAll ();

  private static final ICommonsMap <String, NiceNameEntry> DOCTYPE_NAMES = new CommonsHashMap <> ();
  private static final ICommonsMap <String, NiceNameEntry> PROCESS_NAMES = new CommonsHashMap <> ();

  @Nonnull
  private static String _ensurePrefix (@Nonnull final String sPrefix, @Nonnull final String s)
  {
    final String sReal = StringHelper.trimStart (s, "PEPPOL").trim ();

    if (sReal.startsWith (sPrefix))
      return sReal;
    return sPrefix + sReal;
  }

  static
  {
    for (final com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier e : com.helger.peppolid.peppol.doctype.EPredefinedDocumentTypeIdentifier.values ())
      DOCTYPE_NAMES.put (e.getURIEncoded (),
                         new NiceNameEntry (_ensurePrefix ("Peppol ", e.getCommonName ()),
                                            e.isDeprecated (),
                                            e.getAllProcessIDs ()));
    for (final com.helger.peppolid.peppol.process.EPredefinedProcessIdentifier e : com.helger.peppolid.peppol.process.EPredefinedProcessIdentifier.values ())
      PROCESS_NAMES.put (e.getURIEncoded (), new NiceNameEntry ("Peppol predefined", e.isDeprecated (), null));

    // Custom document types
    final PeppolIdentifierFactory PIF = PeppolIdentifierFactory.INSTANCE;
    DOCTYPE_NAMES.put ("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biitrns010:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0:extended:e-fff:ver3.0::2.1",
                       new NiceNameEntry ("e-FFF 3.0 Invoice",
                                          true,
                                          new CommonsArrayList <> (PIF.createProcessIdentifierWithDefaultScheme ("urn:www.cenbii.eu:profile:bii05:ver1.0"))));
    DOCTYPE_NAMES.put ("busdox-docid-qns::urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2::CreditNote##urn:www.cenbii.eu:transaction:biitrns014:ver2.0:extended:urn:www.peppol.eu:bis:peppol5a:ver2.0:extended:e-fff:ver3.0::2.1",
                       new NiceNameEntry ("e-FFF 3.0 CreditNote",
                                          true,
                                          new CommonsArrayList <> (PIF.createProcessIdentifierWithDefaultScheme ("urn:www.cenbii.eu:profile:bii05:ver1.0"))));
  }

  private AppCommonUI ()
  {}

  public static void init ()
  {
    RequestParameterManager.getInstance ().setParameterHandler (new RequestParameterHandlerURLPathNamed ());

    BootstrapDataTables.setConfigurator ( (aLEC, aTable, aDataTables) -> {
      final IRequestWebScopeWithoutResponse aRequestScope = aLEC.getRequestScope ();
      aDataTables.setAutoWidth (false)
                 .setLengthMenu (LENGTH_MENU)
                 .setAjaxBuilder (new JQueryAjaxBuilder ().url (CAjax.DATATABLES.getInvocationURL (aRequestScope))
                                                          .data (new JSAssocArray ().add (AjaxExecutorDataTables.OBJECT_ID,
                                                                                          aTable.getID ())))
                 .setServerFilterType (EDataTablesFilterType.ALL_TERMS_PER_ROW)
                 .setTextLoadingURL (CAjax.DATATABLES_I18N.getInvocationURL (aRequestScope),
                                     AjaxExecutorDataTablesI18N.REQUEST_PARAM_LANGUAGE_ID,
                                     AjaxExecutorDataTablesI18N.REQUEST_PARAM_MAX_PAGES)
                 .addPlugin (new DataTablesPluginSearchHighlight ());
    });
    // By default allow markdown in system message
    BootstrapSystemMessage.setDefaultUseMarkdown (true);
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsMap <String, NiceNameEntry> getDocTypeNames ()
  {
    return DOCTYPE_NAMES.getClone ();
  }
}
