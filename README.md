
# react-native-sunmi-dual-screen

## Getting started

`$ npm install react-native-sunmi-dual-screen --save`

### Mostly automatic installation

`$ react-native link react-native-sunmi-dual-screen`

### Manual installation


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


## Usage
```javascript
import RNSunmiDualScreen from 'react-native-sunmi-dual-screen';

// TODO: What to do with the module?
RNSunmiDualScreen;
```
  