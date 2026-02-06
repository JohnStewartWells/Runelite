package com.RunLocked;

public class CommandConfig {

    public final String runCommand = "adb shell input tap 660 1000" ;
    public final String walkCommand = "adb shell input tap 400 1000";
    public final String slowDownCommand = "adb shell input tap 192 980";
    public final String speedUpCommand = "adb shell input tap 900 980";
    public final String pauseCommand = "adb shell input tap 520 1290";
    public final String playCommand = "adb shell input tap 380 1300";
}
