	<div class="wrapper">
	<div class="left">
		<ul>
			<li><a href="stock.php"><img src="./img/btn_stock_<?=(strpos($_SERVER["PHP_SELF"], "stock") > 0)?"on":"off"?>.png" <?=(strpos($_SERVER["PHP_SELF"], "stock") > 0)?'':'onmouseover="mouseOver(this);" onmouseout="mouseOut(this);"'?> /></a></li>
			<li><a href="chart.php"><img src="./img/btn_chart_<?=(strpos($_SERVER["PHP_SELF"], "chart") > 0)?"on":"off"?>.png" <?=(strpos($_SERVER["PHP_SELF"], "chart") > 0)?'':'onmouseover="mouseOver(this);" onmouseout="mouseOut(this);"'?> /></a></li>
			<li><a href="staff.php"><img src="./img/btn_staff_<?=(strpos($_SERVER["PHP_SELF"], "staff") > 0)?"on":"off"?>.png" <?=(strpos($_SERVER["PHP_SELF"], "staff") > 0)?'':'onmouseover="mouseOver(this);" onmouseout="mouseOut(this);"'?> /></a></li>
		</ul>
	</div>
