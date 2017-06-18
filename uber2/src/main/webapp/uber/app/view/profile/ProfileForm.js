Ext.define('uber.view.profile.ProfileForm',{
    extend: 'Ext.form.Panel',
    xtype: 'profileform',
    
    margin: 5,
    cls: 'profile-form',
    controller: 'profile',
    reference: 'profileForm',
    itemId: 'profileForm',
    scrollable: 'y',
    layout: {
        type: 'vbox',
        align: 'stretchmax'
    },
    defaults: {
        labelAlign: 'top',
        width: 200
    },
    initComponent(){
    	var school = Ext.create('Ext.data.Store',{
    		fields: [ 'ID', 'NAME' ],
    		proxy: {
    	         type: 'ajax',
    	         url: '/uber2/main/profile!displaySchool.action',
    	         reader: {
    	             type: 'json',
    	             rootProperty: 'data'
    	         }
    	    },
    	});
        this.items = [{
            xtype: 'fieldcontainer',
            defaults: {
            	disabled: true,
            	labelAlign: 'top',
            	flex: 1,
            	margin: '0 5 0 0'
            },
            layout: {
            	type: 'hbox',
            	align: 'stretch'
            },
            items: [{
            	xtype: 'textfield',
	            name: 'fullname',
	            fieldLabel: 'Name',
	            readOnly: true,
	            itemId: 'fullname'
            }]
        },{
            xtype: 'fieldcontainer',
            defaults: {
            	disabled: true,
            	labelAlign: 'top',
            	flex: 1,
            	margin: '0 5 0 0'
            },
            layout: {
            	type: 'hbox',
            	align: 'stretch'
            },
            items: [{
            	xtype: 'textfield',
                name: 'email',
                fieldLabel: 'Email',
                readOnly: true,
                itemId: 'email'
            },{
            	xtype: 'textfield',
	            name: 'mobile',
	            fieldLabel: 'Mobile',
	            readOnly: true,
	            itemId: 'mobile'
	        },{
	            xtype: 'combobox',
	            store: school,
	            valueField: 'name',
	        	displayField: 'name',
	            name: 'name',
	            fieldLabel: 'School',
	            allowBlank: false,
	            editable: false,
	            readOnly: true,
	            itemId: 'school'
            }]
        },{
            xtype: 'fieldcontainer',
            layout: {
            	type: 'hbox',
            	align: 'stretch'
            },
            defaults: {
            	disabled: true,
            	labelAlign: 'top',
            	flex: 1,
            	margin: '0 5 0 0'
            },
            items: [{
            	xtype: 'textarea',
                name: 'bio',
                maxLength: 1000,
                fieldLabel: 'Bio',
                fieldCls: true,
                id: 'bio'
            }]
        },{
        	xtype: 'fieldcontainer',
        	layout: {
            	type: 'hbox',
            	align: 'stretch'
            },
        	items: [{
        		xtype: 'checkbox',
	        	hidden: true,
	        	name: 'isTutor',
	        	fieldLabel: 'Is Tutor',
	        	itemId: 'isTutor',
	        	inputValue: 'Y',
	        	uncheckedValue: 'N',
        	},{
        		xtype: 'displayfield',
            	fieldLabel: 'User status',
//            	name: 'IS_TUTOR',
            	itemId: 'isTutorDisplay',
            	renderer: function (oldValue, value) {
            		this.getValue();
            		if (oldValue == true) {
            			return newValue = "You are a tutor"
            		} else if (oldValue == false) {
            			return newValue = "You are a student"
            		}
            	}
        	}]
        },{
        	xtype: 'fieldcontainer',
        	height: 60,
        	margin: 5,
        	items: [{
        		xtype: 'button',
	    		hidden: true,
	    		itemId: 'saveButton',
	    		name: 'saveButton',
	    		text: 'Save',
	    		handler: 'save'
        	}]
        }];
        this.dockedItems = [{
        	
        }];
        this.callParent();
    },
});