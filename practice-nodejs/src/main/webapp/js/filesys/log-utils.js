var settings = require("./settings.json");
var zlog = function (){

    this.arguments = process.argv.splice(2);

    this.config = settings;

    this.info = function(data){
        console.log('INFO:',data);
    };

    this.infos = function(name,data){
        console.log('INFO:',name,'-',data);
    };

    this.detail = function(){
        this.info('=== settings ===');
        this.info(this.config);
        this.info('=== arguments ===');
        this.info(this.arguments);
    };

    this.getday = function(){
        var day = new Date();
        return day.getFullYear() + '-' +
            (day.getMonth()+1) + '-' +
            day.getDate();
    };

    this.replaceDate = function(str){
        return str.replace("{date}",this.getday());
    };

};


module.exports = new zlog();