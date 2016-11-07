** Android only

~~~~ 
var AF = require("ti.appsflyer")
AF.startTracker("COM.APPID","APPSFLYERDEVID");
AF.setCustomerUserId(uuId);

AF.trackAppLaunch();

AF.trackPurchase({
	'amount': 1.00,
	"service": "any string",
});

~~~~ 
