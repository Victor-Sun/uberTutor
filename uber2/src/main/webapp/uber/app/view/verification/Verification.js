Ext.define('uber.view.verification.Verification', {
	extend: 'Ext.container.Viewport',
    xtype: 'verification',

    requires: [
	   'Ext.container.Viewport',
	],

    layout: 'fit',
    items: [{
    	xtype: 'form',
    	items: [{
    		xtype: 'fieldset',
    		margin: 250,
    		items: [{
    			xtype: 'container',
    			margin: 15,
    			layout: {
    				type: 'hbox',
    				align: 'stretch'
    			},
    			items: [{
    				xtype: 'component',
    				flex: 1
    			},{
    				xtype: 'component',
    				html: '<p>An email has been sent to'+ ' <b> ' + 'EMAIL@DOMAIN.COM' + ' </b> ' + 'with a verification code. Please input the code in the field below <br> *<b>note</b>* <b>verification code is 123456</b></p>'
    			},{
    				xtype: 'component',
    				flex: 1
    			}]
    		},{
    			xtype: 'textfield',
    			fieldLabel: 'Verification code',
				name: 'verifycode',
//				value: '123456'
				placeHolder: '000000'
    		},{
    			xtype: 'toolbar',
    			items: [{
    				xtype: 'button',
    				text: 'Verify',
    				handler: function() {
    					var verify = this.up('verification').down('textfield').getValue();
    					if (verify == "123456") {
    			            Ext.Msg.alert('Verification','Verfication complete!', function () {
    			            	this.up('verification').destroy();
    	        				Ext.create('uber.view.main.Main');
    	        				Ext.getCmp('uberTab').setActiveTab('profile');
    			            }, this);
    			        } else {
    			            Ext.Msg.alert('Verfication','Code incorrect, please input verification code', Ext.emptyFn);
    			        }
//        				this.up('verification').destroy();
//        				Ext.create('uber.view.main.Main');
//        				Ext.getCmp('uberTab').setActiveTab(1);
        			}
    			}]
    		}]
    	}]
    }]
});
