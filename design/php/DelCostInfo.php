<?php

$con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");//localhost라는 IP에 ualsgur98이라는 아이디, A!1470326 비밀번호, ualsgur98이라는 데이터베이스명에 접속을 합니다
mysqli_query($con,'SET NAMES utf8');//앞으로 모든 통신을 utf8로 하겠다고 요청합니다.

    

$gameName =$_POST["userID"]; //게임을 구별하는 역할
$RegNum = $_POST["userPassword"]; //경매장 아이템 등록번호


     //GameName 과 같은 게임을 분류하고 보여준다.
    if($gameName == "MapleStory"){  //MapleStory라는 게임이라면?
        
            $result = mysqli_query($con,"SELECT Price,Quantity  FROM MapleStoryTrade WHERE MapleStoryTrade.RegisterNumber =$RegNum AND MapleStoryTrade.WhetherOnSale = 'T'");
        }//MapleStory 경매장 등록번호, 아이템 이름, 가격, 수량 을 고유값이 같고 판매여부 T인경우만 출력

        if($gameName == "DF"){
             $result = mysqli_query($con,"SELECT Price,Quantity  FROM DFTrade WHERE DFTrade.RegisterNumber =$RegNum AND DFTrade.WhetherOnSale = 'T'");
        }//DF 경매장 등록번호, 아이템 이름, 가격, 수량 을 고유값이 같고 판매여부 T인경우만 출력
            
         if($gameName == "LostArk"){
             $result = mysqli_query($con,"SELECT Price,Quantity  FROM LostArkTrade WHERE LostArkTrade.RegisterNumber =$RegNum AND LostArkTrade.WhetherOnSale = 'T'");
        }//LostArk 경매장 등록번호, 아이템 이름, 가격, 수량 을 고유값이 같고 판매여부 T인경우만 출력
        
        $data = array(); //data 배열을 만듭니다. 
        while($row = mysqli_fetch_array($result)){ //SQL문으로 출력한 만큼 1개씩 리턴해줍니다
            $data["success"]=true; //통신이 완료되었습니다.
            $data["Price"]=$row[0];  //판매중인 가격
            $data["Quantity"]=$row[1];  //판매중인 아이템 수량
        }
          echo json_encode($data); //구매할 아이템을 출력합니다.
    
        mysqli_close($con);//연결은 끝어버립니다.

?>