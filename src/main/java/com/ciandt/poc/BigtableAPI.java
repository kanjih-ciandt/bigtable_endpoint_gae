package com.ciandt.poc;

import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.apache.hadoop.hbase.util.Bytes;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiClass;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;

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

	

	@ApiMethod(httpMethod = HttpMethod.GET)
	public Response hello(@Nullable @Named("name") String name) {
		return new Response("Hello " + (name != null ? name : "world"));
	}

	@ApiMethod(httpMethod = HttpMethod.POST, path = "sample/create")
	public Object createTable(@Nullable @Named("tableName") String tableName,
			@Nullable LinkedHashMap<String, Object> column) {

		return new Response(new BigtableHelper().allTasksBigtable());
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

	

}
