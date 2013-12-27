<html>
<head>
<script src="./js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="./js/jquery.pjax.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$(document).pjax('a', '#pjax-container')
});
</script>
</head>
<body>
<h2>Hello Pjax!</h2>
	<div class="container" id="pjax-container">
	  Go to <a href="pjax/1">next page</a>.
	</div>
</body>
</html>
