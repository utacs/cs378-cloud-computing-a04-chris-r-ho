package edu.utexas.cs.cs378;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public class FinalItem {
    private String driverId;
	private int numTaxis;
	private float totalAmount;
	private float moneyPerMinute;

	private int taskNum;

    public FinalItem(DataItem d) {
       this.driverId = d.getDriverId();
       this.numTaxis = d.getNumTaxis();
       this.totalAmount = d.getTotalAmount();
       this.moneyPerMinute = d.getMoneyPerMinute();
       this.taskNum = d.getTaskNum();
    }

    public FinalItem(String driver, int numTaxis, float earnings, float earningsPerMin, int taskNum){
        this.driverId = driver;
        this.numTaxis = numTaxis;
        this.totalAmount = earnings;
        this.moneyPerMinute = earningsPerMin;
        this.taskNum = taskNum;
    }

	public int getTaskNum(){
		return taskNum;
	}

    public float getTotalAmount() {
		return Utils.floatToMoney(this.totalAmount);
	}

	public float getMoneyPerMinute() {
		return Utils.floatToMoney(this.moneyPerMinute);
	}

    /**
	 * This method manually serializes a data object of this type into a byte array.
	 * Order of writing the data into byte array matters and should be de-serialized
	 * in the same order.
	 * 
	 * @return
	 */
	public byte[] handSerializationWithByteBuffer() {

		byte[] driverIdBytes = driverId.getBytes(Charset.forName("UTF-8"));

		ByteBuffer byteBuffer = ByteBuffer.allocate(4 + driverIdBytes.length + 4 + 4 + 4 + 4);

		// 1. String driverId
		// First length of it and then its bytes
		// Each string value might be of different size. We have to write down its
		// length.
		byteBuffer.putInt(driverIdBytes.length);
		byteBuffer.put(driverIdBytes);

		// 2. Num Taxis
		byteBuffer.putInt(numTaxis);

		// 3. Total Amount
		byteBuffer.putFloat(totalAmount);

		// 4. Money Per Minute
		byteBuffer.putFloat(moneyPerMinute);

        // 5. Task Number
        byteBuffer.putInt(taskNum);

		return byteBuffer.array();
	}

	/**
	 * This method manually de-serializes an object of DataItem from a given byte
	 * array.
	 * 
	 * @param buf
	 * @return
	 */
	public static FinalItem deserializeFromBytes(byte[] buf) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(buf);

		// 1. Read the driverId string back from the byte array.

		int stringSize = byteBuffer.getInt(); // 4 bytes
		String tmpDriverId = extractString(byteBuffer, stringSize);

		// 2. Read the num taxis back
		int numTaxisTemp = byteBuffer.getInt(); // 4 bytes

		// 3. read the next float byte array back.
		float totalAmountTemp = byteBuffer.getFloat();
		
		// 4. read the next float byte array back.
		float totalMoneyPerMinuteTemp = byteBuffer.getFloat();

        // 5. read the last int byte array back.
        int taskNumTemp = byteBuffer.getInt();

		return new FinalItem(tmpDriverId, numTaxisTemp, totalAmountTemp, totalMoneyPerMinuteTemp, taskNumTemp);

	}

	/**
	 * This method reads a string from a byteBuffer.
	 * 
	 * @param byteBuffer
	 * @param stringSize
	 * @return
	 */
	private static String extractString(ByteBuffer byteBuffer, int stringSize) {
		byte[] stringBytes = new byte[stringSize];
		byteBuffer.get(stringBytes, 0, stringSize);

		String mystring = new String(stringBytes, Charset.forName("UTF-8"));
		return mystring;
	}

    @Override
	public String toString() {

		// Task 1: (Driver ID, Number of Taxis, totalAmount)
		if (this.taskNum == 1) {
			return "(" + driverId + ", " + numTaxis + ", " + getTotalAmount() + ")";
		}
		// Task 2: (Driver IDs, Money per Minute) 
		else if (this.taskNum == 2) {
			return "(" + driverId + ", " + getMoneyPerMinute() + ")";
		}
		// Default, print everything
		else {
			return "DataItem< dID=" + driverId + ", taxiCount=" + numTaxis + ", totalAmount=" + totalAmount + ", moneyPerMinute=" + moneyPerMinute + ">";
		}
	}
}
