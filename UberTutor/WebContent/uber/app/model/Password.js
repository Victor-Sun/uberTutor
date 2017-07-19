Ext.define('uber.model.Password',{
	extend: 'Ext.data.Model',
	fields: [
		{ name: 'currentpassword', type: 'string' },
		{ name: 'newpassword', type: 'string' },
	],
	validators: {
		currentpassword: [
		              { type: 'presence', message: 'please input current password'}    
	                  ],
       	newpassword: [
       	              { type: 'length', message: 'new password must be atleast 6 characters long'}
       	              ]
	}
});