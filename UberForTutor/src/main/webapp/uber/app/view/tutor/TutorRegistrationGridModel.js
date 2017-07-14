Ext.define('uber.view.tutor.TutorRegistrationGridModel',{
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.tutorRegistrationGrid',
	
	stores: {
		tutorRegistrationGrid: {
            type: 'tutorRegistrationGrid',
            autoLoad: true,
            sorters: [
                { property: 'date', direction: 'DESC' }
            ],
            filters: {
                property: 'type',
                operator: 'in',
                value: '{typeFilter}'
            }
        }
	}
});