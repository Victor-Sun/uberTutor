/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('uber.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.main',

    onItemSelected: function (sender, record) {
        Ext.Msg.confirm('Confirm', 'Are you sure?', 'onConfirm', this);
    },

    onConfirm: function (choice) {
        if (choice === 'yes') {
            //
        }
    },
    
    registration: function () {
    	var me = this;
		var mainCard = me.lookupReference('mainCardPanel')
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('tutorregistration');
    },
    
    mainpage: function () {
    	var me = this;
		var mainCard = me.lookupReference('mainCardPanel')
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('mainpage');
    },
    
    search: function() {
		var me = this;
		var mainCard = me.lookupReference('mainCardPanel')
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('search');
    },
    
    changepassword: function() {
    	var me = this;
		var mainCard = me.lookupReference('mainCardPanel')
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('changepassword');
    },
    
    profile: function() {
		var me = this;
		var mainCard = me.lookupReference('mainCardPanel')
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('profile');
    },
    
    sessions: function () {
    	var me = this;
		var mainCard = me.lookupReference('mainCardPanel')
        var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('sessions');
    },
    
    logout: function() {
		var me = this;
		this.getView().destroy();
		Ext.create('uber.view.login.Login');
    }
});
