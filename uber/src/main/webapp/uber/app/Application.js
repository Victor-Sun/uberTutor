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
        'uber.view.login.Signup',
        'uber.view.login.Login',
        'uber.view.main.Main'
    ],

    launch: function () {
        // TODO - Launch the application
        var me = this;
        Ext.Viewport.add(Ext.create('uber.view.login.Signup'));
        var admin = Ext.create('uber.model.User',{
        	username: 'admin',
        	password: '123456'
        });
//        Ext.Viewport.add(Ext.create('uber.view.main.Main'));
//        Ext.define('uber.model.User',{
//        	extend: 'Ext.data.Model',
////        	config: {
////        	},
//        	fields: [
////			'username', 'password'
//				{ name: 'username', type: 'string' },
//				{ name: 'password'}
//			],
////			validations:
////			[
////			    {type: 'presence',field: 'username',  message: 'please input valid username'},
////			    {type: 'presence', field: 'password', message: 'please input valid password'}
////			],
//			validators: {
//				username: [
//		           { type: 'presence', message: 'please input valid username' }
//				],
//				password: [
//		           { type: 'presence', message: 'please input valid password' }
//	           ]
//			}
//        })
        
    }
    // ,

    // onAppUpdate: function () {
    //     Ext.Msg.confirm('Application Update', 'This application has an update, reload?',
    //         function (choice) {
    //             if (choice === 'yes') {
    //                 window.location.reload();
    //             }
    //         }
    //     );
    // }
});
