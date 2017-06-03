Ext.define('uber.model.UserInfo',{
	extend: 'Ext.data.Model',
	fields: [
		{ name: 'fullname', type: 'string' },
//		{ name: 'username', type: 'string' },
		{ name: 'email', type: 'string' },
		{ name: 'mobile', type: 'string' }
	],
	validators: {
			fullname: [
			           { type: 'presence', name: 'fullname',message:"Enter Name"},
			],
//		    username: [
//		               { type: 'presence', message: 'please input valid username' }
//            ],
			email: [
			           { type: 'format',   name: 'emailId', matcher: /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message:"Wrong Email Format"},
			],
			mobile: [
			           { type: 'presence', name: 'mobile',message:"Enter mobile"},
			],
			
	}
})