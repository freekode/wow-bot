# WoW Bot

## Task
System contains of two components. First is the WoW addon, which showing current info about character
by encoding numbers to colors. Second part is the java application, which controlling the character.

WoW addon located in anther repo. This repo is java application.

This application reading information from addon by decoding colors to numbers. After decoding colors and convert it to
digits we have several parameters, which addon is provided. Using this parameters we control our character with several AIs.

Core part include three interfaces. To receive information, completely and easily control the character.

* Receiver - receiving information from the addon (pure information about x, y and etc.). It scanning the screen decoding
the colors and produce understandable numbers
* Controller - for direct control the character (pure control: run, jump, cast a spell and etc.). Mostly this class should
provide many methods to control not only the character but also to control the mouse and keyboard. Ideally only Controller (
and Receiver) can use Robot class, another classes should use robot methods over the Controller class.
* MainAI - combine previous two to provide more appropriate methods to control the character (go to coordinate, set azimuth and etc.)

This is mainly enough to control the character. But to make more complicated things we need more intellectual methods
and for that you can create custom AI. Each AI can be combined with another to make much more complicated processes.




## 0.1 version
 * ai settings
 * info in window    
 * pause button
 * movement AI


