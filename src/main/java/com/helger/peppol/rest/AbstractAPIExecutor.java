package com.helger.peppol.rest;

import java.util.Map;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.photon.api.IAPIDescriptor;
import com.helger.photon.api.IAPIExecutor;
import com.helger.servlet.response.UnifiedResponse;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

public abstract class AbstractAPIExecutor implements IAPIExecutor
{
  protected AbstractAPIExecutor ()
  {}

  protected abstract void rateLimitedInvokeAPI (@Nonnull IAPIDescriptor aAPIDescriptor,
                                                @Nonnull @Nonempty String sPath,
                                                @Nonnull Map <String, String> aPathVariables,
                                                @Nonnull IRequestWebScopeWithoutResponse aRequestScope,
                                                @Nonnull UnifiedResponse aUnifiedResponse) throws Exception;

  public final void invokeAPI (@Nonnull final IAPIDescriptor aAPIDescriptor,
                               @Nonnull @Nonempty final String sPath,
                               @Nonnull final Map <String, String> aPathVariables,
                               @Nonnull final IRequestWebScopeWithoutResponse aRequestScope,
                               @Nonnull final UnifiedResponse aUnifiedResponse) throws Exception
  {
    rateLimitedInvokeAPI (aAPIDescriptor, sPath, aPathVariables, aRequestScope, aUnifiedResponse);
  }
}
