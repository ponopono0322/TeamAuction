<?php

    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98"); //localhost라는 IP에 ualsgur98이라는 아이디, A!1470326 비밀번호, ualsgur98이라는 데이터베이스명에 접속을 합니다. 
    mysqli_query($con,'SET NAMES utf8'); //앞으로 모든 통신을 utf8로 하겠다고 요청합니다.
    

$gameName =$_POST["userID"]; //게임을 구별해주는 역할


     //GameName 과 같은 게임을 분류하고 보여준다.
    if($gameName == "MapleStory"){  //MapleStory이라는 게임이면
        
            $result = mysqli_query($con,"SELECT MapleStoryTrade.RegisterNumber, MapleStoryItemInfo.UniNum,MapleStoryTrade.Registrant,MapleStoryItemInfo.ItemName,MapleStoryTrade.Price,MapleStoryTrade.Quantity FROM MapleStoryTrade, MapleStoryItemInfo WHERE MapleStoryTrade.UniNum = MapleStoryItemInfo.UniNum AND MapleStoryTrade.WhetherOnSale = 'T'");
        }//게임MapleStory 경매장 등록번호, 아이템 고유번호, 아이템 등록자, 아이템 정보, 아이템 이름, 아이템 가격, 아이템 수량을 2개의 테이블에서 가져오고 그테이블 에서 서로 고유값 같은경우, 경매장에서 팔고있는 상태있는것을 나타나게 한다. 

        if($gameName == "DF"){  //DF라는 게임이면
             $result = mysqli_query($con,"SELECT DFTrade.RegisterNumber,DFItemInfo.UniNum,DFTrade.Registrant,DFItemInfo.ItemName,DFTrade.Price,DFTrade.Quantity FROM DFTrade, DFItemInfo WHERE DFTrade.UniNum = DFItemInfo.UniNum AND DFTrade.WhetherOnSale = 'T'");
        }//게임DF 경매장 등록번호, 아이템 고유번호, 아이템 등록자, 아이템 정보, 아이템 이름, 아이템 가격, 아이템 수량을 2개의 테이블에서 가져오고 그테이블 에서 서로 고유값 같은경우, 경매장에서 팔고있는 상태있는것을 나타나게 한다.
            
         if($gameName == "LostArk"){ //LostArk라는 게임이면
             $result = mysqli_query($con,"SELECT LostArkTrade.RegisterNumber,lostArkItemInfo.UniNum,LostArkTrade.Registrant,LostArkItemInfo.ItemName,LostArkTrade.Price,LostArkTrade.Quantity FROM LostArkTrade, LostArkItemInfo WHERE LostArkTrade.UniNum = LostArkItemInfo.UniNum AND LostArk.WhetherOnSale='T'");
        }//게임LostArk 경매장 등록번호, 아이템 고유번호, 아이템 등록자, 아이템 정보, 아이템 이름, 아이템 가격, 아이템 수량을 2개의 테이블에서 가져오고 그테이블 에서 서로 고유값 같은경우, 경매장에서 팔고있는 상태있는것을 나타나게 한다.
        
        $data = array();  //data 배열을 만든다. 
        if($result){   // 위에 sql 명령문이 실행될때 
        while($row = mysqli_fetch_array($result)){   //명령문 결과값의 열의 갯수 만큼 돌린다.  
            array_push($data,
                       array('RegisterNumber'=>$row[0],  // 등록번호
                             'UniNum'=>$row[1],  //아이템 고유번호
                            'Registrant'=>$row[2],  //아이템 등록사람
                             'ItemName'=>$row[3], //아이템 이름
                            'Price'=>$row[4],  //아이템 가격
                            'Quantity'=>$row[5] //아이템 수량
                            ));
            
        }
            header('Content-Type:application/json;charset=utf8');
            $json = json_encode(array("GameAuction"=>$data),
            JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;    //안드로이드에 넘겨주기위해 배열을 JSON 형식으로 변환합니다. 
    }
else{  //SQL처리 하는데 에러가 발생하면 발동합니다. 
    echo "SQL문 처리중 에러 발생:";  
    echo mysqli_error($con);
}
        mysqli_close($con);

?>