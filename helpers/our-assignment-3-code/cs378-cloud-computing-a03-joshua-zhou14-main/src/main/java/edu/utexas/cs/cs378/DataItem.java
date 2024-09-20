package edu.utexas.cs.cs378;
import java.util.HashSet;

import edu.utexas.cs.cs378.Record;

/**
 * This class represents a data item with a string and two float values.
 * 
 * @author kiat
 *
 */
public class DataItem implements Comparable<DataItem> {

	private String driverId;
	private HashSet<String> taxis;
	private float totalAmount;
	private float tripTimeInSec;

	private int taskNum;
	// Task 1: (Driver ID, Number of Taxis, totalAmount)
	// Task 2: (Driver IDs, TotalAmount/tripTimeInSec)

	public DataItem(int taskNum, Record r) {
		super();
		// Set task number
		this.taskNum = taskNum;

		// Populate from record
		this.totalAmount = r.totalAmount;
		this.tripTimeInSec = r.tripTimeInSec;
		this.driverId = r.driverId;

		// Need to store set of taxi IDs for task 1
		this.taxis = taskNum == 1 ? new HashSet<>() : null;
	}

	public String getDriverId(){
		return driverId;
	}

	public int getTaskNum(){
		return taskNum;
	}

	public int getNumTaxis() {
		return (this.taxis == null) ? 0 : this.taxis.size();
	}

	public float getTotalAmount() {
		return Utils.floatToMoney(this.totalAmount);
	}

	public float getMoneyPerMinute() {
		return Utils.floatToMoney(this.totalAmount / (this.tripTimeInSec) * (float) 60);
	}

	// Add a record to the data item
	public void add(Record r) {
		this.totalAmount += r.totalAmount;

		if (taskNum == 1) {
			this.taxis.add(r.taxiId);
		} else {
			this.tripTimeInSec += r.tripTimeInSec;
		}
	}

	@Override
	public int compareTo(DataItem o) {
		// Task 1: Tie break based on highest earnings, then number of taxis
		if (this.taskNum == 1) {
			return (int)((this.totalAmount - o.totalAmount) * 1000);
		}
		// Task 2: Rank based on money per minute
		else if (this.taskNum == 2) {
			return Float.compare(this.getMoneyPerMinute(), o.getMoneyPerMinute());
		}
		// Default
		return 0;
	}

	@Override
	public String toString() {

		// Task 1: (Driver ID, Number of Taxis, totalAmount)
		if (this.taskNum == 1) {
			return "(" + driverId + ", " + taxis.size() + ", " + getTotalAmount() + ")";
		}
		// Task 2: (Driver IDs, Money per Minute) 
		else if (this.taskNum == 2) {
			return "(" + driverId + ", " + getMoneyPerMinute() + ")";
		}
		// Default, print everything
		else {
			return "DataItem< dID=" + driverId + ", taxiCount=" + taxis.size() + ", totalAmount=" + totalAmount + ", tripTimeInSec=" + tripTimeInSec + ", moneyPerMinute=" + this.getMoneyPerMinute() + ">";
		}
	}
}

/**
	 * This method manually serializes a data object of this type into a byte array.
	 * Order of writing the data into byte array matters and should be de-serialized
	 * in the same order.
	 * 
	 * @return
	 */
	// public byte[] handSerializationWithByteBuffer() {

	// 	byte[] driverIdBytes = driverId.getBytes(Charset.forName("UTF-8"));
	// 	// 8 bytes for each float number (two float numbers 16)
	// 	// 4 byte for an integer to write the length of the string
	// 	// driverIdBytes.length for the legth of the string.

	// 	ByteBuffer byteBuffer = ByteBuffer.allocate(2 * 8 + 4 + driverIdBytes.length);

	// 	// 1. String driverId
	// 	// First length of it and then its bytes
	// 	// Each string value might be of different size. We have to write down its
	// 	// length.
	// 	byteBuffer.putInt(driverIdBytes.length);
	// 	byteBuffer.put(driverIdBytes);

	// 	// 2. numTaxis
	// 	byteBuffer.putFloat(numTaxis);

	// 	// 2. numTaxis
	// 	byteBuffer.putFloat(numTaxis);

	// 	return byteBuffer.array();
	// }

	/**
	 * This method manually de-serializes an object of DataItem from a given byte
	 * array.
	 * 
	 * @param buf
	 * @return
	 */
	// public DataItem deserializeFromBytes(byte[] buf) {

	// 	ByteBuffer byteBuffer = ByteBuffer.wrap(buf);

	// 	// 1. Read the driverId string back from the byte array.

	// 	int stringSize = byteBuffer.getInt(); // 4 bytes
	// 	String tmpDriverId = extractString(byteBuffer, stringSize);

	// 	// 2. read a float from the given byte array
	// 	float numTaxisTemp = byteBuffer.getFloat();

	// 	// 3. read the last float byte array back.
	// 	float totalAmountTemp = byteBuffer.getFloat();

	// 	return new DataItem(totalAmountTemp);

	// }

	/**
	 * This method reads a string from a byteBuffer.
	 * 
	 * @param byteBuffer
	 * @param stringSize
	 * @return
	 */
	// String extractString(ByteBuffer byteBuffer, int stringSize) {
	// 	byte[] stringBytes = new byte[stringSize];
	// 	byteBuffer.get(stringBytes, 0, stringSize);

	// 	String mystring = new String(stringBytes, Charset.forName("UTF-8"));
	// 	return mystring;
	// }