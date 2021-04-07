package com.azure.dev.main;

import com.azure.core.util.Configuration;

public class LiteMain {

    private static final String USER = Configuration.getGlobalConfiguration().get("USER");
    private static final String PASS = Configuration.getGlobalConfiguration().get("PAT");
    private static final String ORGANIZATION = "azure-sdk";
    private static final String PROJECT = "internal";

    private static final int BUILD_ID = Integer.parseInt(Configuration.getGlobalConfiguration().get("BUILD_ID"));

    public static void main(String[] args) throws InterruptedException {

    }
}
