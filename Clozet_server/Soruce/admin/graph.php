  <html>
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Year', 'male', 'female'],
          ['1',  1000,      400],
          ['2',  1170,      460],
          ['3',  660,       1120],
          ['4',  1030,      540],
          ['5',  1000,      400],
          ['6',  1170,      460],
          ['7',  660,       1120],
          ['8',  1030,      540],
          ['9',  1000,      400],
          ['10',  1170,      460],
          ['11',  660,       1120],
          ['12',  1030,      540],
          ['13',  1000,      400],
          ['14',  1170,      460],
          ['15',  660,       1120],
          ['17',  1030,      540],
          ['18',  1170,      460],
          ['19',  660,       1120],
          ['20',  1030,      540],
          ['21',  1000,      400],
          ['22',  1170,      460],
          ['23',  660,       1120],
          ['24',  1030,      540],
          ['25',  1030,      540],
          ['26',  1000,      400],
          ['27',  1170,      460],
          ['28',  660,       1120],
          ['29',  1030,      540]
        ]);

        var options = {
          title: 'none',
		  titleTextStyle: { color: '#888',
			  fontName: '맑은 고딕',
			  fontSize: '20',
			  bold: true },
          curveType: 'none',
          legend: { position: 'top right' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <div id="curve_chart" style="width: 900px; height: 500px"></div>
  </body>
</html>