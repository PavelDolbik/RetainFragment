package com.pavel.dolbik.retainfragment.backend;

/**
 * Created by Pavel on 29.08.2015.
 */
public class CommunicatorFactory {

    public static BackendCommunicator createBackendCommunicator() {
        return new BackendCommunicatorStub();
    }
}
