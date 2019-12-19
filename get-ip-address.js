const os = require('os');
var ifaces = os.networkInterfaces();

module.exports = () => {
    let ip = false

    Object.keys(ifaces).forEach(function (ifname) {
        ifaces[ifname].forEach(function (iface) {
            if ('IPv4' !== iface.family || iface.internal !== false || ip !== false) {
                return;
            }

            ip = iface.address;
        });
    });

    return ip;
}