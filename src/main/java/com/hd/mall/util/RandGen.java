package com.hd.mall.util;

import java.util.Random;

public class RandGen {
    public static String fourRandomGen() {
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        int count = 0;

        StringBuilder sb = new StringBuilder();//动态字符串

        while (true) {
            char c = chars[random.nextInt(chars.length)];

            if (sb.indexOf(c + "") == -1) {
                sb.append(c);
                count++;
                if (count == 4) {
                    break;
                }
            }

        }
        return sb.toString();
    }

}
