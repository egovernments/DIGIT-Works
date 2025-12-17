package org.egov.web.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * status of the Property
 */
public enum JITServiceId {

    VA ("VA"),

    PI ("PI"),

    PIS ("PIS"),

    PAG ("PAG"),

    PD ("PD"),

    FD ("FD"),
    COR ("COR"),
    FTPS("FTPS"),
    FTFPS("FTFPS"),
    PA("PA");

	private String value;

  JITServiceId(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static JITServiceId fromValue(String text) {
    for (JITServiceId b : JITServiceId.values()) {
      if (String.valueOf(b.value).equalsIgnoreCase(text)) {
        return b;
      }
    }
    return null;
  }
}
