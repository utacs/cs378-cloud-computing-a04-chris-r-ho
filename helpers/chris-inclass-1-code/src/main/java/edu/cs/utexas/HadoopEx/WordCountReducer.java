package edu.cs.utexas.HadoopEx;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text key, Iterable<Text> values, Context context) 
            throws IOException, InterruptedException {
        int totalCount = 0;
        float totalDelay = 0;

        // Iterate through all values (e.g., "1,5.0")
        for (Text val : values) {
            String[] parts = val.toString().split(";");
            if (parts.length == 2) {
                try {
                    int count = Integer.parseInt(parts[0]);
                    float delay = Float.parseFloat(parts[1]);

                    totalCount += count;
                    totalDelay += delay;
                } catch (NumberFormatException e) {
                    // Handle invalid values, if any

                    continue;
                }
            }
        }

        // Create the output as "totalCount,totalDelay"
        String result = totalCount + ";" + totalDelay;
        context.write(key, new Text(result));
    }
}
