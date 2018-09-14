/* Controller for step 3 - showing customer data with option to send to customer via email */

app.controller('CTRLStep3Controller', [
		'$state',
		'$timeout',
		'$stateParams',
		'$http',
		'SVCConfigurator',
		function($state, $timeout, $stateParams, $http, SVCConfigurator) {

			ctrl = this;

			// Information about email from prior step
			ctrl.emailFetchResult = $stateParams.data1;
			ctrl.content = $stateParams.data2;

			// Value of checkbox - Should email be updated in
			// the database
			ctrl.updateEmail = "NO";
			// email address
			ctrl.email = "";

			// Status
			ctrl.emailUpdateSuccessful = "";
			ctrl.emailSentSuccessful = "";
			
			// Goes to next step with status and end of application
			ctrl.goToStep4 = function() {
				// Update Email Adress if Checkbox is checked
				if (ctrl.updateEmail == "YES") {
					ctrl.updateEmailAddress();
				}
				ctrl.sendEmail();
				$timeout(function() {
					$state.go('step4', {
						data1 : ctrl.emailSentSuccessful,
					});
				}, 3000);
			}
			
			// Update customer email address in database
			ctrl.updateEmailAddress = function() {
				if (ctrl.content.startsWith("Vorname:")) {
					// Customer is private
					ctrl.subject = "UPDATE EMAIL PRIVATE"
				} else {
					// Customer is commercial
					ctrl.subject = "UPDATE EMAIL COMMERCIAL"
				}

				// New Email Content with changed email address
				// attached
				ctrl.content = ctrl.content + "\u000A" + "Email-Adresse: "
						+ ctrl.email;

				ctrl.emailContentUpdate = {
					"subject" : ctrl.subject,
					"content" : ctrl.content,
					"timestamp" : "",
				}

				// HTTP Post request to update customers email address
				$http({
					method : 'POST',
					url : SVCConfigurator.getEmailURL(),
					timeout : 5000,
					data : ctrl.emailContentUpdate,
				}).then(function successCallback(response) {
					ctrl.emailUpdateSuccessful = true;
				}, function errorCallback(response) {
					ctrl.emailUpdateSuccessful = false;
				});
			}
			
			// Send Email to Customer
			ctrl.sendEmail = function() {				
				// Email Content for customer
				ctrl.emailContentCustomer = {
						"subject" : "Information über ihre gespeicherten Daten bei uns (gemäß Artikel 15 Satz 1 DSGVO)",
						"content" : "Sehr geehrter Kunde,\n\nSie haben eine Auskunft darüber angefordert, "
							+ " welche personenbezogenen Daten wir von Ihnen gespeichert haben.\nIm Anhang dieser Mail " 
							+ "erhalten Sie eine Zusammenfassung der gespeicherten Daten. Bei Fragen kommen Sie gerne auf uns zu." 
							+ "\n\nMit freundlichem Gruß,\nMalermeister Mustermann",
						"timestamp" : "",
						"email" : ctrl.email,
				}
				
				// HTTP Post request for sending Email to customer
				$http({
					method : 'POST',
					url : SVCConfigurator.getEmailURL(),
					timeout : 5000,
					data : ctrl.emailContentCustomer,
				}).then(function successCallback(response) {
					ctrl.emailSentSuccessful = true;
				}, function errorCallback(response) {
					ctrl.emailSentSuccessful = false;
				});
			}

		}] );						