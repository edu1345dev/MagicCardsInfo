package com.android.josesantos.magiccardsinfo.presentation.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;

/**
 * @author QuangNguyen (quangctkm9207).
 */
public class BaseActivity extends AppCompatActivity {
  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

  @Override
  public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }
}
