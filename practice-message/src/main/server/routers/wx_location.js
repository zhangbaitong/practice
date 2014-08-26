var db = require("../lib/db")
  dateExt = require("date-ext");

module.exports.location = function (req, res) {
  if (!req.query.openid || !req.query.openid.match(/[a-z\-_0-9]/i)) {
    res.json({error :"bad openid"});
  } else {
    var limit = req.query.limit || 5;
    db.getWxLocation(req.query.openid, limit, function (err, ret) {
      if (err || !ret) {
        res.json({error : "db error"});
      } else {
        if (ret.length == 0) {
          ret[0] = {
              "id": 0,
              "openid": req.query.openid,
              "lat": 26.56807,
              "lon": 106.71751,
              "prec": 40,
              "created": '0000-00-00T00:00:00.000Z'
            };
        }
        res.json({error : null, locations : ret});
      }
    });
  }
};
