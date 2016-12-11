@echo off
REM Use this Batch file to wirelessly connect directly to a laptop with built in WiFi
REM This assumes that your laptop has a wireless card and that you have administrator rights
REM Since the IP address assigned to your phone will change each time you connect, 
REM you will need to lookup your phone's IP  during this script and enter it when prompted.
REM
REM *****  This script MUST be run "As Administrator"
REM
REM *****  For more help, go to www.YouTube.com/user/GEARSinc/playlists
REM
adb kill-server
netsh wlan stop hostednetwork
echo --  Starting a Hosted Network called: Android11096Dev
netsh wlan set hostednetwork mode=allow SSID=Android11096Dev key=ECR11096
netsh wlan start hostednetwork
echo --  Make sure the phone is connected to the computer via USB
set /p ok= --  Hit enter when phone is plugged in: 
adb usb
Timeout 10
adb tcpip 5555
echo --
echo --  Now connect your phone to the "Android11096Dev" network.
echo --  Use Settings-WiFi link to get the phone's NEW IP address
set /p phoneip= --  Enter the IP address here:  
adb connect %phoneip%
adb devices
set /p ok= --  Unplug the phone and hit Enter to see the final connection.
adb devices
Timeout 5
