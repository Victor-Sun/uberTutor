Ext.define('uber.view.profile.Profile', {
    extend: 'Ext.tab.Panel',
    xtype: 'profile',
    itemId: 'profileTab',
    requires: [
        'uber.view.profile.ProfileController',
        'uber.view.profile.ProfileForm'
    ],
    
    controller: 'profile',
    layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel profile',
	tabPosition: 'left',
	tabRotation: 0,
	initComponent: function () {
		var fullname = Ext.ComponentQuery.query('#userNameItemId')[0].getText();
		var profileInfoForm = Ext.create('uber.view.profile.ProfileForm',{
			flex: 1,
			layout: {
            	type: 'vbox',
            	align: 'stretch'
            },
            
		});
		
		
		var loading = function () {
//			Ext.getBody().mask('Loading...Please Wait');
			profileInfoForm.load({
				url: '/uber2/main/profile!display.action',
				params: {
					fullname: this.fullname
				},
				reader: {
					type: 'json',
					rootProperty: 'data'
				},
				success: function () {
					var checkBox = Ext.ComponentQuery.query('#isTutor')[0];
		    		var checkBoxValue = checkBox.getValue();
		    		var display = Ext.ComponentQuery.query('#isTutorDisplay')[0];
		    		display.setValue(checkBoxValue);
		    		var profileTab = Ext.ComponentQuery.query('tab[title=Subjects]')[0];
//		    		var subjectsTab = profileTab.getTabBar();
		    		if (checkBoxValue == true ) {
		    			profileTab.show();
		    		} else if (checkBoxValue == false ){
		    			profileTab.hide();
		    		}; 
				}
			});
		};
		loading();
		this.items = [{
			xtype: 'panel',
			title: 'Profile',
			itemId: 'profile',
			flex: 1,
//	    	cls: 'uber-panel-inner',
	    	layout: {
	    		type: 'hbox',
	    		align: 'stretch'
	    	},
	    	items: [{
	    		xtype: 'panel',
	    		flex: 3,
	    		layout: {
	    			type: 'vbox',
	    			align: 'stretch'
	    		},
	    		items: [{
	    			items: [{
	    	    		xtype: 'panel',
	    	    		// border: true,
	    	    		// cls: 'uber-header',
	    	    		layout: 'hbox',
	    	            items: [{
	    	                xtype: 'container',
	    	                cls: 'btn-container',
	    	                layout: 'hbox',
	    	                items: [{
	    	                	margin: 5,
	    		                html: '<h2>Personal Information</h2>'
	    		            },{
	    		            	xtype: 'button',
	    		            	iconCls: 'x-fa fa-pencil',
    			                margin: 15,
//    			                text: 'edit',
    			                tickCount: 0,
    			                handler: 'toggleEdit'
	    	                }]
	    	            },{
	    	            	xtype: 'container',
	    	            	flex: 1
	    	            }]
	    	        },{
	    	            xtype: 'container',
	    	            layout: 'hbox',
	    	            items: [{
	    	            	xtype: 'container',
	    	            	flex: 1
	    	            },{
	    	            	xtype: 'container',
	    		            layout: 'vbox',
	    		            style: {
	    		            },
	    		            items: [{
	    		            	xtype: 'component',
	    		                html: '<h3>User Avatar</h3>'
	    		            },{
	    		                xtype: 'container',
	    		                margin: 5,
	    		                cls: 'shadow image-container',
	    		                items: [{
	    		                	xtype: 'image',
	    			                width: 80,
	    			                height: 80,
	    		                }]
	    		            }]
	    	            },{
	    	            	xtype: 'container',
	    	            	flex: 1
	    	            }]
	    	    	},profileInfoForm]
	    		}]
	    	},{
	    		xtype: 'panel',
	    		flex: 1
	    	}]
		},{
			title: 'Change Password',
			itemId: 'changePassword',
			xtype: 'changepassword'
		},{
			title: 'Subjects',
			itemId: 'subjects',
			xtype: 'tutorRegistration',
			hidden: true
		}];
		

		
		this.callParent(arguments);
	}
});
