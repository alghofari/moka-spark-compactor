package com.moka.spark.compactor

import org.apache.avro.Schema
import org.apache.spark.sql.{DataFrame, SparkSession}

case class Avro(spark: SparkSession) {

	def read(schema: Schema, input: String): DataFrame = {
		// read avro file
		spark.read
			.format("avro")
			.option("avroSchema", schema.toString())
			.load(input)
	}

	def write(df: DataFrame, numPartitions: Int, output: String) = {
		// create new partitions file
		df.repartition(numPartitions)
			.write
			.format("avro")
			.mode("overwrite")
			.save(output)
	}

}