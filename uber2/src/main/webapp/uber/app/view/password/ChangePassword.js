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
                    readOnly: true,
                    width: 200
//                    anchor: '100%'
                }
            },
            items: [{
                items: [{
	                name: 'password',
	                fieldLabel: 'Current password',
                }]
            },{
                items: [{
                    name: 'newpassword',
                    fieldLabel: 'New password',
                },{
                    name: 'newpasswordagain',
                    fieldLabel: 'Repeat new password',
                }]
            }],
            dockedItems: [{
            	xtype: 'toolbar',
            	dock: 'bottom',
            	items: [{
            		xtype: 'button',
            		text: 'Save'
            	}]
            }]
    	}]
    }]
});