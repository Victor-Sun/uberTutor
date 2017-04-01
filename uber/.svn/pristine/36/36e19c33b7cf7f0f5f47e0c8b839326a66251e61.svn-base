/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.widget.calendar.ResourceCalendarGrid', {
    extend              : 'Ext.grid.Panel',

    requires            : [
        'Ext.data.Store',
        'Ext.grid.plugin.CellEditing',
        'Sch.util.Date',
        'Gnt.model.Calendar',
        'Gnt.data.Calendar'
    ],

    mixins              : ['Gnt.mixin.Localizable'],

    alias               : 'widget.resourcecalendargrid',

    resourceStore       : null,
    calendarStore       : null,

    /*
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

            - name      : 'Name',
            - calendar  : 'Calendar'
     */

    cellEditingConfig   : null,

    initComponent   : function() {
        var me = this;

        this.calendarStore = this.calendarStore || {
            xclass : 'Ext.data.Store',
            model  : 'Gnt.model.Calendar'
        };

        if (!(this.calendarStore instanceof Ext.data.Store)) {
            this.calendarStore = Ext.create(this.calendarStore);
        }

        var plugin  = Ext.create('Ext.grid.plugin.CellEditing', Ext.apply({ clicksToEdit : 2 }, this.cellEditingConfig));

        this.mon(plugin, {
            edit    : function (editor, e) {
                this.onCalendarChange(e.record, e.value);
            },

            scope   : this
        });


        Ext.apply(me, {
            store           : me.resourceStore,

            columns: [{
                header      : this.L('name'),
                dataIndex   : 'Name',
                flex        : 1
            }, {
                header      : this.L('calendar'),
                flex        : 1,
                renderer    : function (value, meta, record) {
                    var cal     = record.getCalendar();
                    var fn      = me.calendarStore.getModelById ? 'getModelById' : 'getById';
                    var rec     = me.calendarStore[fn](cal && cal.calendarId);
                    return rec && rec.getName() || value;
                },
                editor      : {
                    xtype           : 'combobox',
                    store           : me.calendarStore,
                    queryMode       : 'local',
                    displayField    : 'Name',
                    valueField      : 'Id',
                    editable        : false,
                    allowBlank      : false
                }
            }],
            border      : true,
            height      : 180,
            plugins     : plugin
        });

        this.calendarStore.loadData(this.getCalendarData());
        this.callParent(arguments);
    },

    getCalendarData : function () {
        return Ext.Array.map(Gnt.data.Calendar.getAllCalendars(), function (cal) {
            return { Id : cal.calendarId, Name : cal.name || cal.calendarId };
        });
    },

    onCalendarChange : function (record, calendarId) {
        record.setCalendarId(calendarId);
    },

    destroy : function() {
        this.calendarStore.destroy();

        this.callParent(arguments);
    }
});
