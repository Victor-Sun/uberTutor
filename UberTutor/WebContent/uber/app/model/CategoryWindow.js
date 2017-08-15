//Validation for Sign In
Ext.define('uber.model.CategoryWindow',{
	extend: 'Ext.data.Model',
	alias: 'model.categoryWindow',
	
	fields: [
		{ name: 'subject', type: 'string' },
		{ name: 'description', type: 'string' },
	],
	validators: {
		subject: { type: 'presence', message: 'Subject required, please select a subject' },
		description: { type: 'presence', message: 'Description required, please fill in desciption' },
	}
})