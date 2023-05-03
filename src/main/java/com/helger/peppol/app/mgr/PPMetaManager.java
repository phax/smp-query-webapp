package com.helger.peppol.app.mgr;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.annotation.UsedViaReflection;
import com.helger.commons.exception.InitializationException;
import com.helger.dao.DAOException;
import com.helger.scope.IScope;
import com.helger.scope.singleton.AbstractGlobalSingleton;

/**
 * Central manager for all sub managers
 *
 * @author Philip Helger
 */
public final class PPMetaManager extends AbstractGlobalSingleton
{
  private static final String SML_INFO_XML = "sml-info.xml";

  private static final Logger LOGGER = LoggerFactory.getLogger (PPMetaManager.class);

  private SMLConfigurationManager m_aSMLConfigurationMgr;

  @Deprecated
  @UsedViaReflection
  public PPMetaManager ()
  {}

  @Override
  protected void onAfterInstantiation (@Nonnull final IScope aScope)
  {
    try
    {
      // Before TestEndpoint manager!
      m_aSMLConfigurationMgr = new SMLConfigurationManager (SML_INFO_XML);

      LOGGER.info ("MetaManager was initialized");
    }
    catch (final DAOException ex)
    {
      throw new InitializationException ("Failed to init MetaManager", ex);
    }
  }

  @Nonnull
  public static PPMetaManager getInstance ()
  {
    return getGlobalSingleton (PPMetaManager.class);
  }

  @Nonnull
  public static ISMLConfigurationManager getSMLConfigurationMgr ()
  {
    return getInstance ().m_aSMLConfigurationMgr;
  }
}
