 <?php
        $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");
$response["success"] = true;

// 경매장 앱의 유저가 게임에 로그인을 하고 원하는 캐릭터를 선택하면 그 정보가 경매장 앱 유저의 정보가 있는 곳에 저장된다.
$userID = $_POST["userID"]; // 앱 ID와 PW 그리고 선택한 게임의 이름, 게임ID, 게임PW, 게임 캐릭터 닉네임의 정보를 가져온다.
$userPassword = $_POST["userPassword"];
$gameName = $_POST["gameName"];
$gameID = $_POST["gameID"];
$gamePW = $_POST["gamePW"];
$gameNickname= $_POST["gameNickname"];
$gameNameNickname = $gameName.$gameNickname;


if(!$con)   // MySQL 접속에러가 발생할 시 알려준다.
{
    echo "MySQL 접속에러:";
    echo mysqli_connect_error();
    exit();
}
    
    mysqli_set_charset($con,"utf8");
    mysqli_query($con,'SET NAMES utf8');

     //appID랑 appPW가 같은 곳을 찾아내서 gameName,gameID,gamePW,gameNickname정보를 usergame테이블에 넣어준다. 해당게임의 캐릭터닉네임이 이미 usergame의 테이블에 있다면 INSERT 시켜주지 않는다.
     $q = mysqli_prepare($con,"INSERT INTO usergame(userID, userPassword, gameName, gameID, gamePW, gameNickname, gameNameNickname) VALUES (?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($q, "sssssss", $userID, $userPassword, $gameName, $gameID, $gamePW, $gameNickname, $gameNameNickname);
    mysqli_stmt_execute($q);
    
     $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);

    ?>
