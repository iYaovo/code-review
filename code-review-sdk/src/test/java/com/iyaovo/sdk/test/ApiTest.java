package com.iyaovo.sdk.test;


import com.iyaovo.sdk.types.utils.BearerTokenUtils;

public class ApiTest {

    public static void main(String[] args) {
        String apiKeySecret = "75575f7ac33940449c86b588272d9408.pDmlbeaH8KDo7FWs";
        String token = BearerTokenUtils.getToken(apiKeySecret);
        System.out.println(token);
    }




}
