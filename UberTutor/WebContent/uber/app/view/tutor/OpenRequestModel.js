Ext.define('uber.view.tutor.OpenRequestModel',{
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.openRequest',
	
	requires: ['uber.model.OpenRequest'],
	
	formulas: {
        typeFilter: function (get) {
            var category = get('category');
            return this.filters[category];
        }
    },
    
    filters: {
        all:   [ 'news', 'forum' ],
        news:  [ 'news' ],
        forum: [ 'forum' ],
    },
    
    stores: {
        openRequest: {
            type: 'openRequest',
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