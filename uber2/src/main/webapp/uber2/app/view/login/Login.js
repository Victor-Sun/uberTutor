Ext.define('uber.view.login.Login', {
	extend:'Ext.container.Viewport',
	xtype: 'login',
	
	requires: [
       'Ext.container.Viewport',
	],
	
	layout: 'fit',
	items: [{
		xtype: 'form',
		items: [{
			xtype: 'fieldset',
			margin: 250,
			title: 'login',
			items: [{
				xtype: 'textfield',
				required: true,
				name: 'username',
				label: 'Username'
			},{
				xtype: 'textfield',
				required: true,
				name: 'password',
				label: 'Password'
			},{
				xtype: 'toolbar',
				items: [{
					xtype: 'checkboxfield',
					labelWidth: 85,
		            name : 'remember',
		            label: 'Remember'
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
                    handler: function() {
	    				this.up('login').destroy();
	    				Ext.create('uber.view.main.Main');
	    			}
                },{
                	xtype: 'tbspacer'
                },{
                	xtype: 'button',
                	text: 'Sign up',
                	handler: function() {
	    				this.up('login').destroy();
	    				Ext.create('uber.view.login.SignUp');
	    			}
				}]
			}]
		}]
	}]
});