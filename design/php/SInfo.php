<?php

    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");//localhost라는 IP에 ualsgur98이라는 아이디, A!1470326 비밀번호, ualsgur98이라는 데이터베이스명에 접속을 합니다
    mysqli_query($con,'SET NAMES utf8');  //앞으로 모든 통신을 utf8로 하겠다고 요청합니다
    

$gameName =$_POST["userID"];   //게임의 이름 데이터를 받습니다. 
$NickName =$_POST["userPassword"]; //선택된 게임이름의 캐릭터 닉네임 정보를 받습니다. 


     //GameName 과 같은 게임을 분류합니다. 
    if($gameName == "MapleStory"){  // MapleStory 이라는 게임이라면 
            $q = mysqli_query($con,"SELECT MapleStoryTrade.RegisterNumber, MapleStoryTrade.UniNum,MapleStoryItemInfo.ItemName,MapleStoryTrade.Price, MapleStoryTrade.Quantity FROM MapleStoryTrade, MapleStoryItemInfo WHERE MapleStoryTrade.UniNum = MapleStoryItemInfo.UniNum AND MapleStoryTrade.WhetherOnSale = 'T' AND MapleStoryTrade.Registrant = $NickName");     
            //MapleStory 경매장 아이템 등록번호, 아이템 고유번호, 아이템 이름, 경매장의 올라와있는 아이템 가격, 그 아이템 수량, 그 아이템 정보들을 경매장에 올라와져있는 아이템 고유번호랑 아이템 고유번호랑 같고 경매장에서 판매중인 상태이고 아이템 경매장에 올린 사람이랑 이름이 같으면 출력한다.  
        }

        if($gameName == "DF"){ //DF 이라는 게임이라면 
        
            $q = mysqli_query($con,"SELECT DFTrade.RegisterNumber, 
            DFTrade.UniNum,DFItemInfo.ItemName,DFTrade.Price, DFTrade.Quantity FROM DFTrade, DFItemInfo WHERE DFTrade.Registrant = $NickName AND DFTrade.UniNum = DFItemInfo.UniNum AND DFTrade.WhetherOnSale = 'T'");  
            //DF 경매장 아이템 등록번호, 아이템 고유번호, 아이템 이름, 경매장의 올라와있는 아이템 가격, 그 아이템 수량, 그 아이템 정보들을 경매장에 올라와져있는 아이템 고유번호랑 아이템 고유번호랑 같고 경매장에서 판매중인 상태이고 아이템 경매장에 올린 사람이랑 이름이 같으면 출력한다.
        }
            
         if($gameName == "LostArk"){ //LostArk 라는 게임이라면 
            $q = mysqli_query($con,"SELECT LostArkTrade.RegisterNumber, 
            LostArkTrade.UniNum,LostArkItemInfo.ItemName,LostArkTrade.Price, LostArkTrade.Quantity FROM LostArkTrade, LostArkItemInfo WHERE LostArkTrade.Registrant = $NickName AND LostArkTrade.UniNum = LostArkItemInfo.UniNum AND LostArkTrade.WhetherOnSale = 'T'");  
            //LostArk 경매장 아이템 등록번호, 아이템 고유번호, 아이템 이름, 경매장의 올라와있는 아이템 가격, 그 아이템 수량, 그 아이템 정보들을 경매장에 올라와져있는 아이템 고유번호랑 아이템 고유번호랑 같고 경매장에서 판매중인 상태이고 아이템 경매장에 올린 사람이랑 이름이 같으면 출력한다.
        }
        
        $data = array(); //data 배열을 만든다. 
        if($q){// 위에 sql 명령문이 실행될때 
        while($row = mysqli_fetch_array($q)){ //명령문 결과값의 열의 갯수 만큼 돌린다.
            array_push($data,
                       array('RegisterNumber'=>$row[0],   //등록번호
                             'UniNum'=>$row[1],     //아이템 고유번호   
                             'ItemName'=>$row[2],      //아이템 이름
                            'Price'=>$row[3],   //아이템 가격
                            'Quantity'=>$row[4] //아이템 수량
                            ));
            
        }
            header('Content-Type:application/json;charset=utf8');
            $json = json_encode(array("Selling"=>$data),
            JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;     //안드로이드에 넘겨주기위해 배열을 JSON 형식으로 변환합니다.
    }
else{   //SQL문 처리중 에러가 발생하면 출력한다. 
    echo "SQL문 처리중 에러 발생:";
    echo mysqli_error($con);
}

        mysqli_close($con);

?>