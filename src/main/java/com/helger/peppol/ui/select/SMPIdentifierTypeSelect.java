package com.helger.peppol.ui.select;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.peppolid.factory.ESMPIdentifierType;
import com.helger.photon.core.form.RequestField;
import com.helger.photon.uicore.html.select.HCExtSelect;

/**
 * UI select for SMP API types
 *
 * @author Philip Helger
 */
public class SMPIdentifierTypeSelect extends HCExtSelect
{
  public SMPIdentifierTypeSelect (@Nonnull final RequestField aRF, @Nonnull final Locale aDisplayLocale)
  {
    super (aRF);
    addOptionPleaseSelect (aDisplayLocale);
    for (final ESMPIdentifierType e : ESMPIdentifierType.values ())
      addOption (e.getID (), e.getDisplayName ());
  }
}
