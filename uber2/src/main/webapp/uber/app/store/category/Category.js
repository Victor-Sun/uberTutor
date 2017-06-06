Ext.define('uber.store.category.Category',{
	extend: 'Ext.data.Store',
	alias: 'store.category',
	autoLoad: true,
	proxy: {
      type: 'ajax',
      url: '/uber2/main/tutor-subject-register!displayCategories.action',
      reader: {
          type: 'json',
          rootProperty:'data',
      }
	},
});