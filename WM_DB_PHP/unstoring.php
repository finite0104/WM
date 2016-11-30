<?
//데이터 확인해서 -> 남은 재고가 0 이상인 경우에만 update 수행
$p_num = $_POST["p_num"];
$p_name = $_POST["p_name"];

$connect = mysql_connect("localhost:3306", "",""); //id, password
mysql_select_db("", $connect); //database name
mysql_query("set names utf8");
$unstoringSQL = "update _tablename_ set amount = (amount-1) 
                where p_num = '$p_num' and p_name = '$p_name'";
$setQuery = mysql_query($unstoringSQL, $connect);

if($setQuery) 
    echo "데이터 수정 완료";
else
    echo "데이터 수정 실패";
?>