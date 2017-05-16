Ext.define('uber.view.login.SignupController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.signup',

    register: function () {
    	var form = this.lookupReference('formpanel');
    	var signup = form.getValues();
    	Ext.Msg.alert('test','form submitted', function () {
            this.getView().destroy();
            Ext.Viewport.add(Ext.create('uber.view.verification.Verification'));
        }, this);
    	// Ext.Viewport.add(Ext.create('uber.view.main.Main'));
        // Ext.getCmp("uberTab").setActiveItem(1);
        // Ext.Msg.alert('', 'Please fill out your profile information', Ext.emptyFn);
    }, 	 

    login: function () {
    	this.getView().destroy();
    	Ext.Viewport.add(Ext.create('uber.view.login.Login'));
    }
});