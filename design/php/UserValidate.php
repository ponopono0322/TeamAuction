<?php
    $con = mysqli_connect('localhost', 'ualsgur98', 'A!1470326', 'ualsgur98');
    // 로그인하려는 계정이 존재하는 계정인지 알기 위해서 만들어진 php이다.
    $userID = $_POST["userID"]; // 앱 유저가 입력해준 ID를 가져온다.

    $statement = mysqli_prepare($con, "SELECT userID FROM USERR WHERE userID = ?"); // 입력해준 유저 ID가 USERR테이블에 존재하는지 검색한다.
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);   // 생성된 레코드셋을 가져와서 클라이언트에 저장한다
    mysqli_stmt_bind_result($statement, $userID);// 검색 결과로 반환되는 레코드셋의 필드를 php 변수에 바인딩
	                                               // mysqli_stmt_fetch 호출 전, 모든 필드가 바인드되어야 함
    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)){   // 입력해준 유저 ID가 같은 것이 USERR테이블에 존재하지 않는다면 알려준다.
      $response["success"] = false;
      $response["userID"] = $userID;
    }

    echo json_encode($response);
?>