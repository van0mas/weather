package org.example.config.props;

public final class AppConstants {

    private AppConstants() {}

    public static final class Validation {
        private Validation() {}

        public static final int USERNAME_MIN_LENGTH = 3;
        public static final int USERNAME_MAX_LENGTH = 20;

        public static final int PASSWORD_MIN_LENGTH = 6;
        public static final int PASSWORD_MAX_LENGTH = 64;

        public static final String USERNAME_PATTERN = "^[A-Za-z_][A-Za-z0-9_]*$";
        public static final String USERNAME_PATTERN_MESSAGE =
                "Логин должен начинаться с буквы или _, и содержать только буквы, цифры и _";

        public static final class Messages {
            private Messages() {}

            public static final String FIELD_REQUIRED = "Поле обязательно для заполнения";

            public static final String USERNAME_SIZE = "Логин должен быть от "
                    + USERNAME_MIN_LENGTH
                    + " до "
                    + USERNAME_MAX_LENGTH
                    + " символов";

            public static final String PASSWORD_SIZE = "Пароль должен быть от "
                    + PASSWORD_MIN_LENGTH
                    + " до "
                    + PASSWORD_MAX_LENGTH
                    + " символов";

            public static final String PASSWORDS_MISMATCH = "Пароли не совпадают";
        }
    }

    public static final class Session {
        private Session() {}

        public static final int MAX_ACTIVE_SESSIONS = 3;
        public static final int LIFETIME_HOURS = 1;
        public static final int SCHEDULER_INTERVAL_MINUTES = 20;
    }

    public static final class Cookie {
        private Cookie() {}

        public static final int SESSION_MAX_AGE_HOURS = Session.LIFETIME_HOURS;
        public static final String SESSION_NAME = "SESSIONID";
    }

    public static final class Paths {
        private Paths() {}
        public static final String AUTH = "/auth";
        public static final String REGISTER = "/register";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";

        public static final String WEATHER = "/weather";
        public static final String SEARCH = "/search";
        public static final String ADD = "/add";
        public static final String DELETE = "/delete";
    }

    public static final class SecuredPaths {
        public static final String HOME = "/";
        public static final String LOGOUT = Paths.AUTH + Paths.LOGOUT;
        public static final String WEATHER_ALL = Paths.WEATHER + "/**";
    }

    public static final class Templates {
        private Templates() {}
        public static final String REGISTER = "register";
        public static final String LOGIN = "login";
        public static final String INDEX = "index";
        public static final String SEARCH = "search";
    }

    public static final class Redirects {
        private Redirects() {}
        public static final String AUTH_LOGIN = "redirect:" + Paths.AUTH + Paths.LOGIN;
        public  static final String HOME = "redirect:/";
    }
}
