/*
 * Copyright (c) 2021.
 * Arcane Arts Inc. All rights reserved.
 * Proprietary. Do not distribute outside MPower Me LLC or Arcane Arts Inc.
 */

package art.arcane.amulet.metric;


public class DoubleArrayUtils {
    public static void shiftRight(double[] values, double push) {
        for(int index = values.length - 2; index >= 0; index--) {
            values[index + 1] = values[index];
        }

        values[0] = push;
    }

    public static void wrapRight(double[] values) {
        double last = values[values.length - 1];
        shiftRight(values, last);
    }

    public static void fill(double[] values, double value) {
        for(int i = 0; i < values.length; i++) {
            values[i] = value;
        }
    }
}
