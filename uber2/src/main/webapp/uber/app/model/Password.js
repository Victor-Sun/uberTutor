Ext.define('uber.model.Password',{
	extend: 'Ext.data.Model',
//        	config: {
//        	},
	fields: [
//			'username', 'password'
		{ name: 'currentpassword', type: 'string' },
		{ name: 'newpassword', type: 'string' },
	],
//			validations:
//			[
//			    {type: 'presence',field: 'username',  message: 'please input valid username'},
//			    {type: 'presence', field: 'password', message: 'please input valid password'}
//			],
	validators: {
		
       newpassword: [{
    	   type: 'presence', message: 'new password must be atleast 6 characters long'
       }]
	}
})