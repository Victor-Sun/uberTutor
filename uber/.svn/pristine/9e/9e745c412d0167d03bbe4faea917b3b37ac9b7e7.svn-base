/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Gnt.view.dependency.Painter', {

    extend : 'Sch.view.dependency.Painter',

    alias : 'schdependencypainter.ganttdefault',

    createLineDef : function (primaryView, dependency, source, target, sourceBox, targetBox, otherBoxes) {
        var DEP_TYPE         = dependency.self.Type,
            me               = this,
            horizontalMargin = me.pathFinder.getHorizontalMargin(),
            ganttRowHeight   = primaryView.getRowHeight(),
            result           = me.callParent(arguments);

        result.verticalMargin = Math.floor((ganttRowHeight - (sourceBox.bottom - sourceBox.top))/2);

        if (
            // This dependency type
            dependency.getType() == DEP_TYPE.EndToStart &&
            // Target box is below source box
            sourceBox.bottom < targetBox.top &&
            // Horizontal gap between source box end and target box start is less then 5px
            (sourceBox.end - targetBox.start < horizontalMargin)
        ) {
            result.startShift = target.isMilestone() ? 0 : (horizontalMargin - (targetBox.end - targetBox.start) / 2);
            result.endSide    = 'top';
            result.verticalMargin = result.horizontalMargin = result.startArrowMargin = result.endArrowMargin = 0;
        }

        // Reversing start/end endpoints generate more Gantt-friendly arrows
        var endBox  = result.endBox;
        var endSide = result.endSide;

        result.startArrowSize   = result.endArrowSize;
        result.startArrowMargin = result.endArrowMargin;
        result.endArrowSize     = 0;
        result.endArrowMargin   = 0;

        result.endBox    = result.startBox;
        result.endSide   = result.startSide;
        result.startBox  = endBox;
        result.startSide = endSide;

        return result;
    }
});
