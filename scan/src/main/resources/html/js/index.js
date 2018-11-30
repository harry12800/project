$(document).ready(function() {
	$('#searchInput').keypress(function(event) {
		if (event.keyCode == 13) {
			var url = "?key=" + $('#searchInput').val();
			window.location.href = url;
		}
	});
	$("#togbook").click(function() {
		Chat.onmessage = function(data) {
			console.log(data);
			if (data.from != Chat.id)
				loadLeftChatInfo(data);
			else {
				console.log(data);
				
				
				
				loadRightChatInfo(data);
			}
		}
		BootstrapDialog.show({
			closable : true,
			closeByBackdrop : true,
			closeByKeyboard : true,
			size : BootstrapDialog.SIZE_LARGE,
			title : '向周国柱发送消息',
			onshown:function(){
				Chat.pull();
			},
			message : $('<div></div>').load('remote.html'),
			buttons : [ {
				label : '发送',
				cssClass : 'btn-primary',
				action : function() {
					Chat.send($("#sendContent").val());
					var t = {
						from : Chat.id,
						to : 'harry12800',
						data : $("#sendContent").val()
					}
					loadRightChatInfo(t);
				}
			} ]
		});
		
	});
	$('#identifier').on('show.bs.modal', function () {
		  // 执行一些动作...
	})
});
function loadLeftChatInfo(data) {
	var avatar = $("<span  id='lord_avatar'></span>");
	$("#chatPanel").append(avatar);
	var right = $("<span id='chat_content'></span>");
	var name = $("<p id='chat_name'>someone</p>");
	name.text(data.from);
	var content = $("<pre></pre>");
	content.css("clear", "both");
	right.append(name).append(content);
	right.css("float", "left");
	content.text(data.data)
	$("#chatPanel").append(right);
	$("#chatPanel").scrollTop(10000);
}

function loadRightChatInfo(data) {
	var avatar = $("<span id='visitor_avatar'></span>");
	$("#chatPanel").append(avatar);
	var right = $("<span id='chat_content'></span>");
	var name = $("<p id='chat_name'>someone</p>");
	name.text(data.from);
	var content = $("<pre></pre>");
	content.css("clear", "both");
	right.append(name).append(content);
	right.css("float", "right");
	content.text(data.data)
	$("#chatPanel").append(right);
	$("#sendContent").val("");
	$("#chatPanel").scrollTop(10000);
}
function modifyDiary(obj) {
	if ($(obj).attr("cipher") == 1) {
		var dialog = BootstrapDialog
				.alert({
					title : '身份验证',
					message : "<input class='form-control' id='cipherusername' type='text' name='userName'/> <input class='form-control' id='cipherpwd' type='password' name='pwd'/>",
					type : BootstrapDialog.TYPE_WARNING, // <-- Default value
															// is
															// BootstrapDialog.TYPE_PRIMARY
					closable : true, // <-- Default value is false
					draggable : true, // <-- Default value is false
					// size: BootstrapDialog.SIZE_LARGE,
					cssClass : 'login-dialog',
					onshown : function() {
						$('#cipherusername')
								.keypress(
										function(event) {
											if (event.keyCode == 13) {
												$
														.ajax({
															url : '/v1/user/validate?id='
																	+ $(obj)
																			.attr(
																					"did")
																	+ "&userName="
																	+ $(
																			'#cipherusername')
																			.val()
																	+ "&pwd="
																	+ $(
																			'#cipherpwd')
																			.val(),
															type : 'get',
															dataType : 'json',
															contentType : 'application/json',
															success : function(
																	data) {
																console
																		.log(data);
																if (data.code == 10) {
																	console
																			.log(data);
																	if (data.content.html)
																		dialog
																				.setMessage(data.content.html);
																	else {
																		dialog
																				.setMessage(data.content.content);
																	}
																} else {
																	alert(data.msg);
																}
															}
														});
											}
										});
					},
					buttons : [
							{
								label : '验证',
								action : function(dialog) {
									var html = '<div id="layout"> <div id="test-editormd-view"> <textarea style="display:none;" name="test-editormd-markdown-doc" title="showMessage"></textarea>'
											+ ' </div> </div>';
									var param = {
										id : $(obj).attr("did"),
										userName : $("#cipherusername").val(),
										pwd : $("#cipherpwd").val()
									}
									dialog.setMessage(html);
									var testEditormdView;
									$
											.ajax({
												url : '/v1/user/validate',
												type : 'POST',
												data : param,
												dataType : 'json',
												// contentType:
												// 'application/json',
												error : function(
														XMLHttpRequest,
														textStatus, errorThrown) {
													alert(XMLHttpRequest.status); // 200
													alert(textStatus); // parsererror
													alert(errorThrown); // SyntaxError:
																		// Unexpected
																		// end
																		// of
																		// input
												},
												success : function(markdown) {
													console.log(markdown);
													dialog
															.setTitle(markdown.content.title);
													testEditormdView = editormd
															.markdownToHTML(
																	"test-editormd-view",
																	{
																		markdown : markdown.content.content,// +
																											// "\r\n"
																											// +
																											// $("#append-test").text(),
																		htmlDecode : "style,script,iframe", // you
																											// can
																											// filter
																											// tags
																											// decode
																		tocm : true, // Using
																						// [TOCM]
																		emoji : true,
																		taskList : true,
																		tex : true, // 默认不解析
																		flowChart : true, // 默认不解析
																		sequenceDiagram : true
																	// 默认不解析
																	});
												}
											});

								}
							},
							{
								label : '加密',
								action : function(dialog) {
									var html = '<div id="layout"> <div id="test-editormd-view"> <textarea style="display:none;" name="test-editormd-markdown-doc" title="showMessage"></textarea>'
											+ ' </div> </div>';
									var urlpath = '/v1/diary/cipherOrDecode?id='
											+ $(obj).attr("did")
											+ "&userName="
											+ $('#cipherusername').val()
											+ "&pwd="
											+ $('#cipherpwd').val()
											+ "&type=1";
									dialog.setMessage(html);
									$.ajax({
										url : urlpath,
										type : 'get',
										dataType : 'json',
										contentType : 'application/json',
										success : function(data) {
											console.log(data);
											if (data.code == 10) {
												dialog.close();
											} else {
												alert(data.msg);
											}
										}
									});
								}
							},
							{
								label : '解密',
								action : function(dialog) {
									var html = '<div id="layout"> <div id="test-editormd-view"> <textarea style="display:none;" name="test-editormd-markdown-doc" title="showMessage"></textarea>'
											+ ' </div> </div>';
									var urlpath = '/v1/diary/cipherOrDecode?id='
											+ $(obj).attr("did")
											+ "&userName="
											+ $('#cipherusername').val()
											+ "&pwd="
											+ $('#cipherpwd').val()
											+ "&type=0";
									dialog.setMessage(html);
									$.ajax({
										url : urlpath,
										type : 'get',
										dataType : 'json',
										contentType : 'application/json',
										success : function(data) {
											console.log(data);
											if (data.code == 10) {
												dialog.close();
											} else {
												alert(data.msg);
											}
										}
									});
								}
							} ]
				});
	} else if ($(obj).attr("cipher") == 3) {
		window.location.href = $(obj).attr("url");
	} else {
		var html = '<div id="layout"> <div id="test-editormd-view"> <textarea style="display:none;" name="test-editormd-markdown-doc" title="showMessage"></textarea>'
				+ ' </div> </div>';
		var dialog = BootstrapDialog.alert({
			title : '标题',
			message : html,
			type : BootstrapDialog.TYPE_WARNING, // <-- Default value is
													// BootstrapDialog.TYPE_PRIMARY
			closable : true, // <-- Default value is false
			draggable : true, // <-- Default value is false
			// size: BootstrapDialog.SIZE_LARGE,
			cssClass : 'login-dialog',
			onshown : function() {
				$.ajax({
					url : '/v1/diary/getById?id=' + $(obj).attr("did"),
					type : 'get',
					dataType : 'json',
					contentType : 'application/json',
					success : function(data) {
						console.log(data);
						if (data.code == 10) {
							var testEditormdView;
							dialog.setTitle(data.content.title);
							testEditormdView = editormd.markdownToHTML(
									"test-editormd-view", {
										markdown : data.content.content,// +
																		// "\r\n"
																		// +
																		// $("#append-test").text(),
										htmlDecode : "style,script,iframe", // you
																			// can
																			// filter
																			// tags
																			// decode
										tocm : true, // Using [TOCM]
										emoji : true,
										taskList : true,
										tex : true, // 默认不解析
										flowChart : true, // 默认不解析
										sequenceDiagram : true
									// 默认不解析
									});
						} else {
							alert(data.msg);
						}
					}
				});
			}
		});
		$.ajax({
			url : '/v1/diary/incHint?id=' + $(obj).attr("did"),
			type : 'get',
			dataType : 'json',
			contentType : 'application/json',
			success : function(data) {
				console.log(data);
			}
		});

	}
}
