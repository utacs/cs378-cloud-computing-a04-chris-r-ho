package edu.cs.utexas.HadoopEx;


import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;


public class WordAndCount implements Comparable<WordAndCount> {

        private Text word;
        private IntWritable count;
        private FloatWritable totalDepartureDelay;


        public WordAndCount(Text word, IntWritable count, FloatWritable totalDepartureDelay) {
            this.word = word;
            this.count = count;
            this.totalDepartureDelay = totalDepartureDelay;
        }
        

        

        public Text getWord() {
            return word;
        }

        public IntWritable getCount() {
            return count;
        }

        public FloatWritable getTotalDepartureDelay() {
            return totalDepartureDelay;
        }

        public float getRatio() {
            return totalDepartureDelay.get() / count.get();
        }

    /**
     * Compares two sort data objects by their value.
     * @param other
     * @return 0 if equal, negative if this < other, positive if this > other
     */
        @Override
        public int compareTo(WordAndCount other) {
            float ratio1 = totalDepartureDelay.get() / count.get();
            float ratio2 = other.totalDepartureDelay.get() / other.count.get();

            float diff = ratio1 - ratio2;
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            }
            return 0;
        }


        @Override
        public String toString() {
            return "(" + word.toString() + " ; " + count.toString() + " ; " + totalDepartureDelay.toString() + ")";
        }

    }

