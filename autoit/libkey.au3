$paused = false
HotKeySet("{F11}", "Pause")
HotKeySet("{F10}", "Kill")
Func Pause()
    $Paused = NOT $Paused
    While $Paused
        Sleep(100)
    WEnd
EndFunc
Func Kill()
   Exit
EndFunc