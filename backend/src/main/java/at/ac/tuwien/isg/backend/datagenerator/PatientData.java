package at.ac.tuwien.isg.backend.datagenerator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public final class PatientData {

    public PatientData() {
        fillMedicationHashMap();
        fillTextHashMap();
    }

    public static HashMap<Diagnosis, List<Medication>> MEDICATION_MAP = new HashMap<>();
    public static HashMap<Diagnosis, List<String>> TEXT_MAP = new HashMap<>();
    public static final List<Diagnosis> DIAGNOSES = List.of(
        new Diagnosis("Diabetes mellitus Typ 2", "E11"),
        new Diagnosis("Diabetes mellitus Typ 1", "E10"),
        new Diagnosis("Arterielle Hypertonie", "I10"),
        new Diagnosis("Chronische Herzinsuffizienz", "I50"),
        new Diagnosis("Koronare Herzkrankheit", "I25"),

        new Diagnosis("Chronisch obstruktive Lungenerkrankung (COPD)", "J44"),
        new Diagnosis("Asthma bronchiale", "J45"),
        new Diagnosis("Chronische Gastritis", "K29"),
        new Diagnosis("Gastroösophageale Refluxkrankheit (GERD)", "K21"),
        new Diagnosis("Reizdarmsyndrom", "K58"),

        new Diagnosis("Chronische Niereninsuffizienz", "N18"),
        new Diagnosis("Leberzirrhose", "K74"),
        new Diagnosis("Nichtalkoholische Fettleber (NAFLD)", "K76.0"),
        new Diagnosis("Rheumatoide Arthritis", "M06"),
        new Diagnosis("Osteoarthritis (Arthrose)", "M19"),

        new Diagnosis("Osteoporose", "M81"),
        new Diagnosis("Chronische Migräne", "G43"),
        new Diagnosis("Epilepsie", "G40"),
        new Diagnosis("Parkinson-Krankheit", "G20"),
        new Diagnosis("Multiple Sklerose", "G35"),

        new Diagnosis("Hypothyreose", "E03"),
        new Diagnosis("Hyperthyreose", "E05"),
        new Diagnosis("Adipositas", "E66"),
        new Diagnosis("Hyperlipidämie", "E78"),
        new Diagnosis("Anämie (Eisenmangel)", "D50"),

        new Diagnosis("Depressive Störung", "F32"),
        new Diagnosis("Rezidivierende depressive Störung", "F33"),
        new Diagnosis("Generalisierte Angststörung", "F41.1"),
        new Diagnosis("Schizophrenie", "F20"),
        new Diagnosis("Bipolare affektive Störung", "F31"),

        new Diagnosis("Psoriasis", "L40"),
        new Diagnosis("Atopische Dermatitis", "L20"),
        new Diagnosis("Chronische Urtikaria", "L50"),
        new Diagnosis("Chronische Sinusitis", "J32"),
        new Diagnosis("Obstruktive Schlafapnoe", "G47.3"),

        new Diagnosis("Benigne Prostatahyperplasie (BPH)", "N40"),
        new Diagnosis("Endometriose", "N80"),
        new Diagnosis("Chronische Zystitis", "N30"),
        new Diagnosis("Glaukom", "H40"),
        new Diagnosis("Chronische Konjunktivitis", "H10"),

        new Diagnosis("Anorexia nervosa", "F50.0"),
        new Diagnosis("Bulimia nervosa", "F50.2"),
        new Diagnosis("Binge-Eating-Störung", "F50.8")
    );

    public static final List<Diagnosis> ACUTE_DIAGNOSIS = List.of(
        new Diagnosis("Pneumonie", "J18"),
        new Diagnosis("Masern", "B05"),
        new Diagnosis("Akute Bronchitis", "J20"),
        new Diagnosis("Grippe (Influenza)", "J10"),
        new Diagnosis("Magen-Darm-Infekt", "A09"),
        new Diagnosis("Harnwegsinfekt", "N39"),
        new Diagnosis("Akute Sinusitis", "J01"),
        new Diagnosis("Gastroenteritis", "A08"),
        new Diagnosis("Husten", "R05"),
        new Diagnosis("Akute Tonsillitis", "J03"),
        new Diagnosis("Schürfwunde", "S50"),
        new Diagnosis("Verstauchung", "S93"),
        new Diagnosis("Akute Otitis media", "H66"),
        new Diagnosis("Blasenentzündung", "N30"),
        new Diagnosis("Windpocken", "B01"),
        new Diagnosis("Grippaler Infekt", "J06"),
        new Diagnosis("Appendizitis", "K35"),
        new Diagnosis("Nasenbluten", "R04.0"),
        new Diagnosis("Verbrennung 1. Grades", "T20"),
        new Diagnosis("Lebensmittelvergiftung", "T62")
    );

    public static final List<String> FIRST_NAMES = List.of(
            "Anna", "Ben", "Clara", "David", "Emma",
            "Felix", "Greta", "Hannah", "Jonas", "Lea",
            "Lukas", "Marie", "Nico", "Olivia", "Paul",
            "Mia", "Tim", "Sophie", "Noah", "Lara",
            "Elias", "Paula", "Finn", "Julia", "Max",
            "Sarah", "Leon", "Laura", "Tom", "Lisa",
            "Jan", "Nina", "Markus", "Klara", "Alex",
            "Marlon", "Eva", "Tobias", "Franziska", "Matteo",
            "Isabella", "Samuel", "Mira", "Jule", "Raphael",
            "Selina", "Johanna", "Theo", "Amelia", "Kilian"
    );

    public static final List<String> LAST_NAMES = List.of(
            "Smith", "Garcia", "Müller", "Kim", "Hernandez",
            "Rodriguez", "Kumar", "Chen", "Silva", "Martinez",
            "Nguyen", "Lopez", "Ivanov", "Brown", "Yamamoto",
            "Singh", "Hassan", "Kowalski", "Santos", "Dubois",
            "Khalil", "Andersson", "Nowak", "Petrov", "Rossi",
            "O'Connor", "Bakker", "Huang", "Novak", "da Silva",
            "Rahman", "Larsen", "Khan", "Fischer", "Watanabe",
            "Nielsen", "Morales", "Sato", "Costa", "Johansson",
            "Dimitrov", "Ali", "Ferreira", "Papadopoulos", "Gonzalez",
            "Takahashi", "Verma", "Ramos", "Borges", "Stein"
    );

    public static final List<Address> ADDRESSES_LIST = List.of(
            new Address("1010", "Graben", "12"),
            new Address("1010", "Kärntner Straße", "28"),
            new Address("1020", "Praterstraße", "44"),
            new Address("1020", "Heinestraße", "7"),
            new Address("1030", "Landstraßer Hauptstraße", "59"),
            new Address("1030", "Rennweg", "102"),
            new Address("1040", "Wiedner Hauptstraße", "33"),
            new Address("1040", "Favoritenstraße", "12"),
            new Address("1050", "Reinprechtsdorfer Straße", "85"),
            new Address("1050", "Margaretenstraße", "41"),
            new Address("1060", "Mariahilfer Straße", "176"),
            new Address("1060", "Gumpendorfer Straße", "22"),
            new Address("1070", "Neubaugasse", "53"),
            new Address("1070", "Lerchenfelder Straße", "104"),
            new Address("1080", "Josefstädter Straße", "18"),
            new Address("1080", "Lange Gasse", "67"),
            new Address("1090", "Alser Straße", "24"),
            new Address("1090", "Währinger Straße", "92"),
            new Address("1100", "Favoritenstraße", "214"),
            new Address("1100", "Laxenburger Straße", "143"),
            new Address("1110", "Simmeringer Hauptstraße", "70"),
            new Address("1110", "Erdbergstraße", "199"),
            new Address("1120", "Meidlinger Hauptstraße", "45"),
            new Address("1120", "Schönbrunner Straße", "279"),
            new Address("1130", "Hietzinger Hauptstraße", "38"),
            new Address("1130", "Maxingstraße", "12"),
            new Address("1140", "Hadikgasse", "147"),
            new Address("1140", "Hütteldorfer Straße", "89"),
            new Address("1150", "Mariahilfer Gürtel", "3"),
            new Address("1150", "Sechshauser Straße", "98"),
            new Address("1160", "Ottakringer Straße", "156"),
            new Address("1160", "Thaliastraße", "48"),
            new Address("1170", "Hernalser Hauptstraße", "75"),
            new Address("1170", "Elterleinplatz", "10"),
            new Address("1180", "Währinger Gürtel", "119"),
            new Address("1180", "Kutschkergasse", "21"),
            new Address("1190", "Heiligenstädter Straße", "280"),
            new Address("1190", "Grinzinger Straße", "54"),
            new Address("1200", "Brigittenauer Lände", "23"),
            new Address("1200", "Wallengasse", "8"),
            new Address("1210", "Floridsdorfer Hauptstraße", "61"),
            new Address("1210", "Brünner Straße", "202")
        );


    private void fillMedicationHashMap() {
        MEDICATION_MAP.put(DIAGNOSES.get(0), List.of(
            new Medication("Metformin 1000mg", "1000", "0", "1000", "0", "Tablette"),
            new Medication("Dapagliflozin 10mg", "10", "0", "0", "0", "Tablette"),
            new Medication("Glimepirid 2mg", "2", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(1), List.of(
            new Medication("Insulin basal 20E", "20", "0", "0", "20", "Dosis"),
            new Medication("Insulin bolus 5E", "5", "5", "5", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(2), List.of(
            new Medication("Amlodipin 5mg", "5", "0", "0", "0", "Tablette"),
            new Medication("Valsartan 80mg", "80", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(3), List.of(
            new Medication("Bisoprolol 5mg", "5", "0", "0", "0", "Tablette"),
            new Medication("Furosemid 40mg", "40", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(4), List.of(
            new Medication("Atorvastatin 20mg", "20", "0", "0", "0", "Tablette"),
            new Medication("Acetylsalicylsäure 100mg", "100", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(5), List.of(
            new Medication("Tiotropium 18µg", "18", "0", "0", "0", "Dosis"),
            new Medication("Formoterol 12µg", "12", "0", "12", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(6), List.of(
            new Medication("Budesonid 200µg", "200", "0", "200", "0", "Dosis"),
            new Medication("Salbutamol 100µg", "0", "100", "0", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(7), List.of(
            new Medication("Pantoprazol 40mg", "40", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(8), List.of(
            new Medication("Esomeprazol 40mg", "40", "0", "40", "0", "Tablette"),
            new Medication("Gaviscon 500mg", "0", "500", "500", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(9), List.of(
            new Medication("Butylscopolamin 10mg", "10", "0", "10", "0", "Tablette"),
            new Medication("Probiotika 1 Kapsel", "1", "0", "0", "0", "Kapsel")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(10), List.of(
            new Medication("Sevelamer 800mg", "800", "800", "800", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(11), List.of(
            new Medication("Spironolacton 50mg", "50", "0", "50", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(12), List.of(
            new Medication("Vitamin E 400mg", "400", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(13), List.of(
            new Medication("Methotrexat 15mg", "15", "0", "0", "0", "Dosis"),
            new Medication("Folsäure 5mg", "0", "5", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(14), List.of(
            new Medication("Ibuprofen 400mg", "400", "400", "400", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(15), List.of(
            new Medication("Alendronat 70mg", "70", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(16), List.of(
            new Medication("Topiramat 50mg", "50", "0", "50", "0", "Tablette"),
            new Medication("Propranolol 40mg", "40", "0", "40", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(17), List.of(
            new Medication("Lamotrigin 100mg", "100", "0", "100", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(18), List.of(
            new Medication("Levodopa/Carbidopa 100/25mg", "100", "100", "100", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(19), List.of(
            new Medication("Interferon beta 30µg", "30", "0", "0", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(20), List.of(
            new Medication("Levothyroxin 100µg", "100", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(21), List.of(
            new Medication("Thiamazol 10mg", "10", "0", "10", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(22), List.of(
            new Medication("Orlistat 120mg", "120", "120", "120", "0", "Kapsel")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(23), List.of(
            new Medication("Simvastatin 20mg", "20", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(24), List.of(
            new Medication("Eisentabletten 100mg", "100", "0", "100", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(25), List.of(
            new Medication("Sertralin 50mg", "50", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(26), List.of(
            new Medication("Escitalopram 10mg", "10", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(27), List.of(
            new Medication("Duloxetin 30mg", "30", "0", "30", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(28), List.of(
            new Medication("Risperidon 2mg", "2", "0", "2", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(29), List.of(
            new Medication("Lithium 300mg", "300", "0", "300", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(30), List.of(
            new Medication("Cremetherapie 1 Anwendung", "1", "0", "1", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(31), List.of(
            new Medication("Cortisoncreme 1 Anwendung", "1", "0", "1", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(32), List.of(
            new Medication("Antihistaminikum 10mg", "10", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(33), List.of(
            new Medication("Nasenspray 2 Hub", "2", "0", "2", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(34), List.of(
            new Medication("CPAP Nutzung 1h", "1", "0", "0", "1", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(35), List.of(
            new Medication("Tamsulosin 0.4mg", "0.4", "0", "0", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(36), List.of(
            new Medication("Hormontherapie 1 Dosis", "1", "0", "1", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(37), List.of(
            new Medication("Antibiotikum 500mg", "500", "0", "500", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(38), List.of(
            new Medication("Augentropfen 1 Tropfen", "1", "0", "1", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(39), List.of(
            new Medication("Augentropfen mild 1 Tropfen", "1", "0", "1", "0", "Dosis")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(40), List.of(
            new Medication("Multivitaminpräparat 1 Tablette", "1", "0", "1", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(41), List.of(
            new Medication("Fluoxetin 20mg", "20", "0", "20", "0", "Tablette")
        ));

        MEDICATION_MAP.put(DIAGNOSES.get(42), List.of(
            new Medication("Lisdexamfetamin 30mg", "30", "0", "0", "0", "Tablette")
        ));

    }

    private void fillTextHashMap() {

        TEXT_MAP.put(DIAGNOSES.get(0), List.of(
            "Blutzuckerwerte erhöht, Patient berichtet über Müdigkeit.",
            "Patient hat seit 3 Monaten erhöhte HbA1c-Werte.",
            "Bericht über häufigen Durst und vermehrtes Wasserlassen.",
            "Ernährungsanpassung und Medikation besprochen.",
            "Kontrolle nach Änderung der Medikation, Werte stabil."
        ));


        TEXT_MAP.put(DIAGNOSES.get(1), List.of(
            "Patient berichtet über Schwäche und gelegentliche Hypoglykämien.",
            "Insulinpumpeneinstellung überprüft, Anpassung empfohlen.",
            "Blutzuckerwerte im Zielbereich, Patient stabil.",
            "Schulung zu Hypoglykämie-Symptomen durchgeführt.",
            "Kontrolle nach Ketoazidose, keine weiteren Symptome."
        ));


        TEXT_MAP.put(DIAGNOSES.get(2), List.of(
            "Blutdruck leicht erhöht, Lebensstilberatung gegeben.",
            "Kontrolle der Medikation, Werte im Zielbereich.",
            "Patient berichtet über Kopfschmerzen, kein Schwindel.",
            "Risikofaktoren besprochen, Blutwerte überprüft.",
            "Routineuntersuchung, Blutdruck stabil."
        ));


        TEXT_MAP.put(DIAGNOSES.get(3), List.of(
            "Patient berichtet über Dyspnoe bei Belastung.",
            "Kontrolle Gewicht und Ödeme, Medikation angepasst.",
            "Echokardiographie zeigt stabile Werte.",
            "Patient über Flüssigkeitsaufnahme beraten.",
            "Routinekontrolle, keine akuten Beschwerden."
        ));

        TEXT_MAP.put(DIAGNOSES.get(4), List.of(
            "Patient berichtet über Belastungsangina.",
            "EKG unauffällig, Kontrolle nach Infarkt.",
            "Medikation angepasst, Cholesterinwerte überprüft.",
            "Patient über Risikofaktoren aufgeklärt.",
            "Nachuntersuchung nach Stent-Implantation, Status stabil."
        ));

        TEXT_MAP.put(DIAGNOSES.get(5), List.of(
            "Patient berichtet über Husten und Auswurf.",
            "Inhalationstechnik überprüft, Medikation angepasst.",
            "Leichte Dyspnoe bei Belastung, Werte stabil.",
            "Raucherberatung durchgeführt.",
            "Kontrolle der Lungenfunktion, stabile Werte."
        ));

        TEXT_MAP.put(DIAGNOSES.get(6), List.of(
            "Patient berichtet über nächtliche Atemnot.",
            "Inhalationstechnik überprüft, Dosierung angepasst.",
            "Keine akuten Asthmaanfälle seit letzter Kontrolle.",
            "Peak-Flow-Werte stabil, Medikation ausreichend.",
            "Allergene identifiziert, Allergieplan besprochen."
        ));

        TEXT_MAP.put(DIAGNOSES.get(7), List.of(
            "Patient klagt über Oberbauchschmerzen nach Mahlzeiten.",
            "Ernährungsberatung durchgeführt, Medikation angepasst.",
            "H. pylori Test durchgeführt, Therapie geplant.",
            "Symptome leicht verbessert, Kontrolle vereinbart.",
            "Patient über Einnahme von Säureblockern informiert."
        ));


        TEXT_MAP.put(DIAGNOSES.get(8), List.of(
            "Sodbrennen tritt mehrmals wöchentlich auf.",
            "PPI-Dosierung angepasst, Lifestyle-Tipps gegeben.",
            "Patient berichtet über nächtliches Aufstoßen.",
            "Ernährungstagebuch empfohlen, Kontrolle in 4 Wochen.",
            "Symptome unter Medikation teilweise verbessert."
        ));


        TEXT_MAP.put(DIAGNOSES.get(9), List.of(
            "Bauchschmerzen und Blähungen seit 6 Wochen.",
            "Ballaststoffreiche Ernährung besprochen.",
            "Patient berichtet über wechselnde Stuhlgewohnheiten.",
            "Medikation zur Symptomlinderung angepasst.",
            "Stressbewältigungsstrategien empfohlen."
        ));


        TEXT_MAP.put(DIAGNOSES.get(10), List.of(
            "Patient berichtet über Müdigkeit und Wassereinlagerungen.",
            "Blutwerte zeigen erhöhte Kreatininwerte.",
            "Medikation zur Blutdruckkontrolle überprüft.",
            "Diätberatung zur Protein- und Salzreduktion durchgeführt.",
            "Routinekontrolle, keine akuten Beschwerden."
        ));


        TEXT_MAP.put(DIAGNOSES.get(11), List.of(
            "Patient berichtet über Müdigkeit und Gewichtsverlust.",
            "Laborkontrolle zeigt erhöhte Leberwerte.",
            "Ödeme an Unterschenkeln festgestellt.",
            "Diät- und Flüssigkeitsberatung durchgeführt.",
            "Kontrolle der Medikation, Patient stabil."
        ));


        TEXT_MAP.put(DIAGNOSES.get(12), List.of(
            "Patient berichtet über Müdigkeit.",
            "Leberwerte leicht erhöht, Ultraschall durchgeführt.",
            "Ernährungs- und Gewichtsmanagement besprochen.",
            "Alkoholkonsum überprüft, empfohlen zu reduzieren.",
            "Kontrolle nach 3 Monaten, Werte stabil."
        ));


        TEXT_MAP.put(DIAGNOSES.get(13), List.of(
            "Patient berichtet über Morgensteifigkeit der Gelenke.",
            "Schwellungen an Handgelenken und Fingern festgestellt.",
            "Medikation angepasst, Physiotherapie empfohlen.",
            "Entzündungswerte im Blut überprüft.",
            "Kontrolle nach 6 Wochen, Beschwerden leicht verbessert."
        ));

        TEXT_MAP.put(DIAGNOSES.get(14), List.of(
            "Patient berichtet über Knieschmerzen beim Treppensteigen.",
            "Medikation zur Schmerzreduktion angepasst.",
            "Physiotherapie empfohlen, Bewegungstagebuch angelegt.",
            "Röntgenbilder zeigen typische Arthroseveränderungen.",
            "Kontrolle nach 3 Monaten, Beschwerden stabil."
        ));

        TEXT_MAP.put(DIAGNOSES.get(15), List.of(
            "Knochendichtemessung durchgeführt, Werte reduziert.",
            "Vitamin-D- und Kalzium-Einnahme besprochen.",
            "Patient über Sturzprävention informiert.",
            "Medikation zur Knochendichteerhöhung angepasst.",
            "Kontrolle nach 6 Monaten geplant."
        ));

        TEXT_MAP.put(DIAGNOSES.get(16), List.of(
            "Patient berichtet über 4 Migräneanfälle pro Monat.",
            "Medikation zur Prophylaxe angepasst.",
            "Triggerfaktoren besprochen, Tagebuch empfohlen.",
            "Keine Aura-Symptome seit letzter Kontrolle.",
            "Kontrolle in 3 Monaten, Anfallshäufigkeit reduziert."
        ));

        TEXT_MAP.put(DIAGNOSES.get(17), List.of(
            "Patient berichtet über Anfälle alle 2-3 Wochen.",
            "Medikation überprüft und angepasst.",
            "Keine neuen Nebenwirkungen festgestellt.",
            "EEG durchgeführt, keine akuten Auffälligkeiten.",
            "Kontrolle in 3 Monaten, Anfälle stabil."
        ));

        TEXT_MAP.put(DIAGNOSES.get(18), List.of(
            "Patient berichtet über Zittern der rechten Hand.",
            "Medikation angepasst, Beweglichkeit verbessert.",
            "Muskelsteifigkeit leicht erhöht, Physiotherapie empfohlen.",
            "Gangbild stabil, keine Stürze berichtet.",
            "Kontrolle in 6 Wochen, Patient stabil."
        ));

        TEXT_MAP.put(DIAGNOSES.get(19), List.of(
            "Patient berichtet über Sensibilitätsstörungen im Bein.",
            "Medikation zur Schubprophylaxe überprüft.",
            "Keine neuen Schübe seit letzter Kontrolle.",
            "Physiotherapie und Ergotherapie empfohlen.",
            "Kontrolle in 3 Monaten, Patient stabil."
        ));

        TEXT_MAP.put(DIAGNOSES.get(20), List.of(
            "Patient berichtet über Müdigkeit und Gewichtszunahme.",
            "TSH-Werte leicht erhöht, Medikation angepasst.",
            "Blutdruck und Herzfrequenz stabil.",
            "Kontrolle nach 6 Wochen geplant.",
            "Patient über Symptome und Medikation aufgeklärt."
        ));

        TEXT_MAP.put(DIAGNOSES.get(21), List.of(
            "Patient berichtet über Gewichtsverlust und Herzrasen.",
            "TSH-Werte reduziert, Medikation angepasst.",
            "Angstsymptome besprochen.",
            "Kontrolle in 4 Wochen empfohlen.",
            "Patient über Nebenwirkungen der Medikation informiert."
        ));

        TEXT_MAP.put(DIAGNOSES.get(22), List.of(
            "BMI 32, Patient berichtet über geringe körperliche Aktivität.",
            "Ernährungsberatung durchgeführt.",
            "Sportprogramm empfohlen, Motivation besprochen.",
            "Blutwerte überprüft, Cholesterin leicht erhöht.",
            "Kontrolle nach 3 Monaten, Gewicht stabil."
        ));


        TEXT_MAP.put(DIAGNOSES.get(23), List.of(
            "LDL-Wert erhöht, Medikation angepasst.",
            "Ernährungsberatung zur Fettreduktion durchgeführt.",
            "Patient über Risiken aufgeklärt.",
            "Kontrolle in 3 Monaten geplant.",
            "Blutwerte leicht verbessert, Lebensstil besprochen."
        ));

        TEXT_MAP.put(DIAGNOSES.get(24), List.of(
            "Patient berichtet über Müdigkeit und Blässe.",
            "Eisenwerte niedrig, Eisensupplementierung begonnen.",
            "Ernährungsberatung durchgeführt.",
            "Kontrolle nach 6 Wochen, Werte steigen langsam.",
            "Nebenwirkungen der Medikation besprochen."
        ));

        TEXT_MAP.put(DIAGNOSES.get(25), List.of(
            "Patient berichtet über gedrückte Stimmung und Antriebslosigkeit.",
            "Medikation begonnen, Verhaltenstherapie empfohlen.",
            "Schlafprobleme besprochen.",
            "Suizidrisiko evaluiert, kein akuter Handlungsbedarf.",
            "Kontrolle in 2 Wochen geplant."
        ));

        TEXT_MAP.put(DIAGNOSES.get(26), List.of(
            "Patient berichtet über Rückfall der depressiven Symptome.",
            "Medikation angepasst, Psychotherapie fortgeführt.",
            "Antrieb und Konzentration leicht reduziert.",
            "Familienanamnese besprochen.",
            "Kontrolle in 2 Wochen geplant."
        ));

        TEXT_MAP.put(DIAGNOSES.get(27), List.of(
            "Patient berichtet über ständige Sorgen und Anspannung.",
            "Medikation angepasst, Entspannungstechniken empfohlen.",
            "Schlafprobleme bestehen weiterhin.",
            "Stressbewältigung besprochen.",
            "Kontrolle in 4 Wochen geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(28), List.of(
            "Patient berichtet über gelegentliche Halluzinationen.",
            "Medikation überprüft, Dosis stabil.",
            "Soziale Funktion weitgehend erhalten.",
            "Psychosoziale Betreuung empfohlen.",
            "Kontrolle in 4 Wochen geplant."
        ));

        TEXT_MAP.put(DIAGNOSES.get(29), List.of(
            "Patient berichtet über Stimmungsschwankungen.",
            "Medikation zur Stabilisierung angepasst.",
            "Schlafrhythmus besprochen.",
            "Frühwarnzeichen für Manie und Depression identifiziert.",
            "Kontrolle in 4 Wochen geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(30), List.of(
            "Patient berichtet über Schuppenflechte an Ellbogen und Knie.",
            "Topische Therapie angepasst.",
            "Juckreiz kontrolliert, leichte Besserung.",
            "UV-Therapie diskutiert.",
            "Kontrolle in 6 Wochen geplant."
        ));

        TEXT_MAP.put(DIAGNOSES.get(31), List.of(
            "Juckreiz und Rötung der Haut berichten.",
            "Cortisoncreme angepasst, Hautpflege besprochen.",
            "Patient über Auslöser informiert.",
            "Medikation zur Nacht verbessert.",
            "Kontrolle in 4 Wochen geplant."
        ));

        TEXT_MAP.put(DIAGNOSES.get(32), List.of(
            "Patient berichtet über wiederkehrende Quaddeln.",
            "Antihistaminika angepasst.",
            "Allergene überprüft, Trigger reduziert.",
            "Tagebuch der Symptome empfohlen.",
            "Kontrolle nach 4 Wochen geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(33), List.of(
            "Patient berichtet über verstopfte Nase und Druckgefühl.",
            "Nasenspray angepasst, Antibiotikum überprüft.",
            "Röntgenbefunde besprochen.",
            "Symptome über 3 Wochen persistierend.",
            "Kontrolle nach 4 Wochen geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(34), List.of(
            "Patient berichtet über Tagesmüdigkeit.",
            "CPAP-Gerät angepasst, Nutzung besprochen.",
            "Schlafprotokoll analysiert.",
            "Gewichtsmanagement empfohlen.",
            "Kontrolle nach 3 Monaten geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(35), List.of(
            "Patient berichtet über häufiges nächtliches Wasserlassen.",
            "Medikation angepasst, Ultraschall durchgeführt.",
            "Harnstrahl überwacht, Beschwerden leicht reduziert.",
            "Flüssigkeitsaufnahme besprochen.",
            "Kontrolle nach 3 Monaten geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(36), List.of(
            "Patientin berichtet über zyklusabhängige Schmerzen.",
            "Medikation zur Schmerzreduktion angepasst.",
            "Hormontherapie besprochen.",
            "Operation bisher nicht notwendig.",
            "Kontrolle nach 3 Monaten geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(37), List.of(
            "Patientin berichtet über Brennen beim Wasserlassen.",
            "Antibiotikum angepasst, Symptome leicht verbessert.",
            "Rezidive überprüft, Trinkverhalten besprochen.",
            "Urinwerte kontrolliert.",
            "Kontrolle nach 2 Wochen geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(38), List.of(
            "Patient berichtet über keine akuten Beschwerden.",
            "Augendruck gemessen, Medikation angepasst.",
            "Optikusbefund stabil.",
            "Medikation regelmäßig eingenommen.",
            "Kontrolle nach 3 Monaten geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(39), List.of(
            "Rötung und Brennen der Augen berichtet.",
            "Augentropfen angepasst, Hygiene besprochen.",
            "Bindehautentzündung leicht rückläufig.",
            "Allergische Komponente diskutiert.",
            "Kontrolle in 2 Wochen geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(40), List.of(
            "Patientin berichtet über Gewichtsverlust und Angst vor Essen.",
            "Ernährungsberatung und Psychotherapie besprochen.",
            "Vitalwerte stabil, BMI kritisch.",
            "Essprotokoll geführt, tägliche Gewichtskontrolle.",
            "Kontrolle in 1 Woche geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(41), List.of(
            "Patientin berichtet über Essanfälle und Erbrechen.",
            "Therapie mit Psychologe begonnen.",
            "Medikation angepasst.",
            "Gewichtskontrolle durchgeführt.",
            "Kontrolle in 2 Wochen geplant."
        ));


        TEXT_MAP.put(DIAGNOSES.get(42), List.of(
            "Patient berichtet über wiederholte Essanfälle.",
            "Therapie zur Verhaltenstherapie begonnen.",
            "Medikation überprüft.",
            "Gewichtsmanagement besprochen.",
            "Kontrolle nach 4 Wochen geplant."
        ));


    }


    public record Diagnosis(
        String description,
        String icd10
    ) {

    }

    public record Medication(
        String description,
        String morning,
        String midday,
        String evening,
        String night,
        String note
    ) {

    }

    public record Address(
        String zipCode,
        String streetName,
        String streetNumber
    ) {

    }
}
