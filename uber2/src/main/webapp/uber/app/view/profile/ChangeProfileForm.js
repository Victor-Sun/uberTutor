Ext.define('uber.view.profile.ChangeProfileForm',{
	extend: 'Ext.form.Panel',
	xtype: 'changeProfileForm',
	
	flex: 1,
	margin: 5,
	controller: 'profile',
	reference: 'formpanel',
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
    	var school = Ext.create('Ext.data.Store',{
    		fields: [ 'NAME' ],
    		proxy: {
    	         type: 'ajax',
    	         url: '/uber2/main/profile!displaySchool.action',
    	         reader: {
    	             type: 'json',
    	             rootProperty: 'data'
    	         }
    	    },
//    		data: [
//    		       { "school": "Grand Valley State University"},
//    		       { "school": "Michigan State University"},
//    		       { "school": "University of Michigan"}
//    		       ]
    	});
    	this.items = [{
    		name: 'fullname',
            fieldLabel: 'Name&nbsp;*',
        },{
            name: 'email',
            fieldLabel: 'Email&nbsp;*',
        },{
            name: 'mobile',
            fieldLabel: 'Mobile&nbsp;*',
        },{
        	xtype: 'combobox',
        	store: school,
        	valueField: 'NAME',
        	displayField: 'NAME',
        	name: 'school',
        	fieldLabel: 'School',
        	
        },{
        	xtype: 'textarea',
        	name: 'bio',
        	maxLength: 250,
        	fieldLabel: 'Bio',
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