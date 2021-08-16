package sigfox;

import ua.utilix.model.SigfoxData;
import ua.utilix.model.Water5;

public interface Sigfox {

    default SigfoxData parse(String id, String input, int sequence) {
        System.out.println("default Лічильник " + id + ". Невідомий тип лічильника. Данні: " + input);
        return null;
    }

    default SigfoxData parse(String id, String input, int sequence, Water5 startValue) {
        System.out.println("Water5 Лічильник " + id + ". Невідомий тип лічильника. Данні: " + input);
        return null;
    }

    default SigfoxData parse(String id, String input, int sequence, String dec) {
        System.out.println("Kamstrup Лічильник " + id + ". Невідомий тип лічильника. Данні: " + input);
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
        },
        INTERVAL {
            @Override
            public String toString() { return "Передача через інтервал"; }
        }
    }

    enum TypeError {
        NOERROR {
            @Override
            public String toString() {
                return "";
            }
        },
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
        TAMPER {
            @Override
            public String toString() {
                return "Виявлено втручання! ";
            }
        },
        FREEZING {
            @Override
            public String toString() {
                return "Виявлено обмерзання! ";
            }
        },
        BATTERYALARM {
            @Override
            public String toString() {
                return "Виявлено критичний заряд батареї! ";
            }
        },
        OVERRANGE {
            @Override
            public String toString() {
                return "Виявлено перевищення допустимого діапазону! ";
            }
        },
        TEMPERATUREALARM {
            @Override
            public String toString() {
                return "Виявлено перевищення допустимої температури! ";
            }
        },
        EEPROM {
            @Override
            public String toString() {
                return "Виявлено помилку пам'яті! ";
            }
        },
        SHORTCIRCUIT {
            @Override
            public String toString() {
                return "Виявлено замикання температурного датчика! ";
            }
        },
        SENSORBREAK {
            @Override
            public String toString() {
                return "Виявлено несправність температурного датчика! ";
            }
        },
        TEMPERATURELESS {
            @Override
            public String toString() {
                return "Температура води меньша 3 С! ";
            }
        },
        TEMPERATUREMORE {
            @Override
            public String toString() {
                return "Температура води більша 95 С! ";
            }
        }
    }
}
