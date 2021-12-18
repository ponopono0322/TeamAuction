<?php
    $con = mysqli_connect("localhost", "ualsgur98", "A!1470326", "ualsgur98");//localhost라는 IP에 ualsgur98이라는 아이디, A!1470326 비밀번호, ualsgur98이라는 데이터베이스명에 접속을 합니다
    mysqli_query($con,'SET NAMES utf8');//앞으로 모든 통신을 utf8로 하겠다고 요청합니다.
    
 
    
$gameName =$_POST["userID"]; //게임을 구별합니다. 
$UniNum = $_POST["userPassword"];// 아이템 고유번호
 $data = array();//data 배열을 만듭니다. 

    //GameName 과 같은 게임을 분류하고 보여준다.
    if($gameName == "MapleStory"){   //MapleStory 게임이라면?
            $q = mysqli_query($con,"SELECT ItemName, ItemInfo FROM MapleStoryItemInfo WHERE UniNum='".$UniNum."'"); //MapleStory 라는게임에서 아이템 고유값이 같은 아이템의 이름과 정보를 출력합니다.         
        $data["success"] =true; //통신이 완료되었습니다. 

        while($row= mysqli_fetch_array($q)){ //SQL문으로 출력한 만큼 1개씩 리턴해줍니다
            $data["success"]=true; //통신이 완료되었습니다. 
            $data["ItemName"]=$row[0]; // 아이템 이름을 출력하는 역할
            $data["ItemInfo"]=$row[1]; //아이템 정보를 출력하는 역할
        }
        
        
    }
        
        if($gameName == "DF"){ //DF 게임이라면?
              $q = mysqli_query($con,"SELECT ItemName, ItemInfo FROM DFItemInfo WHERE UniNum='".$UniNum."'");   //DF 라는게임에서 아이템 고유값이 같은 아이템의 이름과 정보를 출력합니다.       
        $data["success"] =true;//통신이 완료되었습니다.

        while($row= mysqli_fetch_array($q)){//SQL문으로 출력한 만큼 1개씩 리턴해줍니다
            $data["success"]=true; //통신이 완료되었습니다. 
            $data["ItemName"]=$row[0]; //아이템 이름을 출력하는 역할
            $data["ItemInfo"]=$row[1]; // 아이템 정보를 출력하는 역할
        }    
    }
    
         if($gameName == "LostArk"){//LostArk 게임이라면?
               $q = mysqli_query($con,"SELECT ItemName, ItemInfo FROM LostArkItemInfo WHERE UniNum='".$UniNum."'"); //LostArk 라는게임에서 아이템 고유값이 같은 아이템의 이름과 정보를 출력합니다.   
        $data["success"] =true;//통신이 완료되었습니다.

        while($row= mysqli_fetch_array($q)){//SQL문으로 출력한 만큼 1개씩 리턴해줍니다
            $data["success"]=true; //통신이 완료되었습니다. 
            $data["ItemName"]=$row[0]; // 아이템 이름을 출력하는 역할
            $data["ItemInfo"]=$row[1]; //아이템 정보를 출력하는 역할
        }
        
    }
        echo json_encode($data);//구매할 아이템을 출력합니다. 
        mysqli_close($con);//연결은 끝어버립니다. 

?>   