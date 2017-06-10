Ext.define('uber.view.profile.ChangeProfileForm',{
	extend: 'Ext.form.Panel',
	xtype: 'changeProfileForm',
	
	flex: 1,
	margin: 5,
	controller: 'profile',
	reference: 'profileForm',
	layout: {
		type: 'vbox',
		align: 'stretchmax'
    },
    defaults: {
    	xtype: 'textfield',
		labelAlign: 'top',
		width: 200
    },
    initComponent: function () {
//    	debugger;
//    	var tutorCheck = 
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
    		name: 'FULLNAME',
            fieldLabel: 'Name',
            allowBlank: false
        },{
            name: 'EMAIL',
            fieldLabel: 'Email&nbsp;*',
            allowBlank: false
        },{
            name: 'MOBILE',
            fieldLabel: 'Mobile',
            allowBlank: false
        },{
        	xtype: 'combobox',
        	store: school,
        	valueField: 'NAME',
        	displayField: 'NAME',
        	name: 'NAME',
        	fieldLabel: 'School',
        	allowBlank: false,
        	editable: false
        },{
        	xtype: 'textarea',
        	name: 'BIO',
        	maxLength: 250,
        	fieldLabel: 'Bio',
        },{
        	xtype: 'hidden',
        	name: 'IS_TUTOR'
    	}],
    	this.dockedItems = [{
    		xtype:'toolbar',
    		dock: 'bottom',
    		items: [{
    			xtype: 'button',
    			text: 'Update',
    			handler: 'update'
    		}]
    		
    	}],
    	this.callParent(arguments);
    }
});