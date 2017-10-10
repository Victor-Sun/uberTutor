Ext.define('uber.model.Password',{
	extend: 'Ext.data.Model',
	fields: [
		{ name: 'currentPassword', type: 'string' },
		{ name: 'newPassword', type: 'string', validators: function(value) {
				var newPassword = Ext.ComponentQuery.query('#newPassword')[0];
				var currentPassword = Ext.ComponentQuery.query('#currentPassword')[0];
				if (newPassword == currentPassword ) {
					return 'New password must be different from current'
				}
			}
		},
		{ name: 'newPassword2', type: 'string', validators: function(value) {
//			debugger;
				var newPassword = Ext.ComponentQuery.query('#newPassword')[0];
				var newPassword2 = Ext.ComponentQuery.query('#newPassword2')[0];
				if (newPassword != newPassword2 || newPassword2 == null) {
					return 'Please confirm new password'
				}
			}
		}
	],
	validators: {
		currentPassword: [
			{ type: 'presence', message: 'Currnet password required, please input current password'}    
		],
		newPassword: [
       		{ type: 'presence', message: 'New password required, please input new password'},
       		{ type: 'length', min: 6, message: 'New password must be atleast 6 characters long'},
        ],
        newPassword2: [
        	{ type: 'presence', message: 'Please confirm new password'}
        ]
	}
});