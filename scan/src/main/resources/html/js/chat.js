(function(win, jquery) {
	var $ = jquery;
	var Chat = {
		ws : null,
		isConnection : false,
		onopen : null,
		onmessage : null,
		onclose : null
	};

	if ("WebSocket" in window) {
		ws = new WebSocket("ws://www.harry12800.xyz:80/websocket"); // 打开一个
		// web
		// ws = new WebSocket("ws://127.0.0.1:80/websocket"); // 打开一个 web
		ws.onopen = function() {// Web Socket 已连接上，使用 send() 方法发送数据
			var x = $.cookie("a");
			if (x) {
				var data = JSON.stringify({
					code : 20,
					msg : x,
					content : "",
				});
				ws.send(data);
			} else {
				var data = JSON.stringify({
					code : 21,
					msg : x,
					content : "",
				});
				ws.send(data);
			}
			if (Chat.onopen) {
				Chat.onopen();
			}

		};
		ws.onmessage = function(evt) {
			var received_msg = evt.data;
			var data = JSON.parse(received_msg);
			if (data.code == 21) {
				$.cookie("a", data.content);
				Chat.isConnection = true;
				Chat.id = data.content;
			} else if (data.code == 10) {
				if (Chat.onmessage) {
					Chat.onmessage(JSON.parse(data.content));
				}
			}
		};
		ws.onclose = function() { // 关闭 websocket
			Chat.isConnection = false;
		};
	} else {
		alert("您的浏览器不支持 WebSocket!");
	}
	Chat.send = function(letter) {
		var data = JSON.stringify({
			code : 10,
			msg : "",
			content : JSON.stringify({
				to : 'harry12800',
				data : letter
			})
		});
		ws.send(data);
	}
	Chat.pull = function() {
		var data = JSON.stringify({
			code : 11,
			msg : "",
			content : ""
		});
		ws.send(data);
	}
	win.Chat = Chat;
})(window, $);

function isMobile() {
	if ((navigator.userAgent
			.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
		return true;
	} else {
		return false;
	}
}