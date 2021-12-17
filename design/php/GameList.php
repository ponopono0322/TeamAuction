<?php
        $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");//localhost라는 IP에 ualsgur98이라는 아이디, A!1470326 비밀번호, ualsgur98이라는 데이터베이스명에 접속을 합니다. 
$response["success"] = true;
if(!$con) //MySQL의 접속이 실패하면 나옵니다. 
{
    echo "MySQL 접속에러:";
    echo mysqli_connect_error();
    exit();
}
    
        mysqli_set_charset($con,"utf8"); 
        mysqli_query($con,'SET NAMES utf8');//앞으로 모든 통신을 utf8로 하겠다고 요청합니다.
        $q = "SELECT * FROM GameList";  //GameList테이블을 출력합니다. 
        $result = mysqli_query($con,$q);  
        
        
        $data = array();
        if($result){ //
        
           
        while($row = mysqli_fetch_array($result)){
            array_push($data,
                       array('GameNum'=>$row[0],  // 게임들의 고유번호를 받아온다.
                            'GameName'=>$row[1])); //우리가 연동할수있는 게임이름들을 받아온다.    
            
        }
            
            header('Content-Type:application/json;charset=utf8');
            $json = json_encode(array("GameList"=>$data),
            JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json; //안드로이드 스튜디오에 넘겨주기 위해 배열을 JSON 형식으로 변환
    }
else{  //sql 문 처리중에 에러가 발생하면 나옵니다. 
    echo "SQL문 처리중 에러 발생:";
    echo mysqli_error($con);
}
        mysqli_close($con);
    ?>
    
