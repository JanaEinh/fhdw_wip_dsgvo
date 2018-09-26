/* Controller for Step 4 - Show status and end of application */

app.controller('CTRLStep4Controller', [ '$stateParams', '$timeout', '$state',
		function($stateParams, $timeout, $state) {

			ctrl = this;

			// Parameters from prior Controller
			ctrl.emailSentSuccessful = $stateParams.data1;

			// Go back to start page to request more customer data
			ctrl.goToStart = function() {
				$timeout(function() {
					$state.go('start');
				}, 1000);
			}

		} ]);