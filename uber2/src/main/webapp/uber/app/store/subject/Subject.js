Ext.define('uber.store.subject.Subject',{
	extend: 'Ext.data.Store',
//	autoLoad: true,
	fields: ['ID', 'NAME'],
	proxy: {
      type: 'ajax',
      url: '/uber2/main/tutor-subject-register!displayCategorySubjects.action',
      reader: {
          type: 'json',
          rootProperty:'data',
      }
	},
});