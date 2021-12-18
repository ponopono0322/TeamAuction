<?php
        $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
$response["success"] = true;

$RegisterNumber = $_POST["userName"];//RegisterNumber를 가져옴
$gameName = $_POST["userPhone"];
$gameNickname = $_POST["userEmail"];
$Price = $_POST["userID"];    // 수정할 가격
$Quantity = $_POST["userPassword"]; // 수정할 수량
$times = mktime();
$RegisterTime = date("Y-m-d h:i:s", $times);
$WhetherOnSale = 'T';
$Buyer = "NULL";

if(!$con) {
  echo "MySQL 접속에러:";
  echo mysqli_connect_error();
  exit();
}
    
mysqli_set_charset($con,"utf8");
mysqli_query($con,'SET NAMES utf8');

if($gameName == "MapleStory"){

  $q = "SELECT UniNum,Quantity FROM MapleStoryTrade Where RegisterNumber = $RegisterNumber";
  $result = mysqli_query($con,$q);
  $row = mysqli_fetch_array($result);

  $us = "MapleUser".$gameNickname;
  $q = mysqli_prepare($con, "UPDATE $us SET ItemQuantity = ItemQuantity+(?-$Quantity) WHERE UniNum = ?");
  mysqli_stmt_bind_param($q, "is", $row[1], $row[0]);
  mysqli_stmt_execute($q);
    
    $E = mysqli_query($con, "UPDATE MapleStoryTrade SET Price = $Price, Quantity = $Quantity WHERE RegisterNumber = $RegisterNumber");
    
  $q = mysqli_prepare($con,"INSERT INTO MapleStoryTrade(RegisterTime, Registrant, UniNum, Price, Quantity, WhetherOnSale, Buyer) VALUES (?,?,?,?,?,?,?)");
  mysqli_stmt_bind_param($q, "sssiiss", $RegisterTime, $us, $row[0], $Price, $Quantity, $WhetherOnSale, $Buyer);
  mysqli_stmt_execute($q);
  mysqli_close($con);
}
if($gameName == "DF"){
  $E = mysqli_query($con, "UPDATE DFTrade SET Price = $Price, Quantity = $Quantity WHERE RegisterNumber = $RegisterNumber");

  $q = "SELECT UniNum,Quantity FROM DFTrade Where RegisterNumber = $RegisterNumber";
  $result = mysqli_query($con,$q);
  $row = mysqli_fetch_array($result);

  $us = "DFUser".$gameNickname;
  $q = mysqli_prepare($con, "UPDATE $us SET ItemQuantity = ItemQuantity+(?-$Quantity) WHERE UniNum = ?");
  mysqli_stmt_bind_param($q, "is", $row[1], $row[0]);
  mysqli_stmt_execute($q);

  $q = mysqli_prepare($con,"INSERT INTO DFTrade(RegisterTime, Registrant, UniNum, Price, Quantity, WhetherOnSale, Buyer) VALUES (?,?,?,?,?,?,?)");
  mysqli_stmt_bind_param($q, "sssiiss", $RegisterTime, $us, $row[0], $Price, $Quantity, $WhetherOnSale, $Buyer);
  mysqli_stmt_execute($q);
  mysqli_close($con);
}
if($gameName == "LostArk"){
  $E = mysqli_query($con, "UPDATE LostArkTrade SET Price = $Price, Quantity = $Quantity WHERE RegisterNumber = $RegisterNumber");

  $q = "SELECT UniNum,Quantity FROM LostArkTrade Where RegisterNumber = $RegisterNumber";
  $result = mysqli_query($con,$q);
  $row = mysqli_fetch_array($result);

  $us = "LostUser".$gameNickname;
  $q = mysqli_prepare($con, "UPDATE $us SET ItemQuantity = ItemQuantity+(?-$Quantity) WHERE UniNum = ?");
  mysqli_stmt_bind_param($q, "is", $row[1], $row[0]);
  mysqli_stmt_execute($q);

  $q = mysqli_prepare($con,"INSERT INTO LostArkTrade(RegisterTime, Registrant, UniNum, Price, Quantity, WhetherOnSale, Buyer) VALUES (?,?,?,?,?,?,?)");
  mysqli_stmt_bind_param($q, "sssiiss", $RegisterTime, $us, $row[0], $Price, $Quantity, $WhetherOnSale, $Buyer);
  mysqli_stmt_execute($q);
  mysqli_close($con);
}
echo json_encode($response);

    ?>