/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.field.ShowInTimeline', {
    extend                  : 'Ext.form.field.Checkbox',

    mixins                  : ['Gnt.field.mixin.TaskField', 'Gnt.mixin.Localizable'],

    alias                   : 'widget.showintimelinefield',

    alternateClassName      : ['Gnt.widget.ShowInTimelineField'],

    taskField               : 'showInTimelineField',
    setTaskValueMethod      : 'setShowInTimeline',
    getTaskValueMethod      : 'getShowInTimeline',

    valueToVisible : function (value) {
        return value ? this.L('yes') : this.L('no');
    },

    setValue : function (value) {
        this.callParent(arguments);

        if (this.instantUpdate && !this.getSuppressTaskUpdate() && this.task) {
            // apply changes to task
            this.applyChanges();
        }
    }
});

