#include <Array.au3>
#include "libkey.au3"
#include "libwow.au3"
#include "libcharacter.au3"
#include "libetc.au3"



Global $winName = "World of Warcraft"
Global $fishButton = '='
Global $addon[] = [10, 10]

; try fish with this pitch
Global $standartPitch = -0.25
Global $pitchError = 0.05

; or just write True
Global $failings = 10


Opt("MouseCoordMode", 2)
Opt("PixelCoordMode", 2)


Func _Main()
    _FindWindow($winName)
    if Number(@error) == 2 then
        MsgBox(16, "Error", "There is no window")
        Exit(1)
    EndIf


    _MouseCenterPosition()
    _FPV()
    _CorrectPitch($standartPitch)
    


    _Fishing()
    if Number(@error) == 1 then
        MsgBox(16, 'World of Warcraft Fishing', 'Oh sorry can not found the bobber')
    endif
EndFunc
_Main()


func _Fishing()
    $switchWakeup = 0
    while true
        MouseMove(0, 0, 1)

        ; every 5 min Wake up Neo
        if (Mod(@MIN, 5) == 0) Then
            if ($switchWakeup == 0) Then
                Send('{SPACE}')
                Sleep(1500)

                $switchFirst = 1
            EndIf
        Else
            $switchWakeup = 0
        EndIf


        ; five tryings
        $found = false
        for $i = 1 to $failings
            Send('{' & $fishButton & '}')
            Sleep(1500)

            $boober = _FindBobber()
            if (@error) then
                _Log('not found :(')
            else
                $found = true
                exitloop
            endif
        next


        if ($found == false) then
            SetError(1)
            return
        endif

        _TrackBoober($boober)
    wend
endfunc

Func _FindBobber()
    ; red
    Local $firstColors[] = [0xA72C0B, 0x6B1F0C, 0xBB9B3D, 0x210B04]
    ; the main boober
    local $secondColors[] = [0x334c48, 0x3f5958, 0x60493b]
    ; high metal stck
    local $thirdColors[] = [0x426581, 0x7d98c3, 0x4f749e, 0x575062]

    Local $searchSquare[] = [400, 110, 880, 500]
    

    $found = false
    For $i = 0 to UBound($firstColors) - 1
        $firstCoorMatch = PixelSearch($searchSquare[0], $searchSquare[1], $searchSquare[2], $searchSquare[3], $firstColors[$i], 10, 1, $hwnd)
        if @error == 0 Then
            _Log('first color ok = ' & hex($firstColors[$i]))
            
            _ArrayAdd($firstCoorMatch, $firstColors[$i])
            $found = true
            ExitLoop
        Else
            _Log('first color error = ' & hex($firstColors[$i]))
        EndIf
    Next

    if ($found == false) then
        SetError(1)
        return
    endif

    ; MouseMove($firstCoorMatch[0], $firstCoorMatch[1])


    Local $bobberSqare[] = [$firstCoorMatch[0] - 40, $firstCoorMatch[1] - 20, $firstCoorMatch[0] + 40, $firstCoorMatch[1] + 30]


    $found = false    
    For $i = 0 to UBound($secondColors) - 1
        $centerBoober = PixelSearch($bobberSqare[0], $bobberSqare[1], $bobberSqare[2], $bobberSqare[3], $secondColors[$i], 4, 1, $hwnd)
        if @error == 0 Then
            _Log('second color ok = ' & hex($secondColors[$i]))
            
            _ArrayAdd($centerBoober, $secondColors[$i])
            $found = true
            exitloop
        Else
            _Log('second color error = ' & hex($secondColors[$i]))
        EndIf
    Next


    if ($found == false) then
        SetError(1)
        return
    endif


    $found = false
    For $i = 0 to UBound($thirdColors) - 1
        $highBoober = PixelSearch($bobberSqare[0], $bobberSqare[1], $bobberSqare[2], $bobberSqare[3], $thirdColors[$i], 9, 1, $hwnd)
        if @error == 0 Then
            _Log('third color ok = ' & hex($thirdColors[$i]))

            _ArrayAdd($highBoober, $thirdColors[$i])
            $found = true
            exitloop
        Else
            _Log('third color error ' & hex($thirdColors[$i]))
        EndIf
    next


    if ($found == false) then
        SetError(1)
        return
    endif

    return $centerBoober
EndFunc

func _TrackBoober($coor)
    ; we receive high part of a bobber and we should track little square
    ; to define is it hit or not. boober not just staying it flowing,
    ; so be carefull
    ; third elem in input array will be color which we should track

    ; 20 sec full time
    $fishingTime = 20
    $endTime = _Timestamp() + $fishingTime
    local $square[] = [$coor[0] - 13, $coor[1] - 13, $coor[0] + 13, $coor[1] + 13]


    _Log('track started')
    While True
        if (_Timestamp() >= $endTime) then
            SetError(2)
            _Log('time is up')
            exitloop
        endif

        PixelSearch($square[0], $square[1], $square[2], $square[3], $coor[2], 5, 1, $hwnd)
        if (@error) then
            _Log('wow')

            Send('{SHIFTDOWN}')
            MouseClick("left", $coor[0], $coor[1], 1, 1)
            Sleep(2000)
            Send('{SHIFTUP}')
            
            exitloop
        endif

        Sleep(10)
    WEnd
endfunc

Func _CorrectPitch($newPitch)
    ; init pitch
    _ChangePitchLittle(1)
    _ChangePitchLittle(-1)


    While True
        $currentPitch = _WowGetPitch()
        ConsoleWrite($currentPitch & @lf)
        $side = $currentPitch - $newPitch

        if (abs($currentPitch - $newPitch) <= $pitchError) Then
            Return
        EndIf

        _ChangePitchLittle($side)
    WEnd
EndFunc
