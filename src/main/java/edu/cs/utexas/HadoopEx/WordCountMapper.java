package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

	private final int PICKUP_TIME_IDX = 2;
	private final int PICKUP_LONG_IDX = 6;
	private final int PICKUP_LAT_IDX = 7;

	private final int DROPOFF_TIME_IDX = 3;
	private final int DROPOFF_LONG_IDX = 8;
	private final int DROPOFF_LAT_IDX = 9;

	// Create a counter and initialize with 1
	private final IntWritable counter = new IntWritable(1);
	// Create a hadoop text object to store words
	private Text word = new Text();

	public void map(Object key, Text value, Context context) 
			throws IOException, InterruptedException {
		
		String[] text = value.toString().trim().split(",");

		if (!parse(text, value.toString())) return;

		IntWritable error;
		Text time;
		
		// 0 - hour (between 1-24 if valid, -1 otherwise), 1 - 0/1 for if longitude has error or not, 2 - 0/1 for if the latitude has error or not
		int[] pickup = new int[3];
		pickup = parseGPS(text, PICKUP_TIME_IDX, PICKUP_LONG_IDX, PICKUP_LAT_IDX);
		
		// Map it
		if (pickup[0] != -1) {
			// max of 0 or 1 - 1 means there is an error
			error = new IntWritable(Math.max(pickup[1], pickup[2]));

			// Change time to String & format
			time = new Text((pickup[0] < 10)?"0"+pickup[0]:""+pickup[0]);

			// Map
			context.write(time, error);
		}

		int[] dropoff = new int[3];
		dropoff = parseGPS(text, DROPOFF_TIME_IDX, DROPOFF_LONG_IDX, DROPOFF_LAT_IDX);

		if (dropoff[0] != -1){
			error = new IntWritable(Math.max(dropoff[1], dropoff[2]));
			time = new Text((dropoff[0] < 10)?"0"+dropoff[0]:""+dropoff[0]);
			context.write(time, error);
		}
	}

	public int[] parseGPS(String[] values, int timeIndex, int longIndex, int latIndex) {
		int time = -1;
		int longi = 1;
		int lat = 1;

		try {
			String[] splitDateAndTime = values[timeIndex].trim().split(" ");
			String[] splitTime = splitDateAndTime[1].trim().split(":");
			time = Integer.parseInt(splitTime[0]);
		
			float lo = Float.parseFloat(values[longIndex].trim());
			float la = Float.parseFloat(values[latIndex].trim());
			if (Math.abs(lo) > 0) {
				longi = 0;
			}
			if (Math.abs(la) > 0) {
				lat = 0;
			}
		} catch (Exception e) {
			System.err.println("BIG GPS ERROR " + values[timeIndex] + ": (" + values[longIndex] + ", " + values[latIndex] + ")");
		}
		return new int[]{time, longi, lat};
	}

	public boolean parse(String[] values, String input) {
		if (values.length != 17) return false;

		try {
			float total = Float.parseFloat(values[16]);
			if (total > 500) return false; //do we need to keep this?

			float checkTotal = 0;
			for (int i = 11; i <= 15; i++) {
				checkTotal += Float.parseFloat(values[i]);
			}

			if (Math.round(checkTotal * 1000) != Math.round(total * 1000)) {
				System.out.println("\nBIG ERROR: " + checkTotal + " Not equal " + total + "\n" + input + "\n");
				return false;
			}

			float tripTimeInSec = Float.parseFloat(values[4]);

			if (Math.round(tripTimeInSec*1000) == 0) return false;
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}