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
		<h2>Stock Management</h2>
		<div class="search">
			<form method="post" name="searchForm" action="stock.php" onsubmit="return checkSearchForm();">
				<input type="text" name="searchTxt" value="<?=($_POST[searchTxt]=="")?"제품 번호":$_POST[searchTxt]?>" onfocus="if(this.value == '제품 번호'){this.value = ''};" onfocusout="if(this.value == ''){this.value = '제품 번호'};">
				<input type="image" src="./img/stock_btn_search.png">
			</form>
		</div>
		<div class="table">
			<table>
			<colgroup>
				<col width="10%" />
				<col width="15%" />
				<col width="10%" />
				<col width="20%" />
				<col width="20%" />
				<col width="15%" />
				<col width="10%" />
			</colgroup>
			<tr>
				<th>제품 분류</th>
				<th>제품 분류</th>
				<th>제품 번호</th>
				<th>제품 이름</th>
				<th>SIZE</th>
				<th>COLOR</th>
				<th>수량</th>
			</tr>

<?
			// Query 1 - 상품 정보 가져오기
			$query = "SELECT PrdCategory, PrdCategory2, PrdShopCode, PrdCode, PrdName, PrdImage FROM ProductInfo WHERE PrdShopCode LIKE '%".$_POST[searchTxt]."%' AND ShopCode = '".$shopCode."'";

			$result = mysql_query($query);

			while($row = mysql_fetch_array($result)){
				$PrdCategory = ($row[PrdCategory]=="1")?"상의":"하의";
				$PrdCategory2 = $row[PrdCategory2];
				$PrdShopCode = $row[PrdShopCode];
				$PrdCode = $row[PrdCode];
				$PrdName = $row[PrdName];
				$PrdImage = $row[PrdImage];

				$PrdSize = array();
				$PrdColor = array();
				$PrdStock = array();

				// Query 2 - 상품 사이즈 정보 가져오기
				$query = sprintf("SELECT DISTINCT PrdSize FROM ProductOption WHERE PrdCode = '%s'",
				mysql_real_escape_string($PrdCode));

				$result2 = mysql_query($query);

				while($row = mysql_fetch_array($result2)){
					$PrdSize[] = $row[PrdSize];
				}

				// Query 3 - 상품 색상 정보 가져오기
				$query = sprintf("SELECT DISTINCT PrdColor FROM ProductOption WHERE PrdCode = '%s'",
				mysql_real_escape_string($PrdCode));

				$result2 = mysql_query($query);

				while($row = mysql_fetch_array($result2)){
					$PrdColor[] = $row[PrdColor];
				}

?>
			<form method="post" name="stockForm" action="">
				<tr onclick="changeImage('<?=$PrdImage?>', this);" style="cursor:pointer;">
					<td style="text-align:center;"><?=$PrdCategory?></td>
					<td><?=$PrdCategory2?></td>
					<td style="text-align:center;"><?=$PrdShopCode?></td>
					<td><?=$PrdName?></td>
					<td style="text-align:center;">
						<?for($i=0; $i<count($PrdSize); $i++){?>
						<input type="button" value="<?=$PrdSize[$i]?>" onclick="selectSize(this);" name="size">
						<?}?>
					</td>
					<td style="text-align:center;">
						<?for($i=0; $i<count($PrdColor); $i++){?>
						<input type="button" value="<?=$PrdColor[$i]?>" onclick="selectColor(this);" name="color" style="background-color:#<?=$PrdColor[$i]?>; text-indent:-9999px;">
						<?}?>
					</td>
					<input type="hidden" name="product_size" value="">
					<input type="hidden" name="product_color" value="">
					<input type="hidden" name="product_code" value="<?=$PrdCode?>">
					<td style="text-align:center;"><input type="text" name="stock_count" style="border:none; width:45px; height:30px; font-size:16px; text-align:center;"><input type="button" value="반영" name="stockSubmit" onclick=""></td>
				</tr>
			</form>
<?
			}
?>			
			</table>
		</div>
		<div class="image">
			<img src="../img/product/100001.jpg" width="300" id="productImage"><br/>
			<input type="button" value="주문 요청하기" onclick="alert('주문이 완료되었습니다.');" style="width:300px; height:40px; font-size:17px; background-color:#ffc5c5; border:none; font-weight:bold;">
		</div>
	</div>
	</div>
</body>
</html>
