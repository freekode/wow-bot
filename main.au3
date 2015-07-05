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


    local $point0[] = [68.2, 72.6]
    local $point1[] = [69.4, 71.2]
    local $point2[] = [71.0, 71.1]

    _MouseCenterPosition()
    ; _FPV()
    _CorrectPitch($standartPitch)

    _MoveTo($point0)
    _MoveTo($point1)
    _MoveTo($point2)

EndFunc
_Main()

Func _MoveTo($b)
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

Func _FindAzimyht()
EndFunc

Func _CorrectAzimyth($newAzimyth)
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
