# Project Template - Top-K Hadoop Example. 

## Task 1

ATL	346836
ORD	285884
DFW	239551

## Task 2

NK  Count: 117379  Delay: 1840887.0  Ratio: 15.683274

UA  Count: 515723  Delay: 7355348.0  Ratio: 14.262207

F9  Count: 90836  Delay: 1205449.0  Ratio: 13.270609

B6  Count: 267048  Delay: 3026467.0  Ratio: 11.333045

WN  Count: 1261855  Delay: 1.318652E7  Ratio: 10.450108

MQ  Count: 294632  Delay: 2837908.0  Ratio: 9.632043

VX  Count: 61903  Delay: 553852.0  Ratio: 8.947095

AA  Count: 725984  Delay: 6369435.0  Ratio: 8.7735195

EV  Count: 571977  Delay: 4857338.0  Ratio: 8.492191

OO  Count: 588353  Delay: 4517510.0  Ratio: 7.678231














# Running on Laptop     ####

Prerequisite:

- Maven 3

- JDK 1.6 or higher

- (If working with eclipse) Eclipse with m2eclipse plugin installed


The java main class is:

edu.cs.utexas.HadoopEx.WordCountTopKDriver 

Input file:  Book-Tiny.txt  

Specify your own Output directory like 

# Running:




## Create a JAR Using Maven 

To compile the project and create a single jar file with all dependencies: 
	
```mvn clean package```


## Run your application

Inside your shell with Hadoop

Running as Java Application:

```java -jar target/topKHadoop-0.1-SNAPSHOT-jar-with-dependencies.jar SOME-Text-Fiel.txt  intermediatefolder  output  ``` 

For example 
```java -jar target/topKHadoop-0.1-SNAPSHOT-jar-with-dependencies.jar 20-news-same-line.txt  intermediatefolder  output  ``` 

Or has hadoop application

```hadoop jar your-hadoop-application.jar edu.cs.utexas.HadoopEx.WordCountTopKDriver arg0 arg1 arg2 ```


java -jar target/topKHadoop-0.1-SNAPSHOT-jar-with-dependencies.jar flights.csv  intermediatefolder  output


