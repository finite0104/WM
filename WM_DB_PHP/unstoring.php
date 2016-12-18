<?php
//데이터 확인해서 -> 남은 재고가 0 이상인 경우에만 update(갯수 - 1) 수행
//반대 경우 오류메세지 출력
$p_id = $_POST["p_id"];
$p_name = $_POST["p_name"];

$connect = mysqli_connect("localhost:3306","cinderella","hell","cinderella"); //id, password
mysqli_set_charset($connect, "utf8");  

$selectSQL = "select * from warehouse where p_id = '$p_id' and p_name = '$p_name'";
$selectResult = mysqli_query($connect, $selectSQL);

//select 쿼리 수행 후 데이터 판별 -> db작업 수행
if(mysqli_num_rows($selectResult) == 0) {
    //검색된 행이 없음 => insert
    echo "잘못된 데이터 입력";
}else(mysqli_num_rows($selectResult) >= 1) {
    //검색된 행이 있음 => update
    $unstoringSQL = "update warehouse set amount = (amount-1) 
                    where p_id = '$p_id' and p_name = '$p_name'";
    $setQuery = mysqli_query($connect, $unstoringSQL);

    if($setQuery) 
        echo "데이터 수정 완료";
    else
        echo "데이터 수정 실패";
}
mysqli_close($connect);
?>