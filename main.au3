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
Global $azimythError = 0.008
Global $pitchError = 0.02


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


    local $point[] = [40.9, 53.7]


    _MouseCenterPosition()
    ; _FPV()
    ; _CorrectPitch($standartPitch)
    _MoveTo($point)

EndFunc
_Main()

Func _MoveTo($b)
    ;rotate to point
    $player = _WowGetCoordinates()

    $currentAzimyth = _WowGetAzimyth()
    $newAzimyth = _PointAzimyth($player, $b)
    $diffRad = $currentAzimyth - $newAzimyth
    
    ; _Rotate($diffRad)
    ; MsgBox(0, '', $newAzimyth & @lf & _WowGetAzimyth())

    _CorrectAzimyth($b)

    ;move closely
    ; $fullDistance = _Distance($player, $b)
    ; if ($fullDistance <= 1) Then
    ;     _CorrectAngle($player, $b)
    ; EndIf

    ; _Run($fullDistance - 1)
    ; _CorrectAngle($player, $b)
EndFunc

Func _CorrectAzimyth($b)
    $player = _WowGetCoordinates()
    $newAzimyth = _PointAzimyth($player, $b)

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
