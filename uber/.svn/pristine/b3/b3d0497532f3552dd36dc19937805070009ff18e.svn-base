/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
@class Gnt.plugin.Export
@extends Sch.plugin.Export

A plugin (ptype = 'gantt_export') for generating PDF/PNG out of a Gantt panel. NOTE: This plugin will make an Ajax request to the server, POSTing
 the HTML to be exported. The {@link #printServer} url must therefore be on the same domain as your application.

#Configuring/usage

To use this plugin, add it to your Gantt as any other plugin. It is also required to have [PhantomJS][1] and [Imagemagick][2]
installed on the server. The complete process of setting up a backend for this plugin can be found in the readme file inside export examples
as well as on our [blog][3]. Note that the export feature is currently not supported if your store is buffered.

    var gantt = Ext.create('Sch.panel.Gantt', {
        ...

        plugins         : [
            Ext.create('Gnt.plugin.Export', {
                // default values
                printServer: 'server.php'
            })
        ]
    });

Gantt will be extended with three new methods:

* {@link #setFileFormat}, which allows setting the format to which panel should be exported. Default format is `pdf`.

* {@link #showExportDialog}, which shows export settings dialog

        gantt.showExportDialog();

* {@link #doExport} which actually performs the export operation using {@link #defaultConfig} or provided config object :

        gantt.doExport(
            {
                format: "A5",
                orientation: "landscape",
                range: "complete",
                showHeader: true,
                exporterId: 'singlepage'
            }
        );

#Export options

In the current state, plugin gives few options to modify the look and feel of the generated document/image throught a dialog window :

{@img scheduler/images/export_dialog.png}

If no changes are made to the form, the {@link #defaultConfig} will be used.

##Export Range

This setting controls the timespan visible on the exported document/image. Three options are available here :

{@img scheduler/images/export_dialog_ranges.png}

###Complete schedule

Whole current timespan will be visible on the exported document.

###Date range

User can select the start and end dates (from the total timespan of the panel) visible on the exported document/image.

{@img scheduler/images/export_dialog_ranges_date.png}

###Current view

Timespan of the exported document will be set to the currently visible part of the time axis. User can control
the width of the time column and height of row.

{@img scheduler/images/export_dialog_ranges_current.png}

##Paper Format

This combo gives control of the size of the generated PDF document by choosing one from a list of supported ISO paper sizes : (`A5`, `A4`, `A3`, `Letter`).
Generated PDF has a fixed DPI value of 72. Dafault format is `A4`.

{@img scheduler/images/export_dialog_format.png}

##Orientation

This setting defines the orientation of the generated document.

{@img scheduler/images/export_dialog_orientation.png}

Default option is the `portrait` (horizontal) orientation :

{@img scheduler/images/export_dialog_portrait.png}

Second option is the `landscape` (vertical) orientation :

{@img scheduler/images/export_dialog_landscape.png}


##Custom export styling
A special "sch-export" CSS class is added to the body of the exported pages so that you can have special
styles in your exported chart.

[1]: http://www.phantomjs.org
[2]: http://www.imagemagick.org
[3]: http://bryntum.com/blog

*/
Ext.define('Gnt.plugin.Export', {
    extend              : 'Sch.plugin.Export',

    alias               : 'plugin.gantt_export',
    alternateClassName  : 'Gnt.plugin.PdfExport',

    requires            : [
        'Gnt.plugin.exporter.SinglePage',
        'Gnt.plugin.exporter.MultiPage',
        'Gnt.plugin.exporter.MultiPageVertical'
    ],

    buildExporters : function () {
        return [
            'Gnt.plugin.exporter.SinglePage',
            'Gnt.plugin.exporter.MultiPage',
            'Gnt.plugin.exporter.MultiPageVertical'
        ];
    },

    //override added to turn off vertical resizer in the dialog
    showExportDialog    : function() {
        this.exportDialogConfig.scrollerDisabled = true;

        this.callParent(arguments);
    }

});
