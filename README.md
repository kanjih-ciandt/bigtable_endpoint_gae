bigtable_endpoint_gae
==================

A example project using Google App Engine Flex-Compat in Java with Bigtable.

## Products
- [App Engine][1]

## Language
- [Java][2]

## APIs
- [Google Cloud Endpoints][3]
- [Google App Engine Maven plugin][4]
- [Google Bigtable][5]


## APIs app

- [poc.sample.allTask][6] - Example test to execute:
	1. Connect in Bigtable
	2. Create table
	3. Write some rows to the table
	4. Get the first greeting by row key
	5. Clean up by disabling Table
	6. Delete the table
	
- [poc.sample.createTable][7] - Create Table with Column Family and Column Qualifiers.

	
```json
{
 
  "row_key": "Row Key #mandatory#",  
  
  "columnFamily1": {             
   "columnQualifier1": "Value of Column Qualify",
   "columnQualifier1": "Value of Column Qualify"
  },
  
  "columnFamily1": {
    "columnQualifier1": "Value of Column Qualify",
   "columnQualifier1": "Value of Column Qualify"
  }
}
```
example:
```json
{
  "row_key": "xpto1",
  "cf1": {
    "c1": "k1",
    "c2": "k2"
  },
  "cf2": {
    "c1": "h1",
    "c2": "h2"
  }
   
}
```
	
- poc.sample.deleteTable
- poc.sample.findAllKeys
- poc.sample.upInsertData




[1]: https://developers.google.com/appengine
[2]: http://java.com/en/
[3]: https://developers.google.com/appengine/docs/java/endpoints/
[4]: https://developers.google.com/appengine/docs/java/tools/maven
[5]: https://cloud.google.com/bigtable/docs/
[6]: https://apis-explorer.appspot.com/apis-explorer/?base=https://bigtable-dot-googl-cit-gcp.appspot.com/_ah/api#p/poc/v1/poc.sample.allTask
[7]: https://apis-explorer.appspot.com/apis-explorer/?base=https://bigtable-dot-googl-cit-gcp.appspot.com/_ah/api#p/poc/v1/poc.sample.createTable
