Ext.define('uber.view.login.Login',{
	extend: 'Ext.container.Viewport',
	xtype: 'login',
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
//						reference: 'pageradio',
//				        // Arrange radio buttons into two columns, distributed vertically
//				        columns: 2,
//				        items: [
//				            { boxLabel: 'New User', name: 'ab', inputValue: '1', checked: true },
//				            { boxLabel: 'Existing User', name: 'ab', inputValue: '2'}
//				        ],
//				        listeners: {
//				            change: function (field, newValue, oldValue) {
//				            	var text = this.up('form').down('component');
//				            	var button = this.up('form').down('button');
//				            	var field = this.up('form').getForm().findField('name');
//				            	//var value = Ext.ComponentQuery.query('radiofield[name=cstgrp]');
//				                //console.log(newValue['cstgrp']);
//
//				                switch (parseInt(newValue['ab'])) {
//				                    case 1:
////				                    	debugger;
//				                    	text.update('<h2>Sign Up</h2>');
//				                    	button.setText('Sign Up');
////				                    	field.setHidden('false');
//				                    	field.show();
//				                        break;
//				                    case 2:
//				                    	text.update('<h2>Sign In</h2>');
//				                    	button.setText('Sign In');
//				                    	field.setHidden('true');
//				                        break;
//				                }
//				            }
//				        }
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