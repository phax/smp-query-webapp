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

import java.util.function.Consumer;

import com.helger.photon.api.IAPIExecutor;
import com.helger.smpclient.httpclient.SMPHttpClientSettings;

public abstract class AbstractAPIExecutor implements IAPIExecutor
{
  protected static final String USER_AGENT = "SMP-Query-WebApp/1.0";
  public static final Consumer <? super SMPHttpClientSettings> SMP_HCS_MODIFIER = hcs -> {
    hcs.setUserAgent (USER_AGENT);
  };

  protected AbstractAPIExecutor ()
  {}
}
