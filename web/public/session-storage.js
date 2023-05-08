//什么在index.html中引用就全局使用，傻狗一个，不用进一步封装直接用就行
SessionStorageaa = {
    get: function (key) {
        var v = sessionStorage.getItem(key);
        if (v && typeof (v) !== "undefined" && v !== "undefined") {
            return JSON.parse(v);
        }
    },
    set: function (key, data) {
        sessionStorage.setItem(key, JSON.stringify(data));
    },
    remove: function (key) {
        sessionStorage.removeItem(key);
    },
    clearAll: function () {
        sessionStorage.clear();
    }
};