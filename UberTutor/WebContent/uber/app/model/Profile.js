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
			           { type: 'presence', message:"Please enter name"},
			],
			EMAIL: [
			        { type: 'format', name: 'email', matcher: /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message:"Incorrect email format, the required email format: 'user@example.com'"},
			],
			MOBILE: [
			        { type: 'length', min: 10, message: 'Please enter a valid 10 digit phone number' }
			],
			NAME: [
					{ type: 'presence', message: 'A school is required'}
			]
			
	}
})