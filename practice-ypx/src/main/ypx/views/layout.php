<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title><?php echo $title; ?></title>
    <!-- Bootstrap -->
    <link href="js/bootstrap3/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="js/date.js"></script>
	<script src="js/bootstrap3/js/bootstrap.min.js"></script>
    <div id="container">
        <?php echo $header_content; ?>
        <hr>
        <?php echo $body_content; ?>
        <hr>
        <?php echo $footer_content; ?>
    </div>