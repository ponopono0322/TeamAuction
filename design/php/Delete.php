<?php
    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
    mysqli_query($con,'SET NAMES utf8');
    $response["success"] = true;
    $RegisterNumber = $_POST["gameName"];//RegisterNumber를 가져옴
    $gameName = $_POST["gameID"];  // 게임 이름을 받아옴
    $gameNickname = $_POST["gamePW"]; // 게임 닉네임을 받아옴
    $WhetherOnsale = "F";
    
if($gameName == "MapleStory"){
    $A = mysqli_prepare($con, "UPDATE MapleStoryTrade SET WhetherOnsale = ? WHERE RegisterNumber = ?");
    mysqli_stmt_bind_param($A, "ss", $WhetherOnsale, $RegisterNumber);
    mysqli_stmt_execute($A);

     $q = "SELECT UniNum,Quantity FROM MapleStoryTrade Where RegisterNumber = $RegisterNumber";
        $result = mysqli_query($con,$q);
        $row = mysqli_fetch_array($result);
        //echo $row[0], $row[1];
        $us = "MapleUser".$gameNickname;
         $q = mysqli_prepare($con, "UPDATE $us SET ItemQuantity = ItemQuantity+? WHERE UniNum = ?");
        mysqli_stmt_bind_param($q, "is", $row[1], $row[0]);
        mysqli_stmt_execute($q);
}
if($gameName == "DF"){
    $A = mysqli_prepare($con, "UPDATE DFTrade SET WhetherOnsale = ? WHERE RegisterNumber = ?");
    mysqli_stmt_bind_param($A, "ss", $WhetherOnsale, $RegisterNumber);
    mysqli_stmt_execute($A);
    $us = "DFUser".$gameNickname;
     $q = "SELECT UniNum,Quantity FROM DFTrade Where RegisterNumber = $RegisterNumber";
        $result = mysqli_query($con,$q);
        $row = mysqli_fetch_array($result);
        //echo $row[0], $row[1];
        
         $q = mysqli_prepare($con, "UPDATE $us SET ItemQuantity = ItemQuantity+? WHERE UniNum = ?");
        mysqli_stmt_bind_param($q, "is", $row[1], $row[0]);
        mysqli_stmt_execute($q);
}
if($gameName == "LostArk"){
    $A = mysqli_prepare($con, "UPDATE LostArkTrade SET WhetherOnsale = ? WHERE RegisterNumber = ?");
    mysqli_stmt_bind_param($A, "ss", $WhetherOnsale, $RegisterNumber);
    mysqli_stmt_execute($A);
    $us = "LostUser".$gameNickname;
     $q = "SELECT UniNum,Quantity FROM LostArkTrade Where RegisterNumber = $RegisterNumber";
        $result = mysqli_query($con,$q);
        $row = mysqli_fetch_array($result);
        //echo $row[0], $row[1];
        
         $q = mysqli_prepare($con, "UPDATE $us SET ItemQuantity = ItemQuantity+? WHERE UniNum = ?");
        mysqli_stmt_bind_param($q, "is", $row[1], $row[0]);
        mysqli_stmt_execute($q);
}

    echo json_encode($response);
?>