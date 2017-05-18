Ext.define('uber.view.login.Signup',{
	extend: 'Ext.container.Viewport',
	xtype: 'signup',
	
	requires: [
       'Ext.container.Viewport',
       'uber.view.login.SignupController'
	],
	    	
	controller: 'signup',
	
	layout: 'fit',
	items: [{
		xtype: 'form',
		reference: 'formpanel',
		fieldDefaults: {
			msgTarget: 'none',
	        invalidCls: ''
		},
		items: [{
			xtype: 'fieldset',
			margin: 250,
			title: 'Signup',
			items: [{
				xtype: 'textfield',
				name: 'fullname',
				fieldLabel: 'Full Name'
			},{
				xtype: 'textfield',
				allowBlank: false,
				name: 'username',
				fieldLabel: 'Username'
			},{
				xtype: 'textfield',
				allowBlank: false,
				name: 'email',
				fieldLabel: 'Email'
			},{
				xtype: 'textfield',
				allowBlank: false,
				name: 'password',
				reference: 'password',
				inputType: 'password',
				fieldLabel: 'Password'
			},{
				xtype: 'textfield',
				allowBlank: false,
				name: 'password2',
				reference: 'passwordtwo',
				inputType: 'password',
				fieldLabel: 'Confirm Password',
//				validator: function(value) {
//		            var password = this.previousSibling('[name=password]');
//		            return (value === password.getValue()) ? true : 'Passwords do not match.'
//		        }
			},{
				xtype: 'toolbar',
				items: [{
					xtype: 'button',
					text: 'Reset',
//					handler: function() {
//						debugger;
//						var form = this.up('form');
//						var fields = form.getForm();
//						var fields2 = fields.getFields();
//						fields2.reset;
//	    			}
				},{
					xtype: 'button',
					text: 'Sign Up',
					handler: 'signup'
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