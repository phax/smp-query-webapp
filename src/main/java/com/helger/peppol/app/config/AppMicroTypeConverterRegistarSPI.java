package com.helger.peppol.app.config;

import com.helger.commons.annotation.IsSPIImplementation;
import com.helger.peppol.domain.SMLConfiguration;
import com.helger.peppol.domain.SMLConfigurationMicroTypeConverter;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistrarSPI;
import com.helger.xml.microdom.convert.IMicroTypeConverterRegistry;

/**
 * SPI implementation to register all micro type converters of this application.
 *
 * @author Philip Helger
 */
@IsSPIImplementation
public final class AppMicroTypeConverterRegistarSPI implements IMicroTypeConverterRegistrarSPI
{
  public void registerMicroTypeConverter (final IMicroTypeConverterRegistry aRegistry)
  {
    aRegistry.registerMicroElementTypeConverter (SMLConfiguration.class, new SMLConfigurationMicroTypeConverter ());
  }
}
