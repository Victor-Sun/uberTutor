Ext.define('uber.store.category.Category',{
	extend: 'Ext.data.Store',
	alias: 'store.category',
	fields: ['title'],
	proxy: {
      type: 'ajax',
      url: '/UberForTutor/main/tutor-subject-register!displayCategories.action',
      reader: {
          type: 'json',
          rootProperty:'data',
      }
	},
});