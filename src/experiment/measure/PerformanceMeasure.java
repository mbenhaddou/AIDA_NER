package experiment.measure;

public class PerformanceMeasure {

	/**
     * The number of true positives.
     * <p>
     * 
     */
    public double tp;

    /**
     * The number of false positives.
     */
    public double fp;

    /**
     * The number of true negatives.
     * <p>
     */
    public double tn;

    /**
     * The number of false negatives.
     */
    public double fn;

    public double getCorrelationCoefficient() {

        return (tp * tn - fp * fn) / Math.sqrt((tp + fp) * (tp + fn) * (tn + fp) * (tn + fn));
    }

    public double getCost() {
        return fp / tp;
    }

    /**
     * Constructs a new performance measure using the supplied arguments.
     * 
     * @param tp
     *            the number of true positives
     * @param tn
     *            the number of true negatives
     * @param fp
     *            the number of false positives
     * @param fn
     *            the number of false negatives
     */
    public PerformanceMeasure(double tp, double tn, double fp, double fn) {
        this.tp = tp;
        this.tn = tn;
        this.fp = fp;
        this.fn = fn;

    }

    /**
     * Default constructor for a new performance measure, all values (TP,TN,FP
     * and FN) will be set zero
     */
    public PerformanceMeasure() {
        this(0, 0, 0, 0);
    }

    public double getTPRate() {
        return this.tp / (this.tp + this.fn);
    }

    public double getTNRate() {
        return this.tn / (this.tn + this.fp);
    }

    public double getFNRate() {
        return this.fn / (this.tp + this.fn);
    }

    public double getFPRate() {
        return this.fp / (this.fp + this.tn);
    }

    public double getErrorRate() {
        return (this.fp + this.fn) / this.getTotal();
    }

    public double getAccuracy() {
        return (this.tp + this.tn) / this.getTotal();
    }

    public double getRecall() {
        return this.tp / (this.tp + this.fn);
    }

    public double getPrecision() {
        return this.tp / (this.tp + this.fp);
    }

    public double getCorrelation() {
        return (this.tp * this.tn + this.fp * this.fn)
                / Math.sqrt((this.tn + this.fn) * (this.tp + this.fp) * (this.tn + this.fp) * (this.fn + this.tp));
    }

    /**
     * Calculates F-score for beta equal to 1.
     * 
     * @return f-score
     */
    public double getFMeasure() {
        return getFMeasure(1);
    }

    /**
     * Returns the F-score. When recall and precision are zero, this method will
     * return 0.
     * 
     * @param beta
     * @return f-score
     */
    public double getFMeasure(int beta) {
        double f = ((beta * beta + 1) * this.getPrecision() * this.getRecall())
                / (beta * beta * this.getPrecision() + this.getRecall());
        if (Double.isNaN(f))
            return 0;
        else
            return f;
    }

    public double getQ9() {
        if (this.tp + this.fn == 0) {
            return (this.tn - this.fp) / (this.tn + this.fp);
        } else if (this.tn + this.fp == 0) {
            return (this.tp - this.fn) / (this.tp + this.fn);
        } else
            return 1
                    - Math.sqrt(2)
                    * Math
                            .sqrt(Math.pow(this.fn / (this.tp + this.fn), 2)
                                    + Math.pow(this.fp / (this.tn + this.fp), 2));

    }

    public double getBCR() {
        if (tn == 0 && fp == 0)
            return tp / (tp + fn);
        if (tp == 0 && fn == 0)
            return tn / (tn + fp);

        return 0.5 * (tp / (tp + fn) + tn / (tn + fp));
    }

    @Override
    public String toString() {
        return "[TP=" + this.tp + ", FP=" + this.fp + ", TN=" + this.tn + ", FN=" + this.fn + "]";
    }

    public double getTotal() {
        return fn + fp + tn + tp;
    }

}
