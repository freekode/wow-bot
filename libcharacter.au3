#include-once

Global $PI = 3.1415926535897932384626433832795028
Global $interfaceLagMs = 300
Global $running = false

; 357ms need to run 0.1 distance on a floor
Global $msOnePointDist = 357

;3ms need to rotate to 0.01 rad
Global $msOnePointAzimyth = 3

;5px need to change pitch to 0.04
Global $msOnePointAzimyth = 5




Func _RotateKey($rad)
    Local $rotation
    if ($rad < 0) Then
        if (Abs($rad) > $PI) Then
            $rad = ($PI * 2) - Abs($rad)
            $rotation = 'right'
        Else
            $rad = Abs($rad)
            $rotation = 'left'
        EndIf
    Else
        if (Abs($rad) > $PI) Then
            $rad = ($PI * 2) - Abs($rad)
            $rotation = 'left'
        Else
            $rad = Abs($rad)
            $rotation = 'right'
        EndIf
    EndIf

    if ($rotation == 'right') Then
        _RotateKeyRight($rad)
    ElseIf ($rotation == 'left') Then
        _RotateKeyLeft($rad)
    EndIf
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
