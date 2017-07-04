Ext.define('uber.store.subject.Subject',{
	extend: 'Ext.data.Store',
	fields: ['ID', 'NAME'],
	proxy: {
      type: 'ajax',
      url: '/uber2/main/tutor-subject-register!displayCategorySubject.action',
      reader: {
          type: 'json',
          rootProperty:'data',
      }
	},
});