package com.helger.peppol.ui;

import java.time.LocalDateTime;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.datetime.PDTToString;
import com.helger.commons.lang.ClassHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.type.ITypedObject;
import com.helger.commons.url.ISimpleURL;
import com.helger.html.css.DefaultCSSClassProvider;
import com.helger.html.css.ICSSClassProvider;
import com.helger.html.hc.IHCNode;
import com.helger.html.hc.html.textlevel.HCA;
import com.helger.html.hc.html.textlevel.HCCode;
import com.helger.html.hc.html.textlevel.HCSmall;
import com.helger.html.hc.impl.HCNodeList;
import com.helger.html.hc.impl.HCTextNode;
import com.helger.html.jquery.JQueryAjaxBuilder;
import com.helger.html.jscode.JSAssocArray;
import com.helger.peppol.app.ajax.CAjax;
import com.helger.peppol.domain.NiceNameEntry;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.photon.bootstrap4.badge.BootstrapBadge;
import com.helger.photon.bootstrap4.badge.EBootstrapBadgeType;
import com.helger.photon.bootstrap4.ext.BootstrapSystemMessage;
import com.helger.photon.bootstrap4.pages.BootstrapPagesMenuConfigurator;
import com.helger.photon.bootstrap4.uictrls.datatables.BootstrapDataTables;
import com.helger.photon.core.menu.IMenuObject;
import com.helger.photon.core.requestparam.RequestParameterHandlerURLPathNamed;
import com.helger.photon.core.requestparam.RequestParameterManager;
import com.helger.photon.security.mgr.PhotonSecurityManager;
import com.helger.photon.security.role.IRole;
import com.helger.photon.security.user.IUser;
import com.helger.photon.security.usergroup.IUserGroup;
import com.helger.photon.security.util.SecurityHelper;
import com.helger.photon.uicore.css.CPageParam;
import com.helger.photon.uicore.page.IWebPageExecutionContext;
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
  private static final Logger LOGGER = LoggerFactory.getLogger (AppCommonUI.class);

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
                                     AjaxExecutorDataTablesI18N.LANGUAGE_ID)
                 .addPlugin (new DataTablesPluginSearchHighlight ());
    });
    // By default allow markdown in system message
    BootstrapSystemMessage.setDefaultUseMarkdown (true);
  }

  @Nullable
  public static IHCNode getDTAndUser (@Nonnull final IWebPageExecutionContext aWPEC,
                                      @Nullable final LocalDateTime aDateTime,
                                      @Nullable final String sUserID)
  {
    final Locale aDisplayLocale = aWPEC.getDisplayLocale ();

    String sDateTime = null;
    if (aDateTime != null)
      sDateTime = PDTToString.getAsString (aDateTime, aDisplayLocale);
    IHCNode aUserName = null;
    if (sUserID != null)
    {
      final IUser aUser = PhotonSecurityManager.getUserMgr ().getUserOfID (sUserID);
      aUserName = createViewLink (aWPEC, aUser);
    }

    if (sDateTime != null)
    {
      if (aUserName != null)
      {
        // Date and user
        return new HCNodeList ().addChildren (new HCTextNode ("on " + sDateTime + " by "), aUserName);
      }

      // Date only
      return new HCTextNode ("on  " + sDateTime);
    }

    if (aUserName != null)
    {
      // User only
      return new HCNodeList ().addChildren (new HCTextNode ("by "), aUserName);
    }

    // Neither nor
    return null;
  }

  @Nonnull
  public static ISimpleURL getViewLink (@Nonnull final IWebPageExecutionContext aWPEC,
                                        @Nonnull @Nonempty final String sMenuItemID,
                                        @Nonnull final ITypedObject <String> aObject)
  {
    ValueEnforcer.notNull (aObject, "Object");

    return getViewLink (aWPEC, sMenuItemID, aObject.getID ());
  }

  @Nonnull
  public static ISimpleURL getViewLink (@Nonnull final IWebPageExecutionContext aWPEC,
                                        @Nonnull @Nonempty final String sMenuItemID,
                                        @Nonnull final String sObjectID)
  {
    return aWPEC.getLinkToMenuItem (sMenuItemID)
                .add (CPageParam.PARAM_ACTION, CPageParam.ACTION_VIEW)
                .add (CPageParam.PARAM_OBJECT, sObjectID);
  }

  @Nonnull
  public static IHCNode createViewLink (@Nonnull final IWebPageExecutionContext aWPEC,
                                        @Nullable final ITypedObject <String> aObject)
  {
    return createViewLink (aWPEC, aObject, null);
  }

  @Nonnull
  public static IHCNode createViewLink (@Nonnull final IWebPageExecutionContext aWPEC,
                                        @Nullable final ITypedObject <String> aObject,
                                        @Nullable final String sDisplayName)
  {
    if (aObject == null)
      return HCTextNode.createOnDemand (sDisplayName);

    final Locale aDisplayLocale = aWPEC.getDisplayLocale ();

    if (aObject instanceof IRole)
    {
      final IRole aTypedObj = (IRole) aObject;
      final String sRealDisplayName = sDisplayName != null ? sDisplayName : aTypedObj.getName ();
      final String sMenuItemID = BootstrapPagesMenuConfigurator.MENU_ADMIN_SECURITY_ROLE;
      final IMenuObject aObj = aWPEC.getMenuTree ().getItemDataWithID (sMenuItemID);
      if (aObj != null && aObj.matchesDisplayFilter ())
        return new HCA (getViewLink (aWPEC, sMenuItemID, aTypedObj)).addChild (sRealDisplayName)
                                                                    .setTitle ("Show details of role '" +
                                                                               sRealDisplayName +
                                                                               "'");
      return new HCTextNode (sRealDisplayName);
    }

    if (aObject instanceof IUser)
    {
      final IUser aTypedObj = (IUser) aObject;
      final String sRealDisplayName = sDisplayName != null ? sDisplayName
                                                           : SecurityHelper.getUserDisplayName (aTypedObj,
                                                                                                aDisplayLocale);
      final String sMenuItemID = BootstrapPagesMenuConfigurator.MENU_ADMIN_SECURITY_USER;
      final IMenuObject aObj = aWPEC.getMenuTree ().getItemDataWithID (sMenuItemID);
      if (aObj != null && aObj.matchesDisplayFilter ())
        return new HCA (getViewLink (aWPEC, sMenuItemID, aTypedObj)).addChild (sRealDisplayName)
                                                                    .setTitle ("Show details of user '" +
                                                                               sRealDisplayName +
                                                                               "'");
      return new HCTextNode (sRealDisplayName);
    }
    if (aObject instanceof IUserGroup)
    {
      final IUserGroup aTypedObj = (IUserGroup) aObject;
      final String sRealDisplayName = sDisplayName != null ? sDisplayName : aTypedObj.getName ();
      final String sMenuItemID = BootstrapPagesMenuConfigurator.MENU_ADMIN_SECURITY_USER_GROUP;
      final IMenuObject aObj = aWPEC.getMenuTree ().getItemDataWithID (sMenuItemID);
      if (aObj != null && aObj.matchesDisplayFilter ())
        return new HCA (getViewLink (aWPEC, sMenuItemID, aTypedObj)).addChild (sRealDisplayName)
                                                                    .setTitle ("Show details of user group '" +
                                                                               sRealDisplayName +
                                                                               "'");
      return new HCTextNode (sRealDisplayName);
    }

    // add other types as desired
    throw new IllegalArgumentException ("Unsupported object: " + aObject);
  }

  @Nonnull
  private static String _getString (@Nonnull final Throwable t)
  {
    return StringHelper.getConcatenatedOnDemand (ClassHelper.getClassLocalName (t.getClass ()), " - ", t.getMessage ());
  }

  @Nonnull
  private static IHCNode _createFormattedID (@Nonnull final String sID,
                                             @Nullable final String sName,
                                             @Nullable final EBootstrapBadgeType eType,
                                             final boolean bIsDeprecated,
                                             final boolean bInDetails)
  {
    if (sName == null)
    {
      // No nice name present
      if (bInDetails)
        return new HCCode ().addChild (sID);
      return new HCTextNode (sID);
    }

    final HCNodeList ret = new HCNodeList ();
    ret.addChild (new BootstrapBadge (eType).addChild (sName));
    if (bIsDeprecated)
    {
      ret.addChild (" ")
         .addChild (new BootstrapBadge (EBootstrapBadgeType.WARNING).addChild ("Identifier is deprecated"));
    }
    if (bInDetails)
    {
      ret.addChild (new HCSmall ().addChild (" (").addChild (new HCCode ().addChild (sID)).addChild (")"));
    }
    return ret;
  }

  @Nonnull
  private static IHCNode _createID (@Nonnull final String sID,
                                    @Nullable final NiceNameEntry aNiceName,
                                    final boolean bInDetails)
  {
    if (aNiceName == null)
      return _createFormattedID (sID, null, null, false, bInDetails);
    return _createFormattedID (sID,
                               aNiceName.getName (),
                               EBootstrapBadgeType.SUCCESS,
                               aNiceName.isDeprecated (),
                               bInDetails);
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsMap <String, NiceNameEntry> getDocTypeNames ()
  {
    return DOCTYPE_NAMES.getClone ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsMap <String, NiceNameEntry> getProcessNames ()
  {
    return PROCESS_NAMES.getClone ();
  }
}
