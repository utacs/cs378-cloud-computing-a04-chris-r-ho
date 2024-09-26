package edu.cs.utexas.HadoopEx;

import java.io.IOException;
import java.util.PriorityQueue;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

public class Task2TopKMapper extends Mapper<Text, Text, Text, FloatWritable> {  

	private Logger logger = Logger.getLogger(Task2TopKMapper.class);


	private PriorityQueue<Task2Record> pq;

	public void setup(Context context) {
		pq = new PriorityQueue<>();

	}

	/**
	 * Reads in results from the first job and filters the topk results
	 *
	 * @param key
	 * @param value a float value stored as a string
	 */
	public void map(Text key, Text value, Context context)
			throws IOException, InterruptedException {


		float ratio = Float.parseFloat(value.toString().trim());
		
		pq.add(new Task2Record(new Text(key), new FloatWritable(ratio)));

		if (pq.size() > 5) {
			pq.poll();
		}
	}

	public void cleanup(Context context) throws IOException, InterruptedException {


		while (pq.size() > 0) {
			Task2Record wordAndCount = pq.poll();
			context.write(wordAndCount.getTaxiID(), wordAndCount.getRatio());
			logger.info("Task2TopKMapper PQ Status: " + pq.toString());
		}
	}

}