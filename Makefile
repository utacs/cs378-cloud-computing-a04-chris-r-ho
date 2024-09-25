all:
	mvn clean package
	java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar taxi-data-sorted-small.csv output
	
clean:
	- rm -r intermediatefolder
	- rm -r output