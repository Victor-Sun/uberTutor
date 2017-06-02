Ext.define('uber.model.UserInfo',{
	extend: 'Ext.data.Model',
//        	config: {
//        	},
	fields: [
//			'username', 'password'
		{ name: 'fullname', type: 'string' },
		{ name: 'username', type: 'string' },
		{ name: 'email', type: 'string' },
		{ name: 'password', type: 'string' },
		{ name: 'currentpassword', type: 'string' },
		{ name: 'newpassword', type: 'string' },
	],
//			validations:
//			[
//			    {type: 'presence',field: 'username',  message: 'please input valid username'},
//			    {type: 'presence', field: 'password', message: 'please input valid password'}
//			],
	validators: {
			fullname: [
			           { type: 'presence', name: 'name',message:"Enter Name"},
			],
		    username: [
		               { type: 'presence', message: 'please input valid username' }
            ],
            password: [
                       { type: 'presence', message: 'please input valid password' }
            ],
            currentpassword: [
                       { type: 'presence', message: 'please input valid password' }
            ],
            newpassword: [
                       { type: 'presence', message: 'new password must be atleast 6 characters long' }
            ],
			email: [
			        {type: 'format',   name: 'email', matcher: /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message:"Wrong Email Format"}
	        ]
	}
})