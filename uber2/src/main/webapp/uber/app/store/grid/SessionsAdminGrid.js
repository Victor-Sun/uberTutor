Ext.define('uber.store.grid.SessionsAdminGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsAdminGrid',
    
    fields: [
		{ name: 'CREATE_DATE' , type: 'integer', },
		{ name: 'CANCEL_DATE' , type: 'integer', },
		{ name: 'CLOSE_DATE' , type: 'integer', },
		{ name: 'PENDING_DATE' , type: 'integer', },
		{ name: 'PROCESS_DATE' , type: 'integer', },
		{ name: 'UPDATE_DATE' , type: 'integer', },
        { name: 'STUDENT_NAME', type: 'string' },
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
        url: '/uber2/main/my-session!displayAllSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});