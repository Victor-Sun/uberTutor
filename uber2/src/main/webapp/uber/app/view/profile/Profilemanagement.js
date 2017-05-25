Ext.define('uber.view.profile.Profilemanagement',{
	extend: 'Ext.panel.Panel',
	xtype: 'profilemanagement',
	
	requires: [
       // 'uber.view.profile.ProfileController'
    ],
	
	controller: 'profile',
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'profile-container',
	items: [{
		xtype: 'panel',
		flex: 1,
		cls: 'profile-panel',
		layout: {
    		type: 'vbox',
    		align: 'stretch'
    	},
		items: [{
			xtype: 'container',
    		layout: 'hbox',
    		items: [{
    			margin: 5,
    			html: '<h2>Personal Information</h2>'
    		},{
    			xtype: 'button',
    			margin: 5,
    			text: 'Save',
    			handler: 'backprofile'
    		}]
    	},{
    		xtype: 'container',
    		layout: 'hbox',
    		items: [{
    			xtype: 'image',
    			width: 80,
    			height: 80,
    			style: {
    				borderRadius: '5px'
    			}
    		},{
    			xtype: 'component',
    			html: '<h3>User Avatar</h3>'
    		}]
		},{
			xtype: 'panel',
			padding: 10,
			layout: {
		        // layout-specific configs go here
		        type: 'accordion',
		        animate: true,
//		        multi: true,
		    },
		    defaults: {
		    	xtype: 'panel',
		    	layout: 'vbox',
		    	defaults: {
		    		xtype: 'textfield',
		    		labelAlign: 'top'
		    	}
		    },
			items: [{
				title: 'Name',
				items: [{
					xtype: 'textfield',
					fieldLabel: 'Name'
				}],
				dockedItems:[{
					xtype: 'toolbar',
					dock: 'bottom',
					items: [{
						xtype: 'button',
						text: 'Save'
					}]
				}]
			},{
				title: 'Email',
				items: [{
					xtype: 'textfield',
					fieldLabel: 'Email'
				}],
				dockedItems:[{
					xtype: 'toolbar',
					dock: 'bottom',
					items: [{
						xtype: 'button',
						text: 'Save'
					}]
				}]
			},{
				title: 'Mobile',
				items: [{
					xtype: 'textfield',
					fieldLabel: 'Mobile'
				}],
				dockedItems:[{
					xtype: 'toolbar',
					dock: 'bottom',
					items: [{
						xtype: 'button',
						text: 'Save'
					}]
				}]
			},{
				title: 'Password',
				items: [{
					xtype: 'textfield',
					fieldLabel: 'Current Password'
				},{
					xtype: 'textfield',
					fieldLabel: 'New Password'
				},{
					xtype: 'textfield',
					fieldLabel: 'Report New Password'
				}],
				dockedItems:[{
					xtype: 'toolbar',
					dock: 'bottom',
					items: [{
						xtype: 'button',
						text: 'Save'
					}]
				}]
			}],
			
		}]
	}]
});
//xtype: 'textfield',
//name: 'fullname',
//fieldLabel: 'Name'
//},{
//xtype: 'textfield',
//name: 'email',
//fieldLabel: 'Email'
//},{
//xtype: 'textfield',
//name: 'modile',
//fieldLabel: 'Mobile'