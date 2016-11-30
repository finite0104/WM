<?
$p_num = $_POST["p_num"];
$p_name = $_POST["p_name"];

$connect = mysql_connect("localhost:3306","",""); //id, password
mysql_select_db("",$connect); //db name

$selectSQL = "select * from  where p_num = '$p_num' and p_name = '$p_name'"; //tablename

mysql_query("set names utf8");
$findResult = mysql_query($selectSQL);
$rowCount = mysql_num_rows($findResult);

if($rowCount == 0) 
    echo "데이터 없음";
else
    echo "데이터 있음";
?>