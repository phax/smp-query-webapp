package com.helger.peppol.rest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.annotation.Nonempty;
import com.helger.http.CHttp;
import com.helger.mime.CMimeType;
import com.helger.peppol.app.mgr.ISMLConfigurationManager;
import com.helger.peppol.app.mgr.PPMetaManager;
import com.helger.peppol.domain.ISMLConfiguration;
import com.helger.peppolid.CIdentifier;
import com.helger.peppolid.IParticipantIdentifier;
import com.helger.peppolid.factory.PeppolIdentifierFactory;
import com.helger.peppolid.peppol.PeppolIdentifierHelper;
import com.helger.photon.api.IAPIDescriptor;
import com.helger.photon.app.PhotonUnifiedResponse;
import com.helger.smpclient.url.PeppolNaptrURLProvider;
import com.helger.smpclient.url.SMPDNSResolutionException;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

import jakarta.annotation.Nonnull;

/**
 * Determine if a participant ID is registered in the Peppol Network or not.
 *
 * @author Philip Helger
 */
public final class APICheckPeppolParticipantRegistered extends AbstractAPIExecutor
{
  private static final Logger LOGGER = LoggerFactory.getLogger (APICheckPeppolParticipantRegistered.class);

  private static boolean _isSMPRegisteredInDNSViaInetAddress (@Nonnull final IParticipantIdentifier aParticipantID,
                                                              @Nonnull final String sSMLZoneName)
  {
    try
    {
      // Perform NAPTR resolution
      PeppolNaptrURLProvider.INSTANCE.getSMPURIOfParticipant (aParticipantID, sSMLZoneName);

      // Found it
      return true;
    }
    catch (final SMPDNSResolutionException ex)
    {
      return false;
    }
  }

  @Override
  public void invokeAPI (@Nonnull final IAPIDescriptor aAPIDescriptor,
                         @Nonnull @Nonempty final String sPath,
                         @Nonnull final Map <String, String> aPathVariables,
                         @Nonnull final IRequestWebScopeWithoutResponse aRequestScope,
                         @Nonnull final PhotonUnifiedResponse aUnifiedResponse) throws IOException
  {
    final String sLogPrefix = "[API IsInPeppol-" + COUNTER.incrementAndGet () + "] ";

    final ISMLConfigurationManager aSMLConfigurationMgr = PPMetaManager.getSMLConfigurationMgr ();
    final String sSMLID = aPathVariables.get (PPAPI.PARAM_SML_ID);
    final boolean bSMLAutoDetect = ISMLConfigurationManager.ID_AUTO_DETECT.equals (sSMLID);
    final ISMLConfiguration aSML = aSMLConfigurationMgr.getSMLInfoOfID (sSMLID);
    if (aSML == null && !bSMLAutoDetect)
      throw new APIParamException ("Unsupported SML ID '" + sSMLID + "' provided.");

    String sPPID = aPathVariables.get (PPAPI.PARAM_PARTICIPANT_ID);
    if (sPPID != null)
    {
      // Add prefix on demand
      if (!sPPID.startsWith (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS))
        sPPID = CIdentifier.getURIEncoded (PeppolIdentifierHelper.PARTICIPANT_SCHEME_ISO6523_ACTORID_UPIS, sPPID);
    }

    final IParticipantIdentifier aPPID = PeppolIdentifierFactory.INSTANCE.parseParticipantIdentifier (sPPID);
    if (aPPID == null)
    {
      LOGGER.error (sLogPrefix + "The provided Peppol Participant ID '" + sPPID + "' is invalid");
      aUnifiedResponse.createBadRequest ();
      return;
    }

    boolean bRegistered;
    if (bSMLAutoDetect)
    {
      bRegistered = false;
      for (final ISMLConfiguration aCurSML : aSMLConfigurationMgr.getAllSorted ())
      {
        bRegistered = _isSMPRegisteredInDNSViaInetAddress (aPPID, aCurSML.getDNSZone ());
        if (bRegistered)
          break;
      }
    }
    else
    {
      bRegistered = _isSMPRegisteredInDNSViaInetAddress (aPPID, aSML.getDNSZone ());
    }

    if (bRegistered)
    {
      LOGGER.info (sLogPrefix + "Peppol Participant ID '" + sPPID + "' is registered in DNS");
      aUnifiedResponse.setStatus (CHttp.HTTP_OK).disableCaching ();
    }
    else
    {
      LOGGER.warn (sLogPrefix + "Peppol Participant ID '" + sPPID + "' is NOT registered in DNS");
      aUnifiedResponse.setStatus (CHttp.HTTP_NOT_FOUND)
                      .setContentAndCharset ("Not found: '" + aPPID.getURIEncoded () + "'", StandardCharsets.UTF_8)
                      .setMimeType (CMimeType.TEXT_PLAIN)
                      .disableCaching ();
    }
  }
}
