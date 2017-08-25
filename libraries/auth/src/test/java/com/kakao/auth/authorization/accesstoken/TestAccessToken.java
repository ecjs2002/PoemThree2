package com.kakao.auth.authorization.accesstoken;

import java.util.Date;

/**
 * @author kevin.kang. Created on 2017. 7. 25..
 */

public class TestAccessToken implements AccessToken {
    @Override
    public String getAccessToken() {
        return "access_token";
    }

    @Override
    public String getRefreshToken() {
        return "refresh_token";
    }

    @Override
    public Date accessTokenExpiresAt() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60);
    }

    @Override
    public Date refreshTokenExpiresAt() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 30);
    }

    @Override
    public int getRemainingExpireTime() {
        return 1000 * 60 * 60;
    }

    @Override
    public boolean hasValidAccessToken() {
        return true;
    }

    @Override
    public boolean hasRefreshToken() {
        return true;
    }

    @Override
    public void updateAccessToken(AccessToken accessToken) {

    }

    @Override
    public void clearAccessToken() {

    }

    @Override
    public void clearRefreshToken() {

    }
}
