/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Robo.action.flat.Remove', {
    extend          : 'Robo.action.Base',

    store           : null,
    records         : null,

    index           : null,

    
    undo : function () {
        var me = this;

        me.store.insert(me.index, me.records);
    },

    
    redo : function () {
        var me = this;

        me.store.remove(me.records);
    },

    
    getRecord : function () {
        return this.records[0];
    },
    
    
    getTitle : function () {
        var me          = this
        
        var titles      = Ext.Array.map(this.records, function (record) {
            if (record.getTitle) return record.getTitle(me)
                
            if (record.modelName) return record.modelName + " " + record.getId()
            
            return 'unknown'
        })
        
        return 'Removal of ' + titles.join(',')
    }
});
