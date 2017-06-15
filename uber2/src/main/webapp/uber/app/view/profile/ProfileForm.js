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
//        anchor: '100%'
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
//        var checkboxForm = Ext.create ('Ext.form.Panel',{
//            reference: 'checkboxForm',
//            itemId: 'checkboxForm',
//            items: [{
//                xtype: 'radiogroup',
//                readOnly: false,
//                fieldLabel: 'Is Tutor?',
//                columns: 2,
////                name: 'IS_TUTOR',
////                inputValue: 'Y',
////              hideLabel: true,
////              scope: this,
//                labelAlign: 'left',
//                labelWidth: 75,
//                id: 'isTutor',
//                checkCount:0,
//                items: [
//                	{ boxLabel: 'Yes', name: 'IS_TUTOR', inputValue: 'Y' },
//                    { boxLabel: 'No', name: 'IS_TUTOR', inputValue: 'N' },
//                ],
//            }]  
//        });
//        if (checkboxForm.isValid()) {
//            checkboxForm.load({
//                url:'/uber2/main/profile!display.action',
//                method: 'GET',
//                success: function () {
//                    
//                },
//                failure: function () {
//                    uber.util.Util.showToast("Error loading checkbox value");
//                }
//            })
//        }
        this.items = [{
            xtype: 'fieldcontainer',
            defaults: {
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
	            name: 'FULLNAME',
	            fieldLabel: 'Name',
	            readOnly: true,
	            itemId: 'fullname'
            }]
        },{
            xtype: 'fieldcontainer',
            defaults: {
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
                name: 'EMAIL',
                fieldLabel: 'Email',
                readOnly: true,
                itemId: 'email'
            },{
            	xtype: 'textfield',
	            name: 'MOBILE',
	            fieldLabel: 'Mobile',
	            readOnly: true,
	            itemId: 'mobile'
	        },{
	            xtype: 'combobox',
	            store: school,
	            valueField: 'NAME',
	        	displayField: 'NAME',
	            name: 'NAME',
	            fieldLabel: 'School',
	            allowBlank: false,
	            editable: false,
	            readOnly: true,
	            itemId: 'school'
            }]
        },{
            xtype: 'textarea',
            name: 'BIO',
            maxLength: 1000,
            fieldLabel: 'Bio',
            readOnly: true,
            id: 'bio'
        },{
        	xtype: 'checkbox',
        	fieldLabel: 'Is Tutor',
        	name: 'IS_TUTOR',
        	inputValue: 'Y',
        	uncheckedValue: 'N'
        }];
        this.dockedItems = [{
        	xtype: 'toolbar',
        	dock: 'bottom',
        	items: [{
        		xtype: 'button',
	    		itemId: 'buttonSave',
	    		name: 'saveButton',
	//    		hidden: true,
	    		text: 'Save',
	    		handler: 'save'
        	}]
        }];
        this.callParent();
    },
});