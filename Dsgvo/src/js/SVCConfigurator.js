app.factory('SVCConfigurator', [ function() {

	return {
		getEmailURL : function() {
			return 'http://127.0.0.1:8887/sendmail';
		}
	};
} ]);