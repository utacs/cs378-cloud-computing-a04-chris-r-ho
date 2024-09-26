package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Task2Reducer extends  Reducer<Text, IntWritable, Text, FloatWritable> {

    public void reduce(Text text, Iterable<IntWritable> values, Context context)
           throws IOException, InterruptedException {
	   
        int errorTot = 0;
        int total = 0;
    
        for (IntWritable value : values) {
            errorTot += value.get();
            total++;
        }

        float val = (float)errorTot/(float)total;
        
        context.write(text, new FloatWritable(val));
     
    }
}