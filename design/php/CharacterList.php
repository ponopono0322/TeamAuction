 <?php
        $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
$response["success"] = true;
// 선택한 게임에 해당하는 사용자 계정에 종속이 되어있는 캐릭터의 닉네임과 사이버머니 정보를 가져오기 위한 php이다.
$gameName = $_POST["gameName"]; // 선택했던 게임의 이름정보를 가져온다.
$gameID = $_POST["gameID"];     // 로그인해주었던 해당게임의 ID,PW를 가져온다.
$gamePW = $_POST["gamePW"];

if(!$con)   // MySQL에 접속에러가 일어났을 경우에 알려준다.
{
    echo "MySQL 접속에러:";
    echo mysqli_connect_error();
    exit();
}
    
        mysqli_set_charset($con,"utf8");
        mysqli_query($con,'SET NAMES utf8');
        
        // 동일한 이름의 게임을 찾아가서 해당 테이블에 받아온 게임ID,PW를 이용해서 계정에 종속되어있는 캐릭터의 닉네임과 캐릭터의 사이버머니를 검색한다.
        if($gameName == "MapleStory"){
            $result = mysqli_query($con,"SELECT gameNickname, gameMoney FROM MapleStory WHERE gameID = $gameID AND gamePW = $gamePW");
        }
        if($gameName == "DF"){
            $result = mysqli_query($con,"SELECT gameNickname, gameMoney FROM DF WHERE gameID = $gameID AND gamePW = $gamePW");
        }
         if($gameName == "LostArk"){
             $result = mysqli_query($con,"SELECT gameNickname, gameMoney FROM LostArk WHERE gameID = $gameID AND gamePW = $gamePW");
        }
        
        $data = array();
        if($result){
            
        while($row = mysqli_fetch_array($result)){  // 받아온 캐릭터의 닉네임과 사이버머니 정보를 배열을 통해서 출력시켜준다.
            array_push($data,
                       array('gameNickname'=>$row[0], 'gameMoney'=>$row[1]));
            
        }
            header('Content-Type:application/json;charset=utf8');   // REST 서비스에 대한 JSON 본문으로 POST 요청을 할 때 사용한다.
            $json = json_encode(array("CharacterList"=>$data),  // 전달받은 값을 JSON 형식의 문자열로 변환하여 반환한다.
            JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;
    }
else{   // SQL문 처리중에 에러가 발생하면 알려준다.
    echo "SQL문 처리중 에러 발생:";
    echo mysqli_error($con);
}
        mysqli_close($con);
    ?>