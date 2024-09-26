task1:
	mvn clean package
	java -jar target/MapReduce-Task1-0.1-SNAPSHOT-jar-with-dependencies.jar taxi-data-sorted-small.csv output

task2:
	mvn clean package
	java -jar target/MapReduce-Task2-0.1-SNAPSHOT-jar-with-dependencies.jar taxi-data-sorted-small.csv intermediatefolder output

task3:
	mvn clean package
	java -jar target/MapReduce-Task3-0.1-SNAPSHOT-jar-with-dependencies.jar taxi-data-sorted-small.csv intermediatefolder output

clean:
	- rm -r intermediatefolder
	- rm -r output