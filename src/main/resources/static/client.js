var stompClient = null;

$(document)
		.ready(

				function() {
					console.log("Before socket Insilization");
					//let idd = "Id-" + Math.random();
					let idd= 10567;
					if (stompClient != null)
						stompClient.disconnect();
					//var socket = new SockJS('https://247rsa.softservtest.com/VehicleTrackingBoot/updateMapLocation');
					var socket = new SockJS('http://localhost/VehicleTrackingBoot/updateMapLocation');
					console.log("After socket Insilization");
					//var socket = new SockJS('http://localhost:8080/updateMapLocation');
					//var socket = new SockJS(
						//		'http://localhost/VehicleTrackingBoot/updateMapLocation');
					stompClient = Stomp.over(socket);

					$("button").click(function() {

						sendData2Socket(idd);

					});
				});

function sendData2Socket(idd) {

	var latitudeeValue = $("#latitudeeId").val();
	var longitudeValue = $("#longitudeId").val();
    console.log("In sendData2Socket");
	stompClient.send("/app/loc", {}, JSON.stringify({
		'latitude' : latitudeeValue,
		'longitude' : longitudeValue,
		'id' : idd
	}));

}