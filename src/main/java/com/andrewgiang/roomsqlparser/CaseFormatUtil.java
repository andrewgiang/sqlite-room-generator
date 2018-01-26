package com.andrewgiang.roomsqlparser;

import com.google.common.base.CaseFormat;

import java.util.IllegalFormatException;

class CaseFormatUtil {

  private static final String ALPHA_NUMERIC_UNDERSCORE_REGEX = "[^\\p{IsAlphabetic}^\\p{IsDigit}^_]";

  private static CaseFormat getCaseFormatName(String s) throws IllegalFormatException {
    if (s.contains("_")) {
      if (s.toUpperCase().equals(s))
        return CaseFormat.UPPER_UNDERSCORE;
      if (s.toLowerCase().equals(s))
        return CaseFormat.LOWER_UNDERSCORE;
    } else if (s.contains("-")) {
      if (s.toLowerCase().equals(s))
        return CaseFormat.LOWER_HYPHEN;
    } else {
      if (Character.isLowerCase(s.charAt(0))) {
        if (s.matches("([a-z]+[A-Z]*\\w+)+"))
          return CaseFormat.LOWER_CAMEL;
      } else {
        if (s.matches("([A-Z]+[a-z]*\\w+)+"))
          return CaseFormat.UPPER_CAMEL;
      }
    }
    return null;
  }


  static String convertTo(CaseFormat format, String string) {
    String alphaNumericString = string.replaceAll(ALPHA_NUMERIC_UNDERSCORE_REGEX, "");
    CaseFormat caseFormat = getCaseFormatName(alphaNumericString);
    if (caseFormat != null) {
      return caseFormat.to(format, alphaNumericString);
    }
    return alphaNumericString;
  }
}
