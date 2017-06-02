Ext.define('uber.view.profile.ChangeProfile',{
	extend: 'Ext.panel.Panel',
	xtype: 'changeprofile',
	
	requires: [
       // 'uber.view.profile.ProfileController'
    ],
	
	controller: 'profile',
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
            style: {
            },
            items: [{
                xtype: 'container',
                margin: 5,
                cls: 'shadow image-container',
                items: [{
                	xtype: 'image',
	                width: 80,
	                height: 80,
                }]
            },{
                xtype: 'component',
                html: '<h3>User Avatar</h3>'
            }]
		},{
			xtype: 'form',
			reference: 'formpanel',
			flex: 1,
			margin: 5,
			layout: {
				type: 'vbox',
				align: 'stretchmax'
		    },
		    defaults: {
		    	xtype: 'textfield',
	    		labelAlign: 'top',
	    		width: 200
		    },
			items: [{
                name: 'fullname',
                fieldLabel: 'Name',
            },{
                name: 'email',
                fieldLabel: 'Email',
            },{
                name: 'modile',
                fieldLabel: 'Mobile',
            },{
            	name: 'school',
            	fieldLabel: 'School',
            },{
            	name: 'bio',
            	fieldLabel: 'Bio',
			}]
		}]
	}]
});