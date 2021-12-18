<?php
     $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");//localhost라는 IP에 ualsgur98이라는 아이디, A!1470326 비밀번호, ualsgur98이라는 데이터베이스명에 접속을 합니다
     mysqli_query($con,'SET NAMES utf8');//앞으로 모든 통신을 utf8로 하겠다고 요청합니다.
    
$gameName =$_POST["userID"]; //게임 구별하는 역활
$NickName =$_POST["userPassword"];  //유저 닉네임 구별하는 역할


     //GameName 과 같은 게임을 분류하고 보여준다.
    if($gameName == "MapleStory"){  //MapleStory 게임이라면
            $r1 ="MapleUser";
            $r = $r1.$NickName; 
            $q = mysqli_query($con,"SELECT * FROM $r");      //유저 1 정보 출력    
        }

        if($gameName == "DF"){//DF게임이라면
            $r1 ="DFUser";
            $r = $r1.$NickName;
            $q = mysqli_query($con,"SELECT * FROM $r");    //유저 1 정보 출력  
        }
            
         if($gameName == "LostArk"){//LostArk게임이라면
            $r1 ="LostUser";
            $r = $r1.$NickName;
            $q = mysqli_query($con,"SELECT * FROM $r");//유저 1 정보 출력
           
        }
        
        $data = array(); //data 배열이라면
        if($q){
        while($row = mysqli_fetch_array($q)){   //SQL문으로 출력한 만큼 1개씩 리턴해줍니다
            array_push($data,
                       array(
                            'UniNum'=>$row[0],   //고유번호
                             'ItemName'=>$row[1],  //아이템이름 
                            'ItemQuantity'=>$row[2],  //아이템 개수
                            ));
            
        }
            header('Content-Type:application/json;charset=utf8');
            $json = json_encode(array("UserInfo"=>$data),
            JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json; //안드로이드에 넘겨주기위해 배열을 JSON 형식으로 변환합니다.
    }
else{   //SQL처리 하는데 에러가 발생하면 발동합니다. 
    echo "SQL문 처리중 에러 발생:";
    echo mysqli_error($con);
}
        mysqli_close($con);

?>