package extensions.java.lang.String;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.Locale;

@Extension
public class XString {
    public static String upper(@This String s)
    {
        return s.toUpperCase(Locale.ROOT);
    }

    public static String lower(@This String s)
    {
        return s.toLowerCase(Locale.ROOT);
    }

    public static String replaceAbs(@This String s, String find, String replace)
    {
        return s.replaceAll("\\Q" + find + "\\E", replace);
    }

    public static String[] splitAbs(@This String s, String find)
    {
        return s.split("\\Q" + find + "\\E");
    }

    public static String remove(@This String s, String find)
    {
        return s.replaceAbs(find, "");
    }

    public static boolean isNotEmpty(@This String s)
    {
        return !s.isEmpty();
    }
    public static String normalize(@This String s)
    {
        String buf = s;
        while(buf.contains("  "))
        {
            buf = buf.replaceAbs("  ", " ");
        }

        return buf.trim();
    }

}
