/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

 A specialized field, allowing a user to also specify task manually scheduled value.
 This class inherits from the standard Ext JS "checkbox" field, so any usual `Ext.form.field.Checkbox` configs can be used.

 @class Gnt.field.ManuallyScheduled
 @extends Ext.form.field.Checkbox

 */
Ext.define('Gnt.field.ManuallyScheduled', {
    extend                  : 'Ext.form.field.Checkbox',

    mixins                  : ['Gnt.field.mixin.TaskField', 'Gnt.mixin.Localizable'],

    alias                   : 'widget.manuallyscheduledfield',

    alternateClassName      : ['Gnt.widget.ManuallyScheduledField'],

    taskField               : 'manuallyScheduledField',
    setTaskValueMethod      : 'setManuallyScheduled',
    getTaskValueMethod      : 'isManuallyScheduled',

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
