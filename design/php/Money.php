<?php
    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
    //localhost라는 IP에 ualsgur98이라는 아이디, A!1470326 비밀번호, ualsgur98이라는 데이터베이스명에 접속을 합니다
    mysqli_query($con,'SET NAMES utf8');
    //앞으로 모든 통신을 utf8로 하겠다고 요청합니다
    
$gameName =$_POST["userID"]; //game이름 
$NickName =$_POST["userPassword"];  //유저 닉네임 


     //GameName 과 같은 게임을 분류하고 보여준다.
    if($gameName == "MapleStory"){  //MapleStory라는 게임이라면 
            $q = mysqli_query($con,"SELECT gameMoney FROM MapleStory WHERE MapleStory.gameNickname =$NickName");        
        }       //MapleStory 라는 테이블에서 Maplestroy gameNickname을 접속된 유저가 가지고있는 유저 닉네임이랑 같으면 그 유저 캐릭터가 가지고있는 돈을 출력한다.

        if($gameName == "DF"){  //DF라는 게임이라면 
            $q = mysqli_query($con,"SELECT gameMoney FROM DF WHERE DF.gameNickname =$NickName"); 
        }   //DF 라는 테이블에서 DF gameNickname을 접속된 유저가 가지고있는 유저 닉네임이랑 같으면 그 유저 캐릭터가 가지고있는 돈을 출력한다.
            
         if($gameName == "LostArk"){  //LostArk라는 게임이라면
           $q = mysqli_query($con,"SELECT gameMoney FROM LostArk WHERE LostArk.gameNickname =$NickName"); 
        }   //LostArk 라는 테이블에서 LostArk gameNickname을 접속된 유저가 가지고있는 유저 닉네임이랑 같으면 그 유저 캐릭터가 가지고있는 돈을 출력한다.
        
        $data = array();  //data 배열을 만든다.
        if($q){// 위에 sql 명령문이 실행될때
        while($row = mysqli_fetch_array($q)){ //명령문 결과값의 열의 갯수 만큼 돌린다
            array_push($data,
                       array('gameMoney'=>$row[0], //게임 돈
                            ));
            
        }
            header('Content-Type:application/json;charset=utf8');
            $json = json_encode(array("Money"=>$data),
            JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;   //안드로이드에 넘겨주기위해 배열을 JSON 형식으로 변환합니다.
    }
else{  //SQL처리 하는데 에러가 발생하면 발동합니다.
    echo "SQL문 처리중 에러 발생:";
    echo mysqli_error($con);
}
        mysqli_close($con);

?>