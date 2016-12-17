<?php
//데이터를 먼저 확인
//재고가 없을 경우에만 insert 작업
//재고가 있는 경우에는 update (갯수 + 1) 작업 수행
$p_id = $_POST["p_id"];
$p_name = $_POST["p_name"];

$connect = mysql_connect("localhost:3306", "아이디","패스워드"); //id, password
mysql_select_db("데이터베이스이름", $connect); //database name
mysql_query("set names utf8");

$selectSQL = "select * from 재고테이블 where p_id = '$p_id' and p_name = '$p_name'";
$selectResult = mysql_query($selectSQL);

//select 쿼리 수행 후 데이터 판별 -> db작업 수행
if(mysql_num_rows($selectResult) == 0) {
    //검색된 행이 없음 => insert
    $whInsertSQL = "insert into 재고테이블 values('$p_id', '$p_name', 0)";
    $insertQuery = mysql_query($whInsertSQL, $connect);
    if($insertQuery) 
        echo "데이터 입력 완료";
    else
        echo "데이터 입력 실패";
}else(mysql_num_rows($selectResult) >= 1) {
    //검색된 행이 있음 => update
    $whUpdateSQL = "update 재고테이블 set amount = (amount+1) 
                    where p_id = '$p_id' and p_name = '$p_name'";
    $setQuery = mysql_query($whUpdateSQL, $connect);
    if($setQuery) 
        echo "데이터 수정 완료";
    else
        echo "데이터 수정 실패";
}
mysql_close($connect);
?>