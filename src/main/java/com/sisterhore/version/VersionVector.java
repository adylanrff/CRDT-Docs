package com.sisterhore.version;

import java.util.ArrayList;
import java.util.HashMap;

public class VersionVector {
  private String siteId;
  private HashMap<String, Version> struct;
  private Version localVersion;

  public VersionVector(String siteId) {
    this.siteId = siteId;
    this.struct = new HashMap<>();
    this.localVersion = new Version(siteId);
    this.struct.put(siteId, this.localVersion);
  }

  /**
   * Returns local version
   * @return owned local version
   */
  public Version getLocalVersion() { return this.localVersion; }

  /**
   * Returns site ID
   * @return owned site ID
   */
  public String getSiteId() { return this.siteId; }

  /**
   * Check if certain operation has been applied before
   * @param incomingVersion given version
   * @return version exist or not
   */
  public boolean isApplied(Version incomingVersion) {
    Version localIncomingVersion = this.getVersion(incomingVersion);

    if (localIncomingVersion == null) return false;
    return incomingVersion.getCounter() <= localIncomingVersion.getCounter();
  }

  /**
   * Get version with same site ID in this vector
   * @param version asked version
   * @return version with same site ID
   */
  public Version getVersion(Version version) {
    Version foundVersion = null;
    for (int i=0; i<this.struct.size(); i++) {
      Version current = this.struct.get(i);
      if (version.getSiteId() == current.getSiteId()) {
        foundVersion = current;
        break;
      }
    }
    return foundVersion;
  }

}
