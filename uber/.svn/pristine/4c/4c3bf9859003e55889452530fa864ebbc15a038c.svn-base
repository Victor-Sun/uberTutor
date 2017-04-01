/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Robo.action.tree.Remove', {
    extend          : 'Robo.action.Base',

    parent          : null,
    removedChild    : null,
    nextSibling     : null,

    newParent       : null,
    newNextSibling  : null,
    
    dirty           : false,

    isMove          : false,

    
    constructor : function (config) {
        this.callParent(arguments)
        
        this.dirty      = this.removedChild.dirty
    },
    
    
    undo : function () {
        if (this.isMove) {
            this.newParent          = this.removedChild.parentNode;
            this.newNextSibling     = this.removedChild.nextSibling;
        }
        
        var nextSibling     = this.nextSibling
        var insertedAsFirst = nextSibling && nextSibling.isFirst()
        
        var removedChild    = this.removedChild
        
        this.parent.insertBefore(removedChild, nextSibling);
        
        removedChild.dirty  = this.dirty
        
        if (!this.isMove) {
            var store       = removedChild.getTreeStore()
        
            removedChild.cascadeBy(function (node) {
                Ext.Array.remove(store.removedNodes, node);
            })
        }
        
        if (insertedAsFirst) nextSibling.updateInfo(false, { isFirst : false })
    },

    
    redo : function () {
        if (this.isMove) {
            var newNextSibling      = this.newNextSibling
            var insertedAsFirst     = newNextSibling && newNextSibling.isFirst()
            
            this.newParent.insertBefore(this.removedChild, newNextSibling);
            
            // https://www.sencha.com/forum/showthread.php?308814-6.0.1-quot-isFirst-quot-field-is-not-updated-correctly-after-the-child-node-insertion&p=1127985#post1127985
            if (insertedAsFirst) newNextSibling.updateInfo(false, { isFirst : false })
        }
        else {
            this.parent.removeChild(this.removedChild);
            
            delete this.removedChild.data.lastParentId
        }
    },
    

    getRecord : function () {
        return this.removedChild;
    },
    
    
    getTitle : function () {
        var record      = this.removedChild
        
        var title
        
        if (record.getTitle) 
            title  = record.getTitle(this)
        else
            if (record.modelName) return record.modelName + " " + record.getId()
        
        return this.isMove ? 'Move of ' + title : 'Removal of ' + title
    }
});
