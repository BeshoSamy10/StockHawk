package com.udacity.stockhawk.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.udacity.stockhawk.R;

import timber.log.Timber;


public class QuoteIntentService extends IntentService {

    private Handler handler;

    public QuoteIntentService() {
        super(QuoteIntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("Intent handled");
        boolean success = QuoteSyncJob.getQuotes(getApplicationContext());
        if(!success){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(QuoteIntentService.this, R.string.toast_symbol_not_exist, Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
