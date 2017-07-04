Ext.define('uber.model.User',{
	extend: 'Ext.data.Model',
	alias: 'model.user',
	
	fields: [
		{ name: 'username', type: 'string' },
		{ name: 'password', type: 'string' }		
	],
	validators: {
		username: { type: 'presence', message: 'please input valid username' },
		password: { type: 'presence', message: 'please input valid password' }
	}
})