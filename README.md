** Android only

~~~~ 
APP.APPSFLYER.startTracker("com.polkadoc.ocp","EM3Mu7LDEpVUP5SVw9ULpi");
APP.APPSFLYER.setCustomerUserId(uuId);

APP.APPSFLYER.trackAppLaunch();

APP.APPSFLYER.trackPurchase({
	'amount': APP.ORDER.getCachedOrder().orderlines[0].product.price,
	"service": Alloy.Globals.localStorage.selectedService.code,
});

~~~~ 
