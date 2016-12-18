<?php
$connect = mysqli_connect("localhost:3306","cinderella","hell","cinderella"); //id, password
mysqli_set_charset($connect, "utf8");  

$selectSQL = "select * from warehouse"; //tablename
$selectResult = mysqli_query($connect, $selectSQL);
$result = array();

while($row = mysqli_fetch_assoc($selectResult)){
    array_push($result, array('p_id'=>$row["p_id"], 'p_name'=>$row["p_name"], 'amount'=>$row["amount"]));
}
$json = json_encode(array("result"=>$result));
echo $json;
mysqli_close($connect);
?>