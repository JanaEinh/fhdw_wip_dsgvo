/* Controller for Step 2 - Fetching response email */

app.controller('CTRLStep2Controller', [
		'$state',
		'$timeout',
		'$stateParams',
		'$http',
		'SVCConfigurator',
		function($state, $timeout, $stateParams, $http, SVCConfigurator) {

			ctrl = this;

			// Parameters from prior Controller
			ctrl.emailSentSuccessful = $stateParams.data1;
			ctrl.timestamp = $stateParams.data2;
			ctrl.content = $stateParams.data3;

			// Returned data from Server
			ctrl.emailFetchResult = "";

			// Go to next step for showing customer data after several time of
			// waiting for response
			ctrl.goToStep3 = function() {
				ctrl.emailFetchResult = ctrl.emailFetchResult.replace('\n',
						'<br>');
				$timeout(function() {
					$state.go('step3', {
						data1 : ctrl.emailFetchResult,
						data2 : ctrl.content,
					});
				}, 15000);
			}

			// Executed with initial load of this page
			ctrl.init = function() {
				$timeout(function() {
					ctrl.fetchEmail();
					ctrl.goToStep3();
				}, 30000);
			}

			// Fetch incoming email with response data
			ctrl.fetchEmail = function() {
				var timestamp = {
					"timestamp" : ctrl.timestamp,
				}

				// HTTP Post request to fetch incoming email
				$http({
					method : 'POST',
					url : SVCConfigurator.getFetchMailURL(),
					timeout : 15000,
					data : timestamp,
				}).then(function successCallback(response) {
					ctrl.emailFetchResult = response.data;
				}, function errorCallback(response) {
					ctrl.emailFetchResult = response.statusText;
				});

			}

		} ]);