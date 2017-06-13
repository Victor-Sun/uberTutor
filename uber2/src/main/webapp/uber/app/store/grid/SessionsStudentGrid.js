Ext.define('uber.store.grid.SessionsStudentGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsStudentGrid',
//    TODO: fields for grid
//    model: 'tutorregistrationgrid'
    fields: [
	{ name: 'CREATE_DATE' , type: "date", format: 'Y-m-d' },
	{ name: 'TUTOR_NAME', type: 'string' },
	{ name: 'CATEGORY', type: 'string' },
	{ name: 'SUBJECT', type: 'string' },
	{ name: 'STATUS', type: 'string' }
	],
	sorters: {
		property: 'STATUS',
		direction: 'DESC'
	},
    proxy: {
        type: 'ajax',
        url: '/uber2/main/my-session!displayUserSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});