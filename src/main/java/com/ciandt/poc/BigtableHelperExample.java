package com.ciandt.poc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * BigtableHelper, a ServletContextListener, is setup in web.xml to run before a JSP is run.
 *
 **/

public class BigtableHelperExample implements ServletContextListener {

	private static String PROJECT_ID = "googl-cit-gcp";
	private static String INSTANCE_ID = "poc-study";

// The initial connection to Cloud Bigtable is an expensive operation -- We cache this Connection
// to speed things up.  For this sample, keeping them here is a good idea, for
// your application, you may wish to keep this somewhere else.
  private static Connection connection = null;     // The authenticated connection

  private static ServletContext sc;

/**
 * Connect will establish the connection to Cloud Bigtable.
 **/
  public static void connect() throws IOException {
    Configuration c = HBaseConfiguration.create();

    c.setClass("hbase.client.connection.impl",
        com.google.cloud.bigtable.hbase1_2.BigtableConnection.class,
        org.apache.hadoop.hbase.client.Connection.class);   // Required for Cloud Bigtable

    if (PROJECT_ID == null || INSTANCE_ID == null ) {
      sc.log("environment variables BIGTABLE_PROJECT, and BIGTABLE_INSTANCE need to be defined.");
      return;
    }
    c.set("google.bigtable.project.id", PROJECT_ID);
    c.set("google.bigtable.instance.id", INSTANCE_ID);

    connection = ConnectionFactory.createConnection(c);
  }

  public static Connection getConnection() {
    if(connection == null) {
      try {
        connect();
      } catch (IOException e) {
        sc.log("connect ", e);
      }
    }
    if(connection == null) sc.log("BigtableHelper-No Connection");
    return connection;
  }

  public void contextInitialized(ServletContextEvent event) {
    // This will be invoked as part of a warmup request, or the first user
    // request if no warmup request was invoked.
    sc = event.getServletContext();
    try {
      connect();
    } catch (IOException e) {
        sc.log("BigtableHelper - connect ", e);
    }
     if(connection == null) sc.log("BigtableHelper-No Connection");
 }

  public void contextDestroyed(ServletContextEvent event) {
    // App Engine does not currently invoke this method.
    if (connection == null) return;
    try {
      connection.close();
    } catch(IOException io) {
      sc.log("contextDestroyed ", io);
    }
    connection = null;
  }
}
