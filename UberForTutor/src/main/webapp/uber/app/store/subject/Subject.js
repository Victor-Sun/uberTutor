Ext.define('uber.store.subject.Subject',{
	extend: 'Ext.data.Store',
	fields: ['ID', 'NAME'],
	proxy: {
      type: 'ajax',
      url: '/UberForTutor/main/tutor-subject-register!displayCategorySubject.action',
      reader: {
          type: 'json',
          rootProperty:'data',
      }
	},
});