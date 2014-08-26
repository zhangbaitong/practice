module.exports = Store = function () {
  var store = {};
  return {
    'set': function (key, value) {
      store[key] = {
        v: value,
        t: 0
      };
    },
    'setex': function (key, ttl, value) {
      store[key] = {
        v: value,
        t: Date.now() + ttl * 1000
      };
    },
    'get': function (key) {
      var value = store[key];
      return (value === undefined || (value.t > 0 && value.t < Date.now())) ? null : value;
    }
  };
};
