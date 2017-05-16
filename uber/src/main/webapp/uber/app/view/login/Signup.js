Ext.define('uber.view.login.Signup', {
	extend: 'Ext.Panel',
	xtype: 'signup',
	controller: 'signup',

	requires: [
		'uber.view.login.SignupController'
	],
	
	items: [{
		xtype: 'formpanel',
		reference: 'formpanel',
		margin: 10,
		items: [{
			xtype: 'fieldset',
			margin: 30,
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
					html: '<p><b>Registration</b></p>'
				},{
					xtype: 'container',
					flex: 1
				}]
			},{
				xtype: 'textfield',
				name: 'fullName',
				label: 'Full Name'
			},{
				xtype: 'textfield',
				name: 'username',
				label: 'Username'
			},{
				xtype: 'textfield',
				name: 'email',
				label: 'Email'
			},{
				xtype: 'passwordfield',
				name: 'password',
				label: 'Password'
			},{
                xtype: 'toolbar',
                items: [{
                    xtype: 'button',
                    text: 'Submit',
                    handler: 'register' 
        //             function () {
        //             	var me = this;
				    // 	this.getView().destroy();
				    // 	Ext.Viewport.add(Ext.create('uber.view.main.Main'));
				    // }
                },{
                    xtype: 'button',
                    text: 'Reset'
                },{
                	xtype: 'spacer'
                },{
                	xtype: 'button',
                	text: 'Existing User?',
                	handler: 'login'
                }]
            }]
		}]
	}]
});