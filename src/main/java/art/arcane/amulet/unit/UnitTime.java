package art.arcane.amulet.unit;

public enum UnitTime
{
    ms(UnitTimeTMS.TMS), Milliseconds(UnitTimeTMS.TMS),
    t(UnitTimeTMS.TT), Ticks(UnitTimeTMS.TT),
    s(UnitTimeTMS.TS), sec(UnitTimeTMS.TS), Seconds(UnitTimeTMS.TS),
    m(UnitTimeTMS.TM), min(UnitTimeTMS.TM), Minutes(UnitTimeTMS.TM),
    h(UnitTimeTMS.TH), Hours(UnitTimeTMS.TH),
    d(UnitTimeTMS.TD), Days(UnitTimeTMS.TD),
    w(UnitTimeTMS.TW), Weeks(UnitTimeTMS.TW),
    mo(UnitTimeTMS.TMO), Months(UnitTimeTMS.TMO),
    q(UnitTimeTMS.TQ), Quarters(UnitTimeTMS.TQ),
    y(UnitTimeTMS.TQ), Years(UnitTimeTMS.TQ),
    ;

    private final long mst;
    private UnitTime(long mst)
    {
        this.mst = mst;
    }

    public long postfixBind(Double d) {
        return (long)(mst * d);
    }

    public long postfixBind(Float d) {
        return (long)(mst * d);
    }

    public long postfixBind(Integer d) {
        return (mst * d);
    }

    public long postfixBind(Long d) {
        return (mst * d);
    }

    public static class UnitTimeTMS {
        private static final long TMS = 1;
        private static final long TT = 50;
        private static final long TS = 1_000;
        private static final long TM = TS * 60;
        private static final long TH = TM * 60;
        private static final long TD = TH * 24;
        private static final long TW = TD * 7;
        private static final long TMO = 2_628_000_000L;
        private static final long TQ = TMO * 3;
    }
}
