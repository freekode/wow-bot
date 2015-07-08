#include <Array.au3>
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



    $player = _WowGetCoordinates()
    $pitch = _WowGetPitch()
    $azimyth = _WowGetAzimyth()

    ; MsgBox(0, "", "x = " & $player[0] & @CRLF & _
    ;               "y = " & $player[1] & @CRLF & _
    ;               "pitch =  " & $pitch & @CRLF & _
    ;               "azimyth = " & $azimyth)


    ;  local $points[][] = [ _
    ;     [75.8, 72.3], _
    ;     [74.6, 72.4], _
    ;     [72.8, 72.7], _
    ;     [72.8, 72.7], _
    ;     [70.9, 71.0], _
    ;     [72.8, 72.7], _
    ;     [69.5, 71.1], _
    ;     [68.5, 72.1], _
    ;     [67.5, 73.7] _
    ; ]


    local $points[][] = [[47.0, 81.0]]


    ; _MouseCenterPosition()
    MouseMove(640, 640, 1)
    ; _GatherHerb()
    _FPV()
    _CorrectPitch($standartPitch)

    For $i = 0 to UBound($points, 1) - 1
        local $point[] = [$points[$i][0], $points[$i][1]]
        ConsoleWrite('point [' &  $point[0] & '; ' &  $point[1] & ']'  & @lf)
        _MoveTo($point)
        _GatherHerb()
    Next

    ; _MoveTo($point0)
    ; _MoveTo($point1)
    ; _MoveTo($point2)
    ; _WowIsInCombat()
EndFunc
_Main()


Func _MoveTo($b)
    _Log('move ' & $b[0] & ' ' & $b[1])


    while true
        $player = _WowGetCoordinates()
        $azimyth = _WowGetAzimyth()

        $newAzimyth = _PointAzimyth($player, $b)
        $distance = _Distance($player, $b)
        if ($distance <= $distanceError) then
            return
        endif


        $angle = $azimyth - $newAzimyth
        if ($distance > 1)  then
            _RotateMouse(_CalcRotation($angle))
            _Run($distance - 1)
        else
            if (abs($angle) <= 0.5) then
                _CorrectAzimyth($newAzimyth)
            else
                _RotateMouse(_CalcRotation($angle))
            endif

            _Run($distance)
        endif
    wend
EndFunc


Func _CorrectAzimyth($newAzimyth)
    _Log('rotate ' & $newAzimyth)

    $player = _WowGetCoordinates()

    While True
        $azimyth = _WowGetAzimyth()
        $diffRad = $azimyth - $newAzimyth
        if Abs($diffRad) > $azimythError Then
            if _CalcRotation($diffRad) > 0 then
                _RotateMouseMin(1)
            else
                _RotateMouseMin(-1)
            endif
        Else
            exitloop
        EndIf
    WEnd
EndFunc


Func _CorrectPitch($newPitch)
    ; init pitch
    _ChangePitchLittle(1)
    _ChangePitchLittle(-1)


    While True
        $currentPitch = _WowGetPitch()
        $side = $currentPitch - $newPitch

        if (abs($currentPitch - $newPitch) <= $pitchError) Then
            Return
        EndIf

        _ChangePitchLittle($side)
    WEnd
EndFunc


Func _GatherHerb()
    _Log('gather')

    $azimyth = _WowGetAzimyth()

    ; _MouseCenterPosition()

    While $azimyth 
        $currentAzimyth = _WowGetAzimyth()

        if ($currentAzimyth - $azimyth) > $azimythError

        elseif _WowIsItHerb() == False Then
            _RotateMouseMin(20)
        Else
            exitloop
        EndIf
    WEnd

    Send('{SHIFTDOWN}')
    MouseClick("right")
    Sleep(2000)
    Send('{SHIFTUP}')
EndFunc


Func _MouseCenterPosition()
    MouseMove($winsize[0] / 2, $winsize[1] / 2, 1)
EndFunc