Ext.define('uber.store.Profile',{
	extend: 'Ext.data.Store',
	alias: 'store.profile',
	
	field: ['id','fullname', 'username', 'email', 'mobile', 'bio'],
	
//	proxy: {
//		type: 'ajax',
//		url: '/UberForTutor/main/profile!display.action',
//		reader: {
//			type: 'json',
//			rootProperty: 'data'
//		}
//	},
//	autoLoad: true,
//	listeners: {
//        load: function(store, records) {
//            var record = store.getAt(0);
//            formPanel.getForm().loadRecord(record);
//        }
//    }
});