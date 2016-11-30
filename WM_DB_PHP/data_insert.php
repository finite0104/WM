<?
$p_num = $_POST["p_num"];
$p_name = $_POST["p_name"];

$connect = mysql_connect("localhost:3306", "",""); //id, password
mysql_select_db("", $connect); //database name
mysql_query("set names utf8");
$insertSQL = "insert into _tablenames_ values('$p_num', '$p_name', 1)";
$setQuery = mysql_query($insertSQL, $connect);

if($setQuery) 
    echo "데이터 입력 완료";
else
    echo "데이터 입력 실패";
?>