package com.helger.peppol.rest;

import javax.annotation.Nonnull;

import com.helger.photon.api.APIDescriptor;
import com.helger.photon.api.APIPath;
import com.helger.photon.api.IAPIExceptionMapper;
import com.helger.photon.api.IAPIRegistry;

public final class PPAPI
{
  public static final String PARAM_SML_ID = "smlID";
  public static final String PARAM_PARTICIPANT_ID = "participantID";
  public static final String PARAM_DOCTYPE_ID = "docTypeID";

  private PPAPI ()
  {}

  public static void init (@Nonnull final IAPIRegistry aAPIRegistry)
  {
    final IAPIExceptionMapper aExceptionMapper = new APIExceptionMapper ();

    // More specific to less specific
    {
      final APIDescriptor aSMPQueryEndpoints = new APIDescriptor (APIPath.get ("/smpquery/{" +
                                                                               PARAM_SML_ID +
                                                                               "}/{" +
                                                                               PARAM_PARTICIPANT_ID +
                                                                               "}/{" +
                                                                               PARAM_DOCTYPE_ID +
                                                                               "}"),
                                                                  new APISMPQueryGetServiceInformation ());
      aSMPQueryEndpoints.setExceptionMapper (aExceptionMapper);
      aAPIRegistry.registerAPI (aSMPQueryEndpoints);
    }

    {
      final APIDescriptor aSMPQueryDocTypes = new APIDescriptor (APIPath.get ("/smpquery/{" +
                                                                              PARAM_SML_ID +
                                                                              "}/{" +
                                                                              PARAM_PARTICIPANT_ID +
                                                                              "}"),
                                                                 new APISMPQueryGetDocTypes ());
      aSMPQueryDocTypes.setExceptionMapper (aExceptionMapper);
      aAPIRegistry.registerAPI (aSMPQueryDocTypes);
    }

    {
      final APIDescriptor aSMPQueryBusinessCard = new APIDescriptor (APIPath.get ("/businesscard/{" +
                                                                                  PARAM_SML_ID +
                                                                                  "}/{" +
                                                                                  PARAM_PARTICIPANT_ID +
                                                                                  "}"),
                                                                     new APISMPQueryGetBusinessCard ());
      aSMPQueryBusinessCard.setExceptionMapper (aExceptionMapper);
      aAPIRegistry.registerAPI (aSMPQueryBusinessCard);
    }
  }
}
