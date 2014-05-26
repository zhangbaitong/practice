angular.module('express', ['ionic'])
/**
 * 定义Expresses对象进行数据的相关处理
 */
    .factory('Expresses', function() {
        return {
            all: function() {
                var expressString = window.localStorage['expresses'];
                if(expressString) {
                    return angular.fromJson(expressString);
                }
                return [];
            },
            save: function(expresses) {
                window.localStorage['expresses'] = angular.toJson(expresses);
            },
            newExpress: function(expressName) {
                return {
                    name: expressName,
                    orders: []
                };
            },
            getLastActiveIndex: function() {
                return parseInt(window.localStorage['lastActiveExpress']) || 0;
            },
            setLastActiveIndex: function(index) {
                window.localStorage['lastActiveExpress'] = index;
            },
            getOrderDetails: function(expressName,orderNum){
                console.log("******** getOrderDetails *****");
                console.log("*****" + expressName + orderNum +"*****");
                return ["a","b","c"];
            }
        }
    })

/**
 * 定义ExpressCtrl控制器进行控制器上下文初始化和上下文方法声明
 */
    .controller('ExpressCtrl', function($scope, $timeout, $ionicModal, Expresses, $ionicSideMenuDelegate,$http) {

        var createExpress = function(expressName) {
            var newExpress = Expresses.newExpress(expressName);
            console.log("**** " + expressName + "*****");
            $scope.expresses.push(newExpress);
            Expresses.save($scope.expresses);
            $scope.selectExpress(newExpress, $scope.expresses.length-1);
        }


        //初始化所有快递信息
        $scope.expresses = Expresses.all();

        $scope.orderDetails = [];

        //保存最后一次活动中的快递公司
        $scope.activeExpress = $scope.expresses[Expresses.getLastActiveIndex()];

        $scope.newExpress = function() {
            var expressTitle = prompt('快递公司名称');
            if(expressTitle) {
                createExpress(expressTitle);
            }
        };


        $scope.selectExpress = function(express, index) {
            console.log("**** selectExpress *****");
            console.log("**** " + index + "*****");

            $scope.activeExpress = express;
            Expresses.setLastActiveIndex(index);
            $ionicSideMenuDelegate.toggleLeft(false);
        };

        //创建单号弹出页面
        $ionicModal.fromTemplateUrl('new-order.html', function(modal) {
            $scope.orderModal = modal;
        }, {
            scope: $scope
        });

        $scope.createOrder = function(order) {
            console.log("***** create order ******");
            if(!$scope.activeExpress || !order) {
                return;
            }
            console.log(order.num);
            $scope.activeExpress.orders.push({
                num: order.num
            });
            $scope.orderModal.hide();

            //保存所有快递信息
            Expresses.save($scope.expresses);

            console.log(order.num);

            order.num = "";
        };

        $scope.newOrder = function() {
            $scope.orderModal.show();
        };

        $scope.closeNewOrder = function() {
            $scope.orderModal.hide();
        }

        $scope.toggleExpresses = function() {
            $ionicSideMenuDelegate.toggleLeft();
        };

        $scope.listOrderDetail = function(name,order) {
            console.log("********* list order detail ***********");
            console.log("name : " + name);
            console.log("order : " + order);
            //var xhrUrl = 'http://localhost/express.php?name='+name+'&order='+order;
            //console.log("xhrUrl : " + xhrUrl);
            //获取数据
            //$http.get(xhrUrl).success(function(data) {
            //    console.log(data);
            //    $scope.orderDetails = angular.fromJson(data);
            //});
            //从json文件读取数据模仿网络访问
            $http.get('details.json').success(function(data) {
                console.log(data);
                var orderService = angular.fromJson(data);
                console.log(orderService[name+order]);
                $scope.orderDetails = orderService[name+order];
            });


            $ionicSideMenuDelegate.toggleRight();
        };


        //尝试创建第一个快递公司信息，同事使用timeout来保证其他内容初始化完毕
        $timeout(function() {
            if($scope.expresses.length == 0) {
                while(true) {
                    var expressTitle = prompt('输入您想查找的快递公司名称：');
                    if(expressTitle) {
                        createExpress(expressTitle);
                        break;
                    }
                }
            }
        });

    });