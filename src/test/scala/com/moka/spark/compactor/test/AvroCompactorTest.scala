package com.moka.spark.compactor.test

import java.io.File
import org.scalatest.FunSuite
import org.apache.avro.Schema
import org.apache.spark.sql.SparkSession
import com.moka.spark.compactor.Avro

class AvroCompactorTest extends FunSuite {

	test("Test avro compactor number of files should equal with numPartitions variable") {
		val spark = SparkSession
			.builder
			.master("local")
			.config("spark.jars.packages","org.apache.spark:spark-avro_2.11:2.4.4")
			.getOrCreate()

		val schema = new Schema
			.Parser()
			.parse(new File("src/test/resources/schema/ebdb.public.units.avsc"))

		val input = "src/test/resources/source"
		val output = "src/test/resources/result"
		val numPartitions = 1

		val df = Avro(spark).read(schema, input)
		Avro(spark).write(df, numPartitions, output)

		val result = new File("src/test/resources/result").listFiles.count(f => f.isFile && f.getName.endsWith("avro"))
		assert(result === numPartitions)
	}

	test("Test avro compactor number of records should equal between source and result") {
		val spark = SparkSession
			.builder
			.master("local")
			.config("spark.jars.packages","org.apache.spark:spark-avro_2.11:2.4.4")
			.getOrCreate()

		val source = "src/test/resources/source"
		val result = "src/test/resources/result"

		val sourceOutput = spark.read.format("avro").load(source)
		val resultOutput = spark.read.format("avro").load(result)

		sourceOutput.cache
		resultOutput.cache

		assert(resultOutput.count === sourceOutput.count)
	}

}