Ext.define('uber.store.grid.MySessionAdminGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.mySessionAdminGrid',
//    TODO: fields for grid
//    model: 'tutorregistrationgrid'
    
    proxy: {
        type: 'ajax',
        url: '/uber2/main/my-session!displayAllSessions.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});