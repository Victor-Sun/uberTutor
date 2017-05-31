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
    	cls: 'uber-panel-inner',
    	layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
    	items: [{
    		xtype: 'container',
            layout: 'hbox',
            items: [{
                margin: 5,
                html: '<h2>Change Password</h2>'
            }]
    	},{
    		xtype: 'form',
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
	                name: 'currentpassword'
                }]
            },{
                items: [{
                    name: 'newpassword',
                    fieldLabel: 'New password',
                    name: 'newpassword'
                },{
                    name: 'newpasswordagain',
                    fieldLabel: 'Repeat new password',
                    name: 'newpassword2',
                	validator: function(value) {
                        var password1 = this.previousSibling('[name=newpassword]');
                        return (value === password1.getValue()) ? true : 'Passwords do not match.'
                    }
                }]
            }],
            dockedItems: [{
            	xtype: 'toolbar',
            	dock: 'bottom',
            	items: [{
            		xtype: 'button',
            		text: 'Save',
            		handler: 'changePassword'
            	}]
            }]
    	}]
    }]
});