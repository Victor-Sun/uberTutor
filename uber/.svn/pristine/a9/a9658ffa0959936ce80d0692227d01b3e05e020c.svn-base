/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/*
@class Gnt.field.ReadOnly
@extends Ext.form.field.Checkbox

A specialized field allowing a user to switch a task to readonly mode.

*/
Ext.define('Gnt.field.ReadOnly', {
    extend                  : 'Ext.form.field.Checkbox',
    mixins                  : ['Gnt.field.mixin.TaskField', 'Gnt.mixin.Localizable'],
    alias                   : 'widget.readonlyfield',
    alternateClassName      : ['Gnt.widget.ReadOnlyField'],

    taskField               : 'readOnlyField',
    setTaskValueMethod      : 'setReadOnly',
    getTaskValueMethod      : 'getReadOnly',
    instantUpdate           : true,

    valueToVisible : function (value) {
        return value ? this.L('yes') : this.L('no');
    },

    getValue : function () {
        return this.value;
    },

    setValue : function (value) {

        this.callParent([ value ]);

        if (this.instantUpdate && !this.getSuppressTaskUpdate() && this.task) {
            // apply changes to task
            this.applyChanges();
        }
    }
});
