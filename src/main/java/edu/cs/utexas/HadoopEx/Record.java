package edu.cs.utexas.HadoopEx;


import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;


public class Record implements Comparable<Record> {

        private Text id;
        private FloatWritable ratio;

        public Record(Text id, FloatWritable ratio) {
            this.id = id;
            this.ratio = ratio;
        }

        public Text getID() {
            return id;
        }

        public FloatWritable getRatio() {
            return ratio;
        }

        public String getString(){
            return id.toString();
        }
        
        public float getFloat() {
            return ratio.get();
        }

    /**
     * Compares two sort data objects by their value.
     * @param other
     * @return 0 if equal, negative if this < other, positive if this > other
     */
        @Override
        public int compareTo(Record other) {
            float thisval = getFloat();
            float otherval = other.getFloat();

            float diff = thisval - otherval;
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "("+getString() +" , "+ getFloat()+")";
        }

    }