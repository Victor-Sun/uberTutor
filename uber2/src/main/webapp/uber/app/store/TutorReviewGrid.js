Ext.define('uber.store.tutor.TutorReviewGrid',{
	extend: 'Ext.data.Store',
	alias: 'store.tutorreviewgrid',
	
	fields: [{
		name: 'id'
	},{
		name: 'username'
	},{
		name: 'date',
	},{
		name: 'rating'
	},{
		name: 'content'
	}],
	
	data: [{
		"name": "Nora Watson",
		"date": "2015/08/23 19:15:00",
		"rating": "4",
		"content": "Lorem ipsum dolor sit amet. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
	}],
});