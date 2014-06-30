<script>
 
$(document).ready(function() {
	//jsonjson();
	//扫描码事件
	$("#code").keyup(function(event){
		//回车事件
		if(event.which == 13){
			console.log("enter is done");
			checkCode($("#code"));
		};
	});
	//扫描枪预制事件
	$("#prepare").bind("click",function(){
		$("#code").focus();
	});
	//清空当前计算事件
	$("#clear").bind("click",function(){
		clear();
	});
	//结算事件
	$("#checkout").bind("click",function(){
		checkout();
	});
	xisha_amount = 15;
	chashao_amount = 35;
	huotui_amount = 38;
	function checkCode(code){
		console.log($(code).val());
		strCode = $(code).val();
		if(strCode == '88888'){
			$("#clear").click();
		}else if(strCode == '99999'){
			$("#checkout").click();
		}else if(strCode == '00001'){//00001
			//洗沙
			console.log("xisha-num:" + $("#xisha-num").html());
			xisha_temp = $("#xisha-num").html();
			$("#xisha-num").html(parseInt(xisha_temp) + 1);
			$("#xisha-sum").html((parseInt(xisha_temp) + 1) * xisha_amount);
			calcAmountSum();
		}else if(strCode == '00002'){//00002
			//叉烧
			chashao_temp = $("#chashao-num").html();
			$("#chashao-num").html(parseInt(chashao_temp) + 1);
			$("#chashao-sum").html((parseInt(chashao_temp) + 1) * chashao_amount);
			calcAmountSum();
		}else if(strCode == '00003'){//00003
			//火腿
			huotui_temp = $("#huotui-num").html();
			$("#huotui-num").html(parseInt(huotui_temp) + 1);
			$("#huotui-sum").html((parseInt(huotui_temp) + 1) * huotui_amount);
			calcAmountSum();
		};
		$(code).val("");
		$("#code").focus();
	};

	function calcAmountSum(){
		$("#amount-sum").html(parseInt($("#xisha-sum").html())+parseInt($("#chashao-sum").html())+parseInt($("#huotui-sum").html()));
	};

	function checkout(){
		console.log("check out");
		//insertData = '[{"name":"00001","num":"11","sum":"111"},{"name":"00002","num":"11","sum":"111"},{"name":"00003","num":"11","sum":"111"}]';
		insertData = "";
		//判断是否可以结算
		if($("#amount-sum").html() == "0"){
			$("#info").html("没有销售不需要结算");
		}else{
			$("#info").html("");
			//获取提交数据
			temp = new Array();
			if($("#xisha-num").html() != "0"){
				tempsub = {};
				tempsub["name"] = "00001";
				tempsub["num"] = $("#xisha-num").html();
				tempsub["sum"] = $("#xisha-sum").html();
				temp[temp.length] = tempsub;
				console.log("---xisha---");
				console.log("length" + temp.length);
			}

			if($("#chashao-num").html() != "0"){
				tempsub = {};
				tempsub["name"] = "00002";
				tempsub["num"] = $("#chashao-num").html();
				tempsub["sum"] = $("#chashao-sum").html();
				temp[temp.length] = tempsub;
			}
			if($("#huotui-num").html() != "0"){
				tempsub = {};
				tempsub["name"] = "00003";
				tempsub["num"] = $("#huotui-num").html();
				tempsub["sum"] = $("#huotui-sum").html();
				temp[temp.length] = tempsub;
			}


			insertData = JSON.stringify(temp);
			//alert(insertData);
			// 00001
			// $("#xisha-num").html();
			// $("#xisha-sum").html();
			//提交请求
			$.ajax({ 
				//type: post,
				url: "insert",
				data: {'data':insertData},
				success: function(msg){
	        		//总金额清零
					$("#amount-sum").html(0);
					//清空
					clear();
					$("#info").html("提交结算:" + msg);
	      		}
      		});
		}
		
		
	};

	function clear(){
		console.log("clear");
		$("[id*=num]").text("0");
		$("[id*=sum]").text("0");
		$("#code").val("");
		//扫描枪预制
		$("#code").focus();
	};

	function play(name){
		$('embed').remove();  
        $('body').append('<embed hidden="true" height="100" width="100" src="sound/'+name+'.mp3" />'); 
        //简易模式
        //<embed hidden="true" height="100" width="100" src="sound/nizhan.mp3" />
        //视频模式
        //<video controls="" autoplay="true" name="media"  hidden="true"><source src="sound/nizhan.mp3" type="audio/mpeg"></video>
        //HTML最好的方式
		//<audio controls="controls" height="100" width="100" autoplay="true">
  		//	<source src="sound/nizhan.mp3" type="audio/mp3" />
  		//	<source src="sound/nizhan.mp3" type="audio/ogg" />
		//	<embed height="100" width="100" src="song.mp3" />
		//</audio>

	};

	function jsonjson(){
		// temp = new Array();
		// for(var i=0;i<3;i++){
		// 	tempsub = {};
		// 	tempsub["a"] = "1";
		// 	tempsub["b"] = "2";
		// 	temp[i] = tempsub;
		// }
		// myjsonStr = JSON.stringify(temp);
		// alert(myjsonStr);
		//jquery只支持json str转json obj
		//myjson = $.parseJSON('[{"a":"1","b":"2"},{"a":"1","b":"2"}]');
		//使用浏览器对象JSON
		myjson = JSON.parse('[{"a":"1","b":"2"},{"a":"1","b":"2"}]');
		//ie8(兼容模式),ie7和ie6没有JSON对象
		//从http://www.json.org/下载json.js
		//从https://github.com/douglascrockford/JSON-js下载json2.js
		alert(myjson[0].a);
		myjsonStr = JSON.stringify(myjson);
		alert(myjsonStr);
	};
});
 
</script>
<div id="content" class="container">
	<!-- 扫描码和结算金额 -->
	<div class="row">
		<div class="col-md-1"><label>扫描码:</label></div>
		<div class="col-md-2"><input id="code" type="input" placeholder="" class="form-control input-sm"></input></div>
		<div class="col-md-1"><label>状态信息:</label></div>
		<div class="col-md-2"><label><span id="info" class="label label-info"></span></label></div>
		<div class="col-md-2"><span>DB：</span><span class="label label-danger"><?php echo $body; ?></span></div>
		<div class="col-md-1"><a href="admin">管理</a></div>
	</div>
	<!-- 标题 -->
	<hr>
	<div class="row">
		<h6>
			<div class="col-md-4">月饼名称</div>
			<div class="col-md-4">数量（斤）</div>
			<div class="col-md-4">金额（元）</div>
		</h6>
	</div>
	<!-- 洗沙 -->
	<div class="row">
		<h2>
			<div class="col-md-4"><span class="label label-warning">洗沙</span></div>
			<div class="col-md-4"><span id="xisha-num" class="label label-warning">0</span></div>
			<div class="col-md-4"><span id="xisha-sum" class="label label-warning">0</span></div>
		</h2>
	</div>
	<!-- 叉烧 -->
	<div class="row">
		<h2>
			<div class="col-md-4"><span class="label label-danger">叉烧</span></div>
			<div class="col-md-4"><span id="chashao-num" class="label label-danger">0</span></div>
			<div class="col-md-4"><span id="chashao-sum" class="label label-danger">0</span></div>
		</h2>
	</div>
	<!-- 火腿 -->
	<div class="row">
		<h2>
			<div class="col-md-4"><span class="label label-warning">火腿</span></div>
			<div class="col-md-4"><span id="huotui-num" class="label label-warning">0</span></div>
			<div class="col-md-4"><span id="huotui-sum" class="label label-warning">0</span></div>
		</h2>
	</div>
	<!-- 金额 -->
	<div class="row">
		<div class="col-md-4 col-md-offset-8"><span>金额：</span><span id="amount-sum" style="font-size:80px;color:red">0</span></div>
	</div>
	<!-- 扫描枪预制，清空，结算 -->
	<hr>
	<div class="row" style="height:80px">
		<div class="col-md-4"><input id="prepare" type="button" value="扫描枪预制" class="btn btn-primary btn-lg" style="width:100%;height:80px"></input></div>
		<div class="col-md-4"><input id="clear" type="button" value="清空" class="btn btn-warning btn-lg" style="width:100%;height:80px"></input></div>
		<div class="col-md-4"><input id="checkout" type="button" value="结算" class="btn btn-success btn-lg" style="width:100%;height:80px"></input></div>
	</div>
</div>
