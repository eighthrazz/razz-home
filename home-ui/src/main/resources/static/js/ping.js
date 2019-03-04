$.getJSON("/ping/list/lan", function (ping) {
  var dataPoints = [];
  ping.forEach(function (item) {
    dataPoints.push({
      x: new Date(item.date),
      y: item.millis
    });
  })

  var dataSeries = {type: "line"};
  dataSeries.dataPoints = dataPoints;

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

  $("#chartContainerLan").CanvasJSChart(options);
});


$.getJSON("/ping/list/wan", function (ping) {
  var dataPoints = [];
  ping.forEach(function (item) {
    dataPoints.push({
      x: new Date(item.date),
      y: item.millis
    });
  })

  var dataSeries = {type: "line"};
  dataSeries.dataPoints = dataPoints;

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

  $("#chartContainerWan").CanvasJSChart(options);
});