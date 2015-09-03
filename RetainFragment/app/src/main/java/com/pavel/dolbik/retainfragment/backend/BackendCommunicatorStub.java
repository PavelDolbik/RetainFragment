package com.pavel.dolbik.retainfragment.backend;

/**
 * Created by Pavel on 29.08.2015.
 */
public class BackendCommunicatorStub implements BackendCommunicator {

    private static final String VALID_USER_NANE = "user";
    private static final String VALID_PASSWOR = "password";

    @Override
    public boolean postSignIn(String login, String password) throws InterruptedException {
        Thread.sleep(8000);
        return VALID_USER_NANE.equals(login) && VALID_PASSWOR.equals(password);
    }
}
