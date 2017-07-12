Ext.define('uber.store.grid.MyTutorsGrid',{
	extend: 'Ext.data.Store',
    alias: 'store.myTutorsGrid',
    autoLoad: true,
    model: 'uber.model.grid.MyTutorsGrid',
    proxy: {
        type: 'ajax',
        url: '/UberTutor/main/main-page!displayPreviousTutor.action',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'total'
        }
    }
});