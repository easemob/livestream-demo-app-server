package com.easemob.agora.token.utils;

import org.apache.commons.lang.math.RandomUtils;

public class RandomUidUtil {
    public static String randomUid() {
        int randomUid = RandomUtils.nextInt(Integer.MAX_VALUE);
        if (randomUid == 0) {
            return String.valueOf(randomUid + 1);
        }
        return String.valueOf(randomUid);
    }
}
