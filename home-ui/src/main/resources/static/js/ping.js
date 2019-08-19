window.onload = function () {
  //
  var timeoutMillis = 1000;

  // charts
  var lanChart;
  var wanChart;

  // chart data points
  var lanDataPoints = [];
  var wanDataPoints = [];

  // LAN Chart
  $.getJSON("/ping/list/lan", function (ping) {
    // load initial data
    ping.forEach(function (item) {
      lanDataPoints.push({
        x: new Date(item.date),
        y: item.millis
      });
    })

    // configure chart
    var dataSeries = {type: "line"};
    dataSeries.dataPoints = lanDataPoints;

    var data = [];
    data.push(dataSeries);

    var options = {
      zoomEnabled: true,
      animationEnabled: true,
      title: {
        text: "LAN"
      },
      axisY: {
        includeZero: false
      },
      data: data
    };

    // create chart
    //lanChart = $("#chartContainerLan").CanvasJSChart(options);
    lanChart = new CanvasJS.Chart("chartContainerLan", options);
    lanChart.render();

    // update chart
    updateLanChart();
  });

  // WAN Chart
  $.getJSON("/ping/list/wan", function (ping) {

    ping.forEach(function (item) {
      wanDataPoints.push({
        x: new Date(item.date),
        y: item.millis
      });
    })

    var dataSeries = {type: "line"};
    dataSeries.dataPoints = wanDataPoints;

    var data = [];
    data.push(dataSeries);

    var options = {
      zoomEnabled: true,
      animationEnabled: true,
      title: {
        text: "WAN"
      },
      axisY: {
        includeZero: false
      },
      data: data
    };

    //wanChart = $("#chartContainerWan").CanvasJSChart(options);
    wanChart = new CanvasJS.Chart("chartContainerWan", options);
    wanChart.render();

    // update chart
    updateWanChart();
  });

  // update LAN chart
  function updateLanChart() {
    var offset = lanDataPoints.length + 1;
    $.getJSON("/ping/list/lan?offset=" + offset, function (ping) {
      //
      setTimeout(function () {
        //
        ping.forEach(function (item) {
          lanDataPoints.push({
            x: new Date(item.date),
            y: item.millis
          });
        })

        //
        lanChart.render();

        //
        updateLanChart();
      }, timeoutMillis);
    });
  }

  // update WAN chart
  function updateWanChart() {
    var offset = wanDataPoints.length + 1;
    $.getJSON("/ping/list/wan", {offset: offset}, function (ping) {

      console.log(
          "/ping/list/wan?offset=" + offset + ", length=" + ping.length);

      //
      setTimeout(function () {
        //
        ping.forEach(function (item) {
          //console.log("wan: offset="+offset+", length="+ping.length+", "+new Date(item.date)+", "+item.millis);
          wanDataPoints.push({
            x: new Date(item.date),
            y: item.millis
          });
        })

        //
        wanChart.render();

        //
        updateWanChart();
      }, timeoutMillis);
    });
  }
}