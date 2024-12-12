package com.iesaguadulce.lopez_salazar_mario_pmdm02;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Activity managing the app startup, displaying manually a splash screen for API versions below v31
 * (upper versions manage splash screen automatically). After completion, launches the MainActivity.
 *
 * @author Mario López Salazar
 * @version 1.1
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    /**
     * Initializes the splash activity. Identifies the current API version and, if it's below v31,
     * displays manually the splash screen. In any case, launches the MainActivity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Android < v31:
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {

            // Showing customized splash screen:
            setContentView(R.layout.activity_splash);

            // Launching MainActivity after splash time:
            new Handler().postDelayed(
                    this::intentToMainActivity,
                    getResources().getInteger(R.integer.splashTime));
        }

        // Android >= v31:
        else {
            // Launching MainActivity (splash screen managed automatically from themes.xml):
            intentToMainActivity();
        }
    }


    /**
     * Starts MainActivity.
     */
    void intentToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}