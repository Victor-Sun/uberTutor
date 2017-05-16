Ext.define('uber.model.UserInfo',{
	extend: 'Ext.data.Model',
//        	config: {
//        	},
	fields: [
//			'username', 'password'
		{ name: 'fullname', type: 'string' },
		{ name: 'fullname', type: 'string' },
		
	],
//			validations:
//			[
//			    {type: 'presence',field: 'username',  message: 'please input valid username'},
//			    {type: 'presence', field: 'password', message: 'please input valid password'}
//			],
	validators: {
		username: [
           { type: 'presence', message: 'please input valid username' }
		],
		password: [
           { type: 'presence', message: 'please input valid password' }
       ]
	}
})