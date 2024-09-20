package edu.utexas.cs.cs378;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import edu.utexas.cs.cs378.Record;

public class Record {
    public String driverId;
    public String taxiId;
    public float totalAmount;
	public float tripTimeInSec;

    public Record(String driverId, String taxiId, float totalAmount, float tripTimeInSec) {
        this.driverId = driverId;
        this.taxiId = taxiId;
        this.totalAmount = totalAmount;
		this.tripTimeInSec = tripTimeInSec;
    }


    /**
     * Parse String input to get the values
     * @param input String representing one ride
     * @return a Record with the appropriate data, null if not valid
     */
    public static Record parse(String input) {
        String[] values = input.trim().split(",");
        if (values.length != 17) return null;

        try {
            float total = Float.parseFloat(values[16]);
            if (total > 500) return null;

            float checkTotal = 0;
            for (int i = 11; i <= 15; i++) {
                checkTotal += Float.parseFloat(values[i]);
            }

            if (Math.round(checkTotal * 1000) != Math.round(total * 1000)) {
				System.out.println("\nBIG ERROR: " + checkTotal + " Not equal " + total + "\n" + input + "\n");
				return null;
			}

			float tripTimeInSec = Float.parseFloat(values[4]);

			if (Math.round(tripTimeInSec*1000) == 0) return null;
			
            return new Record(values[1], values[0], total, tripTimeInSec);
        } catch (Exception e) {
            return null;
        }
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
		byte[] taxiIdBytes = taxiId.getBytes(Charset.forName("UTF-8"));
		// 4 bytes for each float number (two float numbers 16)
		// 4 byte for an integer to write the length of the string
		// driverIdBytes.length for the length of the string.

		ByteBuffer byteBuffer = ByteBuffer.allocate(4 + driverIdBytes.length + 4 + taxiIdBytes.length + 4 + 4);

		// 1. String driverId
		// First length of it and then its bytes
		// Each string value might be of different size. We have to write down its
		// length.
		byteBuffer.putInt(driverIdBytes.length);
		byteBuffer.put(driverIdBytes);

		// 2. String taxiId
		// First length of it and then its bytes
		// Each string value might be of different size. We have to write down its
		// length.
		byteBuffer.putInt(taxiIdBytes.length);
		byteBuffer.put(taxiIdBytes);

		// 3. Total Amount
		byteBuffer.putFloat(totalAmount);

		// 4. Trip Time 
		byteBuffer.putFloat(tripTimeInSec);

		return byteBuffer.array();
	}

	/**
	 * This method manually de-serializes an object of DataItem from a given byte
	 * array.
	 * 
	 * @param buf
	 * @return
	 */
	public static Record deserializeFromBytes(byte[] buf) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(buf);

		// 1. Read the driverId string back from the byte array.

		int stringSize = byteBuffer.getInt(); // 4 bytes
		String tmpDriverId = extractString(byteBuffer, stringSize);

		// 2. Read the taxiId string back from the byte array.

		stringSize = byteBuffer.getInt(); // 4 bytes
		String tmpTaxiId = extractString(byteBuffer, stringSize);

		// 3. read the next float byte array back.
		float totalAmountTemp = byteBuffer.getFloat();
		
		// 4. read the last float byte array back.
		float totalTripTimeInSecTemp = byteBuffer.getFloat();

		return new Record(tmpDriverId, tmpTaxiId, totalAmountTemp, totalTripTimeInSecTemp);

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
}
