package ru.nsu.mzaugolnikov.task333;

public class Div extends Expression {
    private final Expression chislitel;
    private final Expression znamenatel;

    public Div(Expression chislitel, Expression znamenatel) {
        this.chislitel = chislitel;
        this.znamenatel = znamenatel;
    }

    @Override
    public double eval(String values) {
        double znamVal = znamenatel.eval(values);
        if (znamVal == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return chislitel.eval(values) / znamVal;
    }

    @Override
    public Expression derivative(String var) {
        Expression fPrime = chislitel.derivative(var);
        Expression gPrime = znamenatel.derivative(var);

        Expression chis = new Sub(new Mult(fPrime, znamenatel.clone()), new Mult(chislitel.clone(), gPrime));
        Expression znam = new Mult(znamenatel.clone(), znamenatel.clone());

        return new Div(chis, znam);
    }

    @Override
    public Expression simplify() {
        Expression c = chislitel.simplify();
        Expression z = znamenatel.simplify();

        // если обе части числа, вычисляем результат
        if (c.isNumber() && z.isNumber()) {
            double zVal = z.getValue();
            if (zVal == 0) throw new ArithmeticException("Division by zero");
            return new Number(c.getValue() / zVal);
        }

        // 0 / x = 0
        if (c.isNumber() && c.getValue() == 0) return new Number(0);

        // x / 1 = x
        if (z.isNumber() && z.getValue() == 1) return c;

        // иначе оставляем деление как есть
        return new Div(c, z);
    }


    @Override
    public Expression clone() {
        return new Div(chislitel.clone(), znamenatel.clone());
    }

    @Override
    public String toString() {
        return "(" + chislitel.toString() + " / " + znamenatel.toString() + ")";
    }
}
