Ext.define('uber.view.login.Login', {
	extend: 'Ext.Panel',
	xtype: 'login',
	controller: 'login',

	requires: [
		'uber.view.login.LoginController'
	],
	
	items: [{
		xtype: 'formpanel',
		reference: 'formpanel',
		standardSubmit: true,
		margin: 50,
		items: [{
			xtype: 'fieldset',
			margin: 0,
			padding: 10,
			items: [{
				xtype: 'container',
				margin: 2,
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				items: [{
					xtype: 'container',
					flex: 1
				},{
					xtype: 'component',
					html: '<p><b>Login</b></p>'
				},{
					xtype: 'container',
					flex: 1
				}]
			},{
				xtype: 'textfield',
				required: true,
				name: 'username',
				label: 'Username'
			},{
				xtype: 'passwordfield',
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
					xtype: 'spacer'
				},{
					xtype: 'component',
					html: '<a>Forgot Password?</a>'
				}]
			},{
                xtype: 'toolbar',
                items: [{
                	xtype: 'button',
                    text: 'Login',
                    handler: 'login'
                },{
                	xtype: 'spacer'
                },{
                	xtype: 'button',
                	text: 'Sign up',
                	handler: 'signup'
        //         	handler: function () {
        //         		var me = this;
				    // 	Ext.getView().destroy();
				    // 	Ext.Viewport.add(Ext.create('uber.view.login.SignUp'));
				    // }
                }]
            }]
		}]
    }]
});