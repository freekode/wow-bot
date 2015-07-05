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


Func _PointAzimyth($a, $b)
    Local $bTr = ($b[1] - $a[1]) * -1
    Local $cTr = _Distance($a, $b)
    Local $rad = ACos($bTr / $cTr)

    Local $azimyth

    ;find half
    if ($b[0] > $a[0]) Then
        $azimyth = ($PI * 2) - $rad
    Else
        $azimyth = $rad
    EndIf


    Return $azimyth
EndFunc


Func _Distance($a, $b)
    Return Sqrt(($a[0] - $b[0])^2 + ($a[1] - $b[1])^2)
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
