/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * @class Gnt.feature.DependencyDragDrop
 * @extends Ext.util.Observable
 * @private
 * Internal class managing the interaction of setting up new dependencies using drag and drop between dependency terminals.
 */
Ext.define("Gnt.feature.DependencyDragDrop", {
    extend : 'Ext.util.Observable',

    mixins : {
        localizable : 'Gnt.mixin.Localizable'
    },

    requires : [
        'Gnt.feature.DependencyDragZone',
        'Gnt.feature.DependencyDropZone',
        'Ext.XTemplate'
    ],

    /**
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

     - fromText    : 'From: <strong>{0},</strong> {1},<br/>',
     - toText      : 'To: <strong>{0},</strong> {1},',
     - startText   : 'Start',
     - endText     : 'End'
     */

    /**
     * @cfg {Boolean} useLineProxy True to display a line while dragging
     */
    useLineProxy     : true,

    /**
     * @cfg {Object} dragZoneConfig
     * A custom config object used to configure the Ext.dd.DragZone instance.
     */
    dragZoneConfig  : null,

    /**
     * @cfg {Object} dropZoneConfig
     * A custom config object used to configure the Ext.dd.DropZone instance.
     */
    dropZoneConfig  : null,


    /**
     * @cfg {Object} toolTipTpl
     * A custom config object to configure the template for the drag tooltip.
     */
    toolTipTpl  : [
        '<div class="sch-dd-dependency">',
        '<table><tbody>',
        '<tr>',
        '<td><span class="sch-dd-dependency-from">{fromLabel}:</span></td>',
        '<td><span class="sch-dd-dependency-from-name">{fromTaskName}</span> - {fromSide}</td>',
        '</tr>',
        '<tr>',
        '<td><span class="sch-dd-dependency-to">{toLabel}:</span></td>',
        '<td><span class="sch-dd-dependency-to-name">{toTaskName}</span> - {toSide}</td>',
        '</tr>',
        '</tbody></table>',
        '</div>'
    ],

    // private, the terminal CSS selector
    terminalSelector : '.sch-gantt-terminal',
    el               : null,
    rtl              : null,
    ddGroup          : null,
    ganttView        : null,
    dependencyStore  : null,

    /**
     * @event beforednd
     * Fires before a drag and drop operation is initiated, return false to cancel it
     * @param {Gnt.feature.DependencyDragDrop} dnd The drag and drop instance
     * @param {Ext.data.Model} fromRecord The task record
     */

    /**
     * @event dndstart
     * Fires when a drag and drop operation starts
     * @param {Gnt.feature.DependencyDragDrop} dnd The drag and drop instance
     */

    /**
     * @event drop
     * Fires after a drop has been made on a receiving terminal
     * @param {Gnt.feature.DependencyDragDrop} dnd The drag and drop instance
     * @param {Mixed} fromId The source dependency task record id
     * @param {Mixed} toId The target dependency task record id
     * @param {Number} type The dependency type, see {@link Gnt.model.Dependency} for more information about possible values.
     */

    /**
     * @event afterdnd
     * Always fires after a dependency drag and drop operation
     * @param {Gnt.feature.DependencyDragDrop} dnd The drag and drop instance
     */

    constructor : function (config) {

        var view = config.ganttView;

        Ext.apply(this, config);

        this.ddGroup = view.id + '-sch-dependency-dd';

        // Lazy setup
        this.el.on('mousemove', this.doSetup, this, { single : true });

        this.callParent(arguments);
    },

    doSetup : function () {
        var me = this;

        // The drag zone behaviour
        this.dragZone = new Gnt.feature.DependencyDragZone(this.el, Ext.apply({
            rtl                    : this.rtl,
            terminalSelector       : this.terminalSelector,
            useLineProxy           : this.useLineProxy,
            ddGroup                : this.ddGroup,
            ganttView              : this.ganttView,
            startText              : this.L('startText'),
            endText                : this.L('endText'),
            fromText               : this.L('fromText'),
            toText                 : this.L('toText'),
            toolTipTpl             : Ext.XTemplate.getTpl(this, 'toolTipTpl')
        }, this.dragZoneConfig));


        this.relayEvents(this.dragZone, [
            'beforednd',
            'dndstart',
            'afterdnd'
        ]);

        this.dropZone = Ext.create("Gnt.feature.DependencyDropZone", this.el, Ext.apply({
            rtl              : this.rtl,
            terminalSelector : this.terminalSelector,
            ddGroup          : this.ddGroup,
            ganttView        : this.ganttView,
            dependencyStore  : this.dependencyStore,

            startText        : this.L('startText'),
            endText          : this.L('endText'),
            toText           : this.L('toText'),
            toolTipTpl       : Ext.XTemplate.getTpl(this, 'toolTipTpl')
        }, this.dropZoneConfig));

        this.relayEvents(this.dropZone, ['drop', 'afterdnd']);

        this.configureAllowedSourceTerminals();


        if (this.dependencyStore.allowedDependencyTypes) {
            // Define the allowed targets at drag start time
            this.dragZone.on('dndstart', this.configureAllowedTargetTerminals, this);
        } else {
            // Allow all types
            this.el.addCls(['sch-gantt-terminal-allow-target-start', 'sch-gantt-terminal-allow-target-end']);
        }
    },

    configureAllowedSourceTerminals : function () {
        var allowed = this.dependencyStore.allowedDependencyTypes;
        var classes = ['sch-gantt-terminal-allow-source-start', 'sch-gantt-terminal-allow-source-end'];

        if (allowed) {
            classes = [];

            if (Ext.Array.indexOf(allowed, 'EndToEnd') > -1 || Ext.Array.indexOf(allowed, 'EndToStart') > -1 ) {
                classes.push('sch-gantt-terminal-allow-source-end');
            }

            if (Ext.Array.indexOf(allowed, 'StartToStart') > -1 || Ext.Array.indexOf(allowed, 'StartToEnd') > -1) {
                classes.push('sch-gantt-terminal-allow-source-start');
            }
        }

        this.el.addCls(classes);
    },


    configureAllowedTargetTerminals : function () {
        var allowed = this.dependencyStore.allowedDependencyTypes;
        var classes = [];

        this.el.removeCls(['sch-gantt-terminal-allow-target-start', 'sch-gantt-terminal-allow-target-end']);

        if (Ext.Array.contains(allowed, 'EndToEnd') || Ext.Array.contains(allowed, 'StartToEnd')) {
            classes.push('sch-gantt-terminal-allow-target-end');
        }

        if (Ext.Array.contains(allowed, 'StartToStart') || Ext.Array.contains(allowed, 'EndToStart')) {
            classes.push('sch-gantt-terminal-allow-target-start');
        }

        this.el.addCls(classes);
    },


    destroy : function () {
        if (this.dragZone) {
            this.dragZone.destroy();
        }

        if (this.dropZone) {
            this.dropZone.destroy();
        }
    }
});
