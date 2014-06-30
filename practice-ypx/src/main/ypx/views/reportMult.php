        <link rel="stylesheet" href="js/amcharts3/samples/style.css" type="text/css">
        <script src="js/amcharts3/amcharts/amcharts.js" type="text/javascript"></script>
		    <script src="js/amcharts3/amcharts/serial.js" type="text/javascript"></script>
        <script type="text/javascript">
           var chart;
           var chartData = [];

           AmCharts.ready(function () {
               // generate some random data first
               generateChartData();

               // SERIAL CHART
               chart = new AmCharts.AmSerialChart();
               chart.pathToImages = "../amcharts/images/";
               chart.dataProvider = chartData;
               chart.categoryField = "date";

               // listen for "dataUpdated" event (fired when chart is inited) and call zoomChart method when it happens
               chart.addListener("dataUpdated", zoomChart);

               // AXES
               // category
               var categoryAxis = chart.categoryAxis;
               categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
               categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
               categoryAxis.minorGridEnabled = true;
               categoryAxis.axisColor = "#DADADA";
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

               // first value axis (on the left)
               var valueAxis1 = new AmCharts.ValueAxis();
               valueAxis1.axisColor = "#FF6600";
               valueAxis1.axisThickness = 2;
               valueAxis1.gridAlpha = 0;
               chart.addValueAxis(valueAxis1);

               // second value axis (on the right)
               var valueAxis2 = new AmCharts.ValueAxis();
               valueAxis2.position = "right"; // this line makes the axis to appear on the right
               valueAxis2.axisColor = "#FCD202";
               valueAxis2.gridAlpha = 0;
               valueAxis2.axisThickness = 2;
               chart.addValueAxis(valueAxis2);

               // third value axis (on the left, detached)
               valueAxis3 = new AmCharts.ValueAxis();
               valueAxis3.offset = 50; // this line makes the axis to appear detached from plot area
               valueAxis3.gridAlpha = 0;
               valueAxis3.axisColor = "#B0DE09";
               valueAxis3.axisThickness = 2;
               chart.addValueAxis(valueAxis3);

               // GRAPHS
               // first graph
               var graph1 = new AmCharts.AmGraph();
               graph1.valueAxis = valueAxis1; // we have to indicate which value axis should be used
               graph1.title = "red line";
               graph1.valueField = "visits";
               graph1.bullet = "round";
               graph1.hideBulletsCount = 30;
               graph1.bulletBorderThickness = 1;
               chart.addGraph(graph1);

               // second graph
               var graph2 = new AmCharts.AmGraph();
               graph2.valueAxis = valueAxis2; // we have to indicate which value axis should be used
               graph2.title = "yellow line";
               graph2.valueField = "hits";
               graph2.bullet = "square";
               graph2.hideBulletsCount = 30;
               graph2.bulletBorderThickness = 1;
               chart.addGraph(graph2);

               // third graph
               var graph3 = new AmCharts.AmGraph();
               graph3.valueAxis = valueAxis3; // we have to indicate which value axis should be used
               graph3.valueField = "views";
               graph3.title = "green line";
               graph3.bullet = "triangleUp";
               graph3.hideBulletsCount = 30;
               graph3.bulletBorderThickness = 1;
               chart.addGraph(graph3);

               // CURSOR
               var chartCursor = new AmCharts.ChartCursor();
               chartCursor.cursorAlpha = 0.1;
               chartCursor.fullWidth = true;
               chart.addChartCursor(chartCursor);

               // SCROLLBAR
               var chartScrollbar = new AmCharts.ChartScrollbar();
               chart.addChartScrollbar(chartScrollbar);

               // LEGEND
               var legend = new AmCharts.AmLegend();
               legend.marginLeft = 110;
               legend.useGraphSettings = true;
               chart.addLegend(legend);

               // WRITE
               chart.write("chartdiv");
           });

           function generateChartData2() {

                

                // for (var i = 0; i < myjson.length; i++) 


                   //  chartData.push({
                   //     date: newDate,
                   //     visits: myjson[i].xisha,
                   //     hits: myjson[i].chashao,
                   //     views: myjson[i].huotui,
                   // });

           }

           function generateChartData() {
                dbData = <?php echo "'$body'"?>;
                //alert(dbData);
                myjson = JSON.parse(dbData);
                // alert(myjson[0].dayday);
                // alert(myjson.length);

                if(myjson.length > 10){

                    for (var i = 0; i < length; i++) {
                       chartData.push({
                           date: myjson[i].dayday,
                           visits: myjson[i].xisha,
                           hits: myjson[i].chashao,
                           views: myjson[i].huotui
                       });
                    }
                }else{
                   var firstDate = new Date();
                   firstDate.setDate(firstDate.getDate() - 50);

                   for (var i = 0; i < 50; i++) {

                       var newDate = new Date(firstDate);
                       newDate.setDate(newDate.getDate() + i);

                       var visits = Math.round(Math.random() * 40) + 100;
                       var hits = Math.round(Math.random() * 80) + 500;
                       var views = Math.round(Math.random() * 6000);
                       chartData.push({
                           date: newDate,
                           visits: visits,
                           hits: hits,
                           views: views
                       });
                   }
                }
           }

           // generate some random data, quite different range
           function generateChartData_bak() {
               var firstDate = new Date();
               firstDate.setDate(firstDate.getDate() - 50);

               for (var i = 0; i < 50; i++) {
                   // we create date objects here. In your data, you can have date strings
                   // and then set format of your dates using chart.dataDateFormat property,
                   // however when possible, use date objects, as this will speed up chart rendering.
                   var newDate = new Date(firstDate);
                   newDate.setDate(newDate.getDate() + i);

                   var visits = Math.round(Math.random() * 40) + 100;
                   var hits = Math.round(Math.random() * 80) + 500;
                   var views = Math.round(Math.random() * 6000);
                   chartData.push({
                       date: newDate,
                       visits: visits,
                       hits: hits,
                       views: views
                   });
               }
           }

           // this method is called when chart is first inited as we listen for "dataUpdated" event
           function zoomChart() {
               // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
               chart.zoomToIndexes(10, 20);
           }
        </script>
        <div class="row">
          <div class="col-md-6"><a href="admin" class="btn btn-default">点击返回管理页面</a></div>
        </div>
        <div id="chartdiv" style="width: 100%; height: 400px;"></div>