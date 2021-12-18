<?php
        $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
$response["success"] = true;
                            // 구매를 위한 php이다.
$gameName = $_POST["userID"];   // 게임 이름을 가져온다.
$RegisterNumber = $_POST["userPassword"];    // 안드로이드 스튜디오에서 산 아이템의 RegisterNumber를 가져온다.
$UniNum = $_POST["gameName"];   // UniNum을 가져온다.
$Quan = $_POST["gameID"]; // 수량 Quantity에서 이만큼 차감을 시켜서 업데이트를 해주어야한다. 자바에서 사용자가 입력한 살려는 수량
$Buyer = $_POST["gamePW"];    // 아이템을 산 사람의 닉네임을 업데이트 해준다.
$ItemName = "work gloves";//$_POST["gameNickname"]; // 아이템 이름을 가져온다.
$times = mktime();  // 현재 서버의 시간을 timestamp 값으로 가져옴 물건을 산 시간
$PurchaseHistory = date("Y-m-d h:i:s", $times);
$bu = "F";// 팔렸다는 의미
$Quantity = (int)$Quan; // 문자열을 정수로 바꿔준다.
if(!$con)
{
    echo "MySQL 접속에러:";
    echo mysqli_connect_error();
    exit();
}
    
    mysqli_set_charset($con,"utf8");
    mysqli_query($con,'SET NAMES utf8');

    if($gameName == "MapleStory"){
        $BuyMan = "MapleUser".$Buyer;   // MapleStory 유저가 소유하고 있는 아이템 내역 테이블의 명칭
        
        $q = mysqli_prepare($con,"UPDATE MapleStoryTrade SET Buyer = ?, Quantity = (Quantity - ?) WHERE RegisterNumber = ? AND WhetherOnSale = 'T'");// AND MapleStory.gameMoney >= ($Quantity*MapleStoryTrade.Price) AND MapleStory.gameNickname = $Buyer
        mysqli_stmt_bind_param($q, "sii", $Buyer, $Quantity, $RegisterNumber);
        mysqli_stmt_execute($q);// 사는사람, 수량을 갱신한다.
        
        $A = mysqli_prepare($con,"INSERT INTO MapleStoryPurchase(PurchaseHistory, Buyer, RegisterNumber, Quantity) VALUES (?,?,?,?)");
        mysqli_stmt_bind_param($A, "ssii", $PurchaseHistory, $Buyer, $RegisterNumber, $Quantity);
        mysqli_stmt_execute($A);// MapleStoryPurchase 테이블에 새로운 구매 정보 삽입
        
        $B = mysqli_prepare($con,"UPDATE MapleStoryTrade SET  WhetherOnSale = ? WHERE Quantity = 0");
        mysqli_stmt_bind_param($B, "s", $bu);
        mysqli_stmt_execute($B);// 판매되고 있는 아이템의 수량이 하나도 남아있지 않다면 WhetherOnSale를 T에서 F로 바꿔준다.
        
        $C = mysqli_prepare($con,"UPDATE MapleStory, MapleStoryTrade SET MapleStory.gameMoney = (MapleStory.gameMoney + (MapleStoryTrade.Price * $Quantity)) WHERE MapleStory.gameNickname = MapleStoryTrade.Registrant AND MapleStoryTrade.RegisterNumber = $RegisterNumber");
        mysqli_stmt_execute($C);// 아이템을 판매한 사람에게 그 금액만큼 게임 머니를 더해준다.
        
        $D = mysqli_prepare($con,"UPDATE MapleStory, MapleStoryTrade SET MapleStory.gameMoney = (MapleStory.gameMoney - (MapleStoryTrade.Price * $Quantity)) WHERE MapleStory.gameNickname = MapleStoryTrade.Buyer AND MapleStoryTrade.RegisterNumber = $RegisterNumber");
        mysqli_stmt_execute($D);// 아이템을 구매한 사람에게 그 금액만큼 게임 머니를 차감해준다.
        
       /* 
            // 아이템을 판매한 사람의 아이템 창에서 그 아이템의 수량을 차감시켜주는 것은 Trade.PHP에 존재한다.
        // 아이템을 구매한 사람의 아이템 창에서 구매한 아이템을 추가시켜준다.
        $E = mysqli_prepare($con, "INSERT INTO $BuyMan(UniNum,ItemName,ItemQuantity) VALUES (?,?,?)");
        mysqli_stmt_bind_param($E, "ssi", $UniNum, $ItemName, $Quantity);
        mysqli_stmt_execute($E);
        */
       $E = mysqli_prepare($con, "UPDATE $BuyMan SET ItemQuantity=ItemQuantity+? WHERE UniNum=? LIMIT 1");
        mysqli_stmt_bind_param($E, "is", $Quantity, $UniNum);
        mysqli_stmt_execute($E);
        mysqli_close($con);
    }
    if($gameName == "DF"){
        $BuyMan = "DFUser".$Buyer;  // DF 유저가 소유하고 있는 아이템 내역 테이블의 명칭
        
        $q = mysqli_prepare($con,"UPDATE DFTrade SET Buyer = ?, Quantity = (Quantity - ?) WHERE RegisterNumber = ? AND WhetherOnSale = 'T'");// AND MapleStory.gameMoney >= ($Quantity*MapleStoryTrade.Price) AND MapleStory.gameNickname = $Buyer
        mysqli_stmt_bind_param($q, "sii", $Buyer, $Quantity, $RegisterNumber);
        mysqli_stmt_execute($q);// 사는사람, 수량을 갱신한다.
        
        $A = mysqli_prepare($con,"INSERT INTO DFPurchase(PurchaseHistory, Buyer, RegisterNumber, Quantity) VALUES (?,?,?,?)");
        mysqli_stmt_bind_param($A, "ssii", $PurchaseHistory, $Buyer, $RegisterNumber, $Quantity);
        mysqli_stmt_execute($A);// MapleStoryPurchase 새로운 구매 정보 삽입
        
        $B = mysqli_prepare($con,"UPDATE DFTrade SET  WhetherOnSale = ? WHERE Quantity = 0");
        mysqli_stmt_bind_param($B, "s", $bu);
        mysqli_stmt_execute($B);// 수량이 하나도 남아있지 않다면 WhetherOnSale를 T에서 F로 바꿔준다.
        
        $C = mysqli_prepare($con,"UPDATE DF, DFTrade SET DF.gameMoney = (DF.gameMoney + (DFTrade.Price * $Quantity)) WHERE DF.gameNickname = DFTrade.Registrant AND DFTrade.RegisterNumber = $RegisterNumber");
        mysqli_stmt_execute($C);// 아이템을 판매한 사람에게 그 금액만큼 게임 머니를 더해준다.
        
        $D = mysqli_prepare($con,"UPDATE DF, DFTrade SET DF.gameMoney = (DF.gameMoney - (DFTrade.Price * $Quantity)) WHERE DF.gameNickname = DFTrade.Buyer AND DFTrade.RegisterNumber = $RegisterNumber");
        mysqli_stmt_execute($D);// 아이템을 구매한 사람에게 그 금액만큼 게임 머니를 차감해준다.
        
        
        // 아이템을 판매한 사람의 아이템 창에서 그 아이템의 수량을 차감시켜주는 것은 Trade.PHP에 존재한다.
          // 아이템을 구매한 사람의 아이템 창에서 구매한 아이템을 추가시켜준다.
        $E = mysqli_prepare($con, "UPDATE $BuyMan SET ItemQuantity=ItemQuantity+? WHERE UniNum=? LIMIT 1");
        mysqli_stmt_bind_param($E, "is", $Quantity, $UniNum);
        mysqli_stmt_execute($E);
        mysqli_close($con);
    }
    if($gameName == "LostArk"){
        $BuyMan = "LostUser".$Buyer;    // LostArk 유저가 소유하고 있는 아이템 내역 테이블의 명칭
        
        $q = mysqli_prepare($con,"UPDATE LostArkTrade SET Buyer = ?, Quantity = (Quantity - ?) WHERE RegisterNumber = ? AND WhetherOnSale = 'T'");// AND MapleStory.gameMoney >= ($Quantity*MapleStoryTrade.Price) AND MapleStory.gameNickname = $Buyer
        mysqli_stmt_bind_param($q, "sii", $Buyer, $Quantity, $RegisterNumber);
        mysqli_stmt_execute($q);// 사는사람, 수량을 갱신한다.
        
        $A = mysqli_prepare($con,"INSERT INTO LostArkPurchase(PurchaseHistory, Buyer, RegisterNumber, Quantity) VALUES (?,?,?,?)");
        mysqli_stmt_bind_param($A, "ssii", $PurchaseHistory, $Buyer, $RegisterNumber, $Quantity);
        mysqli_stmt_execute($A);// MapleStoryPurchase 새로운 구매 정보 삽입
        
        $B = mysqli_prepare($con,"UPDATE LostArkTrade SET  WhetherOnSale = ? WHERE Quantity = 0");
        mysqli_stmt_bind_param($B, "s", $bu);
        mysqli_stmt_execute($B);// 수량이 하나도 남아있지 않다면 WhetherOnSale를 T에서 F로 바꿔준다.
        
        $C = mysqli_prepare($con,"UPDATE LostArk, LostArkTrade SET LostArk.gameMoney = (LostArk.gameMoney + (LostArkTrade.Price * $Quantity)) WHERE LostArk.gameNickname = LostArkTrade.Registrant AND LostArkTrade.RegisterNumber = $RegisterNumber");
        mysqli_stmt_execute($C);// 아이템을 판매한 사람에게 그 금액만큼 게임 머니를 더해준다.
        
        $D = mysqli_prepare($con,"UPDATE LostArk, LostArkTrade SET LostArk.gameMoney = (LostArk.gameMoney - (LostArkTrade.Price * $Quantity)) WHERE LostArk.gameNickname = LostArkTrade.Buyer AND LostArkTrade.RegisterNumber = $RegisterNumber");
        mysqli_stmt_execute($D);// 아이템을 구매한 사람에게 그 금액만큼 게임 머니를 차감해준다.
        
        // 아이템을 판매한 사람의 아이템 창에서 그 아이템의 수량을 차감시켜주는 것은 Trade.PHP에 존재한다.
          // 아이템을 구매한 사람의 아이템 창에서 구매한 아이템을 추가시켜준다.
        $E = mysqli_prepare($con, "UPDATE $BuyMan SET ItemQuantity=ItemQuantity+? WHERE UniNum=? LIMIT 1");
        mysqli_stmt_bind_param($E, "is", $Quantity, $UniNum);
        mysqli_stmt_execute($E);
        mysqli_close($con);
    }
    
    $response = array();
    $response["success"] = true;
   
    echo json_encode($response);
    
    ?>