package edu.cs.utexas.HadoopEx;


import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;


public class Task2Record implements Comparable<Task2Record> {

        private Text taxiID;
        private FloatWritable ratio;

        public Task2Record(Text taxiID, FloatWritable ratio) {
            this.taxiID = taxiID;
            this.ratio = ratio;
        }

        public Text getTaxiID() {
            return taxiID;
        }

        public FloatWritable getRatio() {
            return ratio;
        }

        public String getString(){
            return taxiID.toString();
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
        public int compareTo(Task2Record other) {
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