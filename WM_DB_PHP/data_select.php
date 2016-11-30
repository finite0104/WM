<?
$connect = mysql_connect("localhost:3306","",""); //id, password
mysql_select_db("",$connect); //db name

$selectSQL = "select * from "; //tablename

mysql_query("set names utf8");
$selectResult = mysql_query($selectSQL);

while($row = mysql_fetch_assoc($selectResult)){
    echo $row["p_id"];
    echo "/";
    echo $row["p_name"];
    echo "/";
    echo $row["amount"];
    echo "@";
}
?>