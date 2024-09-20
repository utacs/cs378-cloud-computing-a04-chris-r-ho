package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<Object, Text, Text, Text> {

    // Create a Hadoop Text object to store words
    private Text word = new Text();
    private Text outputValue = new Text();

    public void map(Object key, Text value, Context context) 
            throws IOException, InterruptedException {
        // Split the input CSV line
        String[] fields = value.toString().split(",");

        // Ensure the line has enough columns
        if (fields.length > 11) {
            String airport_name = fields[4];  // Airline
            float departureDelay = 0;

            // Try parsing the departure delay
            try {
                departureDelay = Float.parseFloat(fields[11]);  // DEPARTURE_DELAY
            } catch (NumberFormatException e) {
                departureDelay = 0;  // Handle missing or invalid delay values
            }

            // Set the airport name as the key
            word.set(airport_name);

            // Combine count (1) and departure delay as a single output value (e.g., "1,5.0")
            outputValue.set("1;" + departureDelay);  // "1" for count, and the parsed departureDelay


            // Emit the key (airport name) and value (count and delay)
            context.write(word, outputValue);
        }
    }
}
