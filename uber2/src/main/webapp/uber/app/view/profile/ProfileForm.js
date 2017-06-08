Ext.define('uber.view.profile.ProfileForm',{
	extend: 'Ext.form.Panel',
	xtype: 'profileform',
	
	margin: 5,
	controller: 'profile',
    reference: 'profileForm',
    itemId: 'profileForm',
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
    	var checkbox = Ext.create('Ext.form.field.Checkbox',{
        	readOnly: false,
        	boxLabel: 'Is Tutor?',
        	name: 'isTutor',
        	id: 'isTutor',
        	hideLabel: true,
        	scope: this,
        	checkCount:0
    	});
    	
    	checkbox.on({
    		change: function (th , newValue , oldValue , eOpts) {
	    		var bio = Ext.getCmp('bio');
	    		var subject = Ext.getCmp('subject');
	    		var checkbox = this.getValue();
	    		var formPanel = this.up('form');
	    		if(th.checkCount > 0){
	    			formPanel.submit({
		    			//submit form for user signup
		    			url: '/uber2/main/profile!registerAsTutor.action',
		    			method: 'POST',
		    			params: {
		    				fullname: 'fullname'
		    			},
		    			success: function(response, opts) {
		    				// change to exception output
		    				Ext.Msg.alert( '', 'update success', Ext.emptyFn )
		    			},

		    			failure: function(response, opts) {
		    				// similar to above
		    				var result = uber.util.Util.decodeJSON(response.responseText);
		    				Ext.Msg.alert('Error', result.data, Ext.emptyFn);
		    			},
		    		});
	    		};
	    		
	    		if (checkbox == true)
    			{
    				bio.setVisible(true);
    				subject.setVisible(true);
    			} else {
    				bio.setVisible(false);
    				subject.setVisible(false);
    			};
    			th.checkCount = th.checkCount + 1;
	    	},
    	})
    	this.items = [{
    		xtype: 'textfield',
            name: 'fullname',
            fieldLabel: 'Name',
            readOnly: true,
            itemId: 'fullname'
        },{
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
        	xtype: 'textfield',
            name: 'school',
            fieldLabel: 'School',
            readOnly: true,
            itemId: 'school'
        },{
        	xtype: 'textarea',
        	name: 'bio',
        	maxLength: 1000,
        	fieldLabel: 'Bio',
        	readOnly: true,
        	hidden: true,
        	id: 'bio'
        },{
        	xtype: 'button',
        	name: 'addSubject',
        	text: 'Add Subject',
        	hidden: true,
        	id: 'subject',
        	handler: 'registration'
        },checkbox,
//        {
//        	xtype: 'button',
//        	name: 'buttonTest',
//        	text: 'button test',
//        	handler: function() {
//        		var profileForm = this.up('form');
//        		profileForm.reload();
//        	}
//        }
        ]
    	this.callParent();
    },
});