package com.helger.peppol.pub;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.io.resource.ClassPathResource;
import com.helger.peppol.ui.page.AppPageViewExternal;
import com.helger.photon.core.menu.IMenuTree;

@Immutable
public final class MenuPublic
{
  public static final String MENU_INDEX = "index";

  private MenuPublic ()
  {}

  public static void init (@Nonnull final IMenuTree aMenuTree)
  {
    // Common stuff
    aMenuTree.createRootItem (new AppPageViewExternal (MENU_INDEX,
                                                       "Overview",
                                                       new ClassPathResource ("viewpages/en/index.xml")));

    // Set default
    aMenuTree.setDefaultMenuItemID (MENU_INDEX);
  }
}
