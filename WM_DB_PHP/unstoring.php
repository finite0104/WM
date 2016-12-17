<?php

//데이터 확인해서 -> 남은 재고가 0 이상인 경우에만 update(갯수 - 1) 수행
//반대 경우 오류메세지 출력
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
    echo "잘못된 데이터 입력";
}else(mysql_num_rows($selectResult) >= 1) {
    //검색된 행이 있음 => update
    $unstoringSQL = "update 재고테이블 set amount = (amount-1) 
                    where p_id = '$p_id' and p_name = '$p_name'";
    $setQuery = mysql_query($unstoringSQL, $connect);

    if($setQuery) 
        echo "데이터 수정 완료";
    else
        echo "데이터 수정 실패";
}
mysql_close($connect);
?>