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
    items: [{
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
    	maxLength: 1000,
    	fieldLabel: 'Bio',
    	itemId: 'bio'
    }]
});