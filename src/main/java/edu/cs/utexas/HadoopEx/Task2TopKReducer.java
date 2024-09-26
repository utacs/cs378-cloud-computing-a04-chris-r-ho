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



public class Task2TopKReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {


    private PriorityQueue<Task2Record> pq = new PriorityQueue<>(10);;


    private Logger logger = Logger.getLogger(Task2TopKReducer.class);

    /**
     * Takes in the topK from each mapper and calculates the overall topK
     * @param text
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    public void reduce(Text key, Iterable<FloatWritable> values, Context context)
           throws IOException, InterruptedException {

        // A local counter just to illustrate the number of values here!
        int counter = 0;

        for (FloatWritable value : values) {
            counter = counter + 1;

            // Log for debugging
            logger.info("Reducer Text: counter is " + counter);

            float ratio = value.get();
            try {
                // Add the float to the priority queue
                pq.add(new Task2Record(new Text(key), new FloatWritable(ratio)));
                
                // Log for debugging
                logger.info("Reducer Text: Add this item " + new Task2Record(key, new FloatWritable(ratio)).toString());
            } catch (NumberFormatException e) {
                logger.error("Reducer Error: " + key.toString());
            }

            // Keep the priorityQueue size <= 5
            while (pq.size() > 5) {
                pq.poll();
            }
        }
    }


    public void cleanup(Context context) throws IOException, InterruptedException {
        logger.info("Task2TopKReducer cleanup: cleaning up.");
        logger.info("pq.size() is " + pq.size());

        List<Task2Record> values = new ArrayList<>(10);

        // Collect the values from the priority queue
        while (pq.size() > 0) {
            values.add(pq.poll());
        }

        // Log the size and values in the priority queue
        logger.info("values.size() is " + values.size());
        logger.info(values.toString());

        Collections.reverse(values);

        // Emit the top 5 values in descending order
        for (Task2Record value : values) {
            context.write(value.getTaxiID(), value.getRatio());
            logger.info("Task2TopKReducer - Top-5 are:  " + value.getString() + "  Ratio: " + value.getFloat());
        }
    }
}