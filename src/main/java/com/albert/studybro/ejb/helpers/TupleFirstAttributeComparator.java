package com.albert.studybro.ejb.helpers;

import com.albert.studybro.ejb.helpers.Tuple;
import java.util.Comparator;

/**
 *
 * @author espen1
 */
public class TupleFirstAttributeComparator implements Comparator<Tuple> {

    @Override
    public int compare(Tuple o1, Tuple o2) {
        double waitScore1 = (double) o1.x;
        double waitScore2 = (double) o2.x;
        return (int) Math.round(waitScore1 - waitScore2);
    }

}
