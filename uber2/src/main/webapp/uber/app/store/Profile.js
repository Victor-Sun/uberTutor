Ext.define('uber.store.Profile',{
	extend: 'Ext.data.Store',
	alias: 'store.profile',
	
	field: ['id','fullname', 'username', 'email', 'mobile', 'bio'],
	
	proxy: {
		type: 'ajax',
		url: '',
		reader: {
			type: 'json',
			rootProperty: 'data'
		}
	}
});