<?
include "../lib/dbconnect.php";
$shopCode = "100001";
?>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="./css/style.css">
	<title>CLOZET</title>
	<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
	<script src="./js/common.js"></script>
	<script type="text/javascript">
	<!--
		
		$(document).ready(function(){
			$("input[name=size]").click(function(){
				//alert($(this.form).serialize());
				var form = $(this.form);
				var text = $(this.form.stock_count);
				$.ajax({
					url:'/clozet/product/get_stock.php',
					type:'post',
					data:form.serialize(),
					dataType:'json',
					success:function(data){
						//alert(JSON.stringify(data));
						text.val(data.stock);
					}
				});
			});

			$("input[name=color]").click(function(){
				//alert($(this.form).serialize());
				var form = $(this.form);
				var text = $(this.form.stock_count);
				$.ajax({
					url:'/clozet/product/get_stock.php',
					type:'post',
					data:form.serialize(),
					dataType:'json',
					success:function(data){
						//alert(JSON.stringify(data));
						text.val(data.stock);
					}
				});
			});

			$("input[name=stockSubmit]").click(function(){
				$.ajax({
					url:'/clozet/product/stock_change.php',
					type:'post',
					data:$(this.form).serialize(),
					dataType:'json',
					success:function(data){
						if(data.confirm_message == "success"){
							alert("재고가 반영되었습니다.");
						}else if(data.confirm_message == "fail"){
							alert("사이즈 및 색상을 선택하시기 바랍니다.");
						}
					}
				});
			});
		});
	//-->
	</script>
</head>
<body>
	<?
	include "./include/top.php";
	include "./include/left.php";
	?>
	<div class="contents">
		<h2>Staff Management</h2>
<?
		// Query 1 - 상품 정보 가져오기
		$query = "SELECT * FROM ClerkInfo WHERE WorkingShop = '".$shopCode."'";

		$result = mysql_query($query);

		$rownum = 1;

		while($row = mysql_fetch_array($result)){
			// Query 2 - 상품 정보 가져오기
			$query = "SELECT SUM(StarCount)/COUNT(*) AS StarCount FROM ClerkEvaluation WHERE ClerkCode = '".$row[ClerkCode]."'";

			$result2 = mysql_query($query);

			while($row2 = mysql_fetch_array($result2)){
				$star_count = $row2[StarCount];
			}
?>
		<div class="staff">
			<div class="name">(<?=$rownum++?>) <?=$row[ClerkName]?></div>
			<div class="evaluation"><!-- <span style="font-size:30px;"><?=ceil($star_count)?></span> --><span style="color:#ffbc64; margin-left:45px;"><?for($i=1; $i<=ceil($star_count); $i++){echo "★";}?></span><?for($i=1; $i<=5-ceil($star_count); $i++){echo "★";}?></div>
			<div class="service_count"><span style="font-size:30px;">서비스 횟수</span><span style="font-size:30px; margin-left:80px;"><?=$row[ServiceCount]?></span></div>
		</div>
<?
		}
?>
	</div>
	</div>
</body>
</html>
