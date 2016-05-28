/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.Math;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Johannes
 */
public class ListMath {

    public static class OnDoubles {

        public static Double mean(List<Double> doubles) {
            Double total = 0.;
            for (Double db : doubles) {
                total += db;
            }
            return total / doubles.size();
        }

        public static Double median(List<Double> doubles) {
            Double median;
            if (doubles.size() % 2 == 0) {
                median = evenMedian(doubles);
            } else {
                median = oddMedian(doubles);
            }
            return median;
        }

        private static Double evenMedian(List<Double> doubles) {
            Double a, b;
            Collections.sort(doubles);
            a = doubles.get(doubles.size() / 2 - 1);
            b = doubles.get(doubles.size() / 2);
            return (a + b) / 2;
        }

        private static Double oddMedian(List<Double> doubles) {
            Collections.sort(doubles);
            int index = (int) (doubles.size() / 2.);
            return doubles.get(index);
        }

        public static Double mode(List<Double> doubles) {
            Double maxValue = 0.;
            int maxCount = 0;
            for (int i = 0; i < doubles.size(); ++i) {
                int count = 0;
                for (int j = 0; j < doubles.size(); ++j) {
                    if (doubles.get(j).equals(doubles.get(i))) {
                        ++count;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    maxValue = doubles.get(i);
                }
            }
            return maxValue;
        }
    }
}
