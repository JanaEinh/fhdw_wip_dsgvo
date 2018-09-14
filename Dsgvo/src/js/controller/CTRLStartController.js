/* Controller for Start Screen - Choosing between private or commercial customer */

app.controller('CTRLStartController', [ '$state', '$timeout',
		function($state, $timeout) {

			ctrl = this;

			// Goes to next step for private customer query
			ctrl.goToStep1Priv = function() {
				$timeout(function() {
					$state.go('step1Priv');
				}, 1000);
			}

			// Goes to next step for commercial customer query
			ctrl.goToStep1Comp = function() {
				$timeout(function() {
					$state.go('step1Comp');
				}, 1000);
			}

		} ]);