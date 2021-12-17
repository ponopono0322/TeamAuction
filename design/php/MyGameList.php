<?php
    //app에 로그인하면 게임이름과 게임캐릭터이름이 나와있는 리스트가 나오고 그것을 클릭하면 로그인하는 기능에 씀
    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
    mysqli_query($con,'SET NAMES utf8');
    

$userID = $_POST["userID"]; // 앱 유저의 ID,PW를 가져온다.
$userPassword = $_POST["userPassword"];

// usergame테이블에서 appID와 appPW가 같은 곳을 찾아내고 gameName과 gameNickname의 정보를 리스트로 보여주기 위해서 SELECT한다.
    $result = mysqli_query($con,"SELECT gameName, gameNickname FROM usergame WHERE userID = $userID  AND userPassword = $userPassword");
    
        $data = array();
        if($result){
            
        while($row = mysqli_fetch_array($result)){  // 앱 유저가 게임에 로그인하고 캐릭터를 선택한 적이 있는 게임이름과 게임 닉네임을 보여준다.
            array_push($data,
                       array('gameName'=>$row[0], 'gameNickname'=>$row[1]));
            
        }
            header('Content-Type:application/json;charset=utf8');// REST 서비스에 대한 JSON 본문으로 POST 요청을 할 때
            $json = json_encode(array("MyGameList"=>$data), // Array 또는 String 따위를 JSON 문자열로 변환하는 PHP 함수
            JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;
    }
else{   // SQL문 처리중에 에러가 발생하면 알려준다.
    echo "SQL문 처리중 에러 발생:";
    echo mysqli_error($con);
}
        mysqli_close($con);

?>