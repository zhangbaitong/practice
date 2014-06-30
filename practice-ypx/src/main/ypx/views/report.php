        <link rel="stylesheet" href="js/amcharts3/samples/style.css" type="text/css">
        <script src="js/amcharts3/amcharts/amcharts.js" type="text/javascript"></script>
		<script src="js/amcharts3/amcharts/serial.js" type="text/javascript"></script>
        <script type="text/javascript">
            var chart;
            var chartData = [];
            var chartCursor;



            AmCharts.ready(function () {
                // generate some data first
                generateChartData();

                // SERIAL CHART
                chart = new AmCharts.AmSerialChart();
                chart.pathToImages = "../amcharts/images/";
                chart.dataProvider = chartData;
                chart.categoryField = "date";
                chart.balloon.bulletSize = 5;

                // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
                chart.addListener("dataUpdated", zoomChart);

                // AXES
                // category
                var categoryAxis = chart.categoryAxis;
                categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
                categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
                categoryAxis.dashLength = 1;
                categoryAxis.minorGridEnabled = true;
                categoryAxis.twoLineMode = true;
                categoryAxis.dateFormats = [{
                    period: 'fff',
                    format: 'JJ:NN:SS'
                }, {
                    period: 'ss',
                    format: 'JJ:NN:SS'
                }, {
                    period: 'mm',
                    format: 'JJ:NN'
                }, {
                    period: 'hh',
                    format: 'JJ:NN'
                }, {
                    period: 'DD',
                    format: 'DD'
                }, {
                    period: 'WW',
                    format: 'DD'
                }, {
                    period: 'MM',
                    format: 'MMM'
                }, {
                    period: 'YYYY',
                    format: 'YYYY'
                }];

                categoryAxis.axisColor = "#DADADA";

                // value
                var valueAxis = new AmCharts.ValueAxis();
                valueAxis.axisAlpha = 0;
                valueAxis.dashLength = 1;
                chart.addValueAxis(valueAxis);

                // GRAPH
                var graph = new AmCharts.AmGraph();
                graph.title = "red line";
                graph.valueField = "visits";
                graph.bullet = "round";
                graph.bulletBorderColor = "#FFFFFF";
                graph.bulletBorderThickness = 2;
                graph.bulletBorderAlpha = 1;
                graph.lineThickness = 2;
                graph.lineColor = "#5fb503";
                graph.negativeLineColor = "#efcc26";
                graph.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50 series in selection
                chart.addGraph(graph);

                // CURSOR
                chartCursor = new AmCharts.ChartCursor();
                chartCursor.cursorPosition = "mouse";
                chartCursor.pan = true; // set it to fals if you want the cursor to work in "select" mode
                chart.addChartCursor(chartCursor);

                // SCROLLBAR
                var chartScrollbar = new AmCharts.ChartScrollbar();
                chart.addChartScrollbar(chartScrollbar);

                chart.creditsPosition = "bottom-right";

                // WRITE
                chart.write("chartdiv");
            });


            function generateChartData() {

                dbData = <?php echo "'$body'"?>;
                //alert(dbData);
                myjson = JSON.parse(dbData);
                //alert(myjson[0].sumday);
                //alert(myjson.length);

                for (var i = 0; i < myjson.length; i++) {
                    value = myjson[i].sumday;
                    if(!value){
                        value = myjson[i].numday;
                    }
                    //alert(value);
                    chartData.push({
                        date: myjson[i].dayday,
                        visits: value
                    });
                }
            }

            // generate some random data, quite different range
            function generateChartData_bak() {
                var firstDate = new Date();
                firstDate.setDate(firstDate.getDate() - 500);

                for (var i = 0; i < 500; i++) {
                    // we create date objects here. In your data, you can have date strings
                    // and then set format of your dates using chart.dataDateFormat property,
                    // however when possible, use date objects, as this will speed up chart rendering.
                    var newDate = new Date(firstDate);
                    newDate.setDate(newDate.getDate() + i);

                    var visits = Math.round(Math.random() * 40) - 20;

                    chartData.push({
                        date: newDate,
                        visits: visits
                    });
                }
            }

            // this method is called when chart is first inited as we listen for "dataUpdated" event
            function zoomChart() {
                // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
                chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
            }

            // changes cursor mode from pan to select
            function setPanSelect() {
                if (document.getElementById("rb1").checked) {
                    chartCursor.pan = false;
                    chartCursor.zoomable = true;
                } else {
                    chartCursor.pan = true;
                }
                chart.validateNow();
            }

        </script>
        <div class="row">
            <div class="col-md-6"><a href="admin" class="btn btn-default">点击返回管理页面</a></div>
        </div>
        <div id="chartdiv" style="width: 100%; height: 400px;"></div>
        <div style="margin-left:35px;">
            <input type="radio" name="group" id="rb1" onclick="setPanSelect()">Select
            <input type="radio" checked="true" name="group" id="rb2" onclick="setPanSelect()">Pan
		</div>