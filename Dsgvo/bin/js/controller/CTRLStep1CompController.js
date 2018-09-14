/* Controller for input of query parameters for commercial customers */

app.controller('CTRLStep1CompController', [ '$state', '$timeout', '$http',
		'SVCConfigurator', function($state, $timeout, $http, SVCConfigurator) {

			ctrl = this;

			// Input parameters
			ctrl.name = "";
			ctrl.tax_id = "";

			// True if email to Server was sent successful
			ctrl.emailSentSuccessful = null;
			// Timestamp for identifying response email from Server
			ctrl.timestamp = null;
			
			//Email Content
			ctrl.content = "";

			// Go to next step
			ctrl.goToStep2 = function() {
				ctrl.timestamp = new Date();
				ctrl.sendEmail();
				$timeout(function() {
					$state.go('step2', {
						data1 : ctrl.emailSentSuccessful,
						data2 : ctrl.timestamp,
						//Needed for later identification of customer data
						data3 : ctrl.content
					});
				}, 3000);
			}

			// Send email with query parameters to server
			ctrl.sendEmail = function() {

				// Prepare HTTP Post Data
				var name = "Firmen_Name: " + ctrl.name;
				var tax_id = "Tax_ID: " + ctrl.tax_id;

				name = encodeURI(name);
				tax_id = encodeURI(tax_id);

				ctrl.subject = "REQUEST DATA COMMERCIAL ";
				ctrl.content = name + "\u000A" + tax_id;

				var emailContent = {
					"subject" : ctrl.subject,
					"content" : ctrl.content,
					"timestamp" : ctrl.timestamp,
				}

				// Send HTTP Post Request to Webserver for sending Email
				$http({
					method : 'POST',
					url : SVCConfigurator.getEmailURL(),
					timeout : 5000,
					data : emailContent,
				}).then(function successCallback(response) {
					ctrl.emailSentSuccessful = true;
				}, function errorCallback(response) {
					ctrl.emailSentSuccessful = false;
				});

			}

		} ])
