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
    	xtype: 'checkbox',
    	readOnly: false,
    	boxLabel: 'Is Tutor?',
    	name: 'isTutor',
    	id:'istutor',
    	hideLabel: true,
    	scope: this,
    	handler: function (box, checked) {
    		var bio = Ext.getCmp('bio');
    		var subject = Ext.getCmp('subject');
    		var checkbox = Ext.getCmp('istutor').getValue();
    		if (checkbox == true)
    			{
    				bio.setVisible(true);
    				subject.setVisible(true);
    			} else {
    				bio.setVisible(false);
    				subject.setVisible(false);
    			}
    	}
    },{
    	xtype: 'textarea',
    	name: 'bio',
    	maxLength: 1000,
    	fieldLabel: 'Bio',
    	hidden: true,
    	id: 'bio'
    },{
    	xtype: 'button',
    	name: 'subject',
    	text: 'Add Subject',
    	hidden: true,
    	id: 'subject'
    }]
});