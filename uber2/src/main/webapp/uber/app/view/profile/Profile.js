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
		var profileForm = Ext.create('uber.view.profile.ProfileForm');
		profileForm.load();
//		profileForm.load({
//			url: '/uber2/main/profile!display.action',
//			params: {
//				fullname: 'fullname'
//			},
//			reader: {
//				type: 'json',
//				rootProperty: 'data'
//			}
//		});
		this.items = [{
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
	    	},profileForm,
//	    	{
//	    		xtype: 'form',
//	    		reference: 'formpanel',
//	    	    layout: {
//	    	        type: 'vbox',
//	    	        align: 'stretchmax'
//	    	    },
//	    	    defaults: {
//	    	        labelAlign: 'top',
//	    	        readOnly: true,
//	    	        width: 200
////	    	        anchor: '100%'
//	    	    },
//	    	    items: [{
//	    	    	xtype: 'textfield',
//    	            name: 'fullname',
//    	            fieldLabel: 'Name',
//    	            itemId: 'fullname'
//    	        },{
//    	            xtype: 'textfield',
//    	            name: 'email',
//    	            fieldLabel: 'Email',
//    	            itemId: 'email'
//    	        },{
//    	            xtype: 'textfield',
//    	            name: 'mobile',
//    	            fieldLabel: 'Mobile',
//    	            itemId: 'mobile'
//    	        },{
//    	        	xtype: 'textfield',
//    	            name: 'school',
//    	            fieldLabel: 'School',
//    	            itemId: 'school'
//    	        },{
//    	        	xtype: 'textarea',
//    	        	name: 'bio',
//    	        	fieldLabel: 'Bio',
//    	        	itemId: 'bio'
//	    	    	
//	    	    }],
//	    	    dockedItems: [{
//    	        	xtype: 'toolbar',
//    	        	dock: 'bottom',
//    	        	items: [{
//    	        		xtype: 'button',
//    	        		text: 'load profile',
//    	        		handler: 'getProfile'
//    	        	}]
//    	        }]
//	    		xtype: 'profileform',
//	    	}
		]
		}];
		this.callParent(arguments);
	}
	
});
