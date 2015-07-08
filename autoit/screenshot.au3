#include <Color.au3>
#include <Date.au3>
#include "keylib.au3"


Global $winName = "World of Warcraft"


Opt("MouseCoordMode", 2)
Opt("PixelCoordMode", 2)


Func _FindWindow()
	if WinExists($winName) == False Then
		Return False
	EndIf
	Global $hwnd = WinGetHandle($WinName)
	Global $winsize = WinGetClientSize($hwnd)
	WinActivate($winName)

	Return True
EndFunc

Func _Main()
	if (_FindWindow() == False) Then
		MsgBox(16, "Error", "There is no window")
		Exit(1)
	EndIf


	$switchFirst = 0
	$switchSecond = 0
	$switchThird = 0
	While True
		if (Mod(@MIN, 5) == 0) Then
			if ($switchFirst == 0) Then
				MsgBox(0, 'WoW Picture', 'Wake up', 3)

				_FindWindow()
				_WakeUp()
				_Screenshot()

				$switchFirst = 1
			EndIf
		Else
			$switchFirst = 0
		EndIf

		Sleep(40 * 1000)
	WEnd
EndFunc
_Main()


Func _WakeUp()
	$time = _Date_Time_GetSystemTime()
	ConsoleWrite('wake up ' & _Date_Time_SystemTimeToDateTimeStr($time) & @CRLF)

	Sleep(1000)
	Send('{SPACE}')
;~ 	Sleep(2000)
;~ 	Send('{x}')
	Sleep(3000)
EndFunc

Func _Screenshot()
	$time = _Date_Time_GetSystemTime()
	ConsoleWrite('take a photo ' & _Date_Time_SystemTimeToDateTimeStr($time) & @CRLF)

	Send('{PRINTSCREEN}')
EndFunc