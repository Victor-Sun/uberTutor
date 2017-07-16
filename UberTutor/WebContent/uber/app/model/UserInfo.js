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
			           { type: 'presence', message:"Please enter name"},
			],
//		    username: [
//		               { type: 'presence', message: 'please input valid username' }
//            ],
			email: [
			        { type: 'presence', message: "An email is required" },
			        { type: 'format', name: 'email', matcher: /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message:"Incorrect email format"},
			],
			mobile: [
			        { type: 'presence', name: 'mobile',message:"Please enter mobile"},
			],
			
	}
})