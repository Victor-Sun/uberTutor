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
	                allowBlank: false
                }]
            },{
                items: [{
                    name: 'newpassword',
                    fieldLabel: 'New password',
                    itemId: 'newpassword',
                    inputType: 'password',
                    name: 'newPassword',
                    allowBlank: false
                }]
            },{
            	items: [{
            		name: 'newpasswordagain',
                    fieldLabel: 'Repeat new password',
                    itemId: 'newpassword2',
                    inputType: 'password',
                    name: 'newPassword2',
                    allowBlank: false
//                	validator: function(value) {
//                        var password1 = Ext.ComponentQuery.query('#newpassword')[0];
////                        return (value === password1.getValue()) ? true : 'Passwords do not match.'
//                        if (value != password1.getValue) {
//                        	 return "Passwords do not match!";
//                        } 
//                    }
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
            	}]
            }]
    	}]
    }]
});