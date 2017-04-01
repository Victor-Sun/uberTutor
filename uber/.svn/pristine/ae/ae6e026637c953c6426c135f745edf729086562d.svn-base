/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Robo.action.Base

Base class for actions - entities that represents a change in a managed store.

*/

Ext.define('Robo.action.Base', {

    constructor : function (config) {
        Ext.apply(this, config);
    },

    
    undo : function () {
        throw new Error("Abstract method call");
    },

    
    redo : function () {
        throw new Error("Abstract method call");
    },
    
    
    getTitle : function () {
        return ''
    }
});
