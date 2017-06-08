Ext.define('uber.view.profile.ChangeProfileTutorForm',{
	extend: 'Ext.form.Panel',
	xtype: 'changeprofiletutorform',
	
	margin: 5,
	controller: 'profile',
    reference: 'profileTutorForm',
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
    	xtype: 'checkbox',
    	readOnly: false,
    	boxLabel: 'Is Tutor?',
    	name: 'isTutor',
    	id:'isTutor',
    	hideLabel: true,
    	scope: this,
    	handler: function (box, checked) {
    		var bio = Ext.getCmp('bio');
    		var subject = Ext.getCmp('subject');
    		var checkbox = Ext.getCmp('isTutor').getValue();
    		
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