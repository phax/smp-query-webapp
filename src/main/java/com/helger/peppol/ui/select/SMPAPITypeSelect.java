package com.helger.peppol.ui.select;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.peppol.sml.ESMPAPIType;
import com.helger.photon.core.form.RequestField;
import com.helger.photon.uicore.html.select.HCExtSelect;

/**
 * UI select for SMP API types
 *
 * @author Philip Helger
 */
public class SMPAPITypeSelect extends HCExtSelect
{
  public SMPAPITypeSelect (@Nonnull final RequestField aRF, @Nonnull final Locale aDisplayLocale)
  {
    super (aRF);
    addOptionPleaseSelect (aDisplayLocale);
    for (final ESMPAPIType e : ESMPAPIType.values ())
      addOption (e.getID (), e.getDisplayName ());
  }
}
