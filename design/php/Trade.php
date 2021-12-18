<?php
        $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
$response["success"] = true;

$gameName = $_POST["userName"];
$seller = $_POST["userPhone"];   // 파는 사람
$price = $_POST["userEmail"];//  가격
$Quantity = $_POST["userID"];//수량
$UniNum = $_POST["userPassword"];//UniNum
$times = mktime();
$RegisterTime = date("Y-m-d h:i:s", $times);
$WhetherOnSale = 'T';
$Buyer = "NULL";    // 초기값
if(!$con)
{
    echo "MySQL 접속에러:";
    echo mysqli_connect_error();
    exit();
}
    
    mysqli_set_charset($con,"utf8");
    mysqli_query($con,'SET NAMES utf8');

    if($gameName == "MapleStory"){
        $sellman = "MapleUser".$seller; 
        // 아이템 개수가 0이 되면 트리거로 삭제시켜준다.
        $E = mysqli_prepare($con, "UPDATE $sellman SET ItemQuantity=ItemQuantity-? WHERE UniNum=? LIMIT 1");
        mysqli_stmt_bind_param($E, "ss", $Quantity, $UniNum);
        mysqli_stmt_execute($E);
        
        
        $q = mysqli_prepare($con,"INSERT INTO MapleStoryTrade(RegisterTime, Registrant, UniNum, Price, Quantity, WhetherOnSale, Buyer) VALUES (?,?,?,?,?,?,?)");
        mysqli_stmt_bind_param($q, "sssiiss", $RegisterTime, $seller, $UniNum, $price, $Quantity, $WhetherOnSale, $Buyer);
        mysqli_stmt_execute($q);
        mysqli_close($con);
        
    }
    if($gameName == "DF"){
        $sellman = "DFUser".$seller; 
        // 아이템 개수가 0이 되면 트리거로 삭제시켜준다.
        $E = mysqli_prepare($con, "UPDATE $sellman SET ItemQuantity=ItemQuantity-? WHERE UniNum=? LIMIT 1");
        mysqli_stmt_bind_param($E, "ss", $Quantity, $UniNum);
        mysqli_stmt_execute($E);
        
        
        $q = mysqli_prepare($con,"INSERT INTO DFTrade(RegisterTime, Registrant, UniNum, Price, Quantity, WhetherOnSale, Buyer) VALUES (?,?,?,?,?,?,?)");
        mysqli_stmt_bind_param($q, "sssiiss", $RegisterTime, $seller, $UniNum, $price, $Quantity, $WhetherOnSale, $Buyer);
        mysqli_stmt_execute($q);
        mysqli_close($con);
    }
    if($gameName == "LostArk"){
        $sellman = "LostUser".$seller; 
        // 아이템 개수가 0이 되면 트리거로 삭제시켜준다.
        $E = mysqli_prepare($con, "UPDATE $sellman SET ItemQuantity=ItemQuantity-? WHERE UniNum=? LIMIT 1");
        mysqli_stmt_bind_param($E, "ss", $Quantity, $UniNum);
        mysqli_stmt_execute($E);
        
        
        $q = mysqli_prepare($con,"INSERT INTO LostArkTrade(RegisterTime, Registrant, UniNum, Price, Quantity, WhetherOnSale, Buyer) VALUES (?,?,?,?,?,?,?)");
        mysqli_stmt_bind_param($q, "sssiiss", $RegisterTime, $seller, $UniNum, $price, $Quantity, $WhetherOnSale, $Buyer);
        mysqli_stmt_execute($q);
        mysqli_close($con);
    }
   
echo json_encode($response);

    ?>