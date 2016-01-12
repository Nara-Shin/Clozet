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
		<form method="post" action="chart.php">
			<div class="select">
			연령대 
			<select name="prdAge" onchange="this.form.submit();">
				<option value="20" <?=($_POST[prdAge]=="20")?"selected":""?>>20대</option>
				<option value="30" <?=($_POST[prdAge]=="30")?"selected":""?>>30대</option>
				<option value="40" <?=($_POST[prdAge]=="40")?"selected":""?>>40대</option>
			</select>
		</div>
		</form>
		<div class="ranking">
<?
			// Query 1 - 상품 정보 가져오기
			$query = "SELECT Log.SearchProduct, COUNT(Log.SearchProduct) AS CNT, Info.PrdName, Info.PrdImage, Info.PrdUrl FROM ProductSearchLog Log, ProductInfo Info WHERE Log.SearchProduct = Info.PrdCode AND Info.PrdSex = 'M' AND Info.ShopCode = '".$shopCode."' AND Info.PrdAge LIKE '%".$_POST[prdAge]."%' GROUP BY Log.SearchProduct ORDER BY CNT DESC LIMIT 3";

			$result = mysql_query($query);

			$rownum = 1;

			while($row = mysql_fetch_array($result)){
				$PrdName = $row[PrdName];
				$PrdImage = $row[PrdImage];
				$PrdUrl = $row[PrdUrl];
?>
			<div class="men_ranking<?=$rownum++?>">
				<div class="image"><a href="<?=$PrdUrl?>" target="_blank"><img src="../img/product/<?=$PrdImage?>" alt=""/></a></div>
				<div class="name"><?=$PrdName?></div>
			</div>
<?
			}
?>
		</div>
		<div class="ranking" style="margin-left:15px;">
<?
			// Query 2 - 상품 정보 가져오기
			$query = "SELECT Log.SearchProduct, COUNT(Log.SearchProduct) AS CNT, Info.PrdName, Info.PrdImage, Info.PrdUrl FROM ProductSearchLog Log, ProductInfo Info WHERE Log.SearchProduct = Info.PrdCode AND Info.PrdSex = 'W' AND Info.ShopCode = '".$shopCode."' AND Info.PrdAge LIKE '%".$_POST[prdAge]."%' GROUP BY Log.SearchProduct ORDER BY CNT DESC LIMIT 3";

			$result = mysql_query($query);

			$rownum = 1;

			while($row = mysql_fetch_array($result)){
				$PrdName = $row[PrdName];
				$PrdImage = $row[PrdImage];
				$PrdUrl = $row[PrdUrl];
?>
			<div class="women_ranking<?=$rownum++?>">
				<div class="image"><a href="<?=$PrdUrl?>" target="_blank"><img src="../img/product/<?=$PrdImage?>" alt=""/></a></div>
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
			<div class="title">Monthly Statistics - JAN</div>
			<div id="curve_chart" style="width: 960px; height: 400px"></div>
			<div class="title">Generational Statistics</div>
			<div id="donutchart" style="float:left; width: 480px; height: 450px;"></div>
			<div id="donutchart2" style="float:left; width: 480px; height: 450px;"></div>
		</div>
	</div>
	</div>
</body>
    
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawChart);
	google.charts.setOnLoadCallback(drawChart2);
	google.charts.setOnLoadCallback(drawChart3);

	function drawChart() {
		var data = google.visualization.arrayToDataTable([
		['Year', 'male', 'female'],
<?
	for($i=1; $i<=31; $i++){
?>
		['<?=$i?>',  <?=rand(50,100)?>,   <?=rand(150,200)?>]<?=($i!=31)?",":""?>
<?
	}
?>
	]);

	var options = {
		titlePosition: 'none',
		titleTextStyle: {
			color: '#888',
			fontName: '맑은 고딕',
			fontSize: '20',
			bold: true
		},
		curveType: 'none',
		legend: { position: 'top' },
		hAxis: { ticks: [5,10,15,20,25] },
		colors:['#184c8c','#ff6a79'],
		series: {
			0: {
				lineWidth: 5,
			},
			1: {
				lineWidth: 5,
			},
		}
	};

	var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

	chart.draw(data, options);
  }

  function drawChart2() {
	var data = google.visualization.arrayToDataTable([
	  ['연령대', '검색횟수'],
	  ['20대',  20],
	  ['30대',  45],
	  ['40대',  35],
	]);

	var options = {
	  pieHole: 0.67,
	  legend: {position: 'none', textStyle: {color: 'blue', fontSize: 16}},
	  colors:['#FC9DA9','#FF5865','#962463'],
	  pieSliceText:'label'
	};

	var chart = new google.visualization.PieChart(document.getElementById('donutchart'));
	chart.draw(data, options);
  }

  function drawChart3() {
	var data = google.visualization.arrayToDataTable([
	  ['연령대', '검색횟수'],
	  ['20대',  35],
	  ['30대',  40],
	  ['40대',  25],
	]);

	var options = {
	  pieHole: 0.67,
	  legend: {position: 'none', textStyle: {color: 'blue', fontSize: 16}},
	  colors:['#47D4E2','#3D81E2','#232E6F'],
	  pieSliceText:'label'
	};

	var chart = new google.visualization.PieChart(document.getElementById('donutchart2'));
	chart.draw(data, options);
  }
</script>
<!-- SELECT DATE_FORMAT( RegDate,  '%Y-%m-%d' ) AS DATE, COUNT( * ) AS Cnt
FROM ProductSearchLog
WHERE RegDate >=  '2016-01-01'
AND RegDate <=  '2016-01-31'
GROUP BY DATE
ORDER BY DATE -->
</html>
