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

	@ApiMethod(httpMethod = HttpMethod.GET)
	public Response hello(@Nullable @Named("name") String name) {
		return new Response("Hello " + (name != null ? name : "world"));
	}

	@ApiMethod(httpMethod = HttpMethod.POST, path = "sample/create")
	public Object createTable(@Nullable @Named("tableName") String tableName,
			@Nullable LinkedHashMap<String, Object> column) {

		return new Response(new BigtableHelper().createTable(tableName, column));
	}
	
	@ApiMethod(httpMethod = HttpMethod.POST, path = "sample/allTask")
	public Object allTask() {

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
