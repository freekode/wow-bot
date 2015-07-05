#include-once

Func _FindWindow($title)
    if WinExists($title) == 0 Then
        SetError(2)
        return
    EndIf
    Global $hwnd = WinGetHandle($title)
    Global $winsize = WinGetClientSize($hwnd)
    WinActivate($title)
EndFunc


Func _PointAzimyth($x, $y, $xPoint, $yPoint)
    Local $b = ($yPoint - $y) * -1
    Local $c = _Distance($x, $y, $xPoint, $yPoint)
    Local $rad = ACos($b / $c)

    Local $azimyth

    ;find half
    if ($xPoint > $x) Then
        $azimyth = ($PI * 2) - $rad
    Else
        $azimyth = $rad
    EndIf


    Return $azimyth
EndFunc


Func _Distance($x, $y, $xPoint, $yPoint)
    Return Sqrt(($x - $xPoint)^2 + ($y - $yPoint)^2)
EndFunc


Func _Log($message)
    $time = _Date_Time_GetSystemTime()
    ConsoleWrite(_Date_Time_SystemTimeToDateTimeStr($time) & ' ' & $message & @LF)
EndFunc


Func _Timestamp()
    $EPOCH = "1970/01/01 00:00:00"
    $NOW = _NowCalc()
    Return _DateDiff('s', $EPOCH, $NOW)
EndFunc
