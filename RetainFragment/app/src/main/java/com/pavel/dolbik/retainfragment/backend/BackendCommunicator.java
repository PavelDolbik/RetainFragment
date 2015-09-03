package com.pavel.dolbik.retainfragment.backend;

/**
 * Created by Pavel on 29.08.2015.
 */
public interface BackendCommunicator {

    boolean postSignIn(String login, String password) throws InterruptedException;
}
