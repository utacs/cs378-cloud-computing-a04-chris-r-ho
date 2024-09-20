package edu.utexas.cs.cs378;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {

	static public int portNumber = 33333;
	static public String outputFile = "top10.";

	/**
	 * A main method to run examples.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			System.err.println("Usage: MainServer <port number>");
			portNumber = Integer.parseInt(args[0]);
		}

		ServerSocket serverSocket;
			
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Server is running on port number " + portNumber);

			List<Thread> pool = new ArrayList<>();
			// Accept 2 client connections
			for (int i = 0; i < 2; ++i) {
				System.out.println("Waiting for client connection ...");
				Socket clientSocket = serverSocket.accept();
				
				// Create a new thread to handle each client connection
				pool.add(new Thread(new ClientHandler(clientSocket)));
			}

			for (Thread t : pool) t.start();

			for (Thread t : pool) {
				try {
					t.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

            
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

    	// This class implements Runnable to handle client connections in separate threads
	static class ClientHandler implements Runnable {
		private Socket clientSocket;
		// private ConcurrentHashMap<String, DataItem> map;

		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {

            BufferedWriter bw = null;

			try {
				DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

				System.out.println("Connected to client on port " + portNumber);

                int taskNum = dis.readInt();
                bw = new BufferedWriter(new FileWriter(outputFile + taskNum + ".txt"));

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

					List<FinalItem> records = Utils.readFromPageFinalItem(page);

                    // Write the result to the output file
					for (FinalItem r : records) {
						bw.write(r.toString());
                        bw.newLine();
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
                    bw.flush();
                    bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}