Ext.define('uber.store.grid.SessionsTutorGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsTutorGrid',
//    TODO: fields for grid
//    model: 'tutorregistrationgrid'
	fields: [
	{ name: 'CREATE_DATE' , type: "date", format: 'Y-m-d' },
	{ name: 'STUDENT_NAME', type: 'string' },
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
        url: '/uber2/main/my-session!displayTutorSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});