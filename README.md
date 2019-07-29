
# react-native-sunmi-dual-screen

## Getting Started

`$ npm install react-native-sunmi-dual-screen --save`

### Mostly Automatic Installation

`$ react-native link react-native-sunmi-dual-screen`

### Manual Installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-sunmi-dual-screen` and add `RNSunmiDualScreen.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNSunmiDualScreen.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import id.codecapital.reactnative.RNSunmiDualScreenPackage;` to the imports at the top of the file
  - Add `new RNSunmiDualScreenPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-sunmi-dual-screen'
  	project(':react-native-sunmi-dual-screen').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-sunmi-dual-screen/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-sunmi-dual-screen')
  	```


### Post Installation


#### Android

1. Open up `android/app/src/main/AndroidManifest.xml`
2. Add this SUNMI receiver code in the middle of `application` tag:
    ```
    <receiver android:name="sunmi.ds.MsgReceiver">
      <intent-filter>
        <action android:name="com.sunmi.hcservice"/>
        <action android:name="com.sunmi.hcservice.status"/>
      </intent-filter>
    </receiver>
    ```


## Usage

```javascript
import RNSunmiDualScreen from 'react-native-sunmi-dual-screen';

// TODO: What to do with the module?
RNSunmiDualScreen;
```
