Ext.define('uber.model.grid.TutorRegistrationGrid', {
    extend: 'uber.model.Base',
    alias: 'model.tutorRegistrationGrid',
    
    fields: [
//        // the 'name' below matches the tag name to read
        { name: 'CATEGORY_TITLE'},
        { name: 'SUBJECT_TITLE'},
        { name: 'date', type: 'date', dateFormat: 'Y-m-d H:i:s' },
		{ name: 'description'}
    ]
});