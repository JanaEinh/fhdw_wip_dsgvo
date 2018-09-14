/* Controller for Step 4 - Show status and end of application */

app.controller('CTRLStep4Controller', [ '$stateParams', function($stateParams) {

	ctrl = this;

	// Parameters from prior Controller
	ctrl.emailSentSuccessful = $stateParams.data1;

} ]);