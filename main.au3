#include "libkey.au3"
#include "libwow.au3"
#include "libcharacter.au3"
#include "libetc.au3"


Global $winName = "World of Warcraft"
Global $xAddon = 10
Global $yAddon = 10
;standart pitch should be around -0.25
Global $standartPitch = -0.25

Global $xError = 0.1
Global $yError = 0.1
Global $azimythError = 0.1
Global $pitchError = 0.02


Opt("MouseCoordMode", 2)
Opt("PixelCoordMode", 2)


Func _Main()
    _FindWindow($winName)
    if Number(@error) == 2 then
        MsgBox(16, "Error", "There is no window")
        Exit(1)
    EndIf



    $x = _WowGetX()
    $y = _WowGetY()
    $pitch = _WowGetPitch()
    $azimyth = _WowGetAzimyth()

;~  MsgBox(0, "", "x = " & $x & @CRLF & _
;~                "y = " & $y & @CRLF & _
;~                "pitch =  " & $pitch & @CRLF & _
;~                "azimyth = " & $azimyth)


    $xPoint = 60
    $yPoint = 20



    _MouseCenterPosition()
    _FPV()
    _CorrectPitch($standartPitch)
EndFunc
_Main()

Func _MoveTo($xPoint, $yPoint)
    ;rotate to point
    $xCharacter = _WowGetX()
    $yCharacter = _WowGetY()

    $currentAzimyth = _WowGetAzimyth()
    $newAzimyth = _PointAzimyth($xCharacter, $yCharacter, $xPoint, $yPoint)
    $diffRad = $currentAzimyth - $newAzimyth
    _RotateKeyRight($diffRad)

    ;move closely
    $xCharacter = _WowGetX()
    $yCharacter = _WowGetY()
    $fullDistance = _Distance($xCharacter, $yCharacter, $xPoint, $yPoint)
    if ($fullDistance <= 1) Then

    EndIf

    _Run($fullDistance - 1)
EndFunc

;~ Func _CorrectAngle($x, $y, $xPoint, $yPoint)
;~  While True
;~      Sleep(300)
;~      $azimyth = getWowAzimyth(70, 10)
;~      if Abs($azimyth - $newAzimyth) > $azimythError Then
;~          Send('{d down}')
;~          Sleep(300)
;~          Send('{d up}')
;~      Else
;~          Break
;~      EndIf
;~  WEnd
;~ EndFunc

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
