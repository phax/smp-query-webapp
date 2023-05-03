package com.helger.peppol.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.string.StringHelper;

/**
 * Key value pair
 *
 * @author Philip Helger
 */
@Immutable
public final class KVPair
{
  private final String m_sKey;
  private final String m_sValue;

  public KVPair (@Nonnull final String sKey, @Nullable final String sValue)
  {
    m_sKey = sKey;
    m_sValue = sValue;
  }

  @Nonnull
  public String getKey ()
  {
    return m_sKey;
  }

  public boolean hasValue ()
  {
    return StringHelper.hasText (m_sValue);
  }

  @Nullable
  public String getValue ()
  {
    return m_sValue;
  }
}
