/* In this file the routes of the application are defined. */

app.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise("/");
	$stateProvider.state("login", {
		url : "/",
		templateUrl : "templates/TMPLLogin.html",
		controller : "CTRLLoginController",
		controllerAs : "CTRLLoginController",
	})

	.state("start", {
		url : "/start",
		templateUrl : "templates/TMPLStart.html",
		controller : "CTRLStartController",
		controllerAs : "CTRLStartController",

	}).state("step1Priv", {
		url : "/step1Priv",
		templateUrl : "templates/TMPLStep1Priv.html",
		controller : "CTRLStep1PrivController",
		controllerAs : "CTRLStep1PrivController",

	}).state("step1Comp", {
		url : "/step1Comp",
		templateUrl : "templates/TMPLStep1Comp.html",
		controller : "CTRLStep1CompController",
		controllerAs : "CTRLStep1CompController",
	})

	.state("step2", {
		url : "/step2",
		templateUrl : "templates/TMPLStep2.html",
		controller : "CTRLStep2Controller",
		controllerAs : "CTRLStep2Controller",
		params : {
			data1 : null,
			data2 : null,
			data3 : null,
		}

	}).state("step3", {
		url : "/step3",
		templateUrl : "templates/TMPLStep3.html",
		controller : "CTRLStep3Controller",
		controllerAs : "CTRLStep3Controller",
		params : {
			data1 : null,
			data2 : null,
		}
	})

	.state("step4", {
		url : "/step4",
		templateUrl : "templates/TMPLStep4.html",
		controller : "CTRLStep4Controller",
		controllerAs : "CTRLStep4Controller",
		params : {
			data1 : null,
		}
	})

});