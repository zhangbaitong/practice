<div id="content">
  <a href="admin" class="btn btn-default">点击返回管理页面</a>
	<table class="table table-striped table-bordered">
      <thead>
        <tr>
          <th>ID</th>
          <th>月饼名称</th>
          <th>销售斤数</th>
          <th>销售金额</th>
          <th>销售时间</th>
        </tr>
      </thead>
      <tbody>
		<?php 
	        foreach ($body as $row) {
	        	echo '<tr><td>' . $row['ID'] . '</td><td>' . $row['NAME'] . '</td><td>' . $row['NUM'] . '</td><td>' . $row['SUM'] . '</td><td>' . $row['DATETIME'] . '</td></tr>';
			}
		 ?>
      </tbody>
    </table>
</div>
