#include-once

Global $PI = 3.1415926535897932384626433832795028
Global $interfaceLagMs = 300
Global $running = false

; 357ms need to run 0.1 distance on a floor
Global $msOnePointDist = 357

;3ms need to rotate to 0.01 rad
Global $msOnePointAzimyth = 3

;5px change 0.02 rad
Global $pxForAzimyth = 5




Func _Rotate($rad)
    _RotateMouse(_CalcRotation($rad))
EndFunc

Func _CalcRotation($rad)
    ConsoleWrite('rad ' & $rad & @lf)
    if ($rad < 0) Then
        if (Abs($rad) > $PI) Then
            $rad = ($PI * 2) - Abs($rad)
        EndIf
    Else
        if ($rad > $PI) Then
            $rad = (($PI * 2) - $rad) * -1
        EndIf
    EndIf


    return $rad
EndFunc

Func _RotateKeyRight($rad)
    Local $ms = int($rad / 0.01 * $msOnePointAzimyth)

    Send('{d down}')
    Sleep($ms)
    Send('{d up}')
    Sleep($interfaceLagMs)
EndFunc

Func _RotateKeyLeft($rad)
    Local $ms = int($rad / 0.01 * $msOnePointAzimyth)

    Send('{a down}')
    Sleep($ms)
    Send('{a up}')
    Sleep($interfaceLagMs)
EndFunc

Func _RotateMouse($rad)
    Local $times = $rad / 0.02

    _RotateMouseMin($times)
EndFunc

Func _RotateMouseMin($times)
    $px = int($pxForAzimyth * $times)

    MouseDown("right")
    Sleep(100)
    MouseMove(MouseGetPos(0) + $px, MouseGetPos(1), 1)
    MouseUp("right")
    Sleep($interfaceLagMs + 100)
EndFunc

Func _Run($distance)
    Local $ms = int($distance / 0.1 * $msOnePointDist)

    Send('{w down}')
    Sleep($ms)
    Send('{w up}')
    $running = False
    Sleep($interfaceLagMs)
EndFunc

Func _RunForever()
    if ($running) Then
        $running = False
    Else
        $running = True
    EndIf

    send('{NUMLOCK toggle}')
EndFunc

Func _ChangePitchLittle($side)
    $px = 5
    if ($side < 0) Then
        $px = $px * -1
    EndIf

    MouseDown("right")
    Sleep(100)
    MouseMove(MouseGetPos(0), MouseGetPos(1) + $px)
    MouseUp("right")
    Sleep($interfaceLagMs)
EndFunc

Func _FPV()
    MouseWheel("up", 30)
    Sleep(1000)
EndFunc

Func _MouseCenterPosition()
    MouseMove(640, 360, 1)
EndFunc
