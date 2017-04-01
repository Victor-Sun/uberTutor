/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Robo.action.tree.Insert', {
    extend          : 'Robo.action.Base',

    parent          : null,
    newChild        : null,

    insertedBefore  : null,


    undo : function () {
        var newChild    = this.newChild
        
        this.parent.removeChild(newChild);
        
        delete newChild.data.lastParentId
        
        var store       = this.parent.getTreeStore()
        
        Ext.Array.remove(store.removedNodes, newChild);
    },

    
    redo : function () {
        var insertedBefore      = this.insertedBefore
        var insertedAsFirst     = insertedBefore && insertedBefore.isFirst()
        
        this.parent.insertBefore(this.newChild, insertedBefore);
        
        // https://www.sencha.com/forum/showthread.php?308814-6.0.1-quot-isFirst-quot-field-is-not-updated-correctly-after-the-child-node-insertion&p=1127985#post1127985
        if (insertedAsFirst) insertedBefore.updateInfo(false, { isFirst : false })
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
        
        return 'Insertion of ' + title
    }
});
