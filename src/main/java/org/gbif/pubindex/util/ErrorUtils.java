package org.gbif.pubindex.util;

public class ErrorUtils {

  private ErrorUtils() {
  }

  public static String getErrorMessage(Exception e){
    return e.getMessage()==null ? e.getClass().getSimpleName() : e.getMessage();
  }

}
