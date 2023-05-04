package com.helger.peppol.rest;

import com.helger.photon.api.IAPIExecutor;

public abstract class AbstractAPIExecutor implements IAPIExecutor
{
  protected static final String USER_AGENT = "SMP-Query-WebApp/1.0";

  protected AbstractAPIExecutor ()
  {}
}
