<?php
    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
    mysqli_query($con,'SET NAMES utf8');
    // 선택한 게임에 로그인을 하기 위해서 만들어진 php이다.
    $gameName = $_POST["gameName"]; // 선택을 해준 게임의 정보를 가져온다.
    $gameID = $_POST["gameID"];     // 선택한 게임의 입력해준 ID,PW 정보를 가져온다.
    $gamePW = $_POST["gamePW"];

    // 각 게임의 이름을 비교해서 동일한 이름을 가진 게임을 찾아가 해당 ID와 PW를 가입되어있는 ID,PW인지 SELECT를 통해 확인해준다.
    if($gameName  == "MapleStory"){ 
        $statement = mysqli_prepare($con, "SELECT gameID, gamePW FROM MapleStory WHERE gameID = ? AND gamePW = ?");
        mysqli_stmt_bind_param($statement, "ss", $gameID, $gamePW);
        mysqli_stmt_execute($statement);
    }
    if($gameName == "DF"){
        $statement = mysqli_prepare($con, "SELECT gameID, gamePW FROM DF WHERE gameID = ? AND gamePW = ?");
        mysqli_stmt_bind_param($statement, "ss", $gameID, $gamePW);
        mysqli_stmt_execute($statement);
    }

    if($gameName == "LostArk"){
        $statement = mysqli_prepare($con, "SELECT gameID, gamePW FROM LostArk WHERE gameID = ? AND gamePW = ?");
        mysqli_stmt_bind_param($statement, "ss", $gameID, $gamePW);
        mysqli_stmt_execute($statement);
    }

    mysqli_stmt_store_result($statement);// 생성된 레코드셋을 가져와 저장함
    mysqli_stmt_bind_result($statement, $gameID, $gamePW);// 검색 결과로 반환되는 레코드셋의 필드를 php 변수에 바인딩
	                                                       // mysqli_stmt_fetch 호출 전, 모든 필드가 바인드되어야 함
    $response = array();
    $response["success"] = true;
 
    while(mysqli_stmt_fetch($statement)) {// 위에서 SELECT를 통해 ID,PW 정보를 찾았는지 확인해준다.
        $response["success"] = true;
        $response["gameID"] = $gameID;
        $response["gamePW"] = $gamePW;      
    }

    echo json_encode($response);    // PHP Array 또는 String 따위를 JSON 문자열로 변환하는 PHP 함수

?>