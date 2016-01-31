package puyuntech.com.beihai.ui.activity.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import puyuntech.com.beihai.R;
import puyuntech.com.beihai.app.APP;
import puyuntech.com.beihai.app.BaseAct;
import puyuntech.com.beihai.http.httpContor.HttpAfterExpand;
import puyuntech.com.beihai.http.httpContor.LoginHttpImpl;
import puyuntech.com.beihai.http.httpContor.Result;
import puyuntech.com.beihai.model.UserModel;
import puyuntech.com.beihai.ui.activity.main.MainActivityV3;
import puyuntech.com.beihai.utils.MyUtils;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseAct {


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "zx:123456:0", "zx1:123456:1"
    };

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private ScrollView mLoginFormView;
    private Button mEmailSignInButton;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mLoginFormView.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mLoginFormView = (ScrollView) findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        setListener();
    }

    private void setListener() {
        //登录
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        //禁止获取焦点
//        mLoginFormView.setFocusable(false);
        //禁止手动滑动
//        mLoginFormView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
//        mHandler.sendEmptyMessageDelayed(0,300);
        //软键盘弹出，ScrollView自动滚动到底部
//        mEmailView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mHandler.sendEmptyMessageDelayed(0,300);
//                return false;
//            }
//        });
//        mPasswordView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                mHandler.sendEmptyMessageDelayed(0, 300);
//                return false;
//            }
//        });
        //软键盘弹出，ScrollView自动滚动到底部
        mEmailView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            mEmailView.setFocusable(false);
                            mLoginFormView.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
                            return;
                        }
                    }, 400);
                }
            }
        });
    }


    /**
     * 登录校验
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password), MyUtils.getErrorIcon(this, R.mipmap.error_tip_icon));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required), MyUtils.getErrorIcon(this, R.mipmap.error_tip_icon));
            focusView = mEmailView;
            cancel = true;
        }
//        else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);

            // TODO: 2016/1/28 0028 登录
            LoginHttpImpl.getMHttpImpl(LoginActivity.this).login(email, password, new HttpAfterExpand() {
                @Override
                public void afferHttp() {
                    showProgress(false);
                }

                @Override
                public void afterSuccess(Result resultBean) {
                    //todo 登录成功，保存User
                    for (String credential : DUMMY_CREDENTIALS) {
                        String[] pieces = credential.split(":");
                        if (pieces[0].equals(email)) {
                            APP.user = new UserModel(pieces[0], Integer.parseInt(pieces[2]));
                        }
                    }
                    skipIntent(MainActivityV3.class, true);
                }

                @Override
                public void afterFail(Result resultBean) {

                }

                @Override
                public void afterError(Result resultBean) {
                    mPasswordView.setError(getString(R.string.error_incorrect_password), MyUtils.getErrorIcon(LoginActivity.this, R.mipmap.error_tip_icon));
                    mPasswordView.requestFocus();
                }
            });
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//            LoginHttpImpl.getMHttpImpl(LoginActivity.this).login(mEmail, mPassword, new HttpAfterExpand() {
//                @Override
//                public void afferHttp() {
//
//                }
//
//                @Override
//                public void afterSuccess(Result resultBean) {
//
//                }
//
//                @Override
//                public void afterFail(Result resultBean) {
//
//                }
//
//                @Override
//                public void afterError(Result resultBean) {
//
//                }
//            });
////            try {
////                // Simulate network access.
////                Thread.sleep(2000);
////            } catch (InterruptedException e) {
////                return false;
////            }
////
////            for (String credential : DUMMY_CREDENTIALS) {
////                String[] pieces = credential.split(":");
////                if (pieces[0].equals(mEmail)) {
////                    // Account exists, return true if the password matches.
////                    //保存登录类型【领导，网格员】
//////                    APP.type = Integer.parseInt(pieces[2]);
////                    APP.user = new UserModel(pieces[0], Integer.parseInt(pieces[2]));
////                    return pieces[1].equals(mPassword);
////                }
////            }
//            // TODO: register the new account here.
//            return false;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
////            showProgress(false);
//
//            if (success) {
////                finish();
//                skipIntent(MainActivityV3.class, true);
//
//            } else {
//                showProgress(false);
//                mPasswordView.setError(getString(R.string.error_incorrect_password), MyUtils.getErrorIcon(LoginActivity.this, R.mipmap.error_tip_icon));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
}

