#include-once
#include <Color.au3>

Global $hwnd, $xAddon, $yAddon


Func _WowGetX()
	if (_CheckVars() == False) Then
		Return Null
	EndIf

	Return _WowCoordinate($xAddon, $yAddon)
EndFunc

Func _WowGetY()
	if (_CheckVars() == False) Then
		Return Null
	EndIf

	Return _WowCoordinate($xAddon + 20, $yAddon)
EndFunc

Func _WowGetPitch()
	if (_CheckVars() == False) Then
		Return Null
	EndIf

	Return _WowPitch($xAddon + 40, $yAddon)
EndFunc

Func _WowGetAzimyth()
	if (_CheckVars() == False) Then
		Return Null
	EndIf

	Return _WowAzimyth($xAddon + 60, $yAddon)
EndFunc



Func _CheckVars()
	Local $status = true
	if $hwnd == '' Then
		$status = False
	EndIf
	if $xAddon == '' Then
		$status = False
	EndIf
	if $yAddon == '' Then
		$status = False
	EndIf


	Return $status
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
