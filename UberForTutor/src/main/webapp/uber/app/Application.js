/**
 * The main application class. An instance of this class is created by app.js when it
 * calls Ext.application(). This is the ideal place to handle application launch and
 * initialization details.
 */
Ext.define('uber.Application', {
    extend: 'Ext.app.Application',
    
    name: 'uber',

    stores: [
        // TODO: add global / shared stores here
    ],
    
    requires: [
          'uber.view.homepage.Homepage',
          'uber.util.Util'
    ],
    launch: function () {
        // TODO - Launch the application
//    	Ext.create('uber.view.main.Main');
//    	Ext.create('uber.view.login.Loginpage');
    	Ext.create('uber.view.homepage.Homepage');
//    	Ext.create('uber.view.main.MainPage');
    }
//,
//    onAppUpdate: function () {
//        Ext.Msg.confirm('Application Update', 'This application has an update, reload?',
//            function (choice) {
//                if (choice === 'yes') {
//                    window.location.reload();
//                }
//            }
//        );
//    }
});
