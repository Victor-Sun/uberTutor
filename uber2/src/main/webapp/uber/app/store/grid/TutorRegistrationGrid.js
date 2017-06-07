Ext.define('uber.store.grid.TutorRegistrationGrid', {
    extend: 'Ext.data.Store',
    alias: 'store.tutorregistrationgrid',

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
