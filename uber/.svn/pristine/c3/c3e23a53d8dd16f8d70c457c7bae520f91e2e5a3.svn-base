/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.widget.calendar.DatePicker', {
    extend      : 'Ext.picker.Date',

    alias       : 'widget.gntdatepicker',


    workingDayCls           : 'gnt-datepicker-workingday',
    nonWorkingDayCls        : 'gnt-datepicker-nonworkingday',
    overriddenDayCls        : 'gnt-datepicker-overriddenday',
    overriddenWeekDayCls    : 'gnt-datepicker-overriddenweekday',

    weekOverridesStore      : null,
    dayOverridesCalendar    : null,


    // @OVERRIDE
    // Adds custom classes to certain day cells
    update : function () {
        this.callParent(arguments);

        this.refreshCssClasses();
    },


    refreshCssClasses : function () {
        var me      = this,
            cells   = me.cells.elements;

        this.removeCustomCls();

        for (var i = 0; i < me.numDays; i++) {
            // will contain number of ms since Epoch, so need to convert it into Date on the next line
            var timestamp                = cells[ i ].firstChild.dateValue;
            cells[ i ].className    += ' ' + this.getDateCls(new Date(timestamp));
        }
    },


    getDateCls : function (date) {
        var cls         = "";

        if (date.getMonth() !== this.getActive().getMonth()) return;

        var dayOverridesCalendar   = this.dayOverridesCalendar;

        if (dayOverridesCalendar.getOwnCalendarDay(date)) {
            cls         += " " + this.overriddenDayCls;

            if (!dayOverridesCalendar.isWorkingDay(date)) cls += " " + this.nonWorkingDayCls;

        } else {
            // this will be an internal week override model instance from the weekStore
            var week    = null;

            this.weekOverridesStore.each(function (internalWeekModel) {
                if (Ext.Date.between(date, internalWeekModel.get('startDate'), internalWeekModel.get('endDate'))) {
                    week = internalWeekModel;
                    return false;
                }
            });

            if (week) {
                cls                     += " " + this.overriddenWeekDayCls;

                var index               = date.getDay(),
                    weekAvailability    = week.get('weekAvailability');

                if (weekAvailability && weekAvailability[ index ] && !weekAvailability[ index ].getIsWorkingDay()) {
                    cls                 += " " + this.nonWorkingDayCls;
                }

            } else if (!dayOverridesCalendar.isWorkingDay(date)) {
                cls                     += " " + this.nonWorkingDayCls;
            }
        }

        return cls || this.workingDayCls;
    },


    removeCustomCls : function () {
        this.cells.removeCls([ this.overriddenDayCls, this.nonWorkingDayCls, this.workingDayCls, this.overriddenWeekDayCls ]);
    }
});