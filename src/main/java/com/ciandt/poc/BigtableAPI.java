package com.ciandt.poc;

import java.io.IOException;
import java.util.LinkedHashMap;
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

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.cloud.bigtable.hbase.BigtableConfiguration;

@Api(name = "poc", version = "v1", clientIds = { Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID })
@ApiClass(resource = "sample")
public class BigtableAPI {

	private static final Logger log = Logger.getLogger(BigtableAPI.class.getName());

	// Refer to table metadata names by byte array in the HBase API
	private static final byte[] TABLE_NAME = Bytes.toBytes("Hello-Bigtable");
	private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("cf1");
	private static final byte[] COLUMN_NAME = Bytes.toBytes("greeting");

	private static String PROJECT_ID = "googl-cit-gcp";
	private static String INSTANCE_ID = "poc-study";

	// Write some friendly greetings to Cloud Bigtable
	private static final String[] GREETINGS = { "Hello World!", "Hello Cloud Bigtable!", "Hello HBase!" };

	BigtableHelper bigtableHelper;

	public BigtableAPI() {
		bigtableHelper = new BigtableHelper();
	}

	@ApiMethod(httpMethod = HttpMethod.GET)
	public Response hello(@Nullable @Named("name") String name) {
		return new Response("Hello " + (name != null ? name : "world"));
	}

	@ApiMethod(httpMethod = HttpMethod.POST, path = "sample/create")
	public Object createTable(@Nullable @Named("tableName") String tableName,
			@Nullable LinkedHashMap<String, Object> column) {

		return new Response(allTasksBigtable());
	}

	@ApiMethod(httpMethod = HttpMethod.POST, path = "sample/update")
	public Object updatateTable(@Nullable @Named("tableName") String tableName,
			@Nullable LinkedHashMap<String, Object> column) {

		return new Response("Hello world");
	}

	@ApiMethod(httpMethod = HttpMethod.GET, path = "sample/findAll")
	public Response findAll(@Nullable @Named("name") String name) {
		return new Response("Hello " + (name != null ? name : "world"));
	}

	@ApiMethod(httpMethod = HttpMethod.GET, path = "sample/findByKey")
	public Response findByKey(@Nullable @Named("name") String name) {
		return new Response("Hello " + (name != null ? name : "world"));
	}

	public String allTasksBigtable() {
		StringBuffer buffer = new StringBuffer();
		// [START connecting_to_bigtable]
		// Create the Bigtable connection, use try-with-resources to make sure
		// it gets closed
		try (Connection connection = BigtableConfiguration.connect(PROJECT_ID, INSTANCE_ID)) {

			// The admin API lets us create, manage and delete tables
			Admin admin = connection.getAdmin();
			// [END connecting_to_bigtable]

			// [START creating_a_table]
			// Create a table with a single column family
			HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
			descriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY_NAME));

			log.info("Create table " + descriptor.getNameAsString());
			admin.createTable(descriptor);
			// [END creating_a_table]

			// [START writing_rows]
			// Retrieve the table we just created so we can do some reads and
			// writes
			Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

			// Write some rows to the table
			log.info("Write some greetings to the table");
			for (int i = 0; i < GREETINGS.length; i++) {
				// Each row has a unique row key.
				//
				// Note: This example uses sequential numeric IDs for
				// simplicity, but
				// this can result in poor performance in a production
				// application.
				// Since rows are stored in sorted order by key, sequential keys
				// can
				// result in poor distribution of operations across nodes.
				//
				// For more information about how to design a Bigtable schema
				// for the
				// best performance, see the documentation:
				//
				// https://cloud.google.com/bigtable/docs/schema-design
				String rowKey = "greeting" + i;

				// Put a single row into the table. We could also pass a list of
				// Puts to write a batch.
				Put put = new Put(Bytes.toBytes(rowKey));
				put.addColumn(COLUMN_FAMILY_NAME, COLUMN_NAME, Bytes.toBytes(GREETINGS[i]));
				table.put(put);
			}
			// [END writing_rows]

			// [START getting_a_row]
			// Get the first greeting by row key
			String rowKey = "greeting0";
			Result getResult = table.get(new Get(Bytes.toBytes(rowKey)));
			String greeting = Bytes.toString(getResult.getValue(COLUMN_FAMILY_NAME, COLUMN_NAME));
			log.info("Get a single greeting by row key");
			log.info(rowKey + ": " + greeting);
			// [END getting_a_row]

			// [START scanning_all_rows]
			// Now scan across all rows.
			Scan scan = new Scan();

			log.info("Scan for all greetings:");
			ResultScanner scanner = table.getScanner(scan);
			for (Result row : scanner) {
				byte[] valueBytes = row.getValue(COLUMN_FAMILY_NAME, COLUMN_NAME);
				log.info('\t' + Bytes.toString(valueBytes));
				buffer.append('\t' + Bytes.toString(valueBytes));
			}
			// [END scanning_all_rows]

			// [START deleting_a_table]
			// Clean up by disabling and then deleting the table
			log.info("Delete the table");
			admin.disableTable(table.getName());
			admin.deleteTable(table.getName());
			// [END deleting_a_table]

		} catch (IOException e) {
			return e.toString();
		}

		return buffer.toString();
	}

}
