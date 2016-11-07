** Android only

~~~~ 
var AF = require("ti.appsflyer")
AF.startTracker("COM.APPID","APPSFLYERDEVID");
AF.setCustomerUserId(12334556677);

AF.trackAppLaunch();

AF.trackPurchase({
	'amount': 1.00,
	"service": "any string",
});

~~~~ 
