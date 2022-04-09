# 福特电马 Mach-E 中国版安卓桌面小插件

- 感谢“小件件”sriptable 插件。本代码里所有URL都来自于这个插件的探索。
- 感谢原作者和几位下文里提到的作者。

## 注意：
福特在中国改变了这些服务器接口的返回值，所以本插件远不如国外的强大。国外的是可以远程获取几乎所有车辆信息（包括车门，充电等）甚至也可以远程控制车辆。非常强大。

看起来放在中国之后这些接口有变化。导致仅仅能获得定位，胎压，锁车状态，行驶里程等简单的数据。

本人和给我帮忙的小兄弟时间实在有限，希望未来有朋友能发现更多的福特中国服务器API，让这个插件像是国外一样强大。

## 声明
- 本人不是这个插件的原作者，仅仅做了一些基于公开信息的中国本土的接口适配。
- 本代码遵循GPL协议，必须开源。
- 本人从未从本代码获益，也建议任何组织和个人不要从中获益。涉及到法律相关问题与本人无关。

## 这里下载release版
[app-release.apk](https://github.com/dgntby/MachEWidget/tree/master/app/release/app-release.apk?raw=true)

# Mustang Mach-E Widget for Android

[![Donate](https://img.shields.io/badge/donate-paypal-green.svg?style=for-the-badge)](https://www.paypal.com/donate/?hosted_button_id=HULVHBSWXDU9S)

## Intro

This app/widget is based on the work of [Anthony (tonesto7)](https://github.com/tonesto7), which is in turn based on the earlier work of 
[David Schablowsky](https://github.com/dschablowsky/FordPassWidget), and also the work of [@DevSecOps](https://www.macheforum.com/site/threads/guide-android-mme-widget-more-complex.13588/)
.  It is not a complete implementation of the functionality of their widgets, as it:
- is only intended for the Ford Mustang Mach-E, as that's the only Ford I own.  As of Feb 2022 I'm extending capabilities to support F-150, Bronco, and
  Explorer owners as well (feedback needed) 
- does not include all the function of tonesto7's Apple/iOS widget
- does not currently support accounts outside of the United States, although it may still work

  <img src="app/src/main/assets/appwidget_sample.png" alt="Mach-E widget example" width="300" />
  <img src="app/src/main/assets/appwidget_sample_f150.png" alt="F-150 widget example" width="300" />
  <img src="app/src/main/assets/appwidget_sample_bronco.png" alt="Bronco widget example" width="300" />
  <img src="app/src/main/assets/appwidget_sample_explorer.png" alt="Explorer widget example" width="300" />

My purpose for hosting this code on GitHub under the GPL v3 license is to provide a starting point for anyone who wants to extend its functionality.

## Downloads

Download the [app-release.apk](https://github.com/khpylon/MachEWidget/blob/master/app/release/app-release.apk?raw=true) file and sideload on your Android device.

## Requirements

- Android 9, 10, 11, or 12 (may work on earlier Android versions, but not tested)
- [FordPass account](https://sso.ci.ford.com/authsvc/mtfim/sps/authsvc?PolicyId=urn:ibm:security:authentication:asf:basicldapuser&Target=https%3A%2F%2Fsso.ci.ford.com%2Foidc%2Fendpoint%2Fdefault%2Fauthorize%3FqsId%3D1f0281db-c684-454a-8d31-0c0f297cc9ed%26client_id%3D880cf418-6345-4e3b-81cd-7b623309b571&identity_source_id=75d08ad1-510f-468a-b69b-5ebc34f773e3#appID=CCCA9FB8-B941-46AD-A89F-F9D9D699DD68&propertySpecificContentKey=brand_ford&originURL=https%3A%2F%2Fwww.ford.com%2F&lang=en_us&fsURL=https%3A%2F%2Fapi.mps.ford.com) (has to be working with FordPass app, and NOT MyFord Mobile)

## Features

- Will automatically fetch data from servers (default is every 10 minutes, but configurable)
- Shows recent OTA information
- Send commands to car to lock or unlock doors, and perform remote start
- Assign "short cuts" to other related apps such as FordPass, Waze, etc

## Known Issues & Limitations

As listed above, this app
- is only intended for the Ford Mustang Mach-E, as that's the only Ford I own
- does not include all the function of tonesto7's Apple/iOS widget
- is not guaranteed to support accounts outside of the United States
- if you are using the app with an F-150/Bronco/Explorer, it may be necessary to uninstall when upgrading to newer versions.  This is because the 
  app is registered by the name "Mach-E Info", and using with an F-150 or Bronco aliases the app's name which can confuse
  your Android OS when it comes time to upgrade
  
## Bug Reports

If you discover something which doesn't work the way you expect, check first on the forums to see whether someone else has
reported a similar problem (or whether the app is actual working properly).  Also search under
["Issues"](https://github.com/khpylon/MachEWidget/issues) on GitHub to see if it's been reported.  If not, you will need to gather
some data to create a new bug report.  As of Version 2022.03.02 the app has some built-in support for gathering log information:

1. Under "Settings", activate "Enable logging"
2. Perform any actions you think will demonstrate the issue
3. Under the three-dot menu, select "Save logfile"; this will save a file named *mache_logfile.txt* or something similar in the *Downloads* folder of your
external storage
4. Deactive "Enable logging"
5. Create/append to an Issue and upload the file as an attachment

If the bug is something more serious that it isn't captured in these logs, you may need to capture some logcat output. 
A good summary of ways to get logcat output is
[described here](https://www.xda-developers.com/how-to-take-logs-android/).  Since most likely only crash information 
will be in the logcat, you most likely will want to run the logcat using the "-b crash" option.

## Frequently Asked Questions

Refer to the [FAQ](https://github.com/khpylon/MachEWidget/blob/master/FAQ.md) file.  It's a work in progress.  If your questions isn't there, ask on the forums or 
send a message to suggest adding it.

## To do

- Display information using more graphics and less text where possible.

## Credits

Thanks to [tonesto7](https://github.com/tonesto7/fordpass-scriptable) for his work on the new widget, and to
[dschablowsky](https://github.com/dschablowsky/FordPassWidget) for his work on the original widget, 
[d4v3y0rk](https://github.com/d4v3y0rk) for finding out the information about the Ford api.
Thanks to [@DevSecOps](https://www.macheforum.com/site/members/devsecops.7076/) on the [Mach-E Forums](https://www.macheforum.com/site/) 
for his valuable input and help with updating the appearance of the widget and permitting the use of the assets from his app.
Additional thanks to [marco79cgn](https://github.com/marco79cgn) and [Tobias Battenberg](https://github.com/mountbatt) for your widgets.  Also, thanks to those
who have funded the project via PayPal so that I have funds to purchase additional resources to further enhance the app.

A number of the icons used in this app are derived from free ones found on [Icon8](https://icons8.com/); thanks to the original creators.

## Disclaimer

I was inspired to create this app for Android in order to see more information about my vehicle, and to learn new things about Android programming.
I am not employed by Ford, and this app is not supported by Ford. 
The API used can be changed at any time by Ford. 
I am NOT liable for any kind of damage (special, direct, indirect, consequential or whatsoever) resulting from the use of this app. 

## License

This code is released as open source software under the GPL v3 license: see the [LICENSE](https://github.com/khpylon/MachEWidget/LICENSE.txt) file in the project root for the full license text.
