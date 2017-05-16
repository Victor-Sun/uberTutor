 Ext.define('uber.model.Profile',{
	extend: 'Ext.data.Model',
//        	config: {
//        	},
	fields: [
//			'firstname', 'password'
		{ name: 'firstname', type: 'string' },
		{ name: 'lastname', type: 'string' },
		{ name: 'email', type: 'string' },
		{ name: 'school', type: 'string' },
		{ name: 'email', type: '' }
	]
// ,
//	validators: {
//		username: [
//           { type: 'presence', message: 'please input valid username' }
//		],
//		password: [
//           { type: 'presence', message: 'please input valid password' }
//       ]
//	}
})