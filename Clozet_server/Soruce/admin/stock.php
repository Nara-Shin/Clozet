<?
include "../lib/dbconnect.php";
?>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="Generator" content="EditPlus®">
	<meta name="Author" content="">
	<meta name="Keywords" content="">
	<meta name="Description" content="">
	<link rel="stylesheet" type="text/css" href="./css/style.css">
	<title>CLOZET</title>
	<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
	<script type="text/javascript">
	<!--
		function mouseOver(img){
			if(img.src.indexOf("_on") > 0){
			}else{
				img.src = img.src.replace("_off","_on");
			}
		}

		function mouseOut(img){
			if(img.src.indexOf("_off") > 0){
			}else{
				img.src = img.src.replace("_on","_off");
			}
		}

		function selectSize(button){
			var sizeButtons = button.form.size;
			for(var i=0; i<sizeButtons.length; i++){
				if(sizeButtons[i].value == button.value){
					sizeButtons[i].style.border = "solid 3px #999";
					button.form.prdouct_size.value = button.value;
				}else{
					sizeButtons[i].style.border = "solid 1px #999";
				}
			}
		}

		function selectColor(button){
			var colorButtons = button.form.color;
			for(var i=0; i<colorButtons.length; i++){
				if(colorButtons[i].value == button.value){
					colorButtons[i].style.border = "solid 3px #999";
					button.form.product_color.value = button.value;
				}else{
					colorButtons[i].style.border = "solid 1px #999";
				}
			}
		}

		$('#stockSubmit').click(function(){
			$.ajax({
				url:'/clozet/product/stock_change.php',
				type:'post',
				data:$('form').serialize(),
				success:function(data){
					alert('완료');
				}
			})
		})
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
			<form method="post" action="">
				<input type="text" name="" value="제품 번호" onfocus="this.value = '';">
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
			$query = sprintf("SELECT PrdCategory, PrdShopCode, PrdCode, PrdName FROM ProductInfo");

			$result = mysql_query($query);

			while($row = mysql_fetch_array($result)){
				$PrdCategory = ($row[PrdCategory]=="1")?"상의":"하의";
				$PrdShopCode = $row[PrdShopCode];
				$PrdCode = $row[PrdCode];
				$PrdName = $row[PrdName];

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
				<tr>
					<td style="text-align:center;"><?=$PrdCategory?></td>
					<td>T-SHIRT</td>
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
					<input type="hidden" name="prdouct_size">
					<input type="hidden" name="product_color">
					<input type="hidden" name="product_code" value="<?=$PrdCode?>">
					<td style="text-align:center;"><input type="text" name="stock_count"><input type="button" value="확인" id="stockSubmit" onclick=""></td>
				</tr>
			</form>
<?
			}
?>			
			</table>
		</div>
	</div>
</body>
</html>
