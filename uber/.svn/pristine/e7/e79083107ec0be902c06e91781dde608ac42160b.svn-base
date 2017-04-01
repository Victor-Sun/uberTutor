/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Robo.action.flat.Add', {
    extend          : 'Robo.action.Base',

    store           : null,
    records         : null,

    index           : null,

    
    undo : function () {
        var records     = this.records
        
        this.store.remove(records);
        
        for (var i = 0; i < records.length; i++) {
            this.store.removeFromRemoved(records[ i ])
        }
    },

    
    redo : function () {
        this.store.insert(this.index, this.records);
    },

    
    getRecord : function () {
        return this.records[ 0 ];
    },
    
    
    getTitle : function () {
        var me          = this
        
        var titles      = Ext.Array.map(this.records, function (record) {
            if (record.getTitle) return record.getTitle(me)
                
            if (record.modelName) return record.modelName + " " + record.getId()
            
            return 'unknown'
        })
        
        return 'Addition of ' + titles.join(',')
    }
    
});
