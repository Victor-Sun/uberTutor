Ext.define('uber.view.profile.Profile', {
    extend: 'Ext.panel.Panel',
    xtype: 'profile',

    requires: [
       // 'uber.view.profile.ProfileController'
    ],
    
    controller: 'profile',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	initComponent: function () {
//		debugger;
		var profileInfoForm = Ext.create('uber.view.profile.ProfileForm');
//		var profileTutorForm = Ext.create('uber.view.profile.ProfileTutorForm');
//		var profileMainForm = this.lookupReference('profileMainForm');
		
		profileInfoForm.load({
			url: '/uber2/main/profile!display.action',
			params: {
				fullname: 'fullname'
			},
			reader: {
				type: 'json',
				rootProperty: 'data'
			}
		});
		this.items = [{
			xtype: 'panel',
//			reference: 'profileMainForm',
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
	                text: 'edit',
	                handler: 'profilemanage'
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
	    	},profileInfoForm
		]
		}];
		this.callParent(arguments);
	}
});
