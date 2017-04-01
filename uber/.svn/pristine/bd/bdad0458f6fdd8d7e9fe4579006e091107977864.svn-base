/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.assembla.com/spaces/bryntum/tickets/2405
// https://www.sencha.com/forum/showthread.php?307509
Ext.define('Sch.patches.TablePanel', {
    extend     : 'Sch.util.Patch',

    target     : 'Ext.panel.Table',

    applyFn : function () {
        var overrides = {};

        if (Ext.getVersion().equals('6.0.1.250')) {
            overrides.ensureVisible = function (record, options) {
                if (options && options.column && this.getVisibleColumnManager().indexOf(options.column) === -1) {
                    return;
                }

                this.callParent(arguments);
            };
        }

        // https://www.sencha.com/forum/showthread.php?310933
        //if (Ext.getVersion().equals('6.0.2.437')) {
        //    Ext.apply(overrides, {
        //        onRender: function() {
        //            // BUG: onRender implementation needs removing
        //            // Skip the bugged onRender in TablePanel
        //            this.callSuper(arguments);
        //        },
        //
        //        // BUG.
        //        // TablePanel needs beforeLayout on the first layout to measure the
        //        // width taken up by borders because in addition to the grid
        //        // border, the headerCt might have borders, so they need
        //        // to be subtracted from available width too.
        //        beforeLayout: function() {
        //            var me = this,
        //                gridPanelBorderWidth = me.gridPanelBorderWidth,
        //                totalColumnWidth;
        //
        //            // If this is the locked side, include border width in calculated locked grid width.
        //            // TODO: Use shrinkWrapDock on the locked grid's headerCt when it works.
        //            if (!gridPanelBorderWidth && me.isLocked && me.getSizeModel().width.shrinkWrap) {
        //                me.shrinkWrapColumns = true;
        //                totalColumnWidth = me.headerCt.getTableWidth();
        //                //<debug>
        //                if (isNaN(totalColumnWidth)) {
        //                    Ext.raise("Locked columns in an unsized locked side do NOT support a flex width.");
        //                }
        //                //</debug>
        //                gridPanelBorderWidth = me.gridPanelBorderWidth || (me.gridPanelBorderWidth = me.el.getBorderWidth('lr') + me.headerCt.el.getBorderWidth('lr'));
        //                me.width = totalColumnWidth + gridPanelBorderWidth;
        //            }
        //            me.callParent();
        //        }
        //    });
        //}

        Ext.ClassManager.get(this.target).override(overrides);
    }
});