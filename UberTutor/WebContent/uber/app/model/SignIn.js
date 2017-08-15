//Validation for Sign In
Ext.define('uber.model.SignIn',{
	extend: 'Ext.data.Model',
	alias: 'model.signin',
	
	fields: [
		{ name: 'username', type: 'string' },
//		{ name: 'email', type: 'email' },
		{ name: 'password', type: 'string' },
//		{ name: 'password2', type: 'string' },
	],
	validators: {
		username: { type: 'presence', message: 'A valid username is required, please enter username' },
//		email: { type: 'presence', message: 'A valid email is required' },
		password: { type: 'presence', message: 'A valid password is required, please enter password' },
//		password2: { type: 'presence', message: 'Please re-enter new password' },
	}
})