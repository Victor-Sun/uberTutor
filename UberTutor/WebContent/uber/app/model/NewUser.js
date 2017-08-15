Ext.define('uber.model.NewUser',{
	extend: 'Ext.data.Model',
	fields: [
		{ name: 'username', type: 'string' },
		{ name: 'email', type: 'string' },
		{ name: 'password', type: 'string' }
	],
	validators: {
		    username: [
		               { type: 'presence', message: 'Username required, please input valid username' }
            ],
			email: [
			           { type: 'format',   name: 'emailId', matcher: /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message:"Incorrect email format, the required email format: 'user@example.com'"},
			],
			password: [
			           { type: 'presence', name: 'mobile',message:"Password required, please endter password"},
			],
			
	}
})