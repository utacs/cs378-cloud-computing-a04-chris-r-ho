package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Task3Reducer extends  Reducer<Text, FloatPairWritable, Text, FloatWritable> {

    public void reduce(Text text, Iterable<FloatPairWritable> values, Context context)
           throws IOException, InterruptedException {
	   
        float totalAmt = 0;
        float tripTime = 0;
    
        for (FloatPairWritable value : values) {
            totalAmt += value.getFirst().get();
            tripTime += value.getSecond().get();
        }
        tripTime /= 60;
        float val = (float)totalAmt / (float)tripTime;
        val = Math.round(val*100) / (float)100;
        
        context.write(text, new FloatWritable(val));
     
    }
}