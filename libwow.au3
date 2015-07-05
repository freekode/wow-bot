#include-once
#include <Color.au3>

Global $hwnd, $addon


Func _WowGetCoordinates()
    _CheckVars()
    if @error then
        SetError(Number(@error))
        return
    endif


    local $coor[2]
    $coor[0] = _WowCoordinate($addon[0], $addon[1])
    $coor[1] = _WowCoordinate($addon[0] + 20, $addon[1])

    return $coor
EndFunc

Func _WowGetPitch()
    _CheckVars()
    if @error then
        SetError(Number(@error))
        return
    endif


    Return _WowPitch($addon[0] + 40, $addon[1])
EndFunc

Func _WowGetAzimyth()
    _CheckVars()
    if @error then
        SetError(Number(@error))
        return
    endif


    Return _WowAzimyth($addon[0] + 60, $addon[1])
EndFunc



Func _CheckVars()
    if $hwnd == '' Then
        SetError(2)
        return
    EndIf
    if UBound($addon) < 2 Then
        SetError(3)
        return
    EndIf
EndFunc

Func _WowCoordinate($x, $y)
    Local $color, $part0, $part1, $part2, $coordinate
    $color = _ColorGetRGB(PixelGetColor($x, $y, $hwnd))

    $part0 = StringReplace(StringFormat("%.2f", Round($color[0] / 255, 2)), "0.", "")
    $part1 = StringReplace(StringFormat("%.2f", Round($color[1] / 255, 2)), "0.", "")
    $part2 = StringReplace(StringFormat("%.2f", Round($color[2] / 255, 2)), "0.", "")
    $coordinate = Number($part0 & $part1 & $part2) / 10000

   Return $coordinate
EndFunc

Func _WowPitch($x, $y)
    Local $color, $part0, $part1, $part2, $pitch
    $color = _ColorGetRGB(PixelGetColor($x, $y, $hwnd))

    $part0 = Number(StringFormat("%.2f", Round($color[0] / 255, 2)))
    $part1 = StringReplace(StringFormat("%.2f", Round($color[1] / 255, 2)), "0.", "")
    $part2 = StringReplace(StringFormat("%.2f", Round($color[2] / 255, 2)), "0.", "")

    $pitch = Number($part1 & $part2) / 1000

    if $part0 > 0 Then
        $pitch = $pitch * -1
    EndIf

    Return $pitch
EndFunc

Func _WowAzimyth($x, $y)
    Local $color, $part0, $part1, $part2, $pitch
    $color = _ColorGetRGB(PixelGetColor($x, $y, $hwnd))

    $part0 = StringReplace(StringFormat("%.2f", Round($color[0] / 255, 2)), "0.", "")
    $part1 = StringReplace(StringFormat("%.2f", Round($color[1] / 255, 2)), "0.", "")
    $part2 = StringReplace(StringFormat("%.2f", Round($color[2] / 255, 2)), "0.", "")
    $azimyth = Number($part0 & $part1 & $part2) / 100000

    Return $azimyth
EndFunc
