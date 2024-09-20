package edu.utexas.cs.cs378;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

public class MainReducer {

	static public int portNumber = 33333;
	static public int portNumberServer = 33335;
	static public String hostname = "localhost";
	static public String outputFile = "top10.";
	static public int taskNum;

	/**
	 * A main method to run examples.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			System.err.println("Usage: MainReducer <port number> <hostname> <port number server>");
			portNumber = Integer.parseInt(args[0]);
			hostname = args[1];
			portNumberServer = Integer.parseInt(args[2]);
		}

		// Receiving Socket
		ServerSocket serverSocket;
		Socket socketToServer = null;
		
		BufferedWriter bw = null;

		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Reducer is running on port number " + portNumber);

			ConcurrentHashMap<String, DataItem> map = new ConcurrentHashMap<>();
			List<Thread> pool = new ArrayList<>();
			// Accept 2 client connections
			for (int i = 0; i < 2; ++i) {
				System.out.println("Waiting for client connection ...");
				Socket clientSocket = serverSocket.accept();
				
				// Create a new thread to handle each client connection
				pool.add(new Thread(new ClientHandler(clientSocket, map)));
			}

			for (Thread t : pool) t.start();

			for (Thread t : pool) {
				try {
					t.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// Find the top 10 drivers from the map
			PriorityQueue<DataItem> pq = new PriorityQueue<>();
			for (String key : map.keySet()) {
				DataItem di = map.get(key);
				pq.add(di);
				if (pq.size() > 10) {
					pq.poll();
				}
			}

			// // Write the result to the output file
			// bw = new BufferedWriter(new FileWriter(outputFile + taskNum + ".txt"));
			// while (!pq.isEmpty()) {
			// 	bw.write(pq.poll().toString());
			// 	bw.newLine();
			// }

			// Sending Socket
			socketToServer = new Socket(hostname, portNumberServer);
			DataInputStream dis = new DataInputStream(socketToServer.getInputStream());
			DataOutputStream dos = new DataOutputStream(socketToServer.getOutputStream());

			System.out.println("Main Server is hearing on port " + portNumberServer);
			
			// Task Num
			dos.writeInt(taskNum);
			dos.flush();

			List<FinalItem> finallist = new ArrayList<>();
			// Send the results to the main server
			while (!pq.isEmpty()){
				finallist.add(new FinalItem(pq.poll()));
			}

			List<byte[]> pages = Utils.packageToPagesFinalItem(finallist);

			for (byte[] bs : pages) {
				// tell the server that we have data to send
				dos.writeInt(1);
				dos.flush();

				// then write the entire page and send it
				dos.write(bs);
				dos.flush();
				
				// Here client asks the server if the it can process more data.
				while (dis.readInt() != 1) {
					try {
						// !TODO: We sleep here but you can do a lot more things
						Thread.sleep(500);
						System.out.println("Waiting for the server");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			// finished sending data
			dos.writeInt(0);
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.flush();
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				socketToServer.close();
			} catch (Exception e) {}
		}
	}

	// This class implements Runnable to handle client connections in separate threads
	static class ClientHandler implements Runnable {
		private Socket clientSocket;
		private ConcurrentHashMap<String, DataItem> map;

		public ClientHandler(Socket clientSocket, ConcurrentHashMap<String, DataItem> map) {
			this.clientSocket = clientSocket;
			this.map = map;
		}

		@Override
		public void run() {
			try {
				DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

				System.out.println("Connected to client on port " + portNumber);

				// Read task number from client
				taskNum = dis.readInt(); 

				while (true) {
					int hasData = dis.readInt();

					if (hasData == 0) {
						dos.writeInt(0);
						dos.flush();
						break;
					}

					System.out.println("We have a page of data to process!");

					// 64 MB page Size
					byte[] page = new byte[Const.PAGESIZE];

					// read the main byte array into memory
					dis.readFully(page);

					List<Record> records = Utils.readFromAPage(page);

					for (Record r : records) {
						if (map.containsKey(r.driverId)) {
							map.get(r.driverId).add(r);
						} else {
							DataItem di = new DataItem(taskNum, r);
							di.add(r);
							map.put(r.driverId, di);
						}
					}

					// This tells the client to send more data. 
					System.out.println("Number of Objects received:" + records.size());

					// Give me more data if you have
					dos.writeInt(1);
					dos.flush();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
