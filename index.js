
import { NativeModules } from 'react-native';

const { RNSunmiDualScreen } = NativeModules;

export const SunmiDualScreen = {
  init: () => RNSunmiDualScreen.init(),
  getSecondScreenDeviceModel: () => RNSunmiDualScreen.getSecondScreenDeviceModel(),

  showWelcomeScreen: () =>
    new Promise((resolve, reject) =>
      RNSunmiDualScreen.showWelcomeScreen(() => resolve(), (error) => reject(error))),

  showTwoLineText: (title, content) =>
    new Promise((resolve, reject) =>
      RNSunmiDualScreen.showTwoLineText(title, content, () => resolve(), (error) => reject(error))),

  showList: (data) =>
    new Promise((resolve, reject) =>
      RNSunmiDualScreen.showList(data, () => resolve(), (error) => reject(error))),

  showSlidingImages: (images) =>
    new Promise((resolve, reject) =>
      RNSunmiDualScreen.showSlidingImages(images, () => resolve(), (error) => reject(error))),
}
