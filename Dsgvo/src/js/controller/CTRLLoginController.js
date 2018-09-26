/* Controller for Login Screen */

app.controller('CTRLLoginController', [ '$http', 'SVCConfigurator', '$state',
		'$timeout', function($http, SVCConfigurator, $state, $timeout) {

			ctrl = this;

			// Login Data
			ctrl.email = "";
			ctrl.password = "";
			ctrl.hashString = "DSGVO";

			// Set to true if Login data are correct
			ctrl.loginSuccess = false;
			// If true, status text about incorrect password is shown
			ctrl.showPwIncorrect = false;
			// Times of invalid login tries
			ctrl.invalidLogin = 0;

			// Forms are not submitted when clicking enter
			preventEnterFromSubmit();

			// Executed when clicking Login button
			ctrl.goToStart = function() {
				ctrl.checkLoginData();
				$timeout(function() {
					if (ctrl.loginSuccess == true) {
						$state.go('start');
					} else {
						ctrl.showPwIncorrect = true;
						ctrl.invalidLogin++;
					}
				}, 1000);

			}

			// Checks if login data are correct
			ctrl.checkLoginData = function() {
				// Encryption in Login Database is not implemented yet. Will be
				// activated in productive use of Tool.

				/*
				 * ctrl.password = CryptoJS.AES.encrypt(ctrl.hashString,
				 * ctrl.password);
				 */

				var loginContent = {
					"email" : ctrl.email,
					"password" : ctrl.password,
				}

				// Send HTTP Post Request to Webserver for checking login data
				$http({
					method : 'POST',
					url : SVCConfigurator.getLoginCheckURL(),
					timeout : 5000,
					data : loginContent,
				}).then(function successCallback(response) {
					if (response.data == ("OK")) {
						// HTTP Request responses true if login data are correct
						ctrl.loginSuccess = true;
					} else {
						ctrl.loginSuccess = false;
					}
				}, function errorCallback(response) {
					ctrl.loginSuccess = false;
				});
			}

		} ]);