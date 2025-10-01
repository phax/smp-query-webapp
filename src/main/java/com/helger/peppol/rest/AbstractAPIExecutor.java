/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.peppol.rest;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.base.timing.StopWatch;
import com.helger.json.serialize.JsonWriterSettings;
import com.helger.photon.api.IAPIDescriptor;
import com.helger.photon.api.IAPIExecutor;
import com.helger.photon.app.PhotonUnifiedResponse;
import com.helger.servlet.response.UnifiedResponse;
import com.helger.smpclient.httpclient.SMPHttpClientSettings;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

import jakarta.annotation.Nonnull;

public abstract class AbstractAPIExecutor implements IAPIExecutor
{
  protected static final AtomicInteger COUNTER = new AtomicInteger (0);
  protected static final String USER_AGENT = "phax-SMP-Query-WebApp/1.0 (https://github.com/phax/smp-query-webapp)";
  public static final Consumer <? super SMPHttpClientSettings> SMP_HCS_MODIFIER = hcs -> {
    hcs.setUserAgent (USER_AGENT);
  };

  private static final Logger LOGGER = LoggerFactory.getLogger (AbstractAPIExecutor.class);

  protected AbstractAPIExecutor ()
  {}

  protected abstract void invokeAPI (@Nonnull final IAPIDescriptor aAPIDescriptor,
                                     @Nonnull @Nonempty final String sPath,
                                     @Nonnull final Map <String, String> aPathVariables,
                                     @Nonnull final IRequestWebScopeWithoutResponse aRequestScope,
                                     @Nonnull final PhotonUnifiedResponse aUnifiedResponse) throws Exception;

  public final void invokeAPI (@Nonnull final IAPIDescriptor aAPIDescriptor,
                               @Nonnull @Nonempty final String sPath,
                               @Nonnull final Map <String, String> aPathVariables,
                               @Nonnull final IRequestWebScopeWithoutResponse aRequestScope,
                               @Nonnull final UnifiedResponse aUnifiedResponse) throws Exception
  {
    final StopWatch aSW = StopWatch.createdStarted ();

    final PhotonUnifiedResponse aPUR = (PhotonUnifiedResponse) aUnifiedResponse;
    aPUR.setJsonWriterSettings (JsonWriterSettings.DEFAULT_SETTINGS_FORMATTED);
    invokeAPI (aAPIDescriptor, sPath, aPathVariables, aRequestScope, aPUR);

    aSW.stop ();
    if (aSW.getMillis () > 100)
      LOGGER.info ("[API] Succesfully finished '" +
                   aAPIDescriptor.getPathDescriptor ().getAsURLString () +
                   "' after " +
                   aSW.getMillis () +
                   " milliseconds");
  }
}
