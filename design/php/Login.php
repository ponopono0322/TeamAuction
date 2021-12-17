<?php
    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
    mysqli_query($con,'SET NAMES utf8');
    // 로그인을 위한 php이다.
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";  // 앱의 유저 ID를 입력받음
    $userPassword = isset($_POST["userPassword"]) ? $_POST["userPassword"] : "";    // 앱의 유저 Password를 입력받음
    
    $statement = mysqli_prepare($con, "SELECT userID, userPassword FROM USERR WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);    // 유저의 ID와 유저의 Password를 이용해서 USERR테이블에 유저의 ID와 Password가 있는지 검색한다.


    mysqli_stmt_store_result($statement);   // 생성된 레코드셋을 가져와 저장함
    mysqli_stmt_bind_result($statement, $userID, $userPassword);// 검색 결과로 반환되는 레코드셋의 필드를 php 변수에 바인딩
	                                                           // mysqli_stmt_fetch 호출 전, 모든 필드가 바인드되어야 함
    $response = array();
    $response["success"] = true;
 
    while(mysqli_stmt_fetch($statement)) {  // 유저가 ID와 Password를 입력해준 값이 USERR테이블에 있는 것과 같은지 비교해준다.
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;      
    }

    echo json_encode($response);



?>