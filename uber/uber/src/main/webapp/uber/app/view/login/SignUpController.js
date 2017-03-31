Ext.define('uber.view.login.SignUpController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.signup',

    register: function () {
    	this.getView().destroy();
        Ext.Viewport.add(Ext.create('uber.view.verification.Verification'));
    	// Ext.Viewport.add(Ext.create('uber.view.main.Main'));
        // Ext.getCmp("uberTab").setActiveItem(1);
        // Ext.Msg.alert('', 'Please fill out your profile information', Ext.emptyFn);
    }, 	 

    login: function () {
    	this.getView().destroy();
    	Ext.Viewport.add(Ext.create('uber.view.login.Login'));
    }
});