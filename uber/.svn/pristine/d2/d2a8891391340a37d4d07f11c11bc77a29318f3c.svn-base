/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Robo.action.tree.Append', {
    extend          : 'Robo.action.Base',

    parent          : null,
    newChild        : null,

    
    undo : function () {
        var newChild    = this.newChild
        
        this.parent.removeChild(newChild);
        
        delete newChild.data.lastParentId
        
        var store       = this.parent.getTreeStore()
        
        Ext.Array.remove(store.removedNodes, newChild);
    },

    
    redo : function () {
        this.parent.appendChild(this.newChild);
    },

    
    getRecord : function () {
        return this.newChild;
    },
    
    
    getTitle : function () {
        var record      = this.newChild
        
        var title
        
        if (record.getTitle) 
            title  = record.getTitle(this)
        else
            if (record.modelName) return record.modelName + " " + record.getId()
        
        return 'Append of ' + title
    }
});
