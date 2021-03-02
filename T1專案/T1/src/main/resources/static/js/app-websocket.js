// 设置 STOMP 客户端
var stompClient = null;
// 设置 WebSocket 进入端点
var SOCKET_ENDPOINT = "/mydlq";
// 设置订阅消息的请求前缀
var SUBSCRIBE_PREFIX = "/topic"
// 设置订阅消息的请求地址
var SUBSCRIBE = "";
// 设置服务器端点，访问服务器中哪个接口
var SEND_ENDPOINT = "/app/test";

/* 进行连接 */
function connect() {
    // 设置 SOCKET
    var socket = new SockJS(SOCKET_ENDPOINT);
    // 配置 STOMP 客户端
    stompClient = Stomp.over(socket);
    // STOMP 客户端连接
    stompClient.connect({}, function (frame) {
        //alert("连接成功");
    });
}

connect();

/* 订阅信息 */
function subscribeSocket(){
	$("#chatroom").css("display","unset");
    // 设置订阅地址
    SUBSCRIBE = SUBSCRIBE_PREFIX +'/chat';
    // 输出订阅地址
   // alert("设置订阅地址为：" + SUBSCRIBE);
    // 执行订阅消息
    stompClient.subscribe(SUBSCRIBE, function (responseBody) {
        var receiveMessage = JSON.parse(responseBody.body);
		
		var content=receiveMessage.content.split(':');
		
		
        $("#information").append("<div style='margin-top:13px;margin-left:15px;display:flex;align-items:center;'><div style='color:#265882;white-space: nowrap;'>"+content[0]+"</div><div style='width: 0px;height: 0px;border-right: 11px #dbebff solid;border-top:6px transparent solid;border-bottom:6px transparent solid;margin-left:5px;'></div><div style='color:#265882;background-color:#dbebff;border-radius:2px;padding:3px 10px;word-break:break-all;'>" + content[1] + "</div></div>");
    });
}



/* 断开连接 */
function disconnect() {
    stompClient.disconnect(function() {
        alert("断开连接");
    });
}

/* 发送消息并指定目标地址（这里设置的目标地址为自身订阅消息的地址，当然也可以设置为其它地址） */
function sendMessageNoParameter() {
	
	if($("#content").val()==""){
		return false;
	}
	
	var name=$("#chatname").val()+" : ";
	
	console.log(name);
	
    // 设置发送的内容
    var sendContent = $("#content").val();
    // 设置待发送的消息内容
    var message = '{"destination": "' + SUBSCRIBE + '", "content": "' + name+sendContent + '"}';
    // 发送消息
    stompClient.send(SEND_ENDPOINT, {},message);
 	$("#content").val('');
}