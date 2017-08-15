Ext.define('uber.view.password.ChangePassword',{
	extend: 'Ext.panel.Panel',
	xtype: 'changepassword',
	
	controller: 'changepassword',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
    items: [{
    	xtype: 'panel',
		flex: 1,
//    	cls: 'uber-panel-inner',
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
    	items: [{
    		xtype: 'panel',
//    		border: true,
//    		cls: 'uber-header',
            layout: 'hbox',
            items: [{
                xtype: 'container',
                items: [{
                	margin: 5,
                	html: '<h2>Change Password</h2>'
                }]
            }]
//    	},{
//    		xtype: 'container',
//    		height: 25,
    	},{
    		xtype: 'form',
    		itemId: 'changePasswordForm',
    		cls: 'change-password-form',
            margin: 5,
            reference: 'formpanel',
            layout: {
                type: 'vbox',
                align: 'stretchmax'
            },
            defaults: {
            	xtype: 'container',
            	layout: {
            		type: 'hbox',
            		align: 'stretch'
            	},
                defaults: {
                	xtype: 'textfield',
                	margin: 5,
                	labelAlign: 'top',
                    width: 200
//                    anchor: '100%'
                }
            },
            items: [{
                items: [{
	                name: 'password',
	                fieldLabel: 'Current password',
	                inputType: 'password',
	                name: 'currentPassword',
	                itemId: 'currentPassword',
	                msgTarget: 'side',
	                allowBlank: false
                }]
            },{
                items: [{
                    name: 'newpassword',
                    fieldLabel: 'New password',
                    itemId: 'newPassword',
                    inputType: 'password',
                    name: 'newPassword',
                    allowBlank: false,
                    msgTarget: 'side',
                    minLength: 6,
                    validator: function (value, field) {
						var password1 = Ext.ComponentQuery.query('#currentPassword')[0];
						if (value == password1.getValue()) {
							return 'New password must be different from the current password!'
						} else {
							return true
						}
					}
                }]
            },{
            	items: [{
            		name: 'newpasswordagain',
                    fieldLabel: 'Repeat new password',
                    itemId: 'newpassword2',
                    inputType: 'password',
                    name: 'newPassword2',
                    allowBlank: false,
                    msgTarget: 'side',
                    validator: function (value, field) {
						var password1 = Ext.ComponentQuery.query('#newPassword')[0];
						if (value == password1.getValue()) {
							return true
						} else {
							return 'Passwords do not match'
						}
					}
            	}]
            }],
            dockedItems: [{
            	xtype: 'toolbar',
            	dock: 'bottom',
            	items: [{
            		xtype: 'button',
            		bindForm: true,
            		text: 'Save',
            		handler: 'changePassword'
            	},{
            		xtype: 'component',
            		margin: 5,
            		itemId: 'passwordChangeResult',
            		html: ''
            	}]
            }]
    	}]
    }]
});