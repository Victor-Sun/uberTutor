/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.model.Calendar
@extends Sch.model.Customizable

A model representing a single calendar.
Every model instance will be also decorated with the {@link Gnt.data.Calendar} instance, created based on the model field values.
The fields of the model correspond to the properties of {@link Gnt.data.Calendar} class.

Fields
------

- `Id` - record identifier (corresponds to {@link Gnt.data.Calendar#calendarId})
- `Name` - corresponds to {@link Gnt.data.Calendar#name}
- `DaysPerMonth` - corresponds to {@link Gnt.data.Calendar#daysPerMonth}
- `DaysPerWeek` - corresponds to {@link Gnt.data.Calendar#daysPerWeek}
- `HoursPerDay` - corresponds to {@link Gnt.data.Calendar#hoursPerDay}
- `WeekendsAreWorkdays` - corresponds to {@link Gnt.data.Calendar#weekendsAreWorkdays}
- `WeekendFirstDay` - corresponds to {@link Gnt.data.Calendar#weekendFirstDay}
- `WeekendSecondDay` - corresponds to {@link Gnt.data.Calendar#weekendSecondDay}
- `DefaultAvailability` - corresponds to {@link Gnt.data.Calendar#defaultAvailability}
- `Days` - stores reference to the {@link Gnt.data.Calendar} instance
- `CalendarClass` - calendar class that should be used to create {@link Gnt.data.Calendar} instance
- `PhantomId` - phantom record identifier
- `PhantomParentId` - phantom parent record identifier

A collection of this models is supposed to be provided for the {@link Gnt.data.CalendarManager calendar manager}.
*/
Ext.define('Gnt.model.Calendar', {
    extend                      : 'Sch.model.Customizable',

    requires                    : ['Ext.data.NodeInterface'],

    idProperty                  : 'Id',

    calendar                    : null,

    /**
     * @cfg {String} nameField The name of the field specifying the calendar name.
     */
    nameField                   : 'Name',

    /**
     * @cfg {String} daysPerMonthField The name of the field specifying the number of days per month
     * (used when converting the big duration units like month/year to days).
     */
    daysPerMonthField           : 'DaysPerMonth',

    /**
     * @cfg {String} daysPerWeekField The name of the field specifying the number of days per week
     * (used when converting the duration only).
     */
    daysPerWeekField            : 'DaysPerWeek',

    /**
     * @cfg {String} hoursPerDayField The name of the field specifying the number of hours per day
     * (used when converting the duration only).
     */
    hoursPerDayField            : 'HoursPerDay',

    /**
     * @cfg {String} weekendsAreWorkdaysField The name of the field specifying if all weekdays are working.
     */
    weekendsAreWorkdaysField    : 'WeekendsAreWorkdays',

    /**
     * @cfg {String} weekendFirstDayField The name of the field specifying the index of the first day in weekend.
     */
    weekendFirstDayField        : 'WeekendFirstDay',

    /**
     * @cfg {String} weekendSecondDayField The name of the field specifying the index of the second day in weekend.
     */
    weekendSecondDayField       : 'WeekendSecondDay',

    /**
     * @cfg {String} defaultAvailabilityField The name of the fields specifying the calendar default availability
     */
    defaultAvailabilityField    : 'DefaultAvailability',

    /**
     * @cfg {String} daysField The name of the fields specifying the calendar content ({@link Gnt.data.Calendar} instance)
     */
    daysField                   : 'Days',

    /**
     * @cfg {String} calendarClassField The name of the fields specifying the class that should be used to
     * to create {@link Gnt.data.Calendar the calendar} instance
     */
    calendarClassField          : 'CalendarClass',

    /**
     * @cfg {String} phantomIdField The name of the field specifying the phantom id when this record is being 'realized' by the server.
     */
    phantomIdField              : 'PhantomId',

    /**
     * @cfg {String} phantomParentIdField The name of the field specifying the parent calendar phantom id when this record is being 'realized' by the server.
     */
    phantomParentIdField        : 'PhantomParentId',

    customizableFields      : [
        { name : 'Name' },
        { name : 'DaysPerMonth', type : 'number' },
        { name : 'DaysPerWeek', type : 'number' },
        { name : 'HoursPerDay', type : 'number' },
        { name : 'WeekendsAreWorkdays', type : 'boolean' },
        { name : 'WeekendFirstDay', type : 'integer' },
        { name : 'WeekendSecondDay', type : 'integer' },
        { name : 'DefaultAvailability' },
        { name : 'Days' },
        { name : 'CalendarClass', defaultValue : 'Gnt.data.Calendar' },
        { name : 'PhantomId' },
        { name : 'PhantomParentId' }
    ],

    constructor : function (config, id, node) {
        var cfg         = config || node || {};

        var days        = cfg.calendar || cfg.Days;

        config && delete config.calendar;
        node && delete node.calendar;

        // keep originally provided fields
        this.rawConfig = Ext.apply({}, config);

        this.callParent(arguments);

        this.setDays(days);

        this.data[this.phantomIdField]  = this.getId();
    },

    get : function (field) {
        if (field === 'Days') {
            return this.getCalendar() || this.data[this.daysField];
        } else {
            return this.callParent(arguments);
        }
    },

    set : function (field, value) {
        if (field === 'Days') {
            if (value instanceof Gnt.data.Calendar) {
                this.setCalendar(value);
            } else {
                this.data[this.daysField]   = value;
            }
        } else {
            return this.callParent(arguments);
        }
    },

    /**
     * Gets a calendar assigned to the record.
     */
    getCalendar : function () {
        return this.calendar;
    },

    /**
     * @private
     * Assign a calendar to the record.
     * @param {Gnt.data.Calendar} calendar The calendar to assign.
     */
    setCalendar : function (calendar) {
        this.calendar   = calendar;
    },

    getCalendarConfig : function () {
        var me = this;

        var config = {
            calendarId : me.getId(),
            parent     : me.parentNode && me.parentNode.getCalendar()
        };

        // properties we map to the calendar being created
        var properties = [
            'daysPerMonth',
            'daysPerWeek',
            'hoursPerDay',
            'name',
            'weekendFirstDay',
            'weekendSecondDay',
            'weekendsAreWorkdays',
            'defaultAvailability'
        ];

        for (var i = 0, l = properties.length; i < l; i++) {
            var property = properties[i];

            // we map only properties that were explicitly provided to the model constructor
            // to give calendar class ability to retrieve others from prototype or parent calendars

            if (me[property + 'Field'] in me.rawConfig) {
                // a customizable field getter
                var getterName = 'get' + Ext.String.capitalize(property);
                config[property] = me[getterName]();
            }
        }

        return config;
    },

    getModelConfig : function (calendar, isPrototype) {
        var result = {};

        // if we retrieve data from class prototype we should not
        // set reference to "calendar" instance ..since "calendar" is not an instance really
        if (!isPrototype) {
            result.parentId = calendar.parent && calendar.parent.calendarId;
            result.calendar = calendar;
        }

        result[this.daysPerMonthField]          = calendar.daysPerMonth;
        result[this.daysPerWeekField]           = calendar.daysPerWeek;
        result[this.hoursPerDayField]           = calendar.hoursPerDay;
        result[this.nameField]                  = calendar.name;
        result[this.weekendFirstDayField]       = calendar.weekendFirstDay;
        result[this.weekendSecondDayField]      = calendar.weekendSecondDay;

        // the following two options might be inherited from the calendar parents
        // in this case they should be omitted in the model configuration

        if (calendar.hasOwnProperty('weekendsAreWorkdays')) {
            result[this.weekendsAreWorkdaysField] = calendar.weekendsAreWorkdays;
        }

        if (calendar.hasOwnProperty('defaultAvailability')) {
            result[this.defaultAvailabilityField] = calendar.defaultAvailability;
        }

        result[this.calendarClassField] = Ext.getClassName(calendar);

        return result;
    },

    setCalendarManager : function (calendarManager) {
        this.calendarManager    = calendarManager;
    },

    getCalendarManager : function () {
        return this.calendarManager;
    },

    getParentCalendarClass : function () {
        var parent = this.parentNode,
            result;

        while (parent && !result) {
            result = parent.getCalendarClass();
            parent = parent.parentNode;
        }

        return result;
    },

    fillDataFromPrototype : function (nodeData) {
        // try to get proper calendar class:
        //  1. from the "nodeData" itself
        var cls     = nodeData[this.calendarClassField] ||
                //  2. from this.treeStore if presented
                this.treeStore && this.treeStore.getCalendarClass() ||
                //  2. from "this" or its parents ("this" is supposed to become the "nodeData" parent)
                this.getCalendarClass() || this.getParentCalendarClass() ||
                this.getField(this.calendarClassField).getDefaultValue();

        if (cls) {

            if (!nodeData[this.calendarClassField]) nodeData[this.calendarClassField] = cls;

            var children = nodeData.children;

            if (children && children.length) {
                for (var i = 0; i < children.length; i++) {
                    this.fillDataFromPrototype(children[i]);
                }
            }
        }
    },

    prepareCalendarNode  : function (node) {
        // fill model fields with data retrieved from the calendar
        if (node instanceof Gnt.data.Calendar) {
            node = this.getModelConfig(node);
        // .. or from associated CalendarClass prototype
        } else if (Ext.isObject(node) && !(node instanceof Gnt.model.Calendar)) {
            this.fillDataFromPrototype(node);
        }

        node = this.createNode(node);

        if (this.phantom) {
            if (this.getId() !== node.data[this.phantomParentIdField]) {
                node.modified                               = node.modified || {};
                node.modified[this.phantomParentIdField]    = node.data[this.phantomParentIdField];
                node.data[this.phantomParentIdField]        = this.getId();
            }
        }

        return node;
    }

}, function () {
    // Do this first to be able to override NodeInterface methods
    Ext.data.NodeInterface.decorate(this);

    this.override({
        // @OVERRIDE
        insertBefore : function (node) {
            node = this.prepareCalendarNode(node);

            return this.callParent(arguments);
        },

        // @OVERRIDE
        appendChild : function (node) {
            if (node instanceof Array) {
                for (var i = 0; i < node.length; i++) {
                    node[i] = this.prepareCalendarNode(node[i]);
                }
            } else {
                node = this.prepareCalendarNode(node);
            }

            return this.callParent(arguments);
        }
    });
});
