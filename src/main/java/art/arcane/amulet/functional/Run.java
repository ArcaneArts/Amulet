package art.arcane.amulet.functional;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Run
{
    @FunctionalInterface
    interface Throws<T extends java.lang.Throwable>
    {
        void run() throws T;
    }

    @FunctionalInterface interface IO extends Throws<IOException> { }
    @FunctionalInterface interface Throwable extends Throws<java.lang.Throwable> { }
    @FunctionalInterface interface UnsupportedOp extends Throws<UnsupportedOperationException> { }
    @FunctionalInterface interface Execution extends Throws<ExecutionException> { }
}
