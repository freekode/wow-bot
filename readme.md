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


## Conclusion about the bot
Well, after several tests I have to say with such a type of control, type of reading the data about character, some
things are unable to proceed. I am talking abouth gathering and moving overall. First of all positioning, controlling
are quite precise, really with this there is no any big problem to simply walk by the coordinates in clean area.
But clean area (without mobs, players, etc...), yeah, not achievable. I am setting the azimuth and pitch by using a mouse.
Mouse is a very precise in comparison with keys (A and D). But when mouse "working" it can click on another external object
(for example a cow), and everything can happen. Now, the bot even can fight (quite poor, but mobs are not annoying any more).
Gathering, gathering is working with big plants. Sometimes "plant scanner" can not find a plant, but it exists.
Of course, all these problems of can be solved, but solution will cost too much time, need to find too much cases.
So I think with such technology I can not make it possible.

There is a module of fishing, I really proud of that. By my opinion it is very good module, with simple implementation.

So, try to not be so negative, it the future there is solution about everything, it is OpenCV. Computer Vision for such
project may be only one solution which can allow easy resolve current problem cases.
