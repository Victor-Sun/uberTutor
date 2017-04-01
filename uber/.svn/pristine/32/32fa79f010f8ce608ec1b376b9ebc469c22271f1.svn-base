/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * This class is handling the drawing of scheduled record dependencies
 */
Ext.define("Sch.view.dependency.Painter", {

    alias : 'schdependencypainter.default',

    mixins : [
        'Ext.mixin.Factoryable'
    ],

    uses : [
        'Ext.Array',
        'Ext.XTemplate',
        'Ext.dom.Query',
        'Sch.util.Date',
        'Sch.util.RectangularPathFinder',
        'Sch.template.Dependency'
    ],

    config : {
        /**
         * @cfg {String} selectedCls
         *
         * Selected line's CSS class
         */
        selectedCls : 'sch-dependency-selected',
        /**
         * @cfg {String} cls
         *
         * Line's optional/user defined CSS class
         */
        cls : '',
        /**
         * @cfg {Boolean} rtl
         *
         * true if application is running in RTL mode
         */
        rtl               : false,
        /**
         * @cfg {String/Array/Ext.XTemplate} lineTpl
         *
         * Line template
         */
        lineTpl           : null,
        /**
         * @cfg {String} canvasCls
         *
         * Canvas element CSS class
         */
        canvasCls         : null,
        /**
         * @cfg {Object} pathFinderConfig
         *
         * Path finder instance configuration
         */
        pathFinderConfig  : null,
        /**
         * @cfg {Number} realLineThickness
         *
         * The real dependency line thickness. Visually it will be always 1px, but actual line element with or height
         * is different to allow proper catching pointer events. By default it's set by CSS rules, but for the testing
         * purposes we might override the CSS rules with exact value.
         *
         * @private
         */
        realLineThickness : null
    },

    // Private
    pathFinder       : null,
    dependencyIdAttr : 'data-sch-dependency-id',

    constructor : function(config) {
        var me = this;

        me.initConfig(config);
        me.pathFinder = me.createPathFinder(me.getPathFinderConfig());

        if (!me.getLineTpl()) {
            me.setLineTpl(new Sch.template.Dependency({
                rtl         : me.getRtl()
            }));
        }
    },

    /**
     * Clones this painter
     *
     * @return {Sch.view.dependency.Painter}
     */
    clone : function() {
        var me = this;
        return new me.self(me.getConfig());
    },

    applyLineTpl : function(tpl) {
        return tpl instanceof Ext.XTemplate ? tpl : new Ext.XTemplate(tpl);
    },

    updatePathFinderConfig : function(config) {
        var me = this;

        if (me.pathFinder) {

            // In this case we are to re-create path finder instance since path finder type is changed
            if (config && 'type' in config && config.type !== me.pathFinder.type) {

                Ext.destroy(me.pathFinder);
                me.pathFinder = me.createPathFinder(config);
            }
            // In this case we just update current painter configuration
            else {
                me.pathFinder.setConfig(config);
            }
        }
    },

    /**
     * Create path finder instance
     *
     * @protected
     */
    createPathFinder : function(config) {
        return Sch.util.RectangularPathFinder.create(config);
    },

    /**
     * Returns painter canvas element {@link Ext.dom.Helper} specification
     *
     * @return {Mixed}
     */
    getCanvasSpecification : function() {
        return {
            tag  : 'div',
            role : 'presentation',
            cls  : this.getCanvasCls()
        };
    },

    /**
     * Draws dependencies on `el` whereas `el` is supposed to be a canvas created using {@getCanvasSpecification the provided specification}
     *
     * @param {Ext.view.View} primaryView See primary view interface in {Sch.view.dependency.View dependency view} description
     * @param {String/HtmlElement/Ext.Element} canvasEl
     * @param {Sch.model.Dependency/Sch.model.Dependency[]} dependencies
     * @param {Boolean} overwrite
     */
    paint : function(primaryView, canvasEl, dependencies, overwrite) {
        var me = this,
            markup = me.generatePaintMarkup(primaryView, dependencies);

        canvasEl = Ext.fly(canvasEl);

        if (overwrite) {
            var tmp = document.createElement('div');
            tmp.innerHTML = markup;
            canvasEl.syncContent(tmp);
        }
        else {
            canvasEl.insertHtml('beforeEnd', markup);
        }
    },

    /**
     * Generates paint markup
     *
     * @param {Ext.view.View} primaryView See primary view interface in {Sch.view.dependency.View dependency view} description
     * @param {Sch.model.Dependency/Sch.model.Dependency[]} dependencies
     * @return {String}
     */
    generatePaintMarkup : function(primaryView, dependencies) {
        var me = this,
            lineDefs = me.getLineDefsForDependencies(primaryView, dependencies);

        if (!Ext.isArray(lineDefs)) {
            lineDefs = [ lineDefs ];
        }

        return Ext.Array.map(lineDefs, function(lineDef) {
            var tplData = me.getLineTplData(lineDef);
            return tplData && me.getLineTpl().apply(tplData) || '';
        }).join('');
    },


    /**
     * Returns true if element passed is an element visualizing a dependency
     *
     * @param {HTMLElement/Ext.dom.Element/String} el
     * @return {Boolean}
     */
    isDependencyElement : function(el) {
        return Ext.fly(el).is('.sch-dependency');
    },

    /**
     * Retrieves the canvas elements representing a particular dependency or dependencies
     *
     * @param {String/HtmlElement/Ext.Element} canvasEl
     * @param {Sch.model.Dependency/Sch.model.Dependency[]} dependencies dependency record(s)
     * @return {Ext.CompositeElementLite/Ext.CompositeElement/false}
     */
    getElementsForDependency : function(canvasEl, dependencies) {
        var EDQ = Ext.dom.Query,
            me  = this;

        if (dependencies && !Ext.isArray(dependencies)) {
            dependencies = [ dependencies ];
        }

        return new Ext.dom.CompositeElementLite(
            Ext.Array.reduce(dependencies || [], function(result, dependency) {
                return result.concat(EDQ.select('[' + me.dependencyIdAttr + '="' + dependency.internalId + '"]', Ext.getDom(canvasEl)));
            }, [])
        );
    },

    /**
     * Returns all the elements on the canvas representing the rendered dependencies
     *
     * @param {String/HtmlElement/Ext.Element} canvasEl
     * @return {Ext.CompositeElementLite/Ext.CompositeElement}
     */
    getDependencyElements : function(canvasEl) {
        var canvasDom = Ext.getDom(canvasEl);

        return new Ext.dom.CompositeElementLite(
            canvasDom && canvasDom.childNodes || []
        );
    },

    /**
     * If the element passed constitutes a dependency line then returns the dependency record id this element
     * represents, otherwise returns empty string
     *
     * @param {HTMLElement/Ext.dom.Element/String} el
     * @return {String}
     */
    getElementDependencyInternalId : function(el) {
        return Ext.fly(el).getAttribute(this.dependencyIdAttr);
    },

    /**
     * Highlight the elements representing a particular dependency
     *
     * @param {String/HtmlElement/Ext.Element} canvasEl
     * @param {Sch.model.Dependency/Sch.model.Dependency[]} dependency Dependency model instance
     * @param {String} [cls] The CSS class to use for highlighting. Defaults to the selected-state CSS class.
     */
    highlightDependency : function(canvasEl, dependency, cls) {
        var me = this;

        me.getElementsForDependency(canvasEl, dependency).addCls(cls || me.getSelectedCls());
    },

    /**
     * Remove highlight of the elements representing a particular dependency
     *
     * @param {String/HtmlElement/Ext.Element} canvasEl
     * @param {Sch.model.Dependency/Sch.model.Dependency[]} dependency Dependency model instance
     * @param {String} [cls] The CSS class to used for highlighting. Defaults to the selected-state CSS class.
     */
    unhighlightDependency : function(canvasEl, dependency, cls) {
        var me = this;

        me.getElementsForDependency(canvasEl, dependency).removeCls(cls || me.getSelectedCls());
    },

    /**
     * Gets selected (highlighted) dependency elements
     *
     * @param {String/HtmlElement/Ext.Element} canvasEl
     * @return {Ext.CompositeElementLite/Ext.CompositeElement/false}
     */
    getSelectedDependencyElements : function(canvasEl) {
        var me = this;

        return Ext.fly(canvasEl).select('.' + me.getSelectedCls());
    },

    /**
     * Remove highlighting from any dependency line currently highlighted
     * @param {String/HtmlElement/Ext.Element} canvasEl
     */
    clearSelectedDependencies : function(canvasEl) {
        var me = this;

        me.getDependencyElements(canvasEl).removeCls(me.getSelectedCls());
    },

    /**
     * Converts line definition into line data applicable to line template.
     *
     * @param {Object} lineDef
     * @return {Object|false}
     * @return {String}         return.id
     * @return {[Object]}       return.segments
     * @return {Object|Boolean} return.startArrow
     * @return {Object|Boolean} return.endArrow
     * @protected
     */
    getLineTplData : function(lineDef) {
        var me = this,
            rtl,
            realLineThickness,
            firstSegment,
            lastSegment,
            result;

        lineDef = Ext.apply({}, lineDef, me.getConfig());

        result = me.pathFinder.findPath(lineDef);

        if (result) {

            // Check if we only need to render one vertical line, due to both tasks being outside of view
            if (!lineDef.startBox.rendered && !lineDef.endBox.rendered) {

                for (var i = result.length - 1; i >= 0; i--) {
                    var line = result[ i ];

                    if (line.x1 === line.x2) {
                        result                 = [ line ];
                        lineDef.startArrowSize = lineDef.endArrowSize = 0;

                        break;
                    }
                }
            }

            rtl               = me.getRtl();
            realLineThickness = me.getRealLineThickness();

            firstSegment = result.length && result[0];
            lastSegment  = result.length && result[result.length - 1];

            result = {
                cls : lineDef.cls || '',

                dependencyId : lineDef.dependencyId || '',

                isHighlighted : lineDef.isHighlighted,

                segments : Ext.Array.map(result, function(segment) {
                    var dir = me.getSegmentDir(segment),
                        result;

                    if (dir == 'horizontal') {
                        result = {
                            width  : Math.abs(segment.x1 - segment.x2) + 1,
                            height : realLineThickness,
                            top    : Math.min(segment.y1, segment.y2),
                            side   : Math.min(segment.x1, segment.x2),
                            dir    : dir
                        };
                    }
                    else {
                        result = {
                            height : Math.abs(segment.y1 - segment.y2) + 1,
                            width  : realLineThickness,
                            top    : Math.min(segment.y1, segment.y2),
                            side   : Math.min(segment.x1, segment.x2),
                            dir    : dir
                        };
                    }

                    return result;
                }),

                startArrow : lineDef.startArrowSize && {
                    side : firstSegment.x1,
                    top  : firstSegment.y1,
                    dir  : me.convertSideToDir(lineDef.startSide, rtl)
                },

                endArrow : lineDef.endArrowSize && {
                    side : lastSegment.x2,
                    top  : lastSegment.y2,
                    dir  : me.convertSideToDir(lineDef.endSide, rtl)
                },

                realLineThickness : me.getRealLineThickness()
            };
        }

        return result;
    },

    /**
     * @param {Sch.view.SchedulingView}
     * @param {Sch.model.Dependency[]}
     * @return {Object[]}
     * @protected
     */
    getLineDefsForDependencies : function(primaryView, dependencies) {
        var EA     = Ext.Array,
            me     = this,
            viewId = primaryView.getId(),
            viewStartDate = primaryView.timeAxis.getStart(),
            viewEndDate = primaryView.timeAxis.getEnd(),
            cache  = {},
            internalId;

        if (!Ext.isArray(dependencies)) {
            dependencies = [ dependencies ];
        }

        return EA.reduce(dependencies || [], function(result, dependency) {
            var source = dependency.getSourceEvent(),
                target = dependency.getTargetEvent(),
                sourceBoxes,
                targetBoxes,
                dateRange = dependency.getDateRange();

            if (dateRange && Sch.util.Date.intersectSpans(dateRange.start, dateRange.end, viewStartDate, viewEndDate)) {

                // Getting source boxes
                internalId = source.internalId;

                if (!cache[internalId]) {

                    sourceBoxes = me.getItemBox(primaryView, source) || [];

                    if (!Ext.isArray(sourceBoxes)) {
                        sourceBoxes = [ sourceBoxes ];
                    }

                    cache[internalId] = sourceBoxes;
                }
                else {
                    sourceBoxes = cache[internalId];
                }

                // Getting target boxes
                internalId = target.internalId;

                if (!cache[internalId]) {

                    targetBoxes = me.getItemBox(primaryView, target) || [];

                    if (!Ext.isArray(targetBoxes)) {
                        targetBoxes = [ targetBoxes ];
                    }

                    cache[internalId] = targetBoxes;
                }
                else {
                    targetBoxes = cache[internalId];
                }

                // Create line definitions for each item box cartesian multiplication
                result = EA.reduce(sourceBoxes, function(result, sourceBox, sourceBoxIdx) {
                    return EA.reduce(targetBoxes, function(result, targetBox, targetBoxIdx) {
                        if (sourceBox && targetBox && (sourceBox.rendered || targetBox.rendered || sourceBox.relPos != targetBox.relPos)) {
                            result.push(me.createLineDef(primaryView, dependency, source, target, sourceBox, targetBox, null));
                        }
                        return result;
                    }, result);
                }, result);
            }

            return result;
        }, []);
    },

    /**
     * Returns all the boxes a painter shall take into account, which corresponds to the given record
     *
     * @param {Ext.data.Model} itemRecord
     * @return {Object/Object[]}
     * @protected
     */
    getItemBox : function(primaryView, itemRecord) {
        return primaryView.getItemBox(itemRecord);
    },

    /**
     * Creates dependency line definition recognized by path finder
     *
     * @param {Ext.view.View} primaryView
     * @param {Sch.model.Dependency} dependency
     * @param {Ext.data.Model} source
     * @param {Ext.data.Model} target
     * @param {Object} sourceBox
     * @param {Object} targetBox
     * @param {Object[]/null} otherBoxes
     * @return {Object}
     * @protected
     */
    createLineDef : function(primaryView, dependency, source, target, sourceBox, targetBox, otherBoxes) {
        var DEP_TYPE         = dependency.self.Type,
            me               = this,
            type             = dependency.getType(),
            endShift         = 0,
            horizontalMargin = me.pathFinder.getHorizontalMargin(),
            verticalMargin   = me.pathFinder.getVerticalMargin(),
            bidirectional    = dependency.getBidirectional(),
            startArrowMargin = bidirectional ? me.pathFinder.getStartArrowMargin() : 0,
            startArrowSize   = bidirectional ? me.pathFinder.getStartArrowSize() : 0,
            endArrowMargin   = me.pathFinder.getEndArrowMargin(),
            endArrowSize     = me.pathFinder.getEndArrowSize(),
            startSide,
            endSide;

        switch (true) {
            case type == DEP_TYPE.StartToEnd:
                startSide = primaryView.getConnectorStartSide(source);
                endSide   = primaryView.getConnectorEndSide(target);
                break;

            case type == DEP_TYPE.StartToStart:
                startSide = primaryView.getConnectorStartSide(source);
                endSide   = primaryView.getConnectorStartSide(target);
                break;

            case type == DEP_TYPE.EndToStart:
                startSide = primaryView.getConnectorEndSide(source);
                endSide   = primaryView.getConnectorStartSide(target);
                break;

            case type == DEP_TYPE.EndToEnd:
                startSide = primaryView.getConnectorEndSide(source);
                endSide   = primaryView.getConnectorEndSide(target);
                break;

            default:
                throw 'Invalid dependency type: ' + dependency.getType();
        }

        // if we have source event on the same vertical level as the target one
        if (sourceBox.top === targetBox.top) {
            startArrowMargin = endArrowMargin = 0;

            // and they are too close to show the arrow(s), we hide arrow
            if (Math.abs(sourceBox.end - targetBox.start) < (endArrowSize + startArrowSize)) {
                startArrowSize = endArrowSize = 0;
            }
        }

        switch (true) {
            case (startSide == 'left' || startSide == 'right') && (endSide == 'left' || endSide == 'right'):
                verticalMargin   = 2;
                horizontalMargin = 5;
                break;
            
            case (startSide == 'top' || startSide == 'bottom') && (endSide == 'top' || endSide == 'bottom'):
                verticalMargin   = 7;
                horizontalMargin = 2;
        }

        var distance = Number.MAX_VALUE;

        var centerHorizontalPoint       = {
            from : sourceBox.start + (sourceBox.end - sourceBox.start) / 2,
            to   : targetBox.start + (targetBox.end - targetBox.start) / 2
        };

        var centerVerticalPoint         = {
            from : sourceBox.top + (sourceBox.bottom - sourceBox.top) / 2,
            to   : targetBox.top + (targetBox.bottom - targetBox.top) / 2
        };

        // if points are too close to show the arrow(s) they are hidden
        if ((startSide === 'top' && endSide === 'bottom' || startSide === 'bottom' && endSide === 'top') && centerHorizontalPoint.from === centerHorizontalPoint.to) {
            distance = Math.abs(sourceBox[startSide] - targetBox[endSide]);
        } else if ((startSide === 'left' && endSide === 'right' || startSide === 'right' && endSide === 'left') && centerVerticalPoint.from === centerVerticalPoint.to) {
            var sourceSide  = startSide === 'left' ? 'start' : 'end';
            var targetSide    = endSide === 'left' ? 'start' : 'end';

            if (primaryView.rtl) {
                sourceSide = sourceSide === 'start' ? 'end' : 'start';
                targetSide = targetSide   === 'start' ? 'end' : 'start';
            }

            var startX = sourceBox[sourceSide];
            var endX   = targetBox[targetSide];

            distance = Math.abs(startX - endX);
        }

        if (distance < (endArrowSize * endArrowMargin) * 2) {
            startArrowMargin = endArrowMargin = 0;

            if (distance <= endArrowSize * 2) {
                startArrowSize = endArrowSize = 0;
            }
        }

        return {
            startBox         : sourceBox,
            startSide        : startSide,
            startArrowSize   : startArrowSize,
            startArrowMargin : startArrowMargin,

            endBox           : targetBox,
            endSide          : endSide,
            endArrowSize     : endArrowSize,
            endArrowMargin   : endArrowMargin,

            verticalMargin   : verticalMargin,
            horizontalMargin : horizontalMargin,
            otherBoxes       : otherBoxes,
            dependencyId     : dependency.internalId,
            cls              : dependency.getCls(),
            isHighlighted    : dependency.isHighlighted // NOTE: this could be a non-persistent model field instead of property
        };
    },

    convertSideToDir : function(side, rtl) {
        return this.self.sideToDir[side + (rtl && '-rtl' || '')];
    },

    getSegmentDir : function(segment) {
        var dir = 'vertical';

        if (segment.y1 === segment.y2) {
            dir = 'horizontal';
        }

        return dir;
    },

    inheritableStatics : {
        /**
         * @private
         */
        sideToDir : {
            'left'       : 'right',
            'right'      : 'left',
            'top'        : 'down',
            'bottom'     : 'up',
            'left-rtl'   : 'left',
            'right-rtl'  : 'right',
            'top-rtl'    : 'down',
            'bottom-rtl' : 'up'
        }
    }
});
