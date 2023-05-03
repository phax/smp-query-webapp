package com.helger.peppol.secure;

import javax.annotation.concurrent.Immutable;

/**
 * Menu items for the secure application
 *
 * @author Philip Helger
 */
@Immutable
public final class CMenuSecure
{
  // Menu item IDs
  public static final String MENU_ADMIN = "admin";
  public static final String MENU_ADMIN_CHANGE_PASSWORD = "admin_change_password";
  public static final String MENU_ADMIN_ADDONS = "admin-addons";
  public static final String MENU_SML_CONFIGURATION = "sml_configuration";

  private CMenuSecure ()
  {}
}
