package com.helger.peppol.secure;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.peppol.app.AppSecurity;
import com.helger.peppol.app.CPPApp;
import com.helger.photon.bootstrap4.pages.BootstrapPagesMenuConfigurator;
import com.helger.photon.bootstrap4.pages.security.BasePageSecurityChangePassword;
import com.helger.photon.core.menu.IMenuItemPage;
import com.helger.photon.core.menu.IMenuTree;
import com.helger.photon.core.menu.filter.MenuObjectFilterUserAssignedToUserGroup;
import com.helger.photon.uicore.page.WebPageExecutionContext;
import com.helger.photon.uicore.page.system.BasePageShowChildren;

@Immutable
public final class MenuSecure
{
  private MenuSecure ()
  {}

  public static void init (@Nonnull final IMenuTree aMenuTree)
  {
    // We need this additional indirection layer, as the pages are initialized
    // statically!
    final MenuObjectFilterUserAssignedToUserGroup aFilterAdministrators = new MenuObjectFilterUserAssignedToUserGroup (AppSecurity.USERGROUP_ADMINISTRATORS_ID);

    // Administrator
    {
      final IMenuItemPage aAdmin = aMenuTree.createRootItem (new BasePageShowChildren <WebPageExecutionContext> (CMenuSecure.MENU_ADMIN,
                                                                                                                 "Administration",
                                                                                                                 aMenuTree));
      // Must be accessible for all Config users
      aMenuTree.createItem (aAdmin,
                            new BasePageSecurityChangePassword <WebPageExecutionContext> (CMenuSecure.MENU_ADMIN_CHANGE_PASSWORD));

      // Admins only
      aMenuTree.createItem (aAdmin, new PageSecureAdminAddons (CMenuSecure.MENU_ADMIN_ADDONS))
               .setDisplayFilter (aFilterAdministrators);
      aMenuTree.createItem (aAdmin, new PageSecureSMLConfiguration (CMenuSecure.MENU_SML_CONFIGURATION))
               .setDisplayFilter (aFilterAdministrators);
      BootstrapPagesMenuConfigurator.addAllItems (aMenuTree, aAdmin, aFilterAdministrators, CPPApp.DEFAULT_LOCALE);
    }

    // Default menu item
    aMenuTree.setDefaultMenuItemID (CMenuSecure.MENU_ADMIN);
  }
}
