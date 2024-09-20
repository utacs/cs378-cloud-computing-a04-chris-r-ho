package edu.utexas.cs.cs378;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

public class MainClient {

	static public int portNumber1 = 33333, portNumber2 = 33334;
	static public String hostName1 = "localhost";
	static public String hostName2 = "localhost";

	static public int batchSize = 4000;
	private static Socket socket1, socket2;
	static public String fileName;

	final static int SIZE = 15728640;

	/**
	 * A main method to run examples.
	 *
	 * @param args not used
	 */
	public static void main(String[] args) {

		if (args.length > 5) {
			System.err.println("Usage: MainClient <BatchSize> <hostname 1> <hostname 2> <port number 1> <port number 2> <file name>");
			batchSize = Integer.parseInt(args[0]);
			hostName1 = args[1];
			hostName2 = args[2];
			portNumber1 = Integer.parseInt(args[3]);
			portNumber2 = Integer.parseInt(args[4]);
			fileName = args[5];
		}

		try {
			
			
			// Task 1 socket connection
			socket1 = new Socket(hostName1, portNumber1);
			DataInputStream dis1 = new DataInputStream(socket1.getInputStream());
			DataOutputStream dos1 = new DataOutputStream(socket1.getOutputStream());

			// Task 2 socket connection
			socket2 = new Socket(hostName2, portNumber2);
			DataInputStream dis2 = new DataInputStream(socket2.getInputStream());
			DataOutputStream dos2 = new DataOutputStream(socket2.getOutputStream());


			System.out.println("Servers are hearing on ports " + portNumber1 + " and " + portNumber2);

			List<Record> recordList = new ArrayList<>();
			BufferedReader br = null;
			
			try {
				FileInputStream fin = new FileInputStream(fileName);
				BufferedInputStream bis = new BufferedInputStream(fin, SIZE);

				// Here we uncompress .bz2 file
				CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
				br = new BufferedReader(new InputStreamReader(input));
				String line;
				int batchNum = 0;

				dos1.writeInt(1);
				dos2.writeInt(2);

				dos1.flush();
				dos2.flush();

				// Parse file & add to error list
				while ((line = br.readLine()) != null) {
					Record r = Record.parse(line);

					// error
					if (r == null) {
						continue;
					}

					recordList.add(r);

					if (recordList.size() < batchSize) continue;
					
					System.out.println("------------\nBATCH SIZE REACHED ON BATCH: " + batchNum);
					batchNum++;

					sendData(recordList, dos1, dos2, dis1, dis2);

					// reset the list of records
					recordList = new ArrayList<>();
				}

				sendData(recordList, dos1, dos2, dis1, dis2);

				// Finished sending data, terminate server
				dos1.writeInt(0);
				dos2.writeInt(0);
				dos1.close();
				dos2.close();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				br.close();
			}

		} catch (IOException e) {
			System.err.println("ERROR");
			e.printStackTrace();
		}
	}
	

	/**
	 * Sends the data to both servers
	 * @param recordList list of records to send
	 * @param dos1 output stream for server 1
	 * @param dos2 output stream for server 2
	 * @param dis1 input stream from server 1
	 * @param dis2 input stream from server 2
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static void sendData(List<Record> recordList, DataOutputStream dos1, DataOutputStream dos2, DataInputStream dis1, DataInputStream dis2) throws InterruptedException, IOException {
		List<byte[]> pages = Utils.packageToPages(recordList);

		Thread socketThread1 = new Thread(new SocketHandler(pages, dos1, dis1, 1));
		Thread socketThread2 = new Thread(new SocketHandler(pages, dos2, dis2, 2));

		socketThread1.start();
		socketThread2.start();

		socketThread1.join();
		socketThread2.join();
	}

	// Helper class to handle socket connection
	static class SocketHandler implements Runnable {

		private List<byte[]> pages;
		private DataOutputStream dos;
		private DataInputStream dis;
		private int socketNumber;

		public SocketHandler(List<byte[]> pages, DataOutputStream dos, DataInputStream dis, int socketNumber) {
			this.pages = pages;
			this.dos = dos;
			this.dis = dis;
			this.socketNumber = socketNumber;
		}

		@Override
		public void run() {
			try {
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
							System.out.println("Waiting for the server on Socket " + socketNumber + " ... ");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
