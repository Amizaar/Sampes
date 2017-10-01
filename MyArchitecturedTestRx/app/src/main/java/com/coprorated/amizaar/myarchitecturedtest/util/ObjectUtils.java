package com.coprorated.amizaar.myarchitecturedtest.util;

/**
 * Created by amizaar on 12.09.2017.
 */

public class ObjectUtils {
    public static boolean equals(Object obj1, Object obj2) {
        if (obj1 == null)
            return obj2 == null;
        return obj2 != null && obj1.equals(obj2);
    }
}
