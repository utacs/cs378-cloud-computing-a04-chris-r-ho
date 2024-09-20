package edu.cs.utexas.HadoopEx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;



public class TopKReducer extends Reducer<Text, Text, Text, Text> {


    private PriorityQueue<WordAndCount> pq = new PriorityQueue<>(10);;


    private Logger logger = Logger.getLogger(TopKReducer.class);


//    public void setup(Context context) {
//
//        pq = new PriorityQueue<WordAndCount>(10);
//    }


    /**
     * Takes in the topK from each mapper and calculates the overall topK
     * @param text
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    public void reduce(Text key, Iterable<Text> values, Context context)
           throws IOException, InterruptedException {

        // A local counter just to illustrate the number of values here!
        int counter = 0;

        // Parse both count and delay from the input value and add them to the priority queue
        for (Text value : values) {
            counter = counter + 1;

            // Log for debugging
            logger.info("Reducer Text: counter is " + counter);

            // Split the value to get count and departure delay (value format: "1,5.0")
            String[] parts = value.toString().split(";");
            if (parts.length == 2) {
                try {
                    int count = Integer.parseInt(parts[0]);
                    float delay = Float.parseFloat(parts[1]);

                    // Add the parsed values to the priority queue
                    pq.add(new WordAndCount(new Text(key), new IntWritable(count), new FloatWritable(delay)));
                    
                    // Log for debugging
                    logger.info("Reducer Text: Add this item " + new WordAndCount(key, new IntWritable(count), new FloatWritable(delay)).toString());
                } catch (NumberFormatException e) {
                    // Handle any parsing errors (e.g., if the input is invalid)
                    logger.error("Reducer Error: Invalid number format for key: " + key.toString());
                }
            }

            // Keep the priorityQueue size <= 10
            while (pq.size() > 10) {
                pq.poll();
            }
        }
    }


    public void cleanup(Context context) throws IOException, InterruptedException {
        logger.info("TopKReducer cleanup: cleaning up.");
        logger.info("pq.size() is " + pq.size());

        List<WordAndCount> values = new ArrayList<>(10);

        // Collect the values from the priority queue
        while (pq.size() > 0) {
            values.add(pq.poll());
        }

        // Log the size and values in the priority queue
        logger.info("values.size() is " + values.size());
        logger.info(values.toString());

        // Reverse the list to get values in descending order
        Collections.reverse(values);

        // Emit the top 10 values in descending order
        for (WordAndCount value : values) {
            context.write(value.getWord(), new Text(value.getCount() + "," + value.getTotalDepartureDelay()));
            logger.info("TopKReducer - Top-10 Words are:  " + value.getWord() + "  Count: " + value.getCount() + "  Delay: " + value.getTotalDepartureDelay()+ "  Ratio: " + value.getRatio());
        }
    }
}

