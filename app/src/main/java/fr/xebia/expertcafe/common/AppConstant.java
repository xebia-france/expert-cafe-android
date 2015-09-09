package fr.xebia.expertcafe.common;

public interface AppConstant {

    enum Domain {
        MOBILE,
        UNKNOWN;

        public static Domain from(String from) {
            Domain domain;
            try {
                domain = Domain.valueOf(from);
            } catch (IllegalArgumentException e) {
                domain = UNKNOWN;
            }
            return domain;
        }
    }

    enum Time {
        TEN_0,
        TEN_1,
        TEN_2,
        TEN_3,
        ELEVEN_0,
        ELEVEN_1,
        ELEVEN_2,
        ELEVEN_3
    }

}
