<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>${title}</title>
<link rel="stylesheet" href="/css/style.css" />
<link rel="stylesheet" href="/css/editormd.preview.css" />
<script src="/js/jquery.min.js"></script>
<script src="/lib/marked.min.js"></script>
<script src="/lib/prettify.min.js"></script>
<script src="/lib/raphael.min.js"></script>
<script src="/lib/underscore.min.js"></script>
<script src="/lib/sequence-diagram.min.js"></script>
<script src="/lib/flowchart.min.js"></script>
<script src="/lib/jquery.flowchart.min.js"></script>
<script src="/lib/editormd.min.js"></script>

<style>
.markdown-toc.editormd-markdown-toc{
    POSITION: FIXED;
    LEFT: 0;
    width: 250px;
		overflow: auto;
		max-height: 80%;
		margin-top:10%
}
.editormd-html-preview {
	width: 95%;
    margin: 0 auto;
    padding-left: 250px;
}
</style>
</head>
<body>

	<div id="layout">
		<div id="test-editormd-view">
			<textarea style="display: none;" name="test-editormd-markdown-doc"
				title="showMessage"></textarea>
		</div>
	</div>


	<script type="text/javascript">
		$(function() {
			var testEditormdView;
			$.get("/${name}", function(markdown) {
				testEditormdView = editormd.markdownToHTML(
						"test-editormd-view", {
							markdown : markdown,//+ "\r\n" + $("#append-test").text(),
							htmlDecode : "style,script,iframe", // you can filter tags decode
							tocm : true, // Using [TOCM]
							emoji : true,
							taskList : true,
							tex : true, // 默认不解析
							flowChart : true, // 默认不解析
							sequenceDiagram : true
						// 默认不解析
						});
			});
		});
		$("body").delegate(".close-js", "click", function() {
			$(".iframeTnset").hide();
			$("body").css("overflow", "scroll")
		});
		window.myLoad = [];
		function apiCustom(host, path) {
			var index = window.myLoad.indexOf(host);
			if (index > -1) {
				var id = "iframeTnset" + index + "-js";
				$("." + id).show();
				$("body").css("overflow", "hidden");

				var iframe = document.getElementById(id);
				iframe.contentWindow.postMessage("path::"+path, '*');

			} else {
				window.myLoad.push(host);
				index = window.myLoad.indexOf(host);
				var classJs = "iframeTnset" + index + "-js";
				var html = "<div class='iframeTnset iframeTnset"+index+"-js'><div class='close  close-js' ></div><iframe id='"
						+ classJs
						+ "'   src='"
						+ host
						+ "?path="
						+ path
						+ "' frameborder='0'  width='900px'  height='600px'></iframe></div>"
				$("body").append(html).css("overflow", "hidden");
			}
		}
		
		function modelCustom(host, path) {
			var index = window.myLoad.indexOf(host);
			if (index > -1) {
				var id = "iframeTnset" + index + "-js";
				$("." + id).show();
				$("body").css("overflow", "hidden");

				var iframe = document.getElementById(id);
				iframe.contentWindow.postMessage("model:"+path, '*');

			} else {
				window.myLoad.push(host);
				index = window.myLoad.indexOf(host);
				var classJs = "iframeTnset" + index + "-js";
				var html = "<div class='iframeTnset iframeTnset"+index+"-js'><div class='close  close-js' ></div><iframe id='"
						+ classJs
						+ "'   src='"
						+ host
						+ "?model="
						+ path
						+ "' frameborder='0'  width='900px'  height='600px'></iframe></div>"

				$("body").append(html).css("overflow", "hidden");

			}
		}
	</script>

</body>
</html>