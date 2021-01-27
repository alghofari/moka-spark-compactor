package com.moka.spark.compactor

import org.apache.spark.sql.SparkSession

trait SparkSessionWrapper extends Serializable {

	lazy val sparkSession: SparkSession = SparkSession
		.builder()
		.appName("moka-spark-compactor")
		.getOrCreate()

}