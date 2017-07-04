Ext.define('uber.view.verification.VerificationController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.verification',
    
    verify: function () {
    	this.up('verification').destroy();
		Ext.create('uber.view.main.Main');
    }
    
});