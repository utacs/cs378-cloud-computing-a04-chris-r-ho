package edu.cs.utexas.HadoopEx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class PairWritable<T extends Writable, U extends Writable> implements Writable {
    private T value1;
    private U value2;

    // needed for internal mapreduce stuff
    public PairWritable() {
        // try {
        //     value1 = (T) Writable.class.newInstance();
        //     value2 = (U) Writable.class.newInstance();
        // } catch (InstantiationException | IllegalAccessException e) {
        //     e.printStackTrace();
        // }
    }

    public PairWritable(T value1, U value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public PairWritable(PairWritable<T, U> pair) {
        this.value1 = pair.getFirst();
        this.value2 = pair.getSecond();
    }

    public T getFirst() {
        return this.value1;
    }

    public U getSecond() {
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
