Ext.define('uber.model.Password',{
	extend: 'Ext.data.Model',
	fields: [
		{ name: 'currentPassword', type: 'string' },
		{ name: 'newPassword', type: 'string' },
	],
	validators: {
		currentPassword: [
			{ type: 'presence', message: 'Currnet password required, please input current password'}    
		],
		newPassword: [
       		{ type: 'presence', message: 'New password required, please input new password'},
       		{ type: 'length', min: 6, message: 'New password must be atleast 6 characters long'},
        ]
	}
});