//Validation for Sign In
Ext.define('uber.model.CategoryWindow',{
	extend: 'Ext.data.Model',
	alias: 'model.categoryWindow',
	
	fields: [
		{ name: 'subject', type: 'string' },
		{ name: 'description', type: 'string' },
	],
	validators: {
		subject: { type: 'presence', message: 'A Subject is required' },
		description: { type: 'presence', message: 'A description is required' },
	}
})