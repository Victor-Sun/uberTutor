Ext.define('uber.view.login.SignUp',{
	extend: 'Ext.container.Viewport',
	xtype: 'signup',
	
	requires: [
       'Ext.container.Viewport',
	],
	    	
	layout: 'fit',
	items: [{
		xtype: 'form',
		items: [{
			xtype: 'fieldset',
			margin: 250,
			title: 'signup',
			items: [{
				xtype: 'textfield',
				name: 'firstname',
				fieldLabel: 'First name'
			},{
				xtype: 'textfield',
				name: 'lastname',
				fieldLabel: 'Last name'
			},{
				xtype: 'textfield',
				name: 'username',
				fieldLabel: 'Username'
			},{
				xtype: 'textfield',
				name: 'email',
				fieldLabel: 'Email'
			},{
				xtype: 'textfield',
				name: 'password',
				fieldLabel: 'Password'
			},{
				xtype: 'toolbar',
				items: [{
					xtype: 'button',
					text: 'Sign Up',
					handler: function() {
	    				this.up('signup').destroy();
	    				Ext.create('uber.view.verification.Verification');
	    			}
				},
//				'->',
				{
					xtype: 'button',
					text: 'Login',
					handler: function() {
	    				this.up('signup').destroy();
	    				Ext.create('uber.view.login.Login');
	    			}
				}]
			}]
		}]
	}]
});