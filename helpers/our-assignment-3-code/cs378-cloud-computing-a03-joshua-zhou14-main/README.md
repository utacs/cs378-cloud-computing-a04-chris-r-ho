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
    


# Add your Project REPORT HERE 

# Task 1 and 2 Output

<img width="569" alt="Screenshot 2024-09-19 at 11 01 03 PM" src="https://github.com/user-attachments/assets/83509449-708a-4186-b826-8d3c957e9e45">
https://drive.google.com/file/d/1MV-Ijuuj6eg7MlVqVdhlnP0sCiwS96vE/view?usp=drive_link

# VM Instances

<img width="1502" alt="Screenshot 2024-09-19 at 11 06 59 PM" src="https://github.com/user-attachments/assets/ab07b54c-4814-4e72-a92c-f5d189d8a703">
https://drive.google.com/file/d/1CXkemUqx4owv8O8vL2aKhEGoKDsmrbQ7/view?usp=drive_link

# Connections

## M1 and M2 - Clients
<img width="743" alt="Screenshot 2024-09-19 at 11 04 38 PM" src="https://github.com/user-attachments/assets/2f2184ef-822b-46b4-848c-830d356d833f">
https://drive.google.com/file/d/1NS8WeE7PFTEW6M4BiRnfXXAW1b9cvRz4/view?usp=drive_link

<img width="891" alt="Screenshot 2024-09-19 at 11 05 12 PM" src="https://github.com/user-attachments/assets/25918a93-2d8f-4968-8642-e9c628eccb1e">
https://drive.google.com/file/d/1pE3zfKqsd9N7w4yic22NgxJo9GgubNgp/view?usp=drive_link

## M3 and M4 - Intermediate
<img width="795" alt="Screenshot 2024-09-19 at 11 05 44 PM" src="https://github.com/user-attachments/assets/3c26f76f-4d1f-4bb0-8c57-07c82889a7a9">
https://drive.google.com/file/d/1_90Ftt3CrRKJKuxjXmWXwxy5zd5qaFLJ/view?usp=drive_link

<img width="719" alt="Screenshot 2024-09-19 at 11 05 56 PM" src="https://github.com/user-attachments/assets/62c352f5-d761-4a28-844c-2537bc354a8c">
https://drive.google.com/file/d/1mbQWqO0XbqwFSU9gnqs1dco7O42p3o_6/view?usp=drive_link

## M5 - Server
<img width="965" alt="Screenshot 2024-09-19 at 11 06 38 PM" src="https://github.com/user-attachments/assets/216e17ac-0556-4742-98fd-211cd8051fde">
https://drive.google.com/file/d/1EjAAlKWutoVjTaqqrSt7zxLEkkHE587h/view?usp=drive_link













# Project Template

This is a Java Maven Project Template


# How to compile the project

We use Apache Maven to compile and run this project. 

You need to install Apache Maven (https://maven.apache.org/)  on your system. 

Type on the command line: 

```bash
mvn clean compile
```

# How to create a binary runnable package 


```bash
mvn clean compile assembly:single
```


# How to run the Server

```bash
mvn clean  compile  exec:java@server -Dexec.args="33335"
```

# How to run the Reducers

```bash
mvn clean  compile  exec:java@reducer -Dexec.args="33333 localhost 33335"
```

# How to run the Client

```bash
mvn clean  compile  exec:java@client  -Dexec.args="2000000 localhost localhost 33333 33334 taxi-data-sorted-small.csv.bz2"
```

Change the localhost with the local ip address of your cloud machine. 


Data batch size is: 2000000

You can modify the batch size based on available memory.


If you run this over SSH and it takes a lots of time you can run it in background using the following command

```bash
nohub mvn clean  compile  exec:java@client  -Dexec.args="2000000 localhost 33333"  & 
```

We recommend the above command for running the Main Java executable. 







