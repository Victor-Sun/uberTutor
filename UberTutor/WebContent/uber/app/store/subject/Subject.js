Ext.define('uber.store.subject.Subject',{
	extend: 'Ext.data.Store',
	fields: ['ID', 'NAME'],
	proxy: {
      type: 'ajax',
      url: '/UberTutor/main/tutor-subject-register!displayCategorySubject.action',
      reader: {
          type: 'json',
          rootProperty:'data',
      }
	},
});