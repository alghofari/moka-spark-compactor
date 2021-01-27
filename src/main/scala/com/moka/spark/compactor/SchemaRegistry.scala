package com.moka.spark.compactor

import io.confluent.kafka.schemaregistry.client.{CachedSchemaRegistryClient, SchemaMetadata}
import org.apache.avro.Schema

import scala.collection.JavaConverters._

case class SchemaRegistry(schemaRegistryAuth:String, schemaRegistryUrl:String, schemaRegistrySubject:String) {

	def getSchema(): Schema = {
		// connect to schema registry server with basic auth
		val props = Map(
			"basic.auth.credentials.source" -> "USER_INFO",
			"schema.registry.basic.auth.user.info" -> schemaRegistryAuth
		).asJava
		val schemaRegistry = new CachedSchemaRegistryClient(schemaRegistryUrl, 10000, props)

		// get latest schema
		val schema: Schema = {
			val latestSchemaMetadata: SchemaMetadata = schemaRegistry.getLatestSchemaMetadata(schemaRegistrySubject)
			val id: Int = latestSchemaMetadata.getId
			schemaRegistry.getById(id)
		}
		
		schema
	}

}