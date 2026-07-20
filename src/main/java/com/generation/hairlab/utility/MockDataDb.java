package com.generation.hairlab.utility;

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
 * Popola automaticamente il database HairLab con dati mock
 * all'avvio dell'applicazione.
 *
 * La classe viene eseguita da Spring Boot tramite CommandLineRunner.
 * I dati vengono salvati rispettando l'ordine delle foreign key:
 *
 * Employee / Customer / ProductCategory
 * -> SalonProduct / HairDye / HairProfile
 * -> Appointment
 * -> AppointmentItem
 * -> Consultation
 * -> ColorFormula
 * -> ColorFormulaItem
 *
 * Il caricamento viene eseguito solamente quando le principali tabelle
 * del progetto risultano vuote, evitando duplicazioni dei campi UNIQUE.
 */
@Component
public class MockDataDb implements CommandLineRunner {

    /**
     * EntityManager JPA utilizzato per salvare le Entity mock.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Metodo eseguito automaticamente dopo l'avvio del contesto Spring.
     *
     * @param args argomenti eventualmente passati all'applicazione
     */
    @Override
    @Transactional
    public void run(String... args) {

        if (!isDatabaseEmpty()) {
            System.out.println(
                "HairLab MockDataDb: database già popolato, caricamento mock saltato."
            );
            return;
        }

        System.out.println(
            "HairLab MockDataDb: caricamento dati mock..."
        );

        /*
         * ============================================================
         * 1. EMPLOYEE
         * ============================================================
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
                    Specialization.HIGHLIGHTS,
                    Specialization.COLOR_CORRECTION,
                    Specialization.BLEACHING
                )
            )
        );
        anna.setHireDate(LocalDate.of(2022, 3, 1));
        anna.setActive(true);
        anna.setNotes(
            "Colorista specializzata in balayage e correzione colore."
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
                    Specialization.BLOW_DRY,
                    Specialization.STYLING
                )
            )
        );
        luca.setHireDate(LocalDate.of(2023, 1, 15));
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
        marco.setHireDate(LocalDate.of(2024, 2, 10));
        marco.setActive(true);
        marco.setNotes(
            "Barber specializzato in taglio uomo e barba."
        );
        entityManager.persist(marco);


        /*
         * ============================================================
         * 2. CUSTOMER
         * ============================================================
         */

        Customer maria = new Customer();
        maria.setFirstName("Maria");
        maria.setLastName("Esposito");
        maria.setPhoneNumber("3202000001");
        maria.setEmail("maria.esposito@mock.it");
        maria.setDob(LocalDate.of(1995, 5, 14));
        maria.setActive(true);
        maria.setCreatedAt(LocalDateTime.now().minusMonths(8));
        maria.setUpdatedAt(LocalDateTime.now().minusDays(10));
        entityManager.persist(maria);


        Customer giulia = new Customer();
        giulia.setFirstName("Giulia");
        giulia.setLastName("Romano");
        giulia.setPhoneNumber("3202000002");
        giulia.setEmail("giulia.romano@mock.it");
        giulia.setDob(LocalDate.of(1988, 11, 3));
        giulia.setActive(true);
        giulia.setCreatedAt(LocalDateTime.now().minusMonths(5));
        giulia.setUpdatedAt(LocalDateTime.now().minusDays(5));
        entityManager.persist(giulia);


        Customer sara = new Customer();
        sara.setFirstName("Sara");
        sara.setLastName("Ferrari");
        sara.setPhoneNumber("3202000003");
        sara.setEmail("sara.ferrari@mock.it");
        sara.setDob(LocalDate.of(2001, 2, 21));
        sara.setActive(true);
        sara.setCreatedAt(LocalDateTime.now().minusMonths(2));
        sara.setUpdatedAt(LocalDateTime.now().minusDays(2));
        entityManager.persist(sara);


        /*
         * ============================================================
         * 3. PRODUCT CATEGORY
         * ============================================================
         */

        ProductCategory haircutCategory = new ProductCategory();
        haircutCategory.setName("TAGLIO");
        haircutCategory.setDesc("Servizi professionali di taglio.");
        haircutCategory.setActive(true);
        entityManager.persist(haircutCategory);


        ProductCategory colorCategory = new ProductCategory();
        colorCategory.setName("COLORE");
        colorCategory.setDesc(
            "Servizi tecnici di colorazione e schiaritura."
        );
        colorCategory.setActive(true);
        entityManager.persist(colorCategory);


        ProductCategory stylingCategory = new ProductCategory();
        stylingCategory.setName("STYLING");
        stylingCategory.setDesc("Servizi di piega e styling.");
        stylingCategory.setActive(true);
        entityManager.persist(stylingCategory);


        ProductCategory treatmentCategory = new ProductCategory();
        treatmentCategory.setName("TRATTAMENTI");
        treatmentCategory.setDesc(
            "Trattamenti professionali per capelli e cute."
        );
        treatmentCategory.setActive(true);
        entityManager.persist(treatmentCategory);


        /*
         * ============================================================
         * 4. SALON PRODUCT
         * ============================================================
         */

        SalonProduct womensCut = new SalonProduct();
        womensCut.setProductCategory(haircutCategory);
        womensCut.setName("Taglio donna");
        womensCut.setDesc(
            "Taglio personalizzato con consulenza iniziale."
        );
        womensCut.setDuration(45);
        womensCut.setBasePrice(35.00);
        womensCut.setActive(true);
        entityManager.persist(womensCut);


        SalonProduct mensCut = new SalonProduct();
        mensCut.setProductCategory(haircutCategory);
        mensCut.setName("Taglio uomo");
        mensCut.setDesc("Taglio uomo personalizzato.");
        mensCut.setDuration(30);
        mensCut.setBasePrice(25.00);
        mensCut.setActive(true);
        entityManager.persist(mensCut);


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
         * 5. HAIR DYE
         * ============================================================
         */

        HairDye dye51 = new HairDye();
        dye51.setBrand("Wella");
        dye51.setName("Castano chiaro cenere");
        dye51.setCode("WELLA-5-1");
        dye51.setProductType(ProductType.COLOR);
        dye51.setToneLevel(ToneLevel.LEVEL_5_LIGHT_BROWN);
        dye51.setPrimaryReflection(Reflection.ASH);
        dye51.setSecondaryReflection(null);
        dye51.setActive(true);
        entityManager.persist(dye51);


        HairDye dye60 = new HairDye();
        dye60.setBrand("Wella");
        dye60.setName("Biondo scuro naturale");
        dye60.setCode("WELLA-6-0");
        dye60.setProductType(ProductType.COLOR);
        dye60.setToneLevel(ToneLevel.LEVEL_6_DARK_BLONDE);
        dye60.setPrimaryReflection(Reflection.NATURAL);
        dye60.setSecondaryReflection(null);
        dye60.setActive(true);
        entityManager.persist(dye60);


        HairDye toner81 = new HairDye();
        toner81.setBrand("HairLab Professional");
        toner81.setName("Toner cenere perla");
        toner81.setCode("HL-8-ASH-PEARL");
        toner81.setProductType(ProductType.TONER);
        toner81.setToneLevel(ToneLevel.LEVEL_8_LIGHT_BLONDE);
        toner81.setPrimaryReflection(Reflection.ASH);
        toner81.setSecondaryReflection(Reflection.PEARL);
        toner81.setActive(true);
        entityManager.persist(toner81);


        /*
         * ============================================================
         * 6. HAIR PROFILE
         * ============================================================
         */

        HairProfile mariaProfile = new HairProfile();
        mariaProfile.setCustomer(maria);
        mariaProfile.setNaturalTone(ToneLevel.LEVEL_5_LIGHT_BROWN);
        mariaProfile.setCurrentTone(ToneLevel.LEVEL_6_DARK_BLONDE);
        mariaProfile.setReflection(Reflection.GOLD);
        mariaProfile.setHairType(HairType.WAVY);
        mariaProfile.setTexture(HairTexture.FINE);
        mariaProfile.setPorosity(PhysicalValue.HIGH);
        mariaProfile.setDensity(PhysicalValue.MEDIUM);
        mariaProfile.setScalpCondition(
            new HashSet<>(Set.of("CUTE NORMALE"))
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
                Set.of("LUNGHEZZE SENSIBILIZZATE")
            )
        );
        mariaProfile.setContraindications(new HashSet<>());
        mariaProfile.setNotes(
            "Capello fine con maggiore porosità sulle lunghezze."
        );
        entityManager.persist(mariaProfile);


        HairProfile giuliaProfile = new HairProfile();
        giuliaProfile.setCustomer(giulia);
        giuliaProfile.setNaturalTone(ToneLevel.LEVEL_4_MEDIUM_BROWN);
        giuliaProfile.setCurrentTone(ToneLevel.LEVEL_4_MEDIUM_BROWN);
        giuliaProfile.setReflection(Reflection.NATURAL);
        giuliaProfile.setHairType(HairType.STRAIGHT);
        giuliaProfile.setTexture(HairTexture.MEDIUM);
        giuliaProfile.setPorosity(PhysicalValue.LOW);
        giuliaProfile.setDensity(PhysicalValue.HIGH);
        giuliaProfile.setScalpCondition(
            new HashSet<>(Set.of("CUTE LEGGERMENTE SECCA"))
        );
        giuliaProfile.setChemicalHistory(
            new HashSet<>(
                Set.of("NESSUN TRATTAMENTO CHIMICO RECENTE")
            )
        );
        giuliaProfile.setSensitivities(new HashSet<>());
        giuliaProfile.setContraindications(new HashSet<>());
        giuliaProfile.setNotes(
            "Capello naturale, forte e con buona densità."
        );
        entityManager.persist(giuliaProfile);


        /*
         * ============================================================
         * 7. APPOINTMENT
         * ============================================================
         */

        LocalDateTime appointment1Date =
            LocalDateTime.now()
                .minusDays(20)
                .withHour(9)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        Appointment appointment1 = new Appointment();
        appointment1.setCustomer(maria);
        appointment1.setStartDateTime(appointment1Date);
        appointment1.setStatus(AppointmentStatus.COMPLETED);
        appointment1.setNotes("Appuntamento colore e taglio.");
        appointment1.setCreatedAt(appointment1Date.minusDays(10));
        appointment1.setUpdatedAt(appointment1Date.plusHours(3));
        entityManager.persist(appointment1);


        LocalDateTime appointment2Date =
            LocalDateTime.now()
                .minusDays(10)
                .withHour(14)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        Appointment appointment2 = new Appointment();
        appointment2.setCustomer(giulia);
        appointment2.setStartDateTime(appointment2Date);
        appointment2.setStatus(AppointmentStatus.COMPLETED);
        appointment2.setNotes("Taglio e piega.");
        appointment2.setCreatedAt(appointment2Date.minusDays(8));
        appointment2.setUpdatedAt(appointment2Date.plusHours(2));
        entityManager.persist(appointment2);


        /*
         * ============================================================
         * 8. APPOINTMENT ITEM
         * ============================================================
         */

        AppointmentItem itemColor = new AppointmentItem();
        itemColor.setAppointment(appointment1);
        itemColor.setSalonProduct(fullColor);
        itemColor.setEmployee(anna);
        itemColor.setScheduledTime(appointment1Date);
        itemColor.setDuration(90);
        itemColor.setAgreedPrice(65.00);
        itemColor.setResultNotes(
            "Colorazione uniforme con neutralizzazione dei riflessi caldi."
        );
        itemColor.setCompletedAt(appointment1Date.plusMinutes(90));
        entityManager.persist(itemColor);


        AppointmentItem itemCutMaria = new AppointmentItem();
        itemCutMaria.setAppointment(appointment1);
        itemCutMaria.setSalonProduct(womensCut);
        itemCutMaria.setEmployee(luca);
        itemCutMaria.setScheduledTime(appointment1Date.plusMinutes(90));
        itemCutMaria.setDuration(45);
        itemCutMaria.setAgreedPrice(35.00);
        itemCutMaria.setResultNotes(
            "Taglio bob leggermente graduato."
        );
        itemCutMaria.setCompletedAt(appointment1Date.plusMinutes(135));
        entityManager.persist(itemCutMaria);


        AppointmentItem itemCutGiulia = new AppointmentItem();
        itemCutGiulia.setAppointment(appointment2);
        itemCutGiulia.setSalonProduct(womensCut);
        itemCutGiulia.setEmployee(luca);
        itemCutGiulia.setScheduledTime(appointment2Date);
        itemCutGiulia.setDuration(45);
        itemCutGiulia.setAgreedPrice(35.00);
        itemCutGiulia.setResultNotes("Taglio di mantenimento.");
        itemCutGiulia.setCompletedAt(appointment2Date.plusMinutes(45));
        entityManager.persist(itemCutGiulia);


        AppointmentItem itemBlowDryGiulia = new AppointmentItem();
        itemBlowDryGiulia.setAppointment(appointment2);
        itemBlowDryGiulia.setSalonProduct(blowDry);
        itemBlowDryGiulia.setEmployee(luca);
        itemBlowDryGiulia.setScheduledTime(appointment2Date.plusMinutes(45));
        itemBlowDryGiulia.setDuration(40);
        itemBlowDryGiulia.setAgreedPrice(25.00);
        itemBlowDryGiulia.setResultNotes(
            "Piega liscia con movimento naturale sulle punte."
        );
        itemBlowDryGiulia.setCompletedAt(appointment2Date.plusMinutes(85));
        entityManager.persist(itemBlowDryGiulia);


        /*
         * ============================================================
         * 9. CONSULTATION
         * ============================================================
         *
         * IMPORTANTE:
         * Consultation nel Model attuale non possiede un costruttore
         * personalizzato. Si utilizza quindi il costruttore vuoto
         * generato da Java/Lombok e si valorizzano i campi con i setter.
         */

        Consultation mariaConsultation = new Consultation();
        mariaConsultation.setCustomer(maria);
        mariaConsultation.setEmployee(anna);
        mariaConsultation.setAppointment(appointment1);
        mariaConsultation.setConsultationDate(
            appointment1Date.minusDays(5)
        );
        mariaConsultation.setType(ConsultationType.HAIR_COLOR);
        mariaConsultation.setObjective(
            "Ottenere un castano chiaro più freddo eliminando "
            + "i riflessi dorati indesiderati."
        );
        mariaConsultation.setInitialDiagnosis(
            "Capello precedentemente colorato, fibra fine "
            + "e lunghezze sensibilizzate."
        );
        mariaConsultation.setCurrentCondition(
            "Base livello 6 con riflesso caldo-dorato."
        );
        mariaConsultation.setFeasibility(
            FeasibilityStatus.FEASIBLE_WITH_LIMITATIONS
        );
        mariaConsultation.setRisks(
            "Possibile aumento della sensibilizzazione sulle lunghezze."
        );
        mariaConsultation.setProposedProcedure(
            "Colorazione fredda controllata con trattamento "
            + "ricostruttivo finale."
        );
        mariaConsultation.setTechnicalNotes(
            "Evitare schiariture aggressive nella stessa seduta."
        );
        entityManager.persist(mariaConsultation);


        Consultation giuliaConsultation = new Consultation();
        giuliaConsultation.setCustomer(giulia);
        giuliaConsultation.setEmployee(luca);
        giuliaConsultation.setAppointment(appointment2);
        giuliaConsultation.setConsultationDate(
            appointment2Date.minusDays(3)
        );
        giuliaConsultation.setType(ConsultationType.HAIR_CUT);
        giuliaConsultation.setObjective(
            "Rinnovare la forma mantenendo una lunghezza media."
        );
        giuliaConsultation.setInitialDiagnosis(
            "Capello naturale, sano e ad alta densità."
        );
        giuliaConsultation.setCurrentCondition(
            "Struttura liscia e fibra media."
        );
        giuliaConsultation.setFeasibility(
            FeasibilityStatus.FEASIBLE
        );
        giuliaConsultation.setRisks(
            "Nessun rischio tecnico rilevante."
        );
        giuliaConsultation.setProposedProcedure(
            "Taglio strutturato con alleggerimento controllato."
        );
        giuliaConsultation.setTechnicalNotes(
            "Mantenere il volume naturale."
        );
        entityManager.persist(giuliaConsultation);


        Consultation saraConsultation = new Consultation();
        saraConsultation.setCustomer(sara);
        saraConsultation.setEmployee(anna);

        /*
         * appointment = null è valido perché la relazione
         * Consultation -> Appointment è opzionale nel Model.
         */
        saraConsultation.setAppointment(null);

        saraConsultation.setConsultationDate(
            LocalDateTime.now().minusDays(2)
        );
        saraConsultation.setType(ConsultationType.HAIR_COLOR);
        saraConsultation.setObjective(
            "Valutare la possibilità di eseguire un balayage freddo."
        );
        saraConsultation.setInitialDiagnosis(
            "Capello naturale con buona struttura."
        );
        saraConsultation.setCurrentCondition(
            "Base naturale livello medio con lunghezze sane."
        );
        saraConsultation.setFeasibility(
            FeasibilityStatus.FEASIBLE
        );
        saraConsultation.setRisks(
            "Possibile lieve sensibilizzazione dopo la schiaritura."
        );
        saraConsultation.setProposedProcedure(
            "Balayage progressivo con tonalizzazione finale."
        );
        saraConsultation.setTechnicalNotes(
            "Eseguire test preliminare della ciocca."
        );
        entityManager.persist(saraConsultation);


        /*
         * ============================================================
         * 10. COLOR FORMULA
         * ============================================================
         */

        ColorFormula formulaMaria = new ColorFormula();
        formulaMaria.setConsultation(mariaConsultation);
        formulaMaria.setAppointmentItem(itemColor);
        formulaMaria.setName("Formula Maria - Castano Freddo");
        formulaMaria.setTargetResult(
            "Castano chiaro freddo livello 5-6 "
            + "con neutralizzazione dei riflessi caldi."
        );
        formulaMaria.setVolumeDeveloper(Oxygen.VOL_10);
        formulaMaria.setMixingRatio(MixingRatio.RATIO_1_TO_1);
        formulaMaria.setStatus(ColorFormulaStatus.USED);
        formulaMaria.setNotes(
            "Applicazione uniforme con controllo delle lunghezze porose."
        );
        formulaMaria.setCreatedAt(
            appointment1Date.minusDays(5)
        );
        entityManager.persist(formulaMaria);


        /*
         * ============================================================
         * 11. COLOR FORMULA ITEM
         * ============================================================
         *
         * Il Model attuale usa Set<HairDye>.
         * Per evitare ambiguità sulla quantità, ogni item mock contiene
         * volutamente un solo HairDye.
         */

        ColorFormulaItem formulaItem51 = new ColorFormulaItem();
        formulaItem51.setColorFormula(formulaMaria);
        formulaItem51.setHairDyes(
            new HashSet<>(Set.of(dye51))
        );
        formulaItem51.setQuantity(30.0);
        formulaItem51.setNotes(
            "Componente principale freddo."
        );
        entityManager.persist(formulaItem51);


        ColorFormulaItem formulaItem60 = new ColorFormulaItem();
        formulaItem60.setColorFormula(formulaMaria);
        formulaItem60.setHairDyes(
            new HashSet<>(Set.of(dye60))
        );
        formulaItem60.setQuantity(10.0);
        formulaItem60.setNotes(
            "Componente naturale di supporto."
        );
        entityManager.persist(formulaItem60); 


        /*
         * Forza Hibernate a inviare le INSERT al database subito.
         * Eventuali errori di vincoli o mapping emergono durante l'avvio.
         */
        entityManager.flush();

        System.out.println(
            "HairLab MockDataDb: dati mock creati correttamente."
        );
    }

    /**
     * Verifica se le principali tabelle HairLab sono completamente vuote.
     *
     * Se anche una sola delle tabelle controllate contiene dati,
     * il seed viene saltato per evitare duplicazioni di email, codici
     * o nomi definiti UNIQUE.
     *
     * @return true solamente se le tabelle principali sono vuote
     */
    private boolean isDatabaseEmpty() {

        Long customers = entityManager.createQuery(
            "SELECT COUNT(c) FROM Customer c",
            Long.class
        ).getSingleResult();

        Long employees = entityManager.createQuery(
            "SELECT COUNT(e) FROM Employee e",
            Long.class
        ).getSingleResult();

        Long categories = entityManager.createQuery(
            "SELECT COUNT(pc) FROM ProductCategory pc",
            Long.class
        ).getSingleResult();

        Long products = entityManager.createQuery(
            "SELECT COUNT(sp) FROM SalonProduct sp",
            Long.class
        ).getSingleResult();

        Long dyes = entityManager.createQuery(
            "SELECT COUNT(hd) FROM HairDye hd",
            Long.class
        ).getSingleResult();

        return customers == 0
            && employees == 0
            && categories == 0
            && products == 0
            && dyes == 0;
    }
}