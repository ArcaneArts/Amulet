package art.arcane.amulet.functional;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Run
{
    @FunctionalInterface
    public interface Throws<T extends java.lang.Throwable>
    {
        void run() throws T;
    }

    @FunctionalInterface public interface IO extends Throws<IOException> { }
    @FunctionalInterface public interface Throwable extends Throws<java.lang.Throwable> { }
    @FunctionalInterface public interface UnsupportedOp extends Throws<UnsupportedOperationException> { }
    @FunctionalInterface public interface Execution extends Throws<ExecutionException> { }
}
