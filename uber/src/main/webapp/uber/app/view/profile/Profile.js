Ext.define('uber.view.profile.Profile',{
	extend: 'Ext.form.Panel',
	xtype: 'profile',
    controller: 'profile',
    reference: 'profile',
	config: {
		variableHeights: true,
		items: [{
			xtype: 'fieldset',
			title: 'Profile',
				defaults: {
					labelWidth: '35%'
				},
	    	items: [{
	    		xtype: 'textfield',
				name: 'firstname',
				label: 'First Name',
	        },{
	            xtype: 'textfield',
	            name: 'lastname',
	            label: 'Last Name',
	    	},{
	    		xtype: 'textfield',
				label: 'Email',
				placeHolder: 'me@domail.com'			
	    	},{
	    		xtype: 'textfield',
	    		name: 'school',
	    		label: 'School'
	    	},{
	    		xtype: 'container',
	    		layout: {
	    			type: 'hbox',
	    			align: 'stretch'
	    		},
	    		items: [{
	    			xtype: 'button',
	    			width: '35%',
	    			text: 'add subject(s)',
	    			handler: function (){
	    				var subject = this.up('container').down('textfield').getValue();
	    				console.log(subject);
	    				
	    			}
	    		},{
	    			xtype: 'textfield', //make it so that enter multiple subject (like tags)
	    			reference: 'subjectfield',
	    			flex: 1,
	    			labelWidth: '35%',
	    			name: 'subject',
//	    			label: 'Subject(s)'
	    		}]
	    	},{
	    		xtype: 'subjectgrid',
	    	},{
	            xtype: 'toolbar',
	            items: [{
	                xtype: 'button',
	                text: 'Save Changes',
	                handler: 'save'
	            }]
	        }]
		}]
	},
});