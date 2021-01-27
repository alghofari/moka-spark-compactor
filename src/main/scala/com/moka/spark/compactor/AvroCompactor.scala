package com.moka.spark.compactor

object AvroCompactor extends App with SparkSessionWrapper {

	override def main(args: Array[String]): Unit = {
		// parameters
		val schemaRegistryUrl = args(0)
		val schemaRegistryAuth = args(1)
		val schemaRegistrySubject = args(2)
		val input = args(3)
		val output = args(4)
		val numPartitions = args(5).toInt

		// get schema
		val schema = SchemaRegistry(schemaRegistryAuth, schemaRegistryUrl, schemaRegistrySubject).getSchema()
		
		// read avro file
		val df = Avro(sparkSession).read(schema, input)

		// repartition avro file
		Avro(sparkSession).write(df, numPartitions, output)

		// stop spark application
		sparkSession.stop()
	}

}