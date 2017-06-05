Ext.define('uber.view.login.Login',{
	extend: 'Ext.container.Viewport',
	xtype: 'login',
	
	requires: [ 
       'uber.view.login.LoginController', 
       'uber.view.common.Radio', 
       'uber.view.main.Main',
       'uber.model.User',
       ],
	
	refernce: 'login',
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
					reference: 'formpanel',
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
							submitEmptyText: false
						},
						items: [{
							emptyText: 'Username',
							name: 'username',
							reference: 'username',
//							hidden: true,
						},{
							emptyText: 'Email',
							name: 'email'
						},{
							emptyText: 'Password',
							name: 'password',
							inputType: 'password'
						},{
							emptyText: 'Confirm Password',
							name: 'password2',
							inputType: 'password'
						}]
					},{
						xtype: 'toolbar',
						items: [{
							xtype: 'button',
							text: 'Sign Up',
							handler: 'loginPageStatus'
//							handler: function(){
//								this.up('login').destroy();
//								Ext.create('uber.view.main.Main');
//							}
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