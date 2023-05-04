package com.helger.peppol.app;

import java.util.Locale;

import javax.annotation.concurrent.Immutable;

import com.helger.commons.locale.LocaleCache;

/**
 * Contains application wide constants.
 *
 * @author Philip Helger
 */
@Immutable
public final class CPPApp
{
  public static final Locale LOCALE_EN = LocaleCache.getInstance ().getLocale ("en", "US");
  public static final Locale DEFAULT_LOCALE = LOCALE_EN;

  private CPPApp ()
  {}
}
