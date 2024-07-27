package com.oops.major.socialMediaPlatform.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oops.major.socialMediaPlatform.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import java.util.Random;

@Slf4j
public class AppUtil {

    // Static Variables
    private static String hostName;
    private final static Random random = new Random();

    private final static Gson gson = new GsonBuilder()
            .create();


    public static String toJson(Object object) {
        if (ObjectUtils.isEmpty(object)) {
            return AppConstant.EMPTY_STRING;
        }

        try {
            return gson.toJson(object);
        } catch (Exception exception) {
            log.info("Exception happened while converting to json string {}", exception.getMessage());
            return AppConstant.EMPTY_STRING;
        }
    }
}
