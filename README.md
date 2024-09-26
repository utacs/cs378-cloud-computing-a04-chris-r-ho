# Please add your team members' names here. 

## Team members' names 

1. Student Name: Rikhil Kalidindi

   Student UT EID: rvk289

2. Student Name: Joshua Zhou

   Student UT EID: jjz534

3. Student Name: Chris Ho

Student UT EID: crh4595

4. Student Name: Alexander Lee

Student UT EID: al55332

 ...

##  Course Name: CS378 - Cloud Computing 

##  Unique Number: 51515

# Task 1
## Output:
01	198453
02	149629
03	113354
04	85924
05	63662
06	50864
07	104927
08	180293
09	219755
10	230093
11	223244
12	228677
13	242793
14	241225
15	251143
16	247963
17	215000
18	244767
19	304665
20	317971
21	296600
22	291543
23	279601
24	247429

## YARN History:
![Screenshot 2024-09-26 184241](https://github.com/user-attachments/assets/54af42c5-864c-4248-a8d6-1d8cd118ea7e)

# Task 2
## Output:
0219EB9A4C74AAA118104359E5A5914C	1.0
8D90E4C96A0C162BC9A663F7F3C45379	1.0
12CE65C3876AAB540925B368E8A0E181	1.0
14C5001FBF4706F49E6D436FA1EC8428	1.0
FE757A29F1129533CD6D4A0EC6034106	1.0

## YARN History:
![Screenshot 2024-09-26 184414](https://github.com/user-attachments/assets/62cd2e58-c431-438c-8be3-3057b24a25f2)
![Screenshot 2024-09-26 184410](https://github.com/user-attachments/assets/e01ba9a1-98ac-4082-b156-061e798c1afc)

# Task 3
## Output:
FD2AE1C5F9F5FBE73A6D6D3D33270571	4095.0
A7C9E60EEE31E4ADC387392D37CD06B8	1260.0
D8E90D724DBD98495C1F41D125ED029A	630.0
E9DA1D289A7E321CC179C51C0C526A73	231.3
95A921A9908727D4DC03B5D25A4B0F62	210.0
74071A673307CA7459BCF75FBD024E09	210.0
42AB6BEE456B102C1CF8D9D8E71E845A	191.55
FA587EC2731AAB9F2952622E89088D4B	180.0
28EAF0C54680C6998F0F2196F2DA2E21	180.0
E79402C516CEF1A6BB6F526A142597D4	144.55

## YARN History:
![Screenshot 2024-09-26 184501](https://github.com/user-attachments/assets/68f68500-c7a3-405b-8d4b-2a3b3b43a3c4)
![Screenshot 2024-09-26 184457](https://github.com/user-attachments/assets/3d5283e9-14c9-4672-90ef-02eb863ca010)

# Google Cloud - Dataproc
![Screenshot 2024-09-26 183635](https://github.com/user-attachments/assets/1f823e9f-4604-42a2-88bb-ca95cdabacbc)
![Screenshot 2024-09-26 175859](https://github.com/user-attachments/assets/99c9a4f2-9d9f-469e-9d7b-b15a5c47dc99)


# Project Template

# Running on Laptop     ####

Prerequisite:

- Maven 3

- JDK 1.6 or higher

- (If working with eclipse) Eclipse with m2eclipse plugin installed


The java main class is:

edu.cs.utexas.HadoopEx.WordCount 

Input file:  Book-Tiny.txt  

Specify your own Output directory like 

# Running:




## Create a JAR Using Maven 

To compile the project and create a single jar file with all dependencies: 
	
```	mvn clean package ```



## Run your application
Inside your shell with Hadoop

Running as Java Application:

```java -jar target/MapReduce-WordCount-example-0.1-SNAPSHOT-jar-with-dependencies.jar taxi-data-sorted-small.csv output``` 

Or has hadoop application

```hadoop jar your-hadoop-application.jar edu.cs.utexas.HadoopEx.WordCount arg0 arg1 ... ```



## Create a single JAR File from eclipse



Create a single gar file with eclipse 

*  File export -> export  -> export as binary ->  "Extract generated libraries into generated JAR"
