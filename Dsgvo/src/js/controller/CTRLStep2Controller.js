app.controller('CTRLStep2Controller', [ '$state', '$timeout', '$scope',
		'$stateParams', function($http, $state, $scope, $stateParams) {
			ctrl = this;

			ctrl.emailSentSuccessful = $stateParams.data1;

		} ]);