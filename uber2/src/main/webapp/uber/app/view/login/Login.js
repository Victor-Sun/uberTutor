Ext.define('uber.view.login.Login', {
	extend:'Ext.container.Viewport',
	xtype: 'login',
	
	requires: [
       'Ext.container.Viewport',
	],
	
	controller: 'login',
	
	layout: 'fit',
	items: [{
		xtype: 'form',
		reference: 'formpanel',
		items: [{
			xtype: 'fieldset',
			margin: 250,
			title: 'login',
			items: [{
				xtype: 'textfield',
				required: true,
				name: 'username',
				fieldLabel: 'Username'
			},{
				xtype: 'textfield',
				inputType: 'password',
				required: true,
				name: 'password',
				fieldLabel: 'Password'
			},{
				xtype: 'toolbar',
				items: [{
					xtype: 'checkboxfield',
					labelWidth: 85,
		            name : 'remember',
		            fieldLabel: 'Remember'
				},{
					xtype: 'tbspacer'
				},{
					xtype: 'component',
					html: '<a>Forgot Password?</a>'
				}]
			},{
                xtype: 'toolbar',
                items: [{
                	xtype: 'button',
                    text: 'Login',
                    handler: 'login',
                },{
                	xtype: 'tbspacer'
                },{
                	xtype: 'button',
                	text: 'Sign up',
                	handler: function() {
	    				this.up('login').destroy();
	    				Ext.create('uber.view.login.Signup');
	    			}
				}]
			}]
		}]
	}]
});