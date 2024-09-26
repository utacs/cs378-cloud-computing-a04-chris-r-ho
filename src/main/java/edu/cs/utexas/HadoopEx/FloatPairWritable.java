package edu.cs.utexas.HadoopEx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.FloatWritable;

public class FloatPairWritable implements Writable {
    private FloatWritable value1;
    private FloatWritable value2;

    // needed for internal mapreduce stuff
    public FloatPairWritable() {
        value1 = new FloatWritable();
        value2 = new FloatWritable();
    }

    public FloatPairWritable(FloatWritable value1, FloatWritable value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public FloatPairWritable(FloatPairWritable pair) {
        this.value1 = pair.getFirst();
        this.value2 = pair.getSecond();
    }

    public FloatWritable getFirst() {
        return this.value1;
    }

    public FloatWritable getSecond() {
        return this.value2;
    }

    @Override
    public String toString() {
        return value1.toString() + "," + value2.toString();
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        if (value1 == null || value2 == null) return;
        value1.readFields(in);
        value2.readFields(in);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        if (value1 == null || value2 == null) return;
        value1.write(out);
        value2.write(out);
    }
}
