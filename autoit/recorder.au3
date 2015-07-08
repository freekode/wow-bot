#include <Array.au3>
#include <File.au3>
#include "libkey.au3"
#include "libwow.au3"
#include "libcharacter.au3"
#include "libetc.au3"



Global $winName = "World of Warcraft"
Global $addon[] = [10, 10]
;standart pitch should be around -0.25
Global $standartPitch = -0.25

Global $xError = 0.1
Global $yError = 0.1
Global $azimythError = 0.01
Global $pitchError = 0.02
Global $distanceError = 0.05


Opt("MouseCoordMode", 2)
Opt("PixelCoordMode", 2)


Func _Main()
    _FindWindow($winName)
    if Number(@error) == 2 then
        MsgBox(16, "Error", "There is no window")
        Exit(1)
    EndIf

    OnAutoItExitRegister('_OnExit')

    

    $filename = @year & '.' & @mon & '.' & @mday & '_' & @hour & '.' & @min & '.' & @sec
    
    Global $file = FileOpen($filename, 1)
    FileWriteLine($file, '')

    ; there are: move, rotate, gather
    HotKeySet("{F5}", "Move")
    HotKeySet("{F6}", "Rotate")
    HotKeySet("{F7}", "Gather")

    while true    
        sleep(100)
    wend
EndFunc
_Main()


Func Move()
    $player = _WowGetCoordinates()
    
    $x = StringFormat("%.2f", $player[0])
    $y = StringFormat("%.2f", $player[1])
    $line = 'move ' & $x & ' ' & $y
    FileWriteLine($file, $line)

    _Log($line)
EndFunc

Func Rotate()
    $azimyth = _WowGetAzimyth()
    
    $line = 'rotate ' & $azimyth
    FileWriteLine($file, $line)
    
    _Log($line)
EndFunc

Func Gather()
    $line = 'gather'
    FileWriteLine($file, $line)
    
    _Log($line)
EndFunc

Func _OnExit()
    FileClose($file)
EndFunc