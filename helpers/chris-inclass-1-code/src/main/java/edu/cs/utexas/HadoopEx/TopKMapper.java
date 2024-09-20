package edu.cs.utexas.HadoopEx;

import java.io.IOException;
import java.util.PriorityQueue;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;


public class TopKMapper extends Mapper<Text, Text, Text, Text> {  // Change output value type to Text


	private Logger logger = Logger.getLogger(TopKMapper.class);


	private PriorityQueue<WordAndCount> pq;

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


		String[] parts = value.toString().split(";");
		int count = Integer.parseInt(parts[0]);
		float delay = Float.parseFloat(parts[1]);
		
		pq.add(new WordAndCount(new Text(key), new IntWritable(count), new FloatWritable(delay)));
				

		if (pq.size() > 10) {
			pq.poll();
		}
	}

	public void cleanup(Context context) throws IOException, InterruptedException {


		while (pq.size() > 0) {
			WordAndCount wordAndCount = pq.poll();
			context.write(wordAndCount.getWord(), new Text(wordAndCount.getCount() + ";" + wordAndCount.getTotalDepartureDelay()));
			logger.info("TopKMapper PQ Status: " + pq.toString());
		}
	}

}