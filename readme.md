# WoW Bot

## Task
Create a program which able automatically control the character in World Of Warcraft.

System contains of two components. First is the WoW addon, which showing current info about character
by encoding numbers to colors. Second part is the java application, which controlling the character.

WoW addon located in anther repo. This repo is java application.

This application reading information from addon by decoding colors to numbers. After decoding colors and convert it to
digits we have several parameters, which addon is provided. Using this parameters we control our character with several AIs.

Core part include three classes. To receive information, completely and easily control the character.
All classes are singleton classes.

* Receiver - receiving information from the addon (pure information about x, y and etc.). It scanning the screen, decoding
the colors and produce understandable numbers or boolean variables.
* Driver - for direct control the character (pure control: run, jump, cast a spell and etc.). Mostly this class should
provide many methods to control not only the character but also to control the mouse and keyboard. Ideally only Driver (
and Receiver) can use Robot class, another classes should use robot methods over the Driver class.
* Controller - combine previous two to provide more appropriate methods to control the character (go to coordinate, set azimuth and etc.)
This class combine previous two classes and provide high level methods.


### AI
Interface which explained in previous part was mostly enough for base steps. But to perform more complicated processes,
we need intellectual methods and for that you can create custom AI.
AI is a thread which started and can be stopped at any time (actually there is many problems about killing the thread).
Each AI extends main abstract class Intelligence. That class created for more easy way to make your own AIs.
Intelligence perform main operations for starting and stopping thread and provide everything what needed to your AI.

Simple example is TestAI.

All AI will started from swing ui, so Intelligence extends SwingWorker class, which provide useful methods to change ui
from the thread.

Main problem is killing the process. There is many places in the code where Thread.sleep() uses. And some where
InterruptedException is ignored, if so it just can not be interrupted, or stopped appropriately. To solve this bug,
you have to DO NOT CATCH InterruptedException. Please simply throw it to higher level.


### Module
Module combine your AI and ui interface which will be show when your module selected in the gui.
Module is abstract class, provide several methods and abstract methods, for easily receive information from your ai
and change values in gui.
