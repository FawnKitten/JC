package Main;

import Exceptions.InterpretException;
import Exceptions.UncaughtInvalidOperation;

public class LanguageType {

    public static Object divide(Object a, Object b) throws InterpretException {
        if (a instanceof Integer && b instanceof Integer)
            return ((Integer) a) / ((Integer) b);
        else if (a instanceof Float || b instanceof Float) {
            try {
                return ((Number) a).floatValue() / ((Number) b).floatValue();
            } catch (ClassCastException e) {
                // FATAL ERROR (Should be caught by Semantic Analysis)
                throw new UncaughtInvalidOperation("[FATAL ERROR] divide operation not supported between `Float' and non-number type");
            }
        }
        throw new UncaughtInvalidOperation("[ERROR] Unhandled operation: divide between `" + a.getClass().getName() + "' and `" + b.getClass().getName() + "'");
    }

    public static Object multiply(Object a, Object b) throws InterpretException {
        if (a instanceof Integer && b instanceof Integer)
            return ((Integer) a) * ((Integer) b);
        else if (a instanceof Float || b instanceof Float) {
            try {
                return ((Number) a).floatValue() * ((Number) b).floatValue();
            } catch (ClassCastException e) {
                // FATAL ERROR (Should be caught by Semantic Analysis)
                throw new UncaughtInvalidOperation("[FATAL ERROR] multiply operation not supported between `Float' and non-number type");
            }
        }
        throw new UncaughtInvalidOperation("[ERROR] Unhandled operation: multiply between `" + a.getClass().getName() + "' and `" + b.getClass().getName() + "'");
    }

    public static Object subtract(Object a, Object b) throws InterpretException {
        if (a instanceof Integer && b instanceof Integer)
            return ((Integer) a) - ((Integer) b);
        else if (a instanceof Float || b instanceof Float) {
            try {
                return ((Number) a).floatValue() - ((Number) b).floatValue();
            } catch (ClassCastException e) {
                // FATAL ERROR (Should be caught by Semantic Analysis)
                throw new UncaughtInvalidOperation("[FATAL ERROR] subtract operation not supported between `Float' and non-number type");
            }
        }
        throw new UncaughtInvalidOperation("[ERROR] Unhandled operation: subtract between `" + a.getClass().getName() + "' and `" + b.getClass().getName() + "'");
    }

    public static Object add(Object a, Object b) throws InterpretException {
        if (a instanceof Integer && b instanceof Integer)
            return ((Integer) a) + ((Integer) b);
        else if (a instanceof Float || b instanceof Float) {
            try {
                return ((Number) a).floatValue() + ((Number) b).floatValue();
            } catch (ClassCastException e) {
                // FATAL ERROR (Should be caught by Semantic Analysis)
                throw new UncaughtInvalidOperation("[FATAL ERROR] add operation not supported between `Float' and non-number type");
            }
        }
        throw new UncaughtInvalidOperation("[ERROR] Unhandled operation: add between `" + a.getClass().getName() + "' and `" + b.getClass().getName() + "'");
    }

    public static Object greaterThan(Object a, Object b) throws InterpretException {
        if (a instanceof Integer && b instanceof Integer)
            return ((Integer) a) > ((Integer) b);
        else if (a instanceof Float || b instanceof Float) {
            try {
                return ((Number) a).floatValue() > ((Number) b).floatValue();
            } catch (ClassCastException e) {
                // FATAL ERROR (Should be caught by Semantic Analysis)
                throw new UncaughtInvalidOperation("[FATAL ERROR] greaterThan operation not supported between `Float' and non-number type");
            }
        }
        throw new UncaughtInvalidOperation("[ERROR] Unhandled operation: greaterThan between `" + a.getClass().getName() + "' and `" + b.getClass().getName() + "'");
    }

    public static Object lessThan(Object a, Object b) throws InterpretException {
        if (a instanceof Integer && b instanceof Integer)
            return ((Integer) a) < ((Integer) b);
        else if (a instanceof Float || b instanceof Float) {
            try {
                return ((Number) a).floatValue() < ((Number) b).floatValue();
            } catch (ClassCastException e) {
                // FATAL ERROR (Should be caught by Semantic Analysis)
                throw new UncaughtInvalidOperation("[FATAL ERROR] lessThan operation not supported between `Float' and non-number type");
            }
        }
        throw new UncaughtInvalidOperation("[ERROR] Unhandled operation: lessThan between `" + a.getClass().getName() + "' and `" + b.getClass().getName() + "'");
    }

    public static Object equals(Object a, Object b) {
        return a.equals(b);
    }

    public static Object and(Object a, Object b) throws InterpretException {
        if (a instanceof Integer && b instanceof Integer)
            return (((Integer) a) != 0) && (((Integer) b) != 0);
        throw new UncaughtInvalidOperation("[ERROR] Unhandled operation: and between `" + a.getClass().getName() + "' and `" + b.getClass().getName() + "'");
    }

    public static Object or(Object a, Object b) throws InterpretException {
        if (a instanceof Integer && b instanceof Integer)
            return (((Integer) a) != 0) || (((Integer) b) != 0);
        throw new UncaughtInvalidOperation("[ERROR] Unhandled operation: or between `" + a.getClass().getName() + "' and `" + b.getClass().getName() + "'");
    }

    public static Object not(Object value) {
        return (((Integer) value) != 0) ? 1 : 0;
    }
}
