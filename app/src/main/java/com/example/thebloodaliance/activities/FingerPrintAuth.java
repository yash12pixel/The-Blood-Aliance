package com.example.thebloodaliance.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;
import com.example.thebloodaliance.R;
import com.example.thebloodaliance.activities.SplashActivity;

public class FingerPrintAuth extends AppCompatActivity implements BiometricCallback {
    BiometricManager mBiometricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print_auth);

        BiometricManager.BiometricBuilder biometricBuilder = new BiometricManager.BiometricBuilder(FingerPrintAuth.this);
        biometricBuilder.setTitle(getString(R.string.biometric_title));
        biometricBuilder.setSubtitle(getString(R.string.biometric_subtitle));
        biometricBuilder.setDescription(getString(R.string.biometric_description));
        biometricBuilder.setNegativeButtonText(getString(R.string.biometric_negative_button_text));
        mBiometricManager = biometricBuilder
                .build();

        //start authentication
        mBiometricManager.authenticate(FingerPrintAuth.this);
    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_success), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }
}