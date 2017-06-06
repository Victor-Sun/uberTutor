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
    	fieldLabel: 'Is Tutor',
    	name: 'isTutor',
//    	handler: function (field, value) {
//    		debugger;
//    		var bio = this.up().down('bio');
//    		var subject = this.up().down('subject');
//    		var checkbox = this.getValue();
//    		if (this.) {
//    			bio.setHidden(true);
//    			subject.setHidden(true);
//    		}
//    	}
    },{
    	xtype: 'textarea',
    	name: 'bio',
    	maxLength: 1000,
    	fieldLabel: 'Bio',
    	itemId: 'bio'
    },{
    	xtype: 'button',
    	name: 'subject',
    	text: 'Add Subject'
    }]
});