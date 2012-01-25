package com.hubspot.techtalk;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class GuavaFunctionComposition {

    static enum Parity {
        EVEN, ODD;
    }

    public static class ParityDecider implements Function<Integer, Parity> {
        public Parity apply(Integer num) {
            if (num % 2 == 0)
                return Parity.EVEN;
            return Parity.ODD;
        }
    }

    public static class NameSpace {
        private final String namespace;

        public NameSpace(String str) {
            this.namespace = String.format("foo.nums.%s", str);
        }

        public String toString() {
            return Objects.toStringHelper(this).add("namesace", namespace).toString();
        }
    }
    
    public static class NameSpacer implements Function<Parity, NameSpace> {
        public NameSpace apply(Parity parity) {
            switch (parity) {
                case EVEN:
                return new NameSpace("even");
                case ODD:
                return new NameSpace("odd");
            }
            throw new IllegalArgumentException("No NameSpace found for Parity " + parity.name());
        }
    }

    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        Function<Integer, NameSpace> intToNameSpace = Functions.compose(new NameSpacer(), new ParityDecider());
        List<NameSpace> nses = Lists.transform(ints, intToNameSpace);
        System.out.println(nses);
    }

}
