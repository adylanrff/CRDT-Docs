package com.sisterhore.version;

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
   * Increment local version
   */
  public void incrementLocalVersion() { this.localVersion.incrementCounter(); }

  /**
   * Returns site ID
   * @return owned site ID
   */
  public String getSiteId() { return this.siteId; }

  /**
   * Update version vector with incoming version
   * @param incomingVersion version to be appended
   */
  public void update(Version incomingVersion) {
    Version existing = this.findVersion(incomingVersion);
    if (existing == null) {
      Version newVersion = new Version(incomingVersion.getSiteId());
    } else {
      existing.incrementCounter();
    }
  }

  /**
   * Check if certain operation has been applied before
   * @param incomingVersion given version
   * @return version exist or not
   */
  public boolean isApplied(Version incomingVersion) {
    Version localIncomingVersion = this.findVersion(incomingVersion);
    System.out.println(String.format("Local incoming version ID: %s & Counter: %d", localIncomingVersion.getSiteId(), localIncomingVersion.getCounter()));
    System.out.println(String.format("Incoming version ID: %s & Counter: %d", incomingVersion.getSiteId(), incomingVersion.getCounter()));

    return false;

//    if (localIncomingVersion == null) return false;
//    return incomingVersion.getCounter() <= localIncomingVersion.getCounter();
  }

  /**
   * Get version with same site ID in this vector
   * @param version asked version
   * @return version with same site ID
   */
  public Version findVersion(Version version) {
    Version foundVersion = null;
//    System.out.println(String.format("Struct size: %d", this.struct.size()));
    if (this.struct.containsKey(version.getSiteId()))
      foundVersion = this.struct.get(version.getSiteId());
    return foundVersion;
  }

}
