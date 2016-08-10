package com.ciandt.poc;

import java.util.Date;

public class Response {
	  public final int code;
	  public final String message;
	  public final Date timestamp;

	  public Response() {
	    this("");
	  }

	  public Response(String message) {
	    this(0, message);
	  }

	  public Response(int code, String message) {
	    this(0, message, new Date());
	  }

	  /** All args constructor. */
	  public Response(int code, String message, Date timestamp) {
	    this.code = code;
	    this.message = message;
	    this.timestamp = timestamp;
	  }

	  public int getCode() {
	    return code;
	  }

	  public String getMessage() {
	    return message;
	  }

	  public Date getTimestamp() {
	    return timestamp;
	  }
	}

