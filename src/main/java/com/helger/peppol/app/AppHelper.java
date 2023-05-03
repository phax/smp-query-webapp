package com.helger.peppol.app;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotation.Nonempty;

/**
 * Misc utility methods
 *
 * @author Philip Helger
 */
@Immutable
public final class AppHelper
{
  private AppHelper ()
  {}

  @Nonnull
  @Nonempty
  public static String getApplicationTitle ()
  {
    return "SMP-QueryWebApp" + (AppConfig.isTestVersion () ? " [TEST]" : "");
  }
}
