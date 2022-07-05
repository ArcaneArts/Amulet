/*
 * Amulet is an extension api for Java
 * Copyright (c) 2022 Arcane Arts
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package Amulet.extensions.java.lang.String;

import art.arcane.amulet.io.IO;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Extension
public class XString {
    public static String upper(@This String s) {
        return s.toUpperCase(Locale.ROOT);
    }

    public static byte[] md5(@This String s) {
        return IO.hashMD5(s);
    }

    public static String fromBase64(@This String s) {
        return new String(Base64.getUrlDecoder().decode(s), StandardCharsets.UTF_8);
    }

    public static String toBase64String(@This String s) {
        return Base64.getUrlEncoder().encodeToString(s.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] toBase64(@This String s) {
        return Base64.getUrlEncoder().encode(s.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] sha1(@This String s) {
        return IO.hashSHA1(s);
    }

    public static byte[] sha512(@This String s) {
        return IO.hashSHA512(s);
    }

    public static byte[] sha384(@This String s) {
        return IO.hashSHA384(s);
    }

    public static byte[] sha256(@This String s) {
        return IO.hashSHA256(s);
    }

    public static byte[] hash(@This String s, String algo) {
        return IO.hash(s, algo);
    }

    public static String lower(@This String s) {
        return s.toLowerCase(Locale.ROOT);
    }

    public static double toDouble(@This String s) {
        return Double.parseDouble(s);
    }
    public static double toDouble(@This String s, double or) {
        try{
            return toDouble(s);
        }

        catch(NumberFormatException e){
            return or;
        }
    }

    public static int toInt(@This String s) {
        return Integer.parseInt(s);
    }
    public static int toInt(@This String s, int or) {
        try{
            return toInt(s);
        }

        catch(NumberFormatException e){
            return or;
        }
    }

    public static long toLong(@This String s) {
        return Long.parseLong(s);
    }
    public static long toLong(@This String s, long or) {
        try{
            return toLong(s);
        }

        catch(NumberFormatException e){
            return or;
        }
    }


    public static String replaceAbs(@This String s, String find, String replace) {
        return s.replaceAll("\\Q" + find + "\\E", replace);
    }

    public static String[] splitAbs(@This String s, String find) {
        return s.split("\\Q" + find + "\\E");
    }

    public static String remove(@This String s, String find) {
        return s.replaceAbs(find, "");
    }

    public static boolean isNotEmpty(@This String s) {
        return !s.isEmpty();
    }

    public static String normalize(@This String s) {
        String buf = s;
        while (buf.contains("  ")) {
            buf = buf.replaceAbs("  ", " ");
        }

        return buf.trim();
    }

    /**
     * Capitalize the first letter
     *
     * @param s the string
     * @return the capitalized string
     */
    public static String capitalize(@This String s) {
        StringBuilder roll = new StringBuilder();
        boolean f = true;

        for (Character i : s.trim().toCharArray()) {
            if (f) {
                roll.append(Character.toUpperCase(i));
                f = false;
            } else {
                roll.append(i);
            }
        }

        return roll.toString();
    }

    /**
     * Capitalize all words in the string
     *
     * @param s the string
     * @return the capitalized string
     */
    public static String capitalizeWords(@This String s) {
        StringBuilder rollx = new StringBuilder();

        for (String i : s.trim().split(" ")) {
            rollx.append(" ").append(capitalize(i.trim()));
        }

        return rollx.substring(1);
    }

    /**
     * Hard word wrap
     *
     * @param s   the words
     * @param len the length per line
     * @return the wrapped string
     */
    public static String wrap(@This String s, int len) {
        return wrap(s, len, null, false);
    }

    /**
     * Soft Word wrap
     *
     * @param s   the string
     * @param len the length to wrap
     * @return the wrapped string
     */
    public static String wrapWords(@This String s, int len) {
        return wrap(s, len, null, true);
    }

    /**
     * Wrap words
     *
     * @param s          the string
     * @param len        the wrap length
     * @param newLineSep the new line seperator
     * @param soft       should it be soft wrapped or hard wrapped?
     * @return the wrapped words
     */
    public static String wrap(@This String s, int len, String newLineSep, boolean soft) {
        return wrap(s, len, newLineSep, soft, " ");
    }

    /**
     * Wrap words
     *
     * @param s          the string
     * @param len        the length
     * @param newLineSep the new line seperator
     * @param soft       soft or hard wrapping
     * @param regex      the regex
     * @return the wrapped string
     */
    public static String wrap(@This String s, int len, String newLineSep, boolean soft, String regex) {
        if (s == null) {
            return null;
        } else {
            if (newLineSep == null) {
                newLineSep = "\n";
            }

            if (len < 1) {
                len = 1;
            }

            if (regex.trim().equals("")) {
                regex = " ";
            }

            Pattern arg4 = Pattern.compile(regex);
            int arg5 = s.length();
            int arg6 = 0;
            StringBuilder arg7 = new StringBuilder(arg5 + 32);

            while (arg6 < arg5) {
                int arg8 = -1;
                Matcher arg9 = arg4.matcher(s.substring(arg6, Math.min(arg6 + len + 1, arg5)));
                if (arg9.find()) {
                    if (arg9.start() == 0) {
                        arg6 += arg9.end();
                        continue;
                    }

                    arg8 = arg9.start();
                }

                if (arg5 - arg6 <= len) {
                    break;
                }

                while (arg9.find()) {
                    arg8 = arg9.start() + arg6;
                }

                if (arg8 >= arg6) {
                    arg7.append(s, arg6, arg8);
                    arg7.append(newLineSep);
                    arg6 = arg8 + 1;
                } else if (soft) {
                    arg7.append(s, arg6, len + arg6);
                    arg7.append(newLineSep);
                    arg6 += len;
                } else {
                    arg9 = arg4.matcher(s.substring(arg6 + len));
                    if (arg9.find()) {
                        arg8 = arg9.start() + arg6 + len;
                    }

                    if (arg8 >= 0) {
                        arg7.append(s, arg6, arg8);
                        arg7.append(newLineSep);
                        arg6 = arg8 + 1;
                    } else {
                        arg7.append(s.substring(arg6));
                        arg6 = arg5;
                    }
                }
            }

            arg7.append(s.substring(arg6));
            return arg7.toString();
        }
    }

    /**
     * Trim a string to a length, then append ... at the end if it extends the limit
     *
     * @param s the string
     * @param l the limit
     * @return the modified string
     */
    public static String elipse(@This String s, int l) {
        if (s.length() <= l) {
            return s;
        }

        return s.substring(0, l) + "...";
    }

    /**
     * Get the number representation from roman numerals.
     *
     * @param number the roman number
     * @return the int representation
     */
    public static int fromRoman(@This String number) {
        if (number.isEmpty()) {
            return 0;
        }

        number = number.toUpperCase();

        if (number.startsWith("M")) {
            return 1000 + fromRoman(number.substring(1));
        }

        if (number.startsWith("CM")) {
            return 900 + fromRoman(number.substring(2));
        }

        if (number.startsWith("D")) {
            return 500 + fromRoman(number.substring(1));
        }

        if (number.startsWith("CD")) {
            return 400 + fromRoman(number.substring(2));
        }

        if (number.startsWith("C")) {
            return 100 + fromRoman(number.substring(1));
        }

        if (number.startsWith("XC")) {
            return 90 + fromRoman(number.substring(2));
        }

        if (number.startsWith("L")) {
            return 50 + fromRoman(number.substring(1));
        }

        if (number.startsWith("XL")) {
            return 40 + fromRoman(number.substring(2));
        }

        if (number.startsWith("X")) {
            return 10 + fromRoman(number.substring(1));
        }

        if (number.startsWith("IX")) {
            return 9 + fromRoman(number.substring(2));
        }

        if (number.startsWith("V")) {
            return 5 + fromRoman(number.substring(1));
        }

        if (number.startsWith("IV")) {
            return 4 + fromRoman(number.substring(2));
        }

        if (number.startsWith("I")) {
            return 1 + fromRoman(number.substring(1));
        }

        return 0;
    }

    /**
     * Scroll text
     *
     * @param smx      the text
     * @param viewport the viewport length
     * @param time     the timeline value
     */
    public static String scroll(@This String smx, int viewport, long time) {
        String src = " ".repeat(viewport) + smx + " ".repeat(viewport);
        int len = src.length();
        int walk = (int) (time % (len - viewport));
        String base = src.substring(walk, Math.min(walk + viewport, len - 1));
        base = base.length() < viewport ? base + " ".repeat((viewport - base.length()) - 3) : base;

        return base;
    }
}
