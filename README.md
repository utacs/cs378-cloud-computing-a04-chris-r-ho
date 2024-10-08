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
01	198453<br/>
02	149629<br/>
03	113354<br/>
04	85924<br/>
05	63662<br/>
06	50864<br/>
07	104927<br/>
08	180293<br/>
09	219755<br/>
10	230093<br/>
11	223244<br/>
12	228677<br/>
13	242793<br/>
14	241225<br/>
15	251143<br/>
16	247963<br/>
17	215000<br/>
18	244767<br/>
19	304665<br/>
20	317971<br/>
21	296600<br/>
22	291543<br/>
23	279601<br/>
24	247429<br/>

## YARN History:
![Screenshot 2024-09-26 184241](https://github.com/user-attachments/assets/54af42c5-864c-4248-a8d6-1d8cd118ea7e)
https://drive.google.com/file/d/1AIvDGzsgVHfO6IJspAXY94-6314nN1YG/view?usp=sharing

# Task 2
## Output:
0219EB9A4C74AAA118104359E5A5914C	1.0<br/>
8D90E4C96A0C162BC9A663F7F3C45379	1.0<br/>
12CE65C3876AAB540925B368E8A0E181	1.0<br/>
14C5001FBF4706F49E6D436FA1EC8428	1.0<br/>
FE757A29F1129533CD6D4A0EC6034106	1.0<br/>

## YARN History:
![Screenshot 2024-09-26 184501](https://github.com/user-attachments/assets/68f68500-c7a3-405b-8d4b-2a3b3b43a3c4)
![Screenshot 2024-09-26 185650](https://github.com/user-attachments/assets/6d98fa1e-bb4e-4032-8b78-f9a18148d718)
https://drive.google.com/file/d/1yzeT4MO1rpSF3EC1VV6so_6pIzjyJn3u/view?usp=sharing
https://drive.google.com/file/d/1Uklf5sQE8aDqTcuSWtlGeJCi9aZHMxNG/view?usp=sharing

# Task 3
## Output:
FD2AE1C5F9F5FBE73A6D6D3D33270571	4095.0<br/>
A7C9E60EEE31E4ADC387392D37CD06B8	1260.0<br/>
D8E90D724DBD98495C1F41D125ED029A	630.0<br/>
E9DA1D289A7E321CC179C51C0C526A73	231.3<br/>
95A921A9908727D4DC03B5D25A4B0F62	210.0<br/>
74071A673307CA7459BCF75FBD024E09	210.0<br/>
42AB6BEE456B102C1CF8D9D8E71E845A	191.55<br/>
FA587EC2731AAB9F2952622E89088D4B	180.0<br/>
28EAF0C54680C6998F0F2196F2DA2E21	180.0<br/>
E79402C516CEF1A6BB6F526A142597D4	144.55<br/>

## YARN History:
![Screenshot 2024-09-26 184414](https://github.com/user-attachments/assets/62cd2e58-c431-438c-8be3-3057b24a25f2)
![Screenshot 2024-09-26 185706](https://github.com/user-attachments/assets/87d0ceea-b1a6-4d01-8d9a-292a5ac98f30)
https://drive.google.com/file/d/1kmAUPKQ5qS2PIz-PXwTAh0ktBozvnKeL/view?usp=sharing
https://drive.google.com/file/d/1Uklf5sQE8aDqTcuSWtlGeJCi9aZHMxNG/view?usp=sharing

# Google Cloud - Dataproc
![Screenshot 2024-09-26 175859](https://github.com/user-attachments/assets/99c9a4f2-9d9f-469e-9d7b-b15a5c47dc99)
![Screenshot 2024-09-26 183635](https://github.com/user-attachments/assets/1f823e9f-4604-42a2-88bb-ca95cdabacbc)

https://drive.google.com/file/d/1uSRx-mRlk4iGOu-CAkxtGidtghL7xROq/view?usp=drive_link
https://drive.google.com/file/d/17ku699dSPgbTRkDP0OzJKvc4WZQi9ptx/view?usp=drive_link


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
