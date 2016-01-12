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
		<div class="staff">
			<div class="name">(1) 최보리</div>
			<div class="evaluation"><span style="font-size:30px;">8</span><span style="color:#ffbc64; margin-left:60px;">★★★★</span>★</div>
			<div class="service_count"><span style="font-size:30px;">서비스 횟수</span><span style="font-size:30px; margin-left:80px;">12</span></div>
		</div>
		<div class="staff">
			<div class="name">(2) 윤소연</div>
			<div class="evaluation"><span style="font-size:30px;">8</span><span style="color:#ffbc64; margin-left:60px;">★★★★</span>★</div>
			<div class="service_count"><span style="font-size:30px;">서비스 횟수</span><span style="font-size:30px; margin-left:80px;">13</span></div>
		</div>
		<div class="staff">
			<div class="name">(2) 신나라</div>
			<div class="evaluation"><span style="font-size:30px;">10</span><span style="color:#ffbc64; margin-left:50px;">★★★★★</span></div>
			<div class="service_count"><span style="font-size:30px;">서비스 횟수</span><span style="font-size:30px; margin-left:80px;">14</span></div>
		</div>
	</div>
	</div>
</body>
</html>
