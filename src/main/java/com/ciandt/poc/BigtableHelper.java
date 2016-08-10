package com.ciandt.poc;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;

public class BigtableHelper {

	private static final Logger log = Logger.getLogger(BigtableHelper.class.getName());

	// Refer to table metadata names by byte array in the HBase API
	private static final byte[] TABLE_NAME = Bytes.toBytes("Hello-Bigtable");
	private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("cf1");
	private static final byte[] COLUMN_NAME = Bytes.toBytes("greeting");

	private static String PROJECT_ID = "googl-cit-gcp";
	private static String INSTANCE_ID = "poc-study";

	// Write some friendly greetings to Cloud Bigtable
	private static final String[] GREETINGS = { "Hello World!", "Hello Cloud Bigtable!", "Hello HBase!" };

	

}
