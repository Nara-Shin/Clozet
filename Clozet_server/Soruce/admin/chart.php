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
	<div class="contents2">
		<h2>Ranking</h2>
		<div class="ranking">
<?
			// Query 1 - 상품 정보 가져오기
			$query = "SELECT Log.SearchProduct, COUNT(Log.SearchProduct) AS CNT, Info.PrdName, Info.PrdImage FROM ProductSearchLog Log, ProductInfo Info WHERE Log.SearchProduct = Info.PrdCode AND Info.PrdSex = 'M' AND Info.ShopCode = '".$shopCode."' GROUP BY Log.SearchProduct ORDER BY CNT DESC";

			$result = mysql_query($query);

			$rownum = 1;

			while($row = mysql_fetch_array($result)){
				$PrdName = $row[PrdName];
				$PrdImage = $row[PrdImage];
?>
			<div class="men_ranking<?=$rownum++?>">
				<div class="image"><img src="../img/product/<?=$PrdImage?>" alt=""/></div>
				<div class="name"><?=$PrdName?></div>
			</div>
<?
			}
?>
		</div>
		<div class="ranking" style="margin-left:15px;">
<?
			// Query 1 - 상품 정보 가져오기
			$query = "SELECT Log.SearchProduct, COUNT(Log.SearchProduct) AS CNT, Info.PrdName, Info.PrdImage FROM ProductSearchLog Log, ProductInfo Info WHERE Log.SearchProduct = Info.PrdCode AND Info.PrdSex = 'W' AND Info.ShopCode = '".$shopCode."' GROUP BY Log.SearchProduct ORDER BY CNT DESC";

			$result = mysql_query($query);

			$rownum = 1;

			while($row = mysql_fetch_array($result)){
				$PrdName = $row[PrdName];
				$PrdImage = $row[PrdImage];
?>
			<div class="women_ranking<?=$rownum++?>">
				<div class="image"><img src="../img/product/<?=$PrdImage?>" alt=""/></div>
				<div class="name"><?=$PrdName?></div>
			</div>
<?
			}
?>
		</div>
	</div>
	<div class="contents3">
		<h2>Statistic Table</h2>
		<div class="area">

		</div>
	</div>
	</div>
</body>
</html>
