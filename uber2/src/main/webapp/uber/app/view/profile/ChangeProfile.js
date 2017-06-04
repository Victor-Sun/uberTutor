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
	initComponent: function () {
		var profileForm = Ext.create('uber.view.profile.ChangeProfileForm');
		profileForm.load({
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
	    			text: 'Finish Edit',
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
			},profileForm
//			{
//				xtype: 'form',
//				reference: 'formpanel',
//				flex: 1,
//				margin: 5,
//				layout: {
//					type: 'vbox',
//					align: 'stretchmax'
//			    },
//			    defaults: {
//			    	xtype: 'textfield',
//		    		labelAlign: 'top',
//		    		width: 200
//			    },
//				items: [{
//	                name: 'fullname',
//	                fieldLabel: 'Name&nbsp;*',
//	            },{
//	                name: 'email',
//	                fieldLabel: 'Email&nbsp;*',
//	            },{
//	                name: 'mobile',
//	                fieldLabel: 'Mobile&nbsp;*',
//	            },{
//	            	xtype: 'combobox',
//	            	name: 'school',
//	            	fieldLabel: 'School',
//	            },{
//	            	xtype: 'textarea',
//	            	name: 'bio',
//	            	maxLength: 250,
//	            	fieldLabel: 'Bio',
//				}],
//				dockedItems: [{
//					xtype:'toolbar',
//					dock: 'bottom',
//					items: [{
//						xtype: 'button',
//						text: 'Update',
//						handler: 'update'
//					}]
//					
//				}]
//			}
			]
		}]
		this.callParent(arguments);
	}
});