Ext.define('uber.store.grid.TutorRegistrationGrid', {
    extend: 'Ext.data.Store',
    alias: 'store.tutorregistrationgrid',
//    TODO: fields for grid
//    model: 'tutorregistrationgrid'
    
    proxy: {
        type: 'ajax',
        url: '/uber2/main/tutor-subject-register!displayUserSubjects.action',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});
