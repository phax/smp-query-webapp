package com.helger.peppol.jetty;

import java.io.IOException;

import com.helger.photon.jetty.JettyStopper;

public final class JettyStopSMPQWA
{
  public static void main (final String [] args) throws IOException
  {
    new JettyStopper ().run ();
  }
}
