/* In this file the urls for the HTTP Post interface for connection to the Webserver are defined. */

app.factory('SVCConfigurator', [ function() {

	return {
		getEmailURL : function() {
			return 'http://127.0.0.1:8887/sendmail';
		},
		getFetchMailURL : function() {
			return 'http://127.0.0.1:8887/fetchmail';
		},
		getLoginCheckURL : function() {
			return 'http://127.0.0.1:8887/checklogin';
		}

	};
} ]);