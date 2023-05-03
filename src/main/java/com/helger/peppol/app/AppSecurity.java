package com.helger.peppol.app;

import javax.annotation.concurrent.Immutable;

import com.helger.photon.security.mgr.PhotonSecurityManager;
import com.helger.photon.security.role.IRoleManager;
import com.helger.photon.security.user.IUserManager;
import com.helger.photon.security.usergroup.IUserGroupManager;

@Immutable
public final class AppSecurity
{
  private AppSecurity ()
  {}

  public static void init ()
  {
    final IUserManager aUserMgr = PhotonSecurityManager.getUserMgr ();
    final IUserGroupManager aUserGroupMgr = PhotonSecurityManager.getUserGroupMgr ();
    final IRoleManager aRoleMgr = PhotonSecurityManager.getRoleMgr ();

    // Standard users
    if (!aUserMgr.containsWithID (CPPApp.USER_ADMINISTRATOR_ID))
    {
      final boolean bDisabled = false;
      aUserMgr.createPredefinedUser (CPPApp.USER_ADMINISTRATOR_ID,
                                     CPPApp.USER_ADMINISTRATOR_LOGINNAME,
                                     CPPApp.USER_ADMINISTRATOR_EMAIL,
                                     CPPApp.USER_ADMINISTRATOR_PASSWORD,
                                     CPPApp.USER_ADMINISTRATOR_FIRSTNAME,
                                     CPPApp.USER_ADMINISTRATOR_LASTNAME,
                                     CPPApp.USER_ADMINISTRATOR_DESCRIPTION,
                                     CPPApp.USER_ADMINISTRATOR_LOCALE,
                                     CPPApp.USER_ADMINISTRATOR_CUSTOMATTRS,
                                     bDisabled);
    }

    // Create all roles
    if (!aRoleMgr.containsWithID (CPPApp.ROLE_CONFIG_ID))
      aRoleMgr.createPredefinedRole (CPPApp.ROLE_CONFIG_ID,
                                     CPPApp.ROLE_CONFIG_NAME,
                                     CPPApp.ROLE_CONFIG_DESCRIPTION,
                                     CPPApp.ROLE_CONFIG_CUSTOMATTRS);

    // User group Administrators
    if (!aUserGroupMgr.containsWithID (CPPApp.USERGROUP_ADMINISTRATORS_ID))
    {
      aUserGroupMgr.createPredefinedUserGroup (CPPApp.USERGROUP_ADMINISTRATORS_ID,
                                               CPPApp.USERGROUP_ADMINISTRATORS_NAME,
                                               CPPApp.USERGROUP_ADMINISTRATORS_DESCRIPTION,
                                               CPPApp.USERGROUP_ADMINISTRATORS_CUSTOMATTRS);
      // Assign administrator user to administrators user group
      aUserGroupMgr.assignUserToUserGroup (CPPApp.USERGROUP_ADMINISTRATORS_ID, CPPApp.USER_ADMINISTRATOR_ID);
    }
    aUserGroupMgr.assignRoleToUserGroup (CPPApp.USERGROUP_ADMINISTRATORS_ID, CPPApp.ROLE_CONFIG_ID);

    // User group for Config users
    if (!aUserGroupMgr.containsWithID (CPPApp.USERGROUP_CONFIG_ID))
      aUserGroupMgr.createPredefinedUserGroup (CPPApp.USERGROUP_CONFIG_ID,
                                               CPPApp.USERGROUP_CONFIG_NAME,
                                               CPPApp.USERGROUP_CONFIG_DESCRIPTION,
                                               CPPApp.USERGROUP_CONFIG_CUSTOMATTRS);
    aUserGroupMgr.assignRoleToUserGroup (CPPApp.USERGROUP_CONFIG_ID, CPPApp.ROLE_CONFIG_ID);
  }
}
