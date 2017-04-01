/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.template.TaskTooltip
 @extends Ext.XTemplate

 Template class for rendering the task tooltip.
 */
Ext.define("Gnt.template.TaskTooltip", {
    extend         : 'Ext.XTemplate',
    mixins         : ['Gnt.mixin.Localizable'],

    disableFormats : true,
    dateFormat     : null,

    /**
     * @cfg {String} markup The tpl markup that will be passed to the XTemplate. Default `_startText_`, `_endText_`, `_percentText` and `_format_` will be localised in the constructor.
     */

    markup         : '<h2 class="sch-task-tip-header">{Name}</h2>' +
                     '<table class="sch-task-tip">' +
                     '<tr><td>_startText_:</td> <td align="right">{[this.getStartDateString(values)]}</td></tr>' +
                     '<tr><td>_endText_:</td> <td align="right">{[this.getEndDateString(values)]}</td></tr>' +
                     '<tr><td>_percentText_:</td><td align="right">{[this.getPercentDoneString(values)]}%</td></tr>' +
                     '</table>',

    constructor : function (markup) {

        this.markup = markup || this.markup;

        this.markup = this.markup.replace(/_startText_/,    this.L('startText'));
        this.markup = this.markup.replace(/_endText_/,      this.L('endText'));
        this.markup = this.markup.replace(/_percentText_/,  this.L('percentText'));
        this.dateFormat = this.L('format');

        this.callParent([this.markup]);
    },

    getStartDateString : function(data) {
        var task = data._record;
        var date = data._useBaselineData ? task.getBaselineStartDate() : task.getStartDate();

        return data._record.getDisplayStartDate(this.dateFormat, true, date, false, data._useBaselineData);
    },

    getEndDateString : function(data) {
        var task = data._record;
        var date = data._useBaselineData ? task.getBaselineEndDate() : task.getEndDate();

        return data._record.getDisplayEndDate(this.dateFormat, true, date, false, data._useBaselineData);
    },

    getPercentDoneString : function(data) {
        var task  = data._record;
        var value = data._useBaselineData ? task.getBaselinePercentDone() : task.getPercentDone();

        return Math.round(value);
    }

});

