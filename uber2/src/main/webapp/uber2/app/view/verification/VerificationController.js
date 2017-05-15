Ext.define('uber.view.verification.VerificationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.verification',


    submit: function () {
        // debugger;
        var key = this.getView().down('textfield').getValue();
        if (key == "123456") {
            Ext.Msg.alert('Verification','Verfication complete!', function () {
                this.getView().destroy();
                Ext.Viewport.add(Ext.create('uber.view.main.Main'));
                Ext.getCmp("uberTab").setActiveItem(1);
                Ext.Msg.alert('Verification', 'Please fill out your profile information', Ext.emptyFn);
            }, this);
        } else {
            Ext.Msg.alert('Verfication','Code incorrect please input verification code', Ext.emptyFn);
        }
    	// this.getView().destroy();
     //    Ext.Viewport.add(Ext.create('uber.view.main.Main'));

    	// Ext.Viewport.add(Ext.create('uber.view.main.Main'));
     //    Ext.getCmp("uberTab").setActiveItem(1);
     //    Ext.Msg.alert('', 'Please fill out your profile information', Ext.emptyFn);
    }, 	 

    login: function () {
    	this.getView().destroy();
    	Ext.Viewport.add(Ext.create('uber.view.login.Login'));
    }
});