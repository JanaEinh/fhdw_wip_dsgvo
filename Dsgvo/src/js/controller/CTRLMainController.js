app.controller('CTRLMainController',
		[
				'$http',
				'SVCConfigurator',
				'$state',
				'$timeout',
				'$scope',
				'$stateParams',
				function($http, SVCConfigurator, $state, $timeout, $scope,
						$stateParams) {
					ctrl = this;
					ctrl.name = "";
					ctrl.forename = "";
					ctrl.birthdate = "";
					ctrl.emailSentSuccessful = null;

					preventEnterFromSubmit();

					ctrl.goToStep2 = function() {
						ctrl.sendEmail();
						$timeout(function() {
							$state.go('step2', {
								data1 : ctrl.emailSentSuccessful
							});
						}, 10000);
					}

					ctrl.sendEmail = function() {

						var subject1 = "Personenbezogene Daten f" + "\u00fc"
								+ "r " + ctrl.name + ", " + ctrl.forename;

						var name = "Name: " + ctrl.name;
						var forename = "Vorname: " + ctrl.forename;
						var birthdate = "Geburtsdatum: " + ctrl.birthdate;

						name = encodeURI(name);
						forename = encodeURI(forename);
						birthdate = encodeURI(birthdate);

						ctrl.subject = encodeURI(subject1);
						ctrl.content = "Daten angefordert für Kunde: "
								+ "\u000A" + name + "\u000A" + forename
								+ "\u000A" + birthdate;

						var emailContent = {
							"subject" : ctrl.subject,
							"content" : ctrl.content,
						}

						$http({
							method : 'POST',
							url : SVCConfigurator.getEmailURL(),
							timeout : 10000,
							data : emailContent,
						}).then(function successCallback(response) {
							ctrl.emailSentSuccessful = true;
						}, function errorCallback(response) {
							ctrl.emailSentSuccessful = false;
						});

					}
				} ]);
