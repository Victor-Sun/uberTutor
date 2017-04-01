/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.model.CalendarDay
@extends Sch.model.CalendarDay

A model representing a single day in the calendar. Depending from the `Type` field, day may be a concrete day per se (2012/01/01),
a certain weekday (all Thursdays), or an override for all certain weekdays in the timeframe
(all Fridays between 2012/01/01 - 2012/01/15, inclusive).

A collection CalendarDay instances is supposed to be provided for the {@link Gnt.data.Calendar calendar}

Fields
------

- `Id`   - The id of the date. Can be an arbitrary unique value, assigned by the server. For backward compatibility, if this field has one of the special formats
then some data will be extracted from it. This behavior will be kept for several coming releases, but you should not rely on it anymore.
- `Type` - The type of this calendar day. Can be one of the following `DAY`, `WEEKDAY`, `WEEKDAYOVERRIDE`:
    - Default value is `DAY` meaning this day represents a "real" day in the calendar (2012/01/01 for example) and contains availability information for that particular day only.
    The date is stored in the `Date` field.
    - The `WEEKDAY` value means calendar day contains information about all weekdays with the index, given in the `Weekday` field (0 - Sunday, 1 - Monday and so on).
    For example - all Fridays. `Date` field is ignored.
    - <p>The `WEEKDAYOVERRIDE` value means calendar day contains information about all weekdays within certain timespan. For example - all Fridays between 2012/01/01 - 2012/01/15.
    Week day index should be stored in the `Weekday` field again, beginning of the timespan - in the `OverrideStartDate` field and the end of timespan - in the `OverrideEndDate`.
    </p>
    <p>
    A single day instance contains the override for a single week day. So, to define overrides for several days (Monday and Tuesday for example) - add an additional instance
    to the calendar with the same `Name/OverrideStartDate/OverrideEndDate` values. There's no need to define an override for every weekday - if some day is not defined - the
    default availability will be used.
    </p>
    <p>
    * **Note** Every week override should also have a "main" calendar day instance, representing the override itself. It should have the same
    values for `Name/OverrideStartDate/OverrideEndDate` fields and -1 for `Weekday`. Also, the timespans of all week overrides should not intersect.
    </p>
    <p>
    To avoid manual creation of week overrides you can use the calendar API (for example, {@link Gnt.data.Calendar#addNonStandardWeek addNonStandardWeek},
    {@link Gnt.data.Calendar#removeNonStandardWeek removeNonStandardWeek} methods), or use a special widget: {@link Gnt.widget.calendar.Calendar}
    </p>

- `Date` - the date for this day in the ISO 8601 format. Any time information in this field will be cleared. If this instance
  represents a weekday or week override, this field will be ignored.
- `Weekday` - the index of the week day (0 - Sunday, 1 - Monday and so on) if this instance contains information about the week day (applicable for `WEEKDAY` and `WEEKDAYOVERRIDE`).
Should be set to -1 for the "main" instance of the week overrides.
- `OverrideStartDate` - The start date of the timespan for week day override.
- `OverrideEndDate` - The end date of the timespan for week day override.
- `Name` - optional name of the day (holiday name for example)
- `Cls` - optional name of the CSS class, which can be used by various plugins working with weekends and holidays. Default value is `gnt-holiday`
If a holiday lasts for several days, then all days should have the same `Cls` value.
- `IsWorkingDay` - optional boolean flag, allowing you to specify exceptions - working days which falls on weekends. Default value is `false`. **Please note**, that simply setting this
field to "true" is not enough - you also need to specify the exact hours that are available for work with the `Availability` field (see below).
- `Availability` - should be an array of strings, containing the hourly availability for this day. Strings should have the following format:

        // two working intervals
        [ '08:00-12:00', '13:00-17:00' ]

        // whole 24 hours are available
        [ '00:00-24:00' ]
* **Please note**, that this field overrides the `IsWorkingDay` - for example, a day with "IsWorkingDay : false" and "Availability : [ '08:00-12:00' ]" - will be considered as
working day.

The name of any field can be customized in the subclass. Please refer to {@link Sch.model.Customizable} for details.

*/
Ext.define('Gnt.model.CalendarDay', {
    extend      : 'Sch.model.CalendarDay',

    customizableFields      : [
        {
            name            : 'Cls',
            defaultValue    : 'gnt-holiday' // For backwards compatibility
        }
    ]
});
