You need the key to decode password. To have it please contact Tung Do.

How to run:
    By command line:
        Open terminal or cmd > cd to the source code folder
        Run this command: test -Dcucumber.options="--tags '@tc-01 and not @skip'" -Denv=staging -Dselenium=chrome,en,false -Dappium=android,Galaxy-S6,7.0,02157df2a156c408,none -Ddebug=true

    By IDE:
        (update later ...)

-Dappium=android,Galaxy_A12,11,R58T30KM9ZX,/Users/macos/Desktop/app.apk
-Dappium=android,emulator_galaxy_a12,12,emulator-5554,/Users/macos/Desktop/app.apk
-Dappium=android,emulator_pixel4,12,emulator-5554,/Users/macos/Desktop/app.apk

Teamcity note:
There are 3 agents available on TC (1 macOS, 2 windows)
- macOS one will be used to run automation for mobile app (appium is installed & config)
- Windows 1 will be used to run automation for webapp (selenium)

-Denv=demo
-Dselenium=chrome,en,false
-Dappium=android,Galaxy-S6,7.0,02157df2a156c408,none -Ddebug=true