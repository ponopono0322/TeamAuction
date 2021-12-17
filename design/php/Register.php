<?php 
    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
    mysqli_query($con,'SET NAMES utf8');
    // 회원가입을 위한 php이다.
    // 안드로이드 스튜디오에서 입력해준 userName, userID, userPassword, userPhone(전화번호), userEmail를 가져온다.
    $userName = $_POST["userName"]; 
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userPhone = $_POST["userPhone"];
    $userEmail = $_POST["userEmail"];
    
    // USERR테이블에 위에서 가져온 정보들을 INSERT 해준다.
    $statement = mysqli_prepare($con, "INSERT INTO USERR VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssss", $userName, $userPhone, $userID, $userPassword, $userEmail);
    mysqli_stmt_execute($statement); 
    
    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>