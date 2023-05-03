package com.helger.peppol.app;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.UsedViaReflection;
import com.helger.commons.debug.GlobalDebug;
import com.helger.config.ConfigFactory;
import com.helger.config.IConfig;
import com.helger.scope.singleton.AbstractGlobalSingleton;

/**
 * This class provides access to the settings as contained in the
 * <code>webapp.properties</code> file.
 *
 * @author Philip Helger
 */
public final class AppConfig extends AbstractGlobalSingleton
{
  /** The name of the file containing the settings */
  private static final IConfig CONFIG = ConfigFactory.getDefaultConfig ();

  @Deprecated
  @UsedViaReflection
  private AppConfig ()
  {}

  @Nonnull
  public static IConfig getConfig ()
  {
    return CONFIG;
  }

  @Nullable
  public static String getGlobalDebug ()
  {
    return getConfig ().getAsString ("global.debug");
  }

  @Nullable
  public static String getGlobalProduction ()
  {
    return getConfig ().getAsString ("global.production");
  }

  @Nullable
  public static String getDataPath ()
  {
    return getConfig ().getAsString ("webapp.datapath");
  }

  public static boolean isCheckFileAccess ()
  {
    return getConfig ().getAsBoolean ("webapp.checkfileaccess", true);
  }

  public static boolean isTestVersion ()
  {
    return getConfig ().getAsBoolean ("webapp.testversion", GlobalDebug.isDebugMode ());
  }

  public static boolean isRestLogExceptions ()
  {
    return getConfig ().getAsBoolean ("rest.log.exceptions", GlobalDebug.isDebugMode ());
  }

  public static boolean isRestExceptionsWithPayload ()
  {
    return getConfig ().getAsBoolean ("rest.exceptions.payload", GlobalDebug.isDebugMode ());
  }
}
