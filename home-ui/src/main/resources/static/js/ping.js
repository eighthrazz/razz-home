
$.getJSON("/ping", function (ping) {
  var $tableBody = $("#ping tbody");
  ping.forEach(function (item) {
    var $line = $("<tr>");
    $line.append( $("<td>").text(item.host) );
    $line.append( $("<td>").text(item.millis) );
    $line.append( $("<td>").text(item.date) );
    $tableBody.append($line);
  })
});