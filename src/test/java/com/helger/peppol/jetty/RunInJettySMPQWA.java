package com.helger.peppol.jetty;

import javax.annotation.concurrent.Immutable;

import com.helger.photon.jetty.JettyStarter;

/**
 * Run peppol-practical as a standalone web application in Jetty on port 8080.
 * <br>
 * http://localhost:8080/
 *
 * @author Philip Helger
 */
@Immutable
public final class RunInJettySMPQWA
{
  public static void main (final String [] args) throws Exception
  {
    new JettyStarter (RunInJettySMPQWA.class).run ();
  }
}
