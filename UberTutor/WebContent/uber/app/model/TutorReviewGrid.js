Ext.define('uber.model.TutorReviewGrid', {
	extend: 'uber.model.Base',
    alias: 'model.tutorreviewgrid',

    fields: [
         {name: 'id'},
         {name: 'username'},
         {name: 'date', type: 'date'},
         {name: 'rating'},
         {name: 'content'}
     ]
});