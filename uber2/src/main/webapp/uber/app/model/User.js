Ext.define('uber.model.User',{
	extend: 'Ext.data.Model',
//        	config: {
//        	},
	fields: [
//			'username', 'password'
		{ name: 'username', type: 'string' },
		{ name: 'password', type: 'string' }		
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