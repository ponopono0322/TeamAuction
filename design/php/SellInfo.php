<?php
    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");//localhost라는 IP에 ualsgur98이라는 아이디, A!1470326 비밀번호, ualsgur98이라는 데이터베이스명에 접속을 합니다
    mysqli_query($con,'SET NAMES utf8');//앞으로 모든 통신을 utf8로 하겠다고 요청합니다.
    
$gameName =$_POST["userID"]; //게임구별하는 역할
$UniNum =$_POST["userPassword"];// 아이템 고유번호

$data = array(); // data 배열을 만든다. 

    //GameName 과 같은 게임을 분류하고 보여준다.
    if($gameName == "MapleStory"){   //MapleStory 라는 게임이라면?
            $q = mysqli_query($con,"SELECT ItemName,ItemInfo FROM MapleStoryItemInfo WHERE UniNum ='".$UniNum."");//MapleStoryItemInfo라는 테이블에서 받아온 고유번호랑 같으면 아이템 이름, 아이템 정보를 출력합니다.
            $data["success"] =true; //통신이 완료되었습니다.

            while($row= mysqli_fetch_array($q)){ //SQL문으로 출력한 만큼 1개씩 리턴해줍니다
                $data["success"]=true; //통신이 완료되었습니다.
                $data["ItemName"]=$row[0]; //판매할 아이템 이름
                $data["ItemInfo"]=$row[1];  //판매할아이템 정보
        }
        
    }
        

        if($gameName == "DF"){//DF 라는 게임이라면?
          $q = mysqli_query($con,"SELECT ItemName,ItemInfo FROM DFItemInfo WHERE UniNum ='".$UniNum."'");//DFItemInfo라는 테이블에서 받아온 고유번호랑 같으면 아이템 이름, 아이템 정보를 출력합니다.
            $data["success"] =true;//통신이 완료되었습니다.

            while($row= mysqli_fetch_array($q)){//SQL문으로 출력한 만큼 1개씩 리턴해줍니다
                $data["success"]=true;//통신이 완료되었습니다.
                $data["ItemName"]=$row[0];//판매할 아이템 이름
                $data["ItemInfo"]=$row[1];//판매할아이템 정보
        }
        
     }
         if($gameName == "LostArk"){//LostArk 라는 게임이라면?
          $q = mysqli_query($con,"SELECT ItemName,ItemInfo FROM LostArkItemInfo WHERE UniNum ='".$UniNum."'");//LostArkItemInfo라는 테이블에서 받아온 고유번호랑 같으면 아이템 이름, 아이템 정보를 출력합니다.
            $data["success"] =true;//통신이 완료되었습니다.

            while($row= mysqli_fetch_array($q)){//SQL문으로 출력한 만큼 1개씩 리턴해줍니다
                $data["success"]=true;//통신이 완료되었습니다.
                $data["ItemName"]=$row[0];//판매할 아이템 이름
                $data["ItemInfo"]=$row[1];//판매할아이템 정보
        }
        
      }
       echo json_encode($data);//구매할 아이템을 출력합니다.
        mysqli_close($con);//연결은 끝어버립니다.

?>   