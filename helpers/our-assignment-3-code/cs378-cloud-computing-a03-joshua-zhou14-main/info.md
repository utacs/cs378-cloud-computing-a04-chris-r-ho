# Info
**Path: File -> MainClient -> MainReducer -> MainServer**

MainClient: Machines 1 and 2
MainReducer: Machines 3 and 4
MainServer: Machine 5

Disambiguation
- Record: A single line of data from the raw data file, standardized with all variables
- DataItem: Task-specific data that is extracted from a Record

1. File -> MainClient
    - **MainClient.main:** Raw data file -> List of Record objects
    - **Utils.packageToPages:** List of Record objects -> List of byte[]

2. MainClient -> MainReducer
    - **MainClient.sendData:** List of byte[] -> Sent out to MainReducer

3. MainReducer -> MainServer

## MainClient -> MainReducer
* 

MainClient.java
- Input taken in: Raw data file
- Output sent out: Cleaned




