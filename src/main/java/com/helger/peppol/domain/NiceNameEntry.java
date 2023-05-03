package com.helger.peppol.domain;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;
import com.helger.peppolid.IProcessIdentifier;

public final class NiceNameEntry implements Serializable
{
  private final String m_sName;
  private final boolean m_bDeprecated;
  private final ICommonsList <IProcessIdentifier> m_aProcIDs;

  public NiceNameEntry (@Nonnull @Nonempty final String sName,
                        final boolean bDeprecated,
                        @Nullable final ICommonsList <IProcessIdentifier> aProcIDs)
  {
    m_sName = sName;
    m_bDeprecated = bDeprecated;
    m_aProcIDs = aProcIDs;
  }

  @Nonnull
  @Nonempty
  public String getName ()
  {
    return m_sName;
  }

  public boolean isDeprecated ()
  {
    return m_bDeprecated;
  }

  public boolean hasProcessIDs ()
  {
    return CollectionHelper.isNotEmpty (m_aProcIDs);
  }

  public boolean containsProcessID (@Nonnull final IProcessIdentifier aProcID)
  {
    return m_aProcIDs != null && m_aProcIDs.containsAny (aProcID::hasSameContent);
  }

  @Nullable
  public ICommonsList <IProcessIdentifier> getAllProcIDs ()
  {
    return m_aProcIDs == null ? null : m_aProcIDs.getClone ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final NiceNameEntry rhs = (NiceNameEntry) o;
    return m_sName.equals (rhs.m_sName) && m_bDeprecated == rhs.m_bDeprecated;
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sName).append (m_bDeprecated).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Name", m_sName)
                                       .append ("Deprecated", m_bDeprecated)
                                       .append ("ProcessIDs", m_aProcIDs)
                                       .getToString ();
  }
}
