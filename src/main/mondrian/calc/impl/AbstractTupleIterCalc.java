/*
// $Id$
// This software is subject to the terms of the Common Public License
// Agreement, available at the following URL:
// http://www.opensource.org/licenses/cpl.html.
// Copyright (C) 2006-2008 Julian Hyde
// All Rights Reserved.
// You must accept the terms of that agreement to use this software.
*/
package mondrian.calc.impl;

import mondrian.olap.*;
import mondrian.olap.type.SetType;
import mondrian.calc.*;

/**
 * Abstract implementation of the {@link mondrian.calc.ListCalc} interface
 * for expressions that return a list of members but never a list of tuples.
 *
 * <p>The derived class must
 * implement the {@link #evaluateTupleIterable(mondrian.olap.Evaluator)} method,
 * and the {@link #evaluate(mondrian.olap.Evaluator)} method will call it.
 *
 * @see mondrian.calc.impl.AbstractListCalc
 *
 * @author jhyde
 * @version $Id$
 * @since Oct 24, 2008
 */
public abstract class AbstractTupleIterCalc
    extends AbstractCalc
    implements TupleIterCalc
{
    private final Calc[] calcs;

    /**
     * Creates an abstract implementation of a compiled expression which
     * returns a list.
     *
     * @param exp Expression which was compiled
     * @param calcs List of child compiled expressions (for dependency
     *   analysis)
     */
    protected AbstractTupleIterCalc(Exp exp, Calc[] calcs) {
        super(exp);
        this.calcs = calcs;
        assert getType().getArity() > 1;
    }

    public SetType getType() {
        return (SetType) super.getType();
    }

    public final Object evaluate(Evaluator evaluator) {
        return evaluateTupleIterable(evaluator);
    }

    public Calc[] getCalcs() {
        return calcs;
    }

    public ResultStyle getResultStyle() {
        return ResultStyle.ITERABLE;
    }

    public String toString() {
        return "AbstractTupleIterCalc object";
    }

    // override return type
    public final Iterable<Member[]> evaluateIterable(Evaluator evaluator) {
        return evaluateTupleIterable(evaluator);
    }
}

// End AbstractTupleIterCalc.java