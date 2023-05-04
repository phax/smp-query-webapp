package com.helger.peppol.ui;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.io.resource.IReadableResource;
import com.helger.html.EHTMLVersion;
import com.helger.html.hc.IHCNode;
import com.helger.photon.bootstrap4.pages.BootstrapWebPageUIHandler;
import com.helger.photon.uicore.page.WebPageExecutionContext;
import com.helger.photon.uicore.page.external.BasePageViewExternal;
import com.helger.photon.uicore.page.external.PageViewExternalHTMLCleanser;
import com.helger.xml.microdom.IMicroContainer;
import com.helger.xml.microdom.util.MicroVisitor;

public class AppPageViewExternal extends BasePageViewExternal <WebPageExecutionContext>
{
  private static void _cleanCode (@Nonnull final IMicroContainer aCont)
  {
    // Do not clean texts, because this destroys "pre" formatting!
    final PageViewExternalHTMLCleanser aCleanser = new PageViewExternalHTMLCleanser (EHTMLVersion.HTML5).setCleanTexts (false);
    MicroVisitor.visit (aCont, aCleanser);
  }

  public AppPageViewExternal (@Nonnull @Nonempty final String sID,
                              @Nonnull final String sName,
                              @Nonnull final IReadableResource aResource)
  {
    // Special content cleaner
    super (sID, sName, aResource, AppPageViewExternal::_cleanCode);
  }

  @Override
  @Nullable
  public IHCNode getHeaderNode (@Nonnull final WebPageExecutionContext aWPEC)
  {
    final String sHeaderText = getHeaderText (aWPEC);
    return BootstrapWebPageUIHandler.INSTANCE.createPageHeader (sHeaderText);
  }
}
