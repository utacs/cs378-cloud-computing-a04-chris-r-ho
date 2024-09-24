all:
	mvn clean package
	java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar Book=Tiny.txt  intermediatefolder  output

clean:
	- rm -r intermediatefolder
	- rm -r output