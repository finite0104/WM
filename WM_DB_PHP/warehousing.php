<?php
//데이터를 먼저 확인
//재고가 없을 경우에만 insert 작업
//재고가 있는 경우에는 update (갯수 + 1) 작업 수행
$p_id = $_POST["p_id"];
$p_name = $_POST["p_name"];

$connect = mysqli_connect("localhost:3306","cinderella","hell","cinderella"); //id, password
mysqli_set_charset($connect, "utf8");  

$selectSQL = "select * from warehouse where p_id = '$p_id' and p_name = '$p_name'";
$selectResult = mysqli_query($connect, $selectSQL);

//select 쿼리 수행 후 데이터 판별 -> db작업 수행
$selectCount = mysqli_num_rows($selectResult);
if($selectCount == 0) {
    //검색된 행이 없음 => insert
    $whInsertSQL = "insert into warehouse values('$p_id', '$p_name', 1)";
    $insertQuery = mysqli_query($connect, $whInsertSQL);
    if($insertQuery) 
        echo "데이터 입력 완료";
    else
        echo "데이터 입력 실패";
}else($selectCount == 1) {
    //검색된 행이 있음 => update
    $whUpdateSQL = "update warehouse set amount = (amount+1) 
                    where p_id = '$p_id' and p_name = '$p_name'";
    $setQuery = mysqli_query($connect, $whUpdateSQL);
    if($setQuery) 
        echo "데이터 수정 완료";
    else
        echo "데이터 수정 실패";
}
mysqli_close($connect);
?>