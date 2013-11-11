/**
 * trident
 * 		- core
 * 			- define module
 * 		- util
 * 			- log
 * 			- msg
 * 
 * 
 * 思考：
 * 1.单js文件是否需要依赖和发布？
 * 		不需要，因为始终都要进行全局加载，而且全部都可以看到，只需要告诉使用者相关命名空间即可
 * 2.全局命令空间是否需要传递？
 * 		既然为单个命名空间，而且是全局可见，那么传递它的意义就在于简化命名使用，由于js的参数传递特殊性，可以选择不用。
 * 3.简化发布,这种方式如何避免？
 * 4.模块的命名空间的嵌套层次有必要么？
 * 
 */
//定义总命名空间
var trident = {
		coreKey : "core",
		utilKey : "util"
};
//定义核心模块
trident.core = (function(t){
	var name = t.coreKey,
	//定义模块，参数：模块名，模块实现
	//TODO:对于已存在module的判断，避免模块命名空间冲突。
	defModule = function(name,func){
		t[name] = (function(){
			return func(t);
		})();
	},
	//扩展模块，参数：模块名，模块扩展实现
	extModule = function(name,func){
		t[name] = (function(){
			return func(t);
		})();
	},
	//获得模块，参数：模块名
	requireModule = function(name){
		//如果有层次嵌套，可以使用hasOwnProperty方法
		return t[name];
	}
	return {
		getName : function(){
			return name;
		},
		definem : defModule,
		extendm : extModule,
		requirem : requireModule
	}
//防止对象不存在,并就进行作用于赋值
})(window.trident = trident.t = trident || {});
//定义工具模块
trident.core.definem(trident.utilKey,function(){
	name : trident.utilKey;
	return {
		getName : function(){
			return name;
		},
		log : function(msg){
			console.log("Trident Log : " + msg);
		},
		msg : function(msg){
			alert(msg);
		}
	}
})
//简化发布
function log(msg){
	trident.util.log(msg);
}
function msg(msg){
	trident.util.msg(msg);
}

//...............Test....................
trident.core.definem("myModule",function(t){
	return {
		getName : function(){
			return "My name is myModule";
		},
		getName2 : function(){
			return "My name2 is " + t.core.getName();
		}
	}
})
log("Trident is running ...");
log(trident.core.getName());
log(trident.myModule.getName());
log(trident.myModule.getName2());

trident.core.extendm("myModule",function(t){
	log("Create method 3...");
	var extModule = t["myModule"];
	extModule.getName2 = function(){
		return "Ok,I am name2...";
	};
	extModule.getName3 = function(){
		return "Ok,I am name3...";
	}
	return extModule;
});
log(trident.core.getName())
log(trident.myModule.getName())
log(trident.myModule.getName2())
log(trident.myModule.getName3())

var myModule = trident.core.requirem("myModule");
log(myModule.getName3());
//...............Test....................
