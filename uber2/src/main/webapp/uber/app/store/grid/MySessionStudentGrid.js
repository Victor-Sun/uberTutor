Ext.define('uber.store.grid.MySessionStudentGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.mySessionStudentGrid',
//    TODO: fields for grid
//    model: 'tutorregistrationgrid'
    
    proxy: {
        type: 'ajax',
        url: '/uber2/main/my-session-action!displayUserSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});