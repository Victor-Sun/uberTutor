/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.plugin.Printable
 @extends Sch.plugin.Printable

 Plugin (ptype = 'gantt_printable') for printing an Ext Gantt instance. To use this plugin, add it to the scheduler as a usual plugin. The plugin will add an additional `print` method to the ganttpanel.

 Please note that this plugin may in some cases not generate a perfect picture for example in Firefox, due to various limitations in the browsers print implementation.
 If you require a high quality print in that case, you should use the Export plugin instead and first export to PDF. Chrome, IE10+ will give the best results.

 The print plugin will open a print dialog. In the dialog there is a DPI field. The DPI (dots per inch) is a ratio how many browser pixels correspond to a `paper` inch.
 The default is set on 72 dots. Depending on the screen resolution, this value can be increased or decreased.
 Run some test prints, and adjust the DPI until the generated pages fit the print paper nicely.

 In the browsers print settings the `Background Graphics` option should be set to on.

        var gantt = Ext.create('Gnt.panel.Gantt', {

            plugins             : [
                Ext.create("Gnt.plugin.Printable")
            ],
            ...
        })

        gantt.print();


 In the opened print window, a special 'sch-print-body' CSS class will be added to the BODY element. You can use this to
 further customize the printed contents.

 */
Ext.define('Gnt.plugin.Printable', {
    extend              : 'Sch.plugin.Printable',

    alias               : 'plugin.gantt_printable',

    requires            : [
        'Gnt.plugin.exporter.MultiPage',
        'Gnt.plugin.exporter.MultiPageVertical'
    ],

    buildExporters : function () {
        return [
            'Gnt.plugin.exporter.MultiPage',
            'Gnt.plugin.exporter.MultiPageVertical'
        ];
    },

    // override added to turn off vertical resizer in the dialog
    showExportDialog    : function() {
        this.exportDialogConfig.scrollerDisabled = true;

        this.callParent(arguments);
    }

});
