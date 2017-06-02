Ext.define('uber.view.profile.ProfileForm',{
	extend: 'Ext.form.Panel',
	xtype: 'profileform',
	
	margin: 5,
	controller: 'profile',
    reference: 'formpanel',
    layout: {
        type: 'vbox',
        align: 'stretchmax'
    },
    defaults: {
        labelAlign: 'top',
        readOnly: true,
        width: 200
//        anchor: '100%'
    },
	initComponent: function () {
		this.items = [{
            xtype: 'textfield',
            name: 'fullname',
            fieldLabel: 'Name',
            itemId: 'fullname'
        },{
            xtype: 'textfield',
            name: 'email',
            fieldLabel: 'Email',
            itemId: 'email'
        },{
            xtype: 'textfield',
            name: 'mobile',
            fieldLabel: 'Mobile',
            itemId: 'mobile'
        },{
        	xtype: 'textfield',
            name: 'school',
            fieldLabel: 'School',
            itemId: 'school'
        },{
        	xtype: 'textarea',
        	name: 'bio',
        	fieldLabel: 'Bio',
        	itemId: 'bio'
        }],
        this.dockedItems = [{
        	xtype: 'toolbar',
        	dock: 'bottom',
        	items: [{
        		xtype: 'button',
        		text: 'load profile',
        		handler: 'getProfile'
        	}]
        }]
		this.callParent(arguments);
	}
});