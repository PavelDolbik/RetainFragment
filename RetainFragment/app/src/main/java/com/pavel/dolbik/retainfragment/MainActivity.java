package com.pavel.dolbik.retainfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SignModel.Observer{

    private EditText    mUserNameEt;
    private EditText    mPasswordEt;
    private View        mSubmitView;
    private ProgressBar mProgressBar;

    private SignModel   mSignModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserNameEt  = (EditText) findViewById(R.id.loginEt);
        mPasswordEt  = (EditText) findViewById(R.id.passwordEt);
        mSubmitView  = findViewById(R.id.submitTv);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mSubmitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login    = mUserNameEt.getText().toString();
                String password = mPasswordEt.getText().toString();

                mSignModel.signIn(login, password);
            }
        });

        RetainFragment retainFragment = (RetainFragment) getFragmentManager().findFragmentByTag("retain");

        if(retainFragment != null) {
            mSignModel = retainFragment.getSignModel();
        } else {
            RetainFragment fragment = new RetainFragment();
            getFragmentManager().beginTransaction().add(fragment, "retain").commit();
            mSignModel = fragment.getSignModel();
        }

        mSignModel.registrationObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSignModel.unregisterObserver(this);
    }

    @Override
    public void onSignInStarted(SignModel signModel) {
        showProgress(true);
    }

    @Override
    public void onSignInSucceeded(SignModel signModel) {
        showProgress(true);
        Toast.makeText(this, "Succeed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignInFailed(SignModel signModel) {
        showProgress(true);
        Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
    }

    private void showProgress(boolean show){
        mUserNameEt.setEnabled(!show);
        mPasswordEt.setEnabled(!show);
        mSubmitView.setEnabled(!show);
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
