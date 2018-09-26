/* Controller for input of query parameters for private customers */

app.controller('CTRLStep1PrivController', [
		'$state',
		'$timeout',
		'$http',
		'SVCConfigurator',
		function($state, $timeout, $http, SVCConfigurator) {

			ctrl = this;

			// Query parameters
			ctrl.name = "";
			ctrl.forename = "";
			ctrl.birthdate = "";

			// True if Email to server was sent successful
			ctrl.emailSentSuccessful = null;
			// Timestamp for identifying response email
			ctrl.timestamp = null;

			// Email Content
			ctrl.content = "";

			// Go to next step
			ctrl.goToStep2 = function() {
				ctrl.timestamp = new Date();
				ctrl.sendEmail();
				$timeout(function() {
					$state.go('step2', {
						data1 : ctrl.emailSentSuccessful,
						data2 : ctrl.timestamp,
						// Needed for later identification of customer data
						data3 : ctrl.content
					});
				}, 4000);
			}

			// Send Email with query parameters to server
			ctrl.sendEmail = function() {

				// Make day and month two-digit
				var day = ("0" + (ctrl.birthdate.getDate())).slice(-2);
				var month = ("0" + (ctrl.birthdate.getMonth() + 1)).slice(-2);

				// HTTP Post data
				var name = "Nachname: " + ctrl.name;
				var forename = "Vorname: " + ctrl.forename;
				var birthdate = "Geburtsdatum: " + ctrl.birthdate.getFullYear()
						+ "-" + month.toString() + "-" + day.toString();

				name = encodeURI(name);
				forename = encodeURI(forename);
				birthdate = encodeURI(birthdate);

				ctrl.subject = "REQUEST DATA PRIVATE ";
				ctrl.content = forename + "\u000A" + name + "\u000A"
						+ birthdate;

				ctrl.emailContent = {
					"subject" : ctrl.subject,
					"content" : ctrl.content,
					"timestamp" : ctrl.timestamp,
				}

				// HTTP Post request to Webserver for sending Email
				$http({
					method : 'POST',
					url : SVCConfigurator.getEmailURL(),
					timeout : 5000,
					data : ctrl.emailContent,
				}).then(function successCallback(response) {
					ctrl.emailSentSuccessful = true;
				}, function errorCallback(response) {
					ctrl.emailSentSuccessful = false;
				});

			}

		} ])
