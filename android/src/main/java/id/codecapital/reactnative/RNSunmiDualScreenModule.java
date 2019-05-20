
package id.codecapital.reactnative;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNSunmiDualScreenModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNSunmiDualScreenModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNSunmiDualScreen";
  }
}