package com.generation.hairlab.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.enums.AppointmentStatus;
import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.enums.ConsultationType;
import com.generation.hairlab.enums.FeasibilityStatus;
import com.generation.hairlab.enums.HairTexture;
import com.generation.hairlab.enums.HairType;
import com.generation.hairlab.enums.JobTitle;
import com.generation.hairlab.enums.MixingRatio;
import com.generation.hairlab.enums.Oxygen;
import com.generation.hairlab.enums.PhysicalValue;
import com.generation.hairlab.enums.ProductType;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.Specialization;
import com.generation.hairlab.enums.ToneLevel;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.AppointmentItem;
import com.generation.hairlab.model.ColorFormula;
import com.generation.hairlab.model.ColorFormulaItem;
import com.generation.hairlab.model.Consultation;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.model.Employee;
import com.generation.hairlab.model.HairDye;
import com.generation.hairlab.model.HairProfile;
import com.generation.hairlab.model.ProductCategory;
import com.generation.hairlab.model.SalonProduct;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Classe utilizzata per popolare automaticamente il database con dati mock
 * all'avvio dell'applicazione Spring Boot.
 *
 * Implementa CommandLineRunner: il metodo run() viene eseguito automaticamente
 * dopo l'avvio del contesto Spring e dopo l'inizializzazione di JPA/Hibernate.
 *
 * I dati vengono inseriti rispettando l'ordine delle relazioni:
 *
 * 1. Employee, Customer e ProductCategory
 * 2. SalonProduct e HairDye
 * 3. HairProfile
 * 4. Appointment
 * 5. AppointmentItem
 * 6. Consultation
 * 7. ColorFormula
 * 8. ColorFormulaItem
 *
 * Questo ordine è importante perché non è possibile, ad esempio, creare un
 * AppointmentItem che fa riferimento a un Appointment, Employee o SalonProduct
 * che non sono ancora stati salvati nel database.
 *
 * La classe verifica inoltre che il database sia vuoto prima di inserire i
 * dati, evitando duplicazioni a ogni riavvio dell'applicazione.
 */
@Component
public class MockDataDb implements CommandLineRunner {

    /**
     * EntityManager permette di salvare direttamente le Entity tramite JPA.
     *
     * In questa fase del progetto è utile perché non richiede di avere già
     * creato un Repository separato per ogni Entity.
     *
     * Quando saranno disponibili tutti i Repository, sarà eventualmente
     * possibile riscrivere il seeder utilizzando i repository stessi.
     */
    @PersistenceContext
    private EntityManager entityManager;


    /**
     * Metodo eseguito automaticamente da Spring Boot all'avvio.
     *
     * @param args eventuali argomenti passati all'applicazione.
     */
    @Override
    @Transactional
    public void run(String... args) {

        /*
         * Evita di inserire nuovamente i dati mock se il database
         * contiene già dati applicativi.
         */
        if (!isDatabaseEmpty()) {

            System.out.println(
                "HairLab MockDataDb: database già popolato, caricamento mock ignorato."
            );

            return;
        }

        System.out.println(
            "HairLab MockDataDb: creazione dati mock..."
        );


        /*
         * ============================================================
         * 1. DIPENDENTI
         * ============================================================
         *
         * Vengono creati prima degli appuntamenti e delle consulenze
         * perché entrambe queste Entity fanno riferimento a Employee.
         */

        Employee anna = new Employee();

        anna.setFirstName("Anna");
        anna.setLastName("Rossi");
        anna.setEmail("anna.rossi@hairlab.mock");
        anna.setTelephoneNumber("3331000001");
        anna.setJobTitle(JobTitle.COLORIST);

        anna.setSpecializations(
            new HashSet<>(
                Set.of(
                    Specialization.HAIR_COLOR,
                    Specialization.BALAYAGE,
                    Specialization.COLOR_CORRECTION,
                    Specialization.BLEACHING
                )
            )
        );

        anna.setHireDate(
            LocalDate.now().minusYears(4)
        );

        anna.setActive(true);

        anna.setNotes(
            "Colorista specializzata in balayage e correzioni colore."
        );

        entityManager.persist(anna);


        Employee luca = new Employee();

        luca.setFirstName("Luca");
        luca.setLastName("Bianchi");
        luca.setEmail("luca.bianchi@hairlab.mock");
        luca.setTelephoneNumber("3331000002");
        luca.setJobTitle(JobTitle.HAIR_STYLIST);

        luca.setSpecializations(
            new HashSet<>(
                Set.of(
                    Specialization.WOMENS_CUT,
                    Specialization.MENS_CUT,
                    Specialization.PIXIE_CUT,
                    Specialization.BOB_CUT,
                    Specialization.BLOW_DRY
                )
            )
        );

        luca.setHireDate(
            LocalDate.now().minusYears(3)
        );

        luca.setActive(true);

        luca.setNotes(
            "Hair stylist specializzato in taglio e styling."
        );

        entityManager.persist(luca);


        Employee marco = new Employee();

        marco.setFirstName("Marco");
        marco.setLastName("Verdi");
        marco.setEmail("marco.verdi@hairlab.mock");
        marco.setTelephoneNumber("3331000003");
        marco.setJobTitle(JobTitle.BARBER);

        marco.setSpecializations(
            new HashSet<>(
                Set.of(
                    Specialization.MENS_CUT,
                    Specialization.BEARD_GROOMING,
                    Specialization.SHAVING
                )
            )
        );

        marco.setHireDate(
            LocalDate.now().minusYears(2)
        );

        marco.setActive(true);

        marco.setNotes(
            "Barber specializzato in taglio uomo e barba."
        );

        entityManager.persist(marco);


        /*
         * ============================================================
         * 2. CLIENTI
         * ============================================================
         */

        Customer maria = new Customer();

        maria.setFirstName("Maria");
        maria.setLastName("Esposito");
        maria.setPhoneNumber("3202000001");
        maria.setEmail("maria.esposito@mock.it");

        maria.setDob(
            LocalDate.of(1995, 5, 14)
        );

        maria.setActive(true);

        maria.setCreatedAt(
            LocalDateTime.now().minusMonths(8)
        );

        maria.setUpdatedAt(
            LocalDateTime.now().minusDays(10)
        );

        entityManager.persist(maria);


        Customer giulia = new Customer();

        giulia.setFirstName("Giulia");
        giulia.setLastName("Romano");
        giulia.setPhoneNumber("3202000002");
        giulia.setEmail("giulia.romano@mock.it");

        giulia.setDob(
            LocalDate.of(1988, 11, 3)
        );

        giulia.setActive(true);

        giulia.setCreatedAt(
            LocalDateTime.now().minusMonths(5)
        );

        giulia.setUpdatedAt(
            LocalDateTime.now().minusDays(5)
        );

        entityManager.persist(giulia);


        Customer sara = new Customer();

        sara.setFirstName("Sara");
        sara.setLastName("Ferrari");
        sara.setPhoneNumber("3202000003");
        sara.setEmail("sara.ferrari@mock.it");

        sara.setDob(
            LocalDate.of(2001, 2, 21)
        );

        sara.setActive(true);

        sara.setCreatedAt(
            LocalDateTime.now().minusMonths(2)
        );

        sara.setUpdatedAt(
            LocalDateTime.now().minusDays(2)
        );

        entityManager.persist(sara);


        /*
         * ============================================================
         * 3. CATEGORIE DEI SERVIZI
         * ============================================================
         */

        ProductCategory haircutCategory = new ProductCategory();

        haircutCategory.setName("TAGLIO");
        haircutCategory.setDesc("Servizi professionali di taglio.");
        haircutCategory.setActive(true);

        entityManager.persist(haircutCategory);


        ProductCategory colorCategory = new ProductCategory();

        colorCategory.setName("COLORE");
        colorCategory.setDesc("Servizi tecnici di colorazione e schiaritura.");
        colorCategory.setActive(true);

        entityManager.persist(colorCategory);


        ProductCategory stylingCategory = new ProductCategory();

        stylingCategory.setName("STYLING");
        stylingCategory.setDesc("Servizi di piega e styling.");
        stylingCategory.setActive(true);

        entityManager.persist(stylingCategory);


        ProductCategory treatmentCategory = new ProductCategory();

        treatmentCategory.setName("TRATTAMENTI");
        treatmentCategory.setDesc("Trattamenti professionali per capelli e cute.");
        treatmentCategory.setActive(true);

        entityManager.persist(treatmentCategory);


        /*
         * ============================================================
         * 4. SERVIZI DEL SALONE
         * ============================================================
         *
         * Ogni SalonProduct appartiene a una ProductCategory.
         */

        SalonProduct womensCut = new SalonProduct();

        womensCut.setProductCategory(haircutCategory);
        womensCut.setName("Taglio donna");
        womensCut.setDesc("Taglio personalizzato con consulenza iniziale.");
        womensCut.setDuration(45);
        womensCut.setBasePrice(35.00);
        womensCut.setActive(true);

        entityManager.persist(womensCut);


        SalonProduct balayage = new SalonProduct();

        balayage.setProductCategory(colorCategory);
        balayage.setName("Balayage");
        balayage.setDesc(
            "Servizio di schiaritura personalizzata con tecnica balayage."
        );

        balayage.setDuration(180);
        balayage.setBasePrice(120.00);
        balayage.setActive(true);

        entityManager.persist(balayage);


        SalonProduct fullColor = new SalonProduct();

        fullColor.setProductCategory(colorCategory);
        fullColor.setName("Colore completo");
        fullColor.setDesc(
            "Colorazione completa di radici, lunghezze e punte."
        );

        fullColor.setDuration(90);
        fullColor.setBasePrice(65.00);
        fullColor.setActive(true);

        entityManager.persist(fullColor);


        SalonProduct blowDry = new SalonProduct();

        blowDry.setProductCategory(stylingCategory);
        blowDry.setName("Piega");
        blowDry.setDesc("Lavaggio e piega professionale.");
        blowDry.setDuration(40);
        blowDry.setBasePrice(25.00);
        blowDry.setActive(true);

        entityManager.persist(blowDry);


        SalonProduct reconstruction = new SalonProduct();

        reconstruction.setProductCategory(treatmentCategory);
        reconstruction.setName("Trattamento ricostruttivo");
        reconstruction.setDesc(
            "Trattamento professionale per capelli sensibilizzati."
        );

        reconstruction.setDuration(30);
        reconstruction.setBasePrice(40.00);
        reconstruction.setActive(true);

        entityManager.persist(reconstruction);


        /*
         * ============================================================
         * 5. TINTE / PRODOTTI TECNICI
         * ============================================================
         */

        HairDye dye51 = new HairDye();

        dye51.setBrand("Wella");
        dye51.setName("Castano chiaro cenere");
        dye51.setCode("WELLA-5-1");

        dye51.setProductType(
            ProductType.COLOR
        );

        dye51.setToneLevel(
            ToneLevel.LEVEL_5_LIGHT_BROWN
        );

        dye51.setPrimaryReflection(
            Reflection.ASH
        );

        dye51.setSecondaryReflection(null);
        dye51.setActive(true);

        entityManager.persist(dye51);


        HairDye dye60 = new HairDye();

        dye60.setBrand("Wella");
        dye60.setName("Biondo scuro naturale");
        dye60.setCode("WELLA-6-0");

        dye60.setProductType(
            ProductType.COLOR
        );

        dye60.setToneLevel(
            ToneLevel.LEVEL_6_DARK_BLONDE
        );

        dye60.setPrimaryReflection(
            Reflection.NATURAL
        );

        dye60.setSecondaryReflection(null);
        dye60.setActive(true);

        entityManager.persist(dye60);


        HairDye toner81 = new HairDye();

        toner81.setBrand("HairLab Professional");
        toner81.setName("Toner cenere perla");
        toner81.setCode("HL-8-ASH-PEARL");

        toner81.setProductType(
            ProductType.TONER
        );

        toner81.setToneLevel(
            ToneLevel.LEVEL_8_LIGHT_BLONDE
        );

        toner81.setPrimaryReflection(
            Reflection.ASH
        );

        toner81.setSecondaryReflection(
            Reflection.PEARL
        );

        toner81.setActive(true);

        entityManager.persist(toner81);


        /*
         * ============================================================
         * 6. HAIR PROFILE
         * ============================================================
         *
         * Ogni HairProfile viene creato dopo il relativo Customer,
         * perché contiene una relazione OneToOne con esso.
         */

        HairProfile mariaProfile = new HairProfile();

        mariaProfile.setCustomer(maria);

        mariaProfile.setNaturalTone(
            ToneLevel.LEVEL_5_LIGHT_BROWN
        );

        mariaProfile.setCurrentTone(
            ToneLevel.LEVEL_6_DARK_BLONDE
        );

        mariaProfile.setReflection(
            Reflection.GOLD
        );

        mariaProfile.setHairType(
            HairType.WAVY
        );

        mariaProfile.setTexture(
            HairTexture.FINE
        );

        mariaProfile.setPorosity(
            PhysicalValue.HIGH
        );

        mariaProfile.setDensity(
            PhysicalValue.MEDIUM
        );

        mariaProfile.setScalpCondition(
            new HashSet<>(
                Set.of(
                    "CUTE NORMALE"
                )
            )
        );

        mariaProfile.setChemicalHistory(
            new HashSet<>(
                Set.of(
                    "COLORE PERMANENTE",
                    "SCHIARITURE PRECEDENTI"
                )
            )
        );

        mariaProfile.setSensitivities(
            new HashSet<>(
                Set.of(
                    "LUNGHEZZE SENSIBILIZZATE"
                )
            )
        );

        mariaProfile.setContraindications(
            new HashSet<>()
        );

        mariaProfile.setNotes(
            "Capello fine con maggiore porosità sulle lunghezze."
        );

        entityManager.persist(mariaProfile);


        HairProfile giuliaProfile = new HairProfile();

        giuliaProfile.setCustomer(giulia);

        giuliaProfile.setNaturalTone(
            ToneLevel.LEVEL_4_MEDIUM_BROWN
        );

        giuliaProfile.setCurrentTone(
            ToneLevel.LEVEL_4_MEDIUM_BROWN
        );

        giuliaProfile.setReflection(
            Reflection.NATURAL
        );

        giuliaProfile.setHairType(
            HairType.STRAIGHT
        );

        giuliaProfile.setTexture(
            HairTexture.MEDIUM
        );

        giuliaProfile.setPorosity(
            PhysicalValue.LOW
        );

        giuliaProfile.setDensity(
            PhysicalValue.HIGH
        );

        giuliaProfile.setScalpCondition(
            new HashSet<>(
                Set.of(
                    "CUTE LEGGERMENTE SECCA"
                )
            )
        );

        giuliaProfile.setChemicalHistory(
            new HashSet<>(
                Set.of(
                    "NESSUN TRATTAMENTO CHIMICO RECENTE"
                )
            )
        );

        giuliaProfile.setSensitivities(
            new HashSet<>()
        );

        giuliaProfile.setContraindications(
            new HashSet<>()
        );

        giuliaProfile.setNotes(
            "Capello naturale, forte e con buona densità."
        );

        entityManager.persist(giuliaProfile);


        /*
         * ============================================================
         * 7. APPUNTAMENTI
         * ============================================================
         */

        Appointment appointment1 = new Appointment();

        appointment1.setCustomer(maria);

        appointment1.setStartDateTime(
            LocalDateTime.now().minusDays(20)
                .withHour(9)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
        );

        appointment1.setStatus(
            AppointmentStatus.COMPLETED
        );

        appointment1.setNotes(
            "Appuntamento colore e taglio."
        );

        appointment1.setCreatedAt(
            LocalDateTime.now().minusDays(30)
        );

        appointment1.setUpdatedAt(
            LocalDateTime.now().minusDays(20)
        );

        entityManager.persist(appointment1);


        Appointment appointment2 = new Appointment();

        appointment2.setCustomer(giulia);

        appointment2.setStartDateTime(
            LocalDateTime.now().minusDays(10)
                .withHour(14)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
        );

        appointment2.setStatus(
            AppointmentStatus.COMPLETED
        );

        appointment2.setNotes(
            "Taglio e piega."
        );

        appointment2.setCreatedAt(
            LocalDateTime.now().minusDays(18)
        );

        appointment2.setUpdatedAt(
            LocalDateTime.now().minusDays(10)
        );

        entityManager.persist(appointment2);


        /*
         * ============================================================
         * 8. APPOINTMENT ITEM
         * ============================================================
         *
         * Vengono creati dopo Appointment, Employee e SalonProduct
         * perché dipendono da tutte e tre queste Entity.
         */

        AppointmentItem itemColor = new AppointmentItem();

        itemColor.setAppointment(appointment1);
        itemColor.setSalonProduct(fullColor);
        itemColor.setEmployee(anna);

        itemColor.setScheduledTime(
            appointment1.getStartDateTime()
        );

        itemColor.setDuration(90);
        itemColor.setAgreedPrice(65.00);

        itemColor.setResultNotes(
            "Colorazione uniforme con neutralizzazione dei riflessi caldi."
        );

        itemColor.setCompletedAt(
            appointment1.getStartDateTime().plusMinutes(90)
        );

        entityManager.persist(itemColor);


        AppointmentItem itemCutMaria = new AppointmentItem();

        itemCutMaria.setAppointment(appointment1);
        itemCutMaria.setSalonProduct(womensCut);
        itemCutMaria.setEmployee(luca);

        itemCutMaria.setScheduledTime(
            appointment1.getStartDateTime().plusMinutes(90)
        );

        itemCutMaria.setDuration(45);
        itemCutMaria.setAgreedPrice(35.00);

        itemCutMaria.setResultNotes(
            "Taglio bob leggermente graduato."
        );

        itemCutMaria.setCompletedAt(
            appointment1.getStartDateTime().plusMinutes(135)
        );

        entityManager.persist(itemCutMaria);


        AppointmentItem itemCutGiulia = new AppointmentItem();

        itemCutGiulia.setAppointment(appointment2);
        itemCutGiulia.setSalonProduct(womensCut);
        itemCutGiulia.setEmployee(luca);

        itemCutGiulia.setScheduledTime(
            appointment2.getStartDateTime()
        );

        itemCutGiulia.setDuration(45);
        itemCutGiulia.setAgreedPrice(35.00);

        itemCutGiulia.setResultNotes(
            "Taglio di mantenimento."
        );

        itemCutGiulia.setCompletedAt(
            appointment2.getStartDateTime().plusMinutes(45)
        );

        entityManager.persist(itemCutGiulia);


        /*
         * ============================================================
         * 9. CONSULTATION
         * ============================================================
         */

        Consultation mariaConsultation = new Consultation(

            maria,

            anna,

            appointment1,

            appointment1.getStartDateTime().minusDays(5),

            ConsultationType.HAIR_COLOR,

            "Ottenere un castano chiaro più freddo eliminando "
                + "i riflessi dorati indesiderati.",

            "Capello precedentemente colorato, fibra fine e "
                + "lunghezze sensibilizzate.",

            "Base livello 6 con riflesso caldo-dorato.",

            FeasibilityStatus.FEASIBLE_WITH_LIMITATIONS,

            "Possibile aumento della sensibilizzazione sulle lunghezze.",

            "Colorazione fredda controllata con trattamento "
                + "ricostruttivo finale.",

            "Evitare schiariture aggressive nella stessa seduta."
        );

        entityManager.persist(mariaConsultation);


        Consultation giuliaConsultation = new Consultation(

            giulia,

            luca,

            appointment2,

            appointment2.getStartDateTime().minusDays(3),

            ConsultationType.HAIR_CUT,

            "Rinnovare la forma mantenendo una lunghezza media.",

            "Capello naturale, sano e ad alta densità.",

            "Struttura liscia e fibra media.",

            FeasibilityStatus.FEASIBLE,

            "Nessun rischio tecnico rilevante.",

            "Taglio strutturato con alleggerimento controllato.",

            "Mantenere volume naturale."
        );

        entityManager.persist(giuliaConsultation);


        /*
         * ============================================================
         * 10. COLOR FORMULA
         * ============================================================
         */

        ColorFormula formulaMaria = new ColorFormula();

        formulaMaria.setConsultation(
            mariaConsultation
        );

        formulaMaria.setAppointmentItem(
            itemColor
        );

        formulaMaria.setName(
            "Formula Maria - Castano Freddo"
        );

        formulaMaria.setTargetResult(
            "Castano chiaro freddo livello 5-6 con neutralizzazione "
                + "dei riflessi caldi."
        );

        formulaMaria.setVolumeDeveloper(
            Oxygen.VOL_10
        );

        formulaMaria.setMixingRatio(
            MixingRatio.RATIO_1_TO_1
        );

        formulaMaria.setStatus(
            ColorFormulaStatus.USED
        );

        formulaMaria.setNotes(
            "Applicazione uniforme con controllo delle lunghezze porose."
        );

        formulaMaria.setCreatedAt(
            appointment1.getStartDateTime().minusDays(5)
        );

        entityManager.persist(formulaMaria);


        /*
         * ============================================================
         * 11. COLOR FORMULA ITEMS
         * ============================================================
         *
         * Ogni item rappresenta qui un componente della formula.
         *
         * Il vostro model permette un Set<HairDye>; nei mock utilizziamo
         * un solo HairDye per item così la quantità rimane chiaramente
         * riferita a uno specifico prodotto.
         */

        ColorFormulaItem formulaItem51 =
            new ColorFormulaItem();

        formulaItem51.setColorFormula(
            formulaMaria
        );

        formulaItem51.setHairDyes(
            new HashSet<>(
                Set.of(dye51)
            )
        );

        formulaItem51.setQuantity(30.0);

        formulaItem51.setNotes(
            "Componente principale freddo."
        );

        entityManager.persist(formulaItem51);


        ColorFormulaItem formulaItem60 =
            new ColorFormulaItem();

        formulaItem60.setColorFormula(
            formulaMaria
        );

        formulaItem60.setHairDyes(
            new HashSet<>(
                Set.of(dye60)
            )
        );

        formulaItem60.setQuantity(10.0);

        formulaItem60.setNotes(
            "Componente naturale di supporto."
        );

        entityManager.persist(formulaItem60);


        /*
         * Forza Hibernate a sincronizzare le operazioni con il database
         * prima della fine del metodo.
         *
         * Non è sempre strettamente necessario, perché la transazione
         * effettua comunque il commit alla fine, ma rende immediatamente
         * evidenti eventuali errori di mapping o constraint durante il seed.
         */
        entityManager.flush();


        System.out.println(
            "HairLab MockDataDb: dati mock creati correttamente."
        );
    }


    /**
     * Controlla se il database contiene già dati HairLab.
     *
     * La verifica impedisce che il metodo run() inserisca nuovamente gli stessi
     * mock a ogni riavvio dell'applicazione.
     *
     * È sufficiente che una delle principali tabelle applicative contenga
     * record per considerare il database già inizializzato.
     *
     * @return true se tutte le principali tabelle controllate sono vuote,
     *         false se è già presente almeno un dato.
     */
    private boolean isDatabaseEmpty() {

        Long customers =
            entityManager.createQuery(
                "SELECT COUNT(c) FROM Customer c",
                Long.class
            )
            .getSingleResult();

        Long employees =
            entityManager.createQuery(
                "SELECT COUNT(e) FROM Employee e",
                Long.class
            )
            .getSingleResult();

        Long categories =
            entityManager.createQuery(
                "SELECT COUNT(pc) FROM ProductCategory pc",
                Long.class
            )
            .getSingleResult();

        Long products =
            entityManager.createQuery(
                "SELECT COUNT(sp) FROM SalonProduct sp",
                Long.class
            )
            .getSingleResult();

        Long dyes =
            entityManager.createQuery(
                "SELECT COUNT(hd) FROM HairDye hd",
                Long.class
            )
            .getSingleResult();

        return customers == 0
            && employees == 0
            && categories == 0
            && products == 0
            && dyes == 0;
    }
}