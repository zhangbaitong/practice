var singleton = (function(window, undefined) {
	var instance = null;
	function initializeNewModule() {
		function myMethod() {
			alert('my method');
		}
		function myOtherMethod() {
			alert('my other method');
		}
		return {
			someMethod : myMethod,
			someOtherMethod : myOtherMethod
		};
	}
	function getInstance() {
		if (!instance) {
			instance = new initializeNewModule();
		}
		return instance;
	}
	return {
		getInstance : getInstance
	};

})(window);
// example usage
mySingleton.getInstance().someMethod(); // alerts "my method"
mySingleton.getInstance().someOtherMethod(); // alerts "my other method"