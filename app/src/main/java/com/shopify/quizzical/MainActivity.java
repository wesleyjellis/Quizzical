package com.shopify.quizzical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.libsodium.jni.Sodium;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sodium.crypto_aead_chacha20poly1305_abytes();
    }
}
