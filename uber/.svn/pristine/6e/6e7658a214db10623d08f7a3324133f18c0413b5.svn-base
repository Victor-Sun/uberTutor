/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.field.Calendar
 @extends Ext.form.field.ComboBox

 A specialized field allowing a user to select particular calendar for a task.
 This class inherits from the standard Ext JS "combo box" field, so any standard `Ext.form.field.ComboBox`
 configs can be used.
 */

Ext.define('Gnt.field.Calendar', {
    extend : 'Ext.form.field.ComboBox',

    requires : ['Ext.data.Store', 'Gnt.model.Calendar', 'Gnt.data.Calendar'],

    mixins : ['Gnt.field.mixin.TaskField', 'Gnt.mixin.Localizable'],

    alias              : 'widget.calendarfield',
    alternateClassName : 'Gnt.widget.CalendarField',

    taskField          : 'calendarIdField',
    getTaskValueMethod : 'getCalendarId',
    setTaskValueMethod : 'setCalendarId',

    /**
     * @cfg {String} pickerAlign The align for combo-box's picker.
     */
    pickerAlign : 'tl-bl?',

    /**
     * @cfg {Boolean} matchFieldWidth Defines if the picker dropdown width should be explicitly set to match the width of the field. Defaults to true.
     */
    matchFieldWidth : true,

    editable : true,

    triggerAction : 'all',

    valueField : 'Id',

    displayField : 'Name',

    queryMode : 'local',

    forceSelection : true,

    allowBlank : true,

    initComponent : function () {
        var me     = this,
            config = me.getInitialConfig();

        if (!config.store || me.store.isEmptyStore) {
            me.store = {
                xclass      : 'Ext.data.Store',
                autoDestroy : true,
                model       : 'Gnt.model.Calendar'
            };
        }

        if (!(me.store instanceof Ext.data.Store)) {
            me.store = Ext.create(me.store);
        }

        me.callParent(arguments);

        // load calendars list
        me.updateCalendarsStore();

        // listen to new calendars creation/removal and update the field store
        me.mon(Ext.data.StoreManager, {
            add    : function (index, store, key) {
                if (store instanceof Gnt.data.Calendar) {
                    this.updateCalendarsStore();
                }
            },
            remove : function (index, store, key) {
                if (store instanceof Gnt.data.Calendar) {
                    this.updateCalendarsStore();
                }
            },
            scope  : me
        });

        me.on({
            show  : function () {
                me.setReadOnly(me.readOnly);
            }
        });

        me.on('change', me.onFieldChange, me);
    },

    updateCalendarsStore : function () {
        this.store.loadData(this.getCalendarData());
    },

    setReadOnly : function(readOnly) {
        readOnly = readOnly || this.store.count() === 0;

        this.callParent([readOnly]);
    },

    getCalendarData : function () {
        return Ext.Array.map(Gnt.data.Calendar.getAllCalendars(), function (cal) {
            return {
                Id   : cal.calendarId,
                Name : cal.name || cal.calendarId
            };
        });
    },


    onSetTask : function () {
        // set field to readonly if no calendars
        this.setReadOnly(this.readOnly);

        this.setValue(this.getTaskValue());
    },


    // Used in the column renderer
    valueToVisible : function (value, task) {
        var me             = this,
            displayTplData = [];

        var record = this.findRecordByValue(value);

        if (record) {
            displayTplData.push(record.data);
        } else if (Ext.isDefined(me.valueNotFoundText) && typeof me.valueNotFoundText == 'string') {
            displayTplData.push(me.valueNotFoundText);
        }

        return me.displayTpl.apply(displayTplData);
    },


    // @OVERRIDE
    getValue : function () {
        return this.value || '';
    },


    getErrors : function (value) {
        if (value) {
            var record = this.findRecordByDisplay(value);
            if (record) {
                if (this.task && !this.task.isCalendarApplicable(record.getId())) return [this.L('calendarNotApplicable')];
            }
        }

        var errors = this.callParent(arguments);

        if (errors && errors.length) {
            return errors;
        }

        // allow empty values by default
        if (!Ext.isEmpty(value) && !(this.findRecordByDisplay(value) || this.findRecordByValue(value))) {
            return [this.L('invalidText')];
        } else {
            return [];
        }
    },


    onFieldChange : function (field, value) {
        this.setValue(value);
    },


    // @OVERRIDE
    // We need to have both onFieldChange and setValue
    // since setValue is not called when user select an option from the dropdown list
    setValue : function (value) {

        this.callParent([value]);

        // we keep '' for empty field
        if (undefined === value || null === value || '' === value) this.value = '';

        if (this.instantUpdate && !this.getSuppressTaskUpdate() && this.task) {

            if (this.getTaskValue() != this.value) {
                // apply changes to task
                this.applyChanges();
            }

        }
    },


    // @OVERRIDE
    assertValue : function () {
        var raw = this.getRawValue();
        if (!raw && this.value) {
            this.setValue('');
        } else {
            this.callParent(arguments);
        }
    }
});
