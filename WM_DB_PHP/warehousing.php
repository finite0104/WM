<?
$p_num = $_POST["p_num"];
$p_name = $_POST["p_name"];

$connect = mysql_connect("localhost:3306", "",""); //id, password
mysql_select_db("", $connect); //database name
mysql_query("set names utf8");
$warehousingSQL = "update _tablename_ set amount = (amount+1) 
                where p_num = '$p_num' and p_name = '$p_name'";
$setQuery = mysql_query($warehousingSQL, $connect);

if($setQuery) 
    echo "데이터 수정 완료";
else
    echo "데이터 수정 실패";
?>