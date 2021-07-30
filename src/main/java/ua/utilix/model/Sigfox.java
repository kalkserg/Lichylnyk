package sigfox;

import ua.utilix.model.SigfoxData;

public interface Sigfox {

    default SigfoxData parse(String id, String input, int sequence) {
        System.out.println("Лічильник " + id + ". Невідомий тип лічильника. Данні: " + input);
        return null;
    }

    enum TypeMessage {
        DAILY {
            @Override
            public String toString() {
                return "Щоденна передача";
            }
        },
        WEEKLY {
            @Override
            public String toString() {
                return "Щотижнева передача";
            }
        },
        INFO {
            @Override
            public String toString() {
                return "Щомісячна передача";
            }
        },
        EXTENDED {
            @Override
            public String toString() {
                return "Розширена щомісячна передача";
            }
        },
        COMMAND {
            @Override
            public String toString() {
                return "Командна передача";
            }
        },
        RESET {
            @Override
            public String toString() {
                return "Сброс";
            }
        },
        HOURLY {
            @Override
            public String toString() {
                return "Щогодинна передача";
            }
        },
        EVENT {
            @Override
            public String toString() {
                return "Передача за подією";
            }
        }
    }

    enum TypeError {
        MAGNETE {
            @Override
            public String toString() {
                return "Виявлено магнит! ";
            }
        },
        LEAK {
            @Override
            public String toString() {
                return "Виявлено течу! ";
            }
        },
        BURST {
            @Override
            public String toString() {
                return "Виявлено сплеск! ";
            }
        },
        DRY {
            @Override
            public String toString() {
                return "Нема води! ";
            }
        },
        REVERSE {
            @Override
            public String toString() {
                return "Виявлено реверс! ";
            }
        },
        NOERROR {
            @Override
            public String toString() {
                return "";
            }
        }
    }
}
