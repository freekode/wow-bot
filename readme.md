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
* Driver - for direct control the character (pure control: run, jump, cast a spell and etc.). Mostly this class should
provide many methods to control not only the character but also to control the mouse and keyboard. Ideally only Driver (
and Receiver) can use Robot class, another classes should use robot methods over the Driver class.
* Controller - combine previous two to provide more appropriate methods to control the character (go to coordinate, set azimuth and etc.)
Actually it is no an interface, it is abstract class, in which Driver and Receiver must be placed in the constructor.


### AI
Interface which explained in previous part was mostly enough for base steps. But to perform more complicated processes,
we need intellectual methods and for that you can create custom AI.
AI is a Thread which started and can be stopped at any time (actually there is many problems about killing the thread).
Each AI extends main abstract class Intelligence. That class created for more easy way to make your own AIs.
Intelligence perform main operations for starting and stopping and provide everything what needed to your AI.

Intelligence children must implement two methods:

* processing - main methods for AI, it is performing all main operations in your AI
* terminating - calling when exception occurs and Intelligence will stop the thread
Simple example is TestAI, it print "test passed" each second five times.

Main problem is killing the process. There is many places in the code where Thread.sleep() uses. And some where
InterruptedException is ignored, if so it just can not be interrupted, or stopped appropriately. To solve this bug,
you have to DO NOT CATCH InterruptedException. Please simply throw it to higher level.


## 0.1 version
 * ai settings
 * info in window    
 * pause button
 * movement AI


