Ext.define('uber.model.MakeRequest',{
	extend: 'Ext.data.Model',
	alias: 'model.makeRequest',
	fields: [
		{ name: 'subject', type: 'string' },
		{ name: 'title', type: 'string' },
		{ name: 'description', type: 'string' },
		
	],
	validators: {
		subject: [
			{ type: 'presence', message:"Subject required, please select subject"},
		],
		title: [
			{ type: 'presence', message:"Title required, please fill in title"},
		],
		description: [
			{ type: 'presence', message:"Description required, please fill in description"},
		],
			
	}
})