Ext.define('uber.model.Profile',{
	extend: 'Ext.data.Model',
	alias: 'model.profile',
	fields: [
		{ name: 'FULLNAME', type: 'string' },
		{ name: 'EMAIL', type: 'string' },
		{ name: 'MOBILE', type: 'string' },
		{ name: 'NAME', type: 'string'}
		
	],
	validators: {
		FULLNAME: [
			           { type: 'presence', message:"Name required, please enter name"},
			],
			EMAIL: [
			        { type: 'format', name: 'email', matcher: /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message:"Incorrect email format, the required email format: 'user@example.com'"},
			],
			MOBILE: [
					{ type: 'presence', message: 'Phone number required, please enter a valid phone number' }
			],
			NAME: [
					{ type: 'presence', message: 'School required, please select a school'}
			]
			
	}
})