Ext.define('uber.store.grid.SessionsAdminGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.sessionsAdminGrid',
    
    fields: [
        { name: 'CREATE_DATE' , type: "date", format: 'Y-m-d' },
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