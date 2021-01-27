name := "moka-spark-compactor"
version := "1.0.0"
scalaVersion := "2.11.12"

val sparkVersion = "2.4.4"

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % sparkVersion % Provided,
	"org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
	"org.apache.spark" %% "spark-avro" % sparkVersion % Provided,
	"com.amazonaws" % "aws-java-sdk" % "1.7.4",
	"org.apache.hadoop" % "hadoop-aws" % "2.7.3",
	"org.apache.kafka" % "kafka-clients" % "2.3.1",
	"io.confluent" % "kafka-schema-registry-client" % "5.3.1" % Provided,
	"org.scalatest" %% "scalatest" % "3.0.8" % Test,
	"com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.1"
)

resolvers += "confluent" at "http://packages.confluent.io/maven"

assemblyMergeStrategy in assembly := {
	case PathList("META-INF", xs @ _*) => MergeStrategy.discard
	case x => MergeStrategy.first
}

assemblyJarName in assembly := s"${name.value}-${version.value}.jar"