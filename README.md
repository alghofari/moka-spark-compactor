# moka-spark-compactor
Compaction and merging avro small files into more compact size

## Pre-requisite
 - Install JDK 8
 - Install Apache Spark 2.4.4
 - Install Scala 2.11
 - Install sbt

## Run Test
    sbt test

## Package JAR
    sbt clean package

## Run via Spark Submit Local
    spark-submit --class com.moka.spark.compactor.AvroCompactor \
      --conf spark.hadoop.fs.s3a.access.key=your_access_key \
      --conf spark.hadoop.fs.s3a.secret.key=your_secret_key \
      --repositories http://packages.confluent.io/maven \
      --packages org.apache.spark:spark-avro_2.11:2.4.4,io.confluent:kafka-schema-registry-client:5.3.1 \
      target/scala-2.11/moka-spark-compactor-1.0.0.jar "schema_registry_url" "schema_registry_auth" "schema_registry_subject" "input_path" "output_path" "num_partitions"