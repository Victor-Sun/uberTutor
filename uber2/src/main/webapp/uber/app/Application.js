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
    
    launch: function () {
        // TODO - Launch the application
//    	Ext.create('uber.view.main.Main');
//    	Ext.create('uber.view.login.Login');
//    	Ext.create('uber.view.login.Loginpage');
    	Ext.create('uber.view.homepage.Homepage');
//    	Ext.widget('login');
//    	Ext.Viewport.add(Ext.create('uber.view.login.Login'));
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
