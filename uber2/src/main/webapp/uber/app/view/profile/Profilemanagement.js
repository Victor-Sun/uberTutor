Ext.define('uber.view.profile.Profilemanagement',{
	extend: 'Ext.container.Viewport',
	xtype: 'profilemanagement',
	
	controller: 'profile',
//  viewModel: 'main',
	cls: 'profile-panel',
	layout: 'center',
	items: [{
		xtype: 'panel',
		border: true,
		cls: 'profile-manager-wrap',
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
    			text: 'edit'
    		}]
    	},{
    		xtype: 'container',
    		layout: 'hbox',
    		items: [{
    			xtype: 'image',
    			border: true,
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
			xtype: 'container',
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
			reference: 'formpanel',
			items: [{
				title: 'Name',
				items: [{
					xtype: 'textfield',
					fieldLabel: 'Name'
				}]
			},{
				title: 'Email',
				items: [{
					xtype: 'textfield',
					fieldLabel: 'Email'
				}]
			},{
				title: 'Mobile',
				items: [{
					xtype: 'textfield',
					fieldLabel: 'Mobile'
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
				}]
			}]
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