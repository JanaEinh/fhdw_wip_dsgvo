app.config(function($stateProvider,$urlRouterProvider) {
	$urlRouterProvider.otherwise("/");
	$stateProvider
	.state ("step1", {
		url: "/",
		templateUrl: "templates/TMPLStart.html",
		controller: "CTRLMainController",
		controllerAs: "CTRLMainController",	
		
	})
	.state("step2", {
		url: "/step2",
		templateUrl: "templates/TMPLStep2.html",
		controller: "CTRLStep2Controller",
		controllerAs: "CTRLStep2Controller",
		params: {
			data1: null,
		}
		
	})
	
});