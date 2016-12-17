<?php
$connect = mysql_connect("localhost:3306","아이디","비밀번호"); //id, password
mysql_select_db("데이터베이스이름",$connect); //db name

$selectSQL = "select * from 재고테이블"; //tablename

mysql_query("set names utf8");
$selectResult = mysql_query($selectSQL);
$result = array();

while($row = mysql_fetch_assoc($selectResult)){
//    echo $row["p_id"];
//    echo "/";
//    echo $row["p_name"];
//    echo "/";
//    echo $row["amount"];
//    echo "@";
    array_push($result, array('p_id'=>$row[0], 'p_name'=>$row[1], 'amount'=>$row[2]));
}
echo json_encode(array("result"=>$result));
mysql_close($connect);
?>