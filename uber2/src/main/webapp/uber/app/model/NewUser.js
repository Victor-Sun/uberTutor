Ext.define('uber.model.NewUser',{
	extend: 'Ext.data.Model',
	fields: [
		{ name: 'username', type: 'string' },
		{ name: 'email', type: 'string' },
		{ name: 'password', type: 'string' }
	],
	validators: {
//			fullname: [
//			           { type: 'presence', name: 'fullname',message:"Enter Name"},
//			],
		    username: [
		               { type: 'presence', message: 'please input valid username' }
            ],
			email: [
			           { type: 'format',   name: 'emailId', matcher: /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message:"Wrong Email Format"},
			],
			password: [
			           { type: 'presence', name: 'mobile',message:"Enter Password"},
			],
			
	}
})