package com.pavel.dolbik.retainfragment;

import android.database.Observable;
import android.os.AsyncTask;
import android.util.Log;

import com.pavel.dolbik.retainfragment.backend.BackendCommunicator;
import com.pavel.dolbik.retainfragment.backend.BackendCommunicatorStub;
import com.pavel.dolbik.retainfragment.backend.CommunicatorFactory;

/**
 * Created by Pavel on 29.08.2015.
 */
public class SignModel {

    private SignInObservable mSignInObservable = new SignInObservable();
    private SignInTask       mSignInTask;
    private boolean          mIsWorking;

    public SignModel() {
        Log.d("Pasha", "SignModel");
    }


    public void signIn(String userName, String password) {
        if(mIsWorking) {
            return;
        }

        mSignInObservable.notifyStarted();
        mIsWorking = true;
        mSignInTask = new SignInTask(userName, password);
        mSignInTask.execute();
    }

    public void stopSignIn(){
        if(mIsWorking) {
            mSignInTask.cancel(true);
            mIsWorking = false;
        }
    }

    public void registrationObserver(Observer observer) {
        mSignInObservable.registerObserver(observer);
        if(mIsWorking) {
            observer.onSignInStarted(this);
        }
    }

    public void unregisterObserver(Observer observer) {
        mSignInObservable.unregisterObserver(observer);
    }

    public interface Observer {
        void onSignInStarted(SignModel signModel);
        void onSignInSucceeded(SignModel signModel);
        void onSignInFailed(SignModel signModel);
    }

    private class SignInObservable extends Observable<Observer> {
        public void notifyStarted(){
            for(Observer o : mObservers) {
                o.onSignInStarted(SignModel.this);
            }
        }

        public void notifySucceeded() {
            for(Observer o : mObservers) {
                o.onSignInSucceeded(SignModel.this);
            }
        }

        public void notifyFailed() {
            for(Observer o : mObservers) {
                o.onSignInFailed(SignModel.this);
            }
        }
    }

    private class SignInTask extends AsyncTask<Void, Void, Boolean>{

        private String mUserName;
        private String mPassword;

        public SignInTask(String userName, String password) {
            this.mUserName = userName;
            this.mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            BackendCommunicator backendCommunicator = CommunicatorFactory.createBackendCommunicator();

            try {
                return backendCommunicator.postSignIn(mUserName, mPassword);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mIsWorking = false;

            if(aBoolean) {
                mSignInObservable.notifySucceeded();
            } else {
                mSignInObservable.notifyFailed();
            }
        }
    }
}
