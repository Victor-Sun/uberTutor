Ext.define('uber.view.login.Login',{
	extend: 'Ext.container.Viewport',
	xtype: 'login',
	
	requires: [ 
       'uber.view.login.LoginController', 
       'uber.view.common.Radio', 
       'uber.view.main.Main',
//       'uber.model.User',
       ],
	reference: 'login',
	itemId: 'login',
	style: {
		'background-color': '#f4f4f4',
	},
	layout: 'fit',
	controller: 'login',
	abValue: '',
	items: [{
		xtype: 'container',
		layout: 'center',
		items: [{
			xtype: 'panel',
			cls: 'panel-wrap',
			width: 500,
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [{
				xtype: 'component',
				cls: 'title-component',
				html: '<a><h1 style="padding: 15px 30px 0;">UberTutor</h1></a>'
			},{
				xtype: 'panel',
				flex: 1,
				layout: 'fit',
				items: [{
					xtype: 'form',
					itemId: 'loginSignUpForm',
//					modelValidation: true,
					padding: 30,
					items: [{
						//Login/SignUp title
						xtype: 'component',
						itemId: 'title',
						cls: 'inner-title-component',
						html: '<h2>Sign Up</h2>',
					},{
						xtype: 'component',
						html: '<hr>'
					},{
						//radio fields
						xtype: 'pageradio',
					},{
						xtype: 'container',
						layout: {
							type: 'vbox',
							align: 'stretch'
						},
						defaults: {
							anchor: '100%',
							xtype: 'textfield',
							submitEmptyText: false,
						},
						items: [{
							emptyText: 'Username',
							name: 'username',
							reference: 'username',
							msgTarget: 'side',
							allowBlank: false
//							hidden: true,
						},{
							emptyText: 'Email',
							itemId: 'email',
							name: 'email',
							vtype: 'email',
							msgTarget: 'side',
							allowBlank: false
						},{
							emptyText: 'Password',
							name: 'password',
							inputType: 'password',
							vtype: 'password',
							msgTarget: 'side',
							allowBlank: false,
							minLength: 6
						},{
							emptyText: 'Confirm Password',
							name: 'password2',
							inputType: 'password',
							msgTarget: 'side',
							allowBlank: false,
							validator: function (value) {
								var password1 = this.previousSibling('[name=password]');
								if (value != password1.getValue()) {
									return 'Passwords do not match'
								}
							}
						}]
					},{
						xtype: 'toolbar',
						items: [{
							xtype: 'button',
							text: 'Sign Up',
							handler: 'loginPageStatus'
						},'->',{
							xtype: 'component',
//							hidden: true,
							html: '<a>Forgot username / password?</a>'
						}]
					}]
				}]
			}]
		}]
	}]
});