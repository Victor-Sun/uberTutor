//Validation for Sign In
Ext.define('uber.model.SignUp',{
	extend: 'Ext.data.Model',
	alias: 'model.signup',
	
	fields: [
		{ name: 'username', type: 'string' },
		{ name: 'email', type: 'string' },
		{ name: 'password', type: 'string' },
		{ name: 'password2', type: 'string' },
	],
	validators: {
		username: { type: 'presence', message: 'A valid username is required' },
		email: { type: 'presence', message: 'A valid email is required' },
		password: { type: 'presence', message: 'A valid password is required' },
//		password2: { type: 'presence', message: 'Please re-enter new password' },
	}
})