package com.helger.peppol.domain;

import java.util.List;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.error.list.ErrorList;

/**
 * Callback interface
 *
 * @author Philip Helger
 */
@FunctionalInterface
public interface IIDTypeValidator
{
  void validate (@Nonnull @Nonempty String sID, @Nonnull ErrorList aErrorList, @Nonnull List <KVPair> aDetails);
}
