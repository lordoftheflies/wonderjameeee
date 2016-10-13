/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.common.ContentType;
import hu.cherubits.wonderjam.dal.AccountRepository;
import hu.cherubits.wonderjam.dal.ContainerContentRepository;
import hu.cherubits.wonderjam.dal.ContentRepository;
import hu.cherubits.wonderjam.dal.LocaleRepository;
import hu.cherubits.wonderjam.dal.MailBoxRepository;
import hu.cherubits.wonderjam.dal.MessageRepository;
import hu.cherubits.wonderjam.dal.NetworkTreeRepository;
import hu.cherubits.wonderjam.dal.ResourceRepository;
import hu.cherubits.wonderjam.entities.AccountEntity;
import hu.cherubits.wonderjam.entities.ContainerContentEntity;
import hu.cherubits.wonderjam.entities.ContentEntity;
import hu.cherubits.wonderjam.entities.ImageContentEntity;
import hu.cherubits.wonderjam.entities.LocaleEntity;
import hu.cherubits.wonderjam.entities.MailBoxEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeEntity;
import hu.cherubits.wonderjam.entities.NetworkNodeType;
import hu.cherubits.wonderjam.entities.ReferenceContentEntity;
import hu.cherubits.wonderjam.entities.TextContentEntity;
import hu.cherubits.wonderjam.entities.VideoContentEntity;
import java.util.UUID;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author lordoftheflies
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TopFlavonContentTest extends ChristeamServerApplicationTests {

//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private ContentManagementService contentManagementServiceMock;
//    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
//            MediaType.APPLICATION_JSON.getSubtype(),
//            Charset.forName("utf8"));
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContainerContentRepository containerContentRepository;

    @Autowired
    private NetworkTreeRepository networkRepo;

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private LocaleRepository localeDao;

    @Autowired
    private ResourceRepository resourceDao;

    @Autowired
    private MailBoxRepository mailBoxRepository;
    private final String SPOON_ICON = "wonderjam-icons:spoon";
    private final String CAN_ICON = "wonderjam-icons:can";

    @Test
    public void atestCleanUp() {
        messageRepository.deleteAll();
        contentRepository.deleteAll();
        containerContentRepository.deleteAll();
        accountRepo.deleteAll();
        mailBoxRepository.deleteAll();
        networkRepo.deleteAll();
    }

    private NetworkNodeEntity heglasNode;

    private NetworkNodeEntity balazspeczelyNode;

    @Test
    public void btestLocale() {
        LocaleEntity englishLocale = new LocaleEntity();
        englishLocale.setDisplayName("English");
        englishLocale.setLanguageCode("en");
        localeDao.save(englishLocale);

        LocaleEntity polishLocale = new LocaleEntity();
        polishLocale.setDisplayName("Polskie");
        polishLocale.setLanguageCode("po");
        localeDao.save(polishLocale);

        LocaleEntity hungarianLocale = new LocaleEntity();
        hungarianLocale.setDisplayName("Magyar");
        hungarianLocale.setLanguageCode("hu");
        localeDao.save(hungarianLocale);
    }

    @Test
    public void ctestNetwork() {

        AccountEntity heglas = new AccountEntity();
        heglas.setEmail("admin@topflavon.net");
        heglas.setName("Teszt adminisztrátor");
        heglas.setPassword("qwe123");
        heglas.setPreferredLanguage("en");
        heglas = accountRepo.save(heglas);
        heglasNode = new NetworkNodeEntity();
        heglasNode.setActive(true);
        heglasNode.setCodes(1);
        heglas.setState(NetworkNodeType.ADMIN);
        heglasNode = networkRepo.save(heglasNode);
        heglas.setNode(heglasNode);
        heglas = accountRepo.save(heglas);
        MailBoxEntity heglasMb = new MailBoxEntity();
        heglasMb.setOwner(heglasNode);
        mailBoxRepository.save(heglasMb);

        AccountEntity balazspeczely = new AccountEntity();
        balazspeczely.setEmail("webmaster@topflavon.net");
        balazspeczely.setName("Teszt felhasználó");
        balazspeczely.setPassword("qwe123");
        balazspeczely.setPreferredLanguage("hu");
        balazspeczely = accountRepo.save(balazspeczely);
        balazspeczelyNode = new NetworkNodeEntity();
        balazspeczelyNode.setActive(true);
        balazspeczelyNode.setCodes(2);
        balazspeczely.setState(NetworkNodeType.USER);
        balazspeczelyNode = networkRepo.save(balazspeczelyNode);
        balazspeczely.setNode(balazspeczelyNode);
        balazspeczely = accountRepo.save(balazspeczely);
        MailBoxEntity balazspeczelyMb = new MailBoxEntity();
        balazspeczelyMb.setOwner(balazspeczelyNode);
        mailBoxRepository.save(balazspeczelyMb);

        AccountEntity cseszkupopoveszku = new AccountEntity();
        cseszkupopoveszku.setEmail("blaise.peczely@gmail.com");
        cseszkupopoveszku.setName("Péczely Balázs (Adminisztrátor)");
        cseszkupopoveszku.setPassword("qwe123");
        cseszkupopoveszku.setPreferredLanguage("en");
        cseszkupopoveszku = accountRepo.save(cseszkupopoveszku);
        NetworkNodeEntity cseszkupopoveszkuNode = new NetworkNodeEntity();
        cseszkupopoveszku.setState(NetworkNodeType.ADMIN);
        cseszkupopoveszkuNode.setActive(true);
        cseszkupopoveszkuNode.setCodes(3);
        cseszkupopoveszkuNode = networkRepo.save(cseszkupopoveszkuNode);
        cseszkupopoveszku.setNode(cseszkupopoveszkuNode);
        cseszkupopoveszku = accountRepo.save(cseszkupopoveszku);
        MailBoxEntity cseszkupopoveszkuMb = new MailBoxEntity();
        cseszkupopoveszkuMb.setOwner(cseszkupopoveszkuNode);
        mailBoxRepository.save(cseszkupopoveszkuMb);

        AccountEntity parazita = new AccountEntity();
        parazita.setEmail("balazs.peczely.sp@gmail.com ");
        parazita.setName("Péczely Balázs (Felhasználó)");
        parazita.setPassword("qwe123");
        parazita.setPreferredLanguage("hu");
        parazita = accountRepo.save(parazita);
        NetworkNodeEntity parazitaNode = new NetworkNodeEntity();
        parazitaNode.setActive(true);
        parazitaNode.setCodes(4);
        parazita.setState(NetworkNodeType.USER);
        parazitaNode = networkRepo.save(parazitaNode);
        parazita.setNode(parazitaNode);
        parazita = accountRepo.save(parazita);
        MailBoxEntity parazitaMb = new MailBoxEntity();
        parazitaMb.setOwner(parazitaNode);
        mailBoxRepository.save(parazitaMb);

        AccountEntity tesztelek = new AccountEntity();
        tesztelek.setEmail("teszt.elek@gmail.com");
        tesztelek.setName("Teszt Elek");
        tesztelek.setPassword("qwe123");
        tesztelek.setPreferredLanguage("hu");
        tesztelek = accountRepo.save(tesztelek);
        NetworkNodeEntity tesztelekNode = new NetworkNodeEntity();
        tesztelekNode.setActive(true);
        tesztelek.setState(NetworkNodeType.USER);
        tesztelekNode.setCodes(5);
        tesztelekNode = networkRepo.save(tesztelekNode);
        tesztelek.setNode(tesztelekNode);
        tesztelek = accountRepo.save(tesztelek);
        MailBoxEntity tesztelekMb = new MailBoxEntity();
        tesztelekMb.setOwner(tesztelekNode);
        mailBoxRepository.save(tesztelekMb);

        AccountEntity feriahegyrol = new AccountEntity();
        feriahegyrol.setEmail("feriahegyrol@gmail.com");
        feriahegyrol.setName("Ferdinand Highlander");
        feriahegyrol.setPassword("qwe123");
        feriahegyrol = accountRepo.save(feriahegyrol);
        feriahegyrol.setPreferredLanguage("en");
        NetworkNodeEntity feriahegyrolNode = new NetworkNodeEntity();
        feriahegyrolNode.setActive(true);
        feriahegyrol.setState(NetworkNodeType.USER);
        feriahegyrolNode.setCodes(6);
        feriahegyrolNode = networkRepo.save(feriahegyrolNode);
        feriahegyrol.setNode(feriahegyrolNode);
        feriahegyrol = accountRepo.save(feriahegyrol);
        MailBoxEntity feriahegyrolMb = new MailBoxEntity();
        feriahegyrolMb.setOwner(feriahegyrolNode);
        mailBoxRepository.save(feriahegyrolMb);

        AccountEntity romanok = new AccountEntity();
        romanok.setName("Digitális Védelem");
        romanok = accountRepo.save(romanok);
        NetworkNodeEntity romanokNode = new NetworkNodeEntity();
        romanokNode.setActive(true);
        romanokNode.setCodes(7);
        romanok.setState(NetworkNodeType.GROUP);
        romanokNode = networkRepo.save(romanokNode);
        romanok.setNode(romanokNode);
        romanok = accountRepo.save(romanok);

        AccountEntity gorogok = new AccountEntity();
        gorogok.setName("Görögország");
        gorogok = accountRepo.save(gorogok);
        NetworkNodeEntity gorogokNode = new NetworkNodeEntity();
        gorogokNode.setActive(true);
        gorogokNode.setCodes(8);
        gorogokNode = networkRepo.save(gorogokNode);
        gorogok.setNode(gorogokNode);
        gorogok.setState(NetworkNodeType.GROUP);
        gorogok = accountRepo.save(gorogok);

        AccountEntity magyarok = new AccountEntity();
        magyarok.setName("Magyarország");
        magyarok = accountRepo.save(magyarok);
        NetworkNodeEntity magyarokNode = new NetworkNodeEntity();
        magyarokNode.setActive(true);
        magyarokNode.setCodes(9);
        magyarok.setState(NetworkNodeType.GROUP);
        magyarokNode = networkRepo.save(magyarokNode);
        magyarok.setNode(magyarokNode);
        magyarok = accountRepo.save(magyarok);

        magyarokNode.setParent(heglasNode);
        networkRepo.save(magyarokNode);
        balazspeczelyNode.setParent(heglasNode);
        networkRepo.save(balazspeczelyNode);
        romanokNode.setParent(heglasNode);
        networkRepo.save(romanokNode);
        cseszkupopoveszkuNode.setParent(romanokNode);
        networkRepo.save(cseszkupopoveszkuNode);
        gorogokNode.setParent(balazspeczelyNode);
        networkRepo.save(gorogokNode);
        tesztelekNode.setParent(balazspeczelyNode);
        networkRepo.save(tesztelekNode);
        feriahegyrolNode.setParent(balazspeczelyNode);
        networkRepo.save(feriahegyrolNode);
        parazitaNode.setParent(romanokNode);
        networkRepo.save(parazitaNode);

    }

    /**
     * Test of publish method, of class ContentManagementService.
     */
    @Test
    public void dtestCustomArticle() throws Exception {
        System.out.println("publish");

        ContainerContentEntity productsContainer = new ContainerContentEntity();
        productsContainer.setContentType(ContentType.linked);
        productsContainer.setNode(heglasNode);
        productsContainer.setPublicIndicator(true);
        productsContainer.setTitle("Products");
        productsContainer = containerContentRepository.save(productsContainer);

        ContainerContentEntity flavonEndActiveContainer = new ContainerContentEntity();
        flavonEndActiveContainer.setContentType(ContentType.assembled);
        flavonEndActiveContainer.setDraft(false);
        flavonEndActiveContainer.setNode(heglasNode);
        flavonEndActiveContainer.setPublicIndicator(true);
        flavonEndActiveContainer.setTitle("FLAVON ACTIVE");
        flavonEndActiveContainer.setParent(productsContainer);
        flavonEndActiveContainer = containerContentRepository.save(flavonEndActiveContainer);
        text(flavonEndActiveContainer, null, FLAVON_ACTIVE_P0_ENG, 0);
        image(flavonEndActiveContainer, FLAVON_ACTIVE_PNG, 1);
        oeti(flavonEndActiveContainer, FLAVON_ACTIVE_OETI, FLAVON_ACTIVE_SPOON, FLAVON_ACTIVE_CAN, 2);
        text(flavonEndActiveContainer, null, FLAVON_ACTIVE_P1_ENG, 3);
        text(flavonEndActiveContainer, null, FLAVON_ACTIVE_P2_ENG, 4);
        text(flavonEndActiveContainer, FLAVON_ACTIVE_T3_ENG, "<ul>"
                + "<li>who has an active, dynamic, sporty lifestyle</li>"
                + "<li>who craves for healthy stimulation</li>"
                + "<li>who wants to successfully meet the challenges of the 21st century</li>"
                + "<li>who would like to enjoy the benefits of today’s super fruits</li>"
                + "</ul>", 5);

        ContainerContentEntity flavonGreenContainer = new ContainerContentEntity();
        flavonGreenContainer.setContentType(ContentType.assembled);
        flavonGreenContainer.setDraft(false);
        flavonGreenContainer.setNode(heglasNode);
        flavonGreenContainer.setPublicIndicator(true);
        flavonGreenContainer.setTitle("FLAVON GREEN");
        flavonGreenContainer.setParent(productsContainer);
        flavonGreenContainer = containerContentRepository.save(flavonGreenContainer);
        text(flavonGreenContainer, null, FLAVON_GREEN_P0_ENG, 0);
        image(flavonGreenContainer, FLAVON_GREEN_PNG, 1);
        oeti(flavonGreenContainer, FLAVON_GREEN_OETI, FLAVON_GREEN_SPOON, FLAVON_GREEN_CAN, 2);
        text(flavonGreenContainer, null, FLAVON_GREEN_P1_ENG, 3);
        text(flavonGreenContainer, null, FLAVON_GREEN_P2_ENG, 4);
        text(flavonGreenContainer, FLAVON_GREEN_T3_ENG, "<ul>"
                + "<li>who cannot ensure the intake of sufficient vegetables</li>"
                + "<li>who considers it important to continuously take in vitamins and minerals from a pure source</li>"
                + "<li>who would like to consume vegetables in a new form they have not tried before</li>"
                + "<li>who is a conscientious consumer and would like to complete a modern diet</li>"
                + "</ul>", 5);

        ContainerContentEntity flavonMaxContainer = new ContainerContentEntity();
        flavonMaxContainer.setContentType(ContentType.assembled);
        flavonMaxContainer.setDraft(false);
        flavonMaxContainer.setNode(heglasNode);
        flavonMaxContainer.setPublicIndicator(true);
        flavonMaxContainer.setTitle("FLAVON MAX");
        flavonMaxContainer.setParent(productsContainer);
        flavonMaxContainer = containerContentRepository.save(flavonMaxContainer);
        text(flavonMaxContainer, null, FLAVON_MAX_P0_ENG, 0);
        image(flavonMaxContainer, FLAVON_MAX_PNG, 1);
        oeti(flavonMaxContainer, FLAVON_MAX_OETI, FLAVON_MAX_SPOON, FLAVON_MAX_CAN, 2);
        text(flavonMaxContainer, null, FLAVON_MAX_P1_ENG, 3);
        text(flavonMaxContainer, null, FLAVON_MAX_P2_ENG, 4);
        text(flavonMaxContainer, FLAVON_MAX_T3_ENG, "<ul>"
                + "<li>who does not consume enough fruits</li>"
                + "<li>who wants to complement their current one-sided nutrition</li>"
                + "<li>who takes good care of the their own and their family’s health</li>"
                + "<li>who wants to enjoy and take advantage of an innovative product</li>"
                + "</ul>", 5);

        ContainerContentEntity flavonJoyContainer = new ContainerContentEntity();
        flavonJoyContainer.setContentType(ContentType.assembled);
        flavonJoyContainer.setDraft(false);
        flavonJoyContainer.setNode(heglasNode);
        flavonJoyContainer.setPublicIndicator(true);
        flavonJoyContainer.setTitle("FLAVON JOY");
        flavonJoyContainer.setParent(productsContainer);
        flavonJoyContainer = containerContentRepository.save(flavonJoyContainer);
        text(flavonJoyContainer, null, FLAVON_JOY_P0_ENG, 0);
        image(flavonJoyContainer, FLAVON_JOY_PNG, 1);
        oeti(flavonJoyContainer, FLAVON_JOY_OETI, FLAVON_JOY_SPOON, FLAVON_JOY_CAN, 2);
        text(flavonJoyContainer, null, FLAVON_JOY_P1_ENG, 3);
        text(flavonJoyContainer, null, FLAVON_JOY_P2_ENG, 4);
        text(flavonJoyContainer, FLAVON_JOY_T3_ENG, "<ul>"
                + "<li>who is exposed to constant stress, does sport regularly</li>"
                + "<li>who needs more mental energy</li>"
                + "<li>who would like to satisfy their desire for sweets in a healthy way</li>"
                + "<li>who wants to make a conscious choice of cocoa bean, ancient spices and the synergy of super fruits and vegetables</li>"
                + "</ul>", 5);

        ContainerContentEntity videosContainer = new ContainerContentEntity();
        videosContainer.setContentType(ContentType.linked);
        videosContainer.setNode(heglasNode);
        videosContainer.setPublicIndicator(true);
        videosContainer.setTitle("Medical presentations");
        videosContainer = containerContentRepository.save(videosContainer);

        ContainerContentEntity video1Container = new ContainerContentEntity();
        video1Container.setContentType(ContentType.linked);
        video1Container.setParent(videosContainer);
        video1Container.setNode(heglasNode);
        video1Container.setPublicIndicator(true);
        video1Container.setTitle("Lisa Ann Robinson");
        video1Container = containerContentRepository.save(video1Container);
        video(video1Container, null, "/backend/video/LisaAnnRobinson.mp4", 0);
        
        ContainerContentEntity video1ContainerYoutube = new ContainerContentEntity();
        video1ContainerYoutube.setContentType(ContentType.linked);
        video1ContainerYoutube.setParent(videosContainer);
        video1ContainerYoutube.setNode(heglasNode);
        video1ContainerYoutube.setPublicIndicator(true);
        video1ContainerYoutube.setTitle("Lisa Ann Robinson (Youtube)");
        video1ContainerYoutube = containerContentRepository.save(video1ContainerYoutube);
        video(video1ContainerYoutube, null, "https://youtu.be/v7Osy8OpoOk", 0);

        ContainerContentEntity video2Container = new ContainerContentEntity();
        video2Container.setContentType(ContentType.linked);
        video2Container.setParent(videosContainer);
        video2Container.setNode(heglasNode);
        video2Container.setPublicIndicator(true);
        video2Container.setTitle("Dr. Leonard Ariel Lado");
        video2Container = containerContentRepository.save(video2Container);
        video(video2Container, null, "/backend/video/DrLeonardArielLado.mp4", 1);
        
        
        ContainerContentEntity video2ContainerYoutube = new ContainerContentEntity();
        video2ContainerYoutube.setContentType(ContentType.linked);
        video2ContainerYoutube.setParent(videosContainer);
        video2ContainerYoutube.setNode(heglasNode);
        video2ContainerYoutube.setPublicIndicator(true);
        video2ContainerYoutube.setTitle("Dr. Leonard Ariel Lado (Youtube)");
        video2ContainerYoutube = containerContentRepository.save(video2ContainerYoutube);
        video(video2ContainerYoutube, null, "https://youtu.be/8Fua1F_RYWM", 1);

        ContainerContentEntity video3Container = new ContainerContentEntity();
        video3Container.setContentType(ContentType.linked);
        video3Container.setParent(videosContainer);
        video3Container.setNode(heglasNode);
        video3Container.setPublicIndicator(true);
        video3Container.setTitle("Dr. Brian Thornburg");
        video3Container = containerContentRepository.save(video3Container);
        video(video3Container, null, "/backend/video/DrBrianThornburg.mp4", 2);
        
        ContainerContentEntity video3ContainerYoutube = new ContainerContentEntity();
        video3ContainerYoutube.setContentType(ContentType.linked);
        video3ContainerYoutube.setParent(videosContainer);
        video3ContainerYoutube.setNode(heglasNode);
        video3ContainerYoutube.setPublicIndicator(true);
        video3ContainerYoutube.setTitle("Dr. Brian Thornburg (Youtube)");
        video3ContainerYoutube = containerContentRepository.save(video3ContainerYoutube);
        video(video3ContainerYoutube, null, "https://youtu.be/DyS2jC0DLZc", 2);

        ContainerContentEntity video4Container = new ContainerContentEntity();
        video4Container.setContentType(ContentType.linked);
        video4Container.setParent(productsContainer);
        video4Container.setNode(heglasNode);
        video4Container.setPublicIndicator(true);
        video4Container.setTitle("Flavon");
        video4Container = containerContentRepository.save(video4Container);
        video(video4Container, null, "https://www.youtube.com/watch?v=MArvZyBm_bU", 3);

    }

    private VideoContentEntity video(ContainerContentEntity container, String title, String content, int index) {
        VideoContentEntity flavonEndActiveParagraph0 = new VideoContentEntity();
        flavonEndActiveParagraph0.setTitle(title);
        flavonEndActiveParagraph0.setContent(content);
        flavonEndActiveParagraph0.setFontSize(12);
        flavonEndActiveParagraph0.setOrderIndex(index);
        flavonEndActiveParagraph0.setParent(container);
        flavonEndActiveParagraph0.setWidth(1000);
        flavonEndActiveParagraph0.setHeight(600);
        flavonEndActiveParagraph0 = contentRepository.save(flavonEndActiveParagraph0);
        return flavonEndActiveParagraph0;
    }

    private TextContentEntity text(ContainerContentEntity container, String title, String content, int index) {
        TextContentEntity flavonEndActiveParagraph0 = new TextContentEntity();
        flavonEndActiveParagraph0.setTitle(title);
        flavonEndActiveParagraph0.setContent(content);
        flavonEndActiveParagraph0.setFontSize(12);
        flavonEndActiveParagraph0.setOrderIndex(index);
        flavonEndActiveParagraph0.setParent(container);
        flavonEndActiveParagraph0 = contentRepository.save(flavonEndActiveParagraph0);
        return flavonEndActiveParagraph0;
    }

    private ImageContentEntity image(ContainerContentEntity container, String image, int index) {
        ImageContentEntity p = new ImageContentEntity();
        p.setContent("/data/" + image);
        p.setHeight(300);
        p.setOrderIndex(index);
        p.setParent(container);
        p.setWidth(300);
        p = contentRepository.save(p);
        return p;
    }

    private TextContentEntity oeti(ContainerContentEntity container, String oeti, int spoon, int can, int index) {

        TextContentEntity p = new TextContentEntity();
        p.setTitle(FLAVON_ACTIVE_OETI);
        p.setContent("<ul>"
                + "<li><span>1</span> x <iron-icon icon=\"" + SPOON_ICON + "\"></iron-icon>=<span>" + spoon + "</span><label>T-ORAC</label></li>"
                + "<li><span>1</span> x <iron-icon icon=\"" + CAN_ICON + "\"></iron-icon>=<span>" + can + "</span><label>T-ORAC</label></li>"
                + "</ul>");
        p.setFontSize(12);
        p.setOrderIndex(index);
        p.setParent(container);
        p = contentRepository.save(p);
        return p;
    }
    private static final String FLAVON_ACTIVE_OETI = "OÉTI notification number: 10026/2011";
    private static final String FLAVON_ACTIVE_T3_ENG = "The consumption of Flavon Active is recommended for everyone";
    private static final String FLAVON_ACTIVE_P2_ENG = "The fifth member of our product line is again the result of serious innovation. The product is a possible solution to the challenges of present times. It helps us every day to do our best even when we are under high pressure.";
    private static final String FLAVON_ACTIVE_P1_ENG = "We often hear that this accelerated rhythm calls for some response. What can we do? We should not react to challenges by ruining our body! Both our body and soul need to stay healthy because they are indispensable for an active life.";
    private static final String FLAVON_ACTIVE_P0_ENG = "If we want to stay on top, to meet the expectations and face the challenges of the 21st century, to keep up with the accelerated pace of the world, we need to live a conscious and active life. This challenge affects all of us.";
    private static final String FLAVON_ACTIVE_PNG = "flavon_active.png";
    private static final int FLAVON_ACTIVE_CAN = 1523760;
    private static final int FLAVON_ACTIVE_SPOON = 38094;

    private static final String FLAVON_GREEN_OETI = "OÉTI notification number: 10027/2011";
    private static final String FLAVON_GREEN_T3_ENG = "The consumption of Flavon Green is recommended for adults";
    private static final String FLAVON_GREEN_P2_ENG = "Although numerous researches prove that regular vegetable consumption protects our health, only a few people consume the required amount day by day. Flavon Green can be the solution, because we can cover the significant part of the daily vegetable intake with a product constituted of only well-selected ingredients of high quality.";
    private static final String FLAVON_GREEN_P1_ENG = "Flavon Green provides the positive physiological effects of vegetables in a complex way. The included vegetables help us maintain a balanced diet rich in vitamins, minerals, antioxidants and fibres.";
    private static final String FLAVON_GREEN_P0_ENG = "Regular vegetable consumption is a significant and inevitable part of healthy nutrition. Vegetables supply our body with essential nutrients, fibre and vitamins and have a beneficial effect on our general well-being. Flavon Green is a revolutionary product, a true innovation for vegetable consumption that reshapes previous habits.";
    private static final String FLAVON_GREEN_PNG = "flavon_green.png";
    private static final int FLAVON_GREEN_CAN = 482880;
    private static final int FLAVON_GREEN_SPOON = 12072;

    private static final String FLAVON_MAX_OETI = "OÉTI notification number: 10029/2011";
    private static final String FLAVON_MAX_T3_ENG = "The consumption of Flavon Max is recommended for everybody";
    private static final String FLAVON_MAX_P2_ENG = "Flavon max was created for the people of the 21st century. It is a health-conscious product that supports life quality and its plant ingredients help the proper function of the antioxidant defence system and suitably support the function of the immune system.";
    private static final String FLAVON_MAX_P1_ENG = "High quality dietary supplements play a major role in one’s nutrition today. By constantly consuming them we might prevent the occurrence of deficiency symptoms caused by inadequate nutrition. Flavon broke with previous methods! Instead of pills and powder, it created a delicious and easily consumable gel-consistency form which makes it possible for its product to supply the human body with the necessary active substances.";
    private static final String FLAVON_MAX_P0_ENG = "The lifestyle of the 21st century including increasing daily stress, polluted environment, and nutrition deficiencies has extremely bad effects on our body and organism. Our body needs help to be able to win over these negative effects.";
    private static final String FLAVON_MAX_PNG = "flavon_max.png";
    private static final int FLAVON_MAX_CAN = 435840;
    private static final int FLAVON_MAX_SPOON = 10896;

    private static final String FLAVON_JOY_OETI = "OÉTI notification number: 16853/2015";
    private static final String FLAVON_JOY_T3_ENG = "The consumption of Flavon Joy is recommended for everyone";
    private static final String FLAVON_JOY_P2_ENG = "Flavon Joy delivers polyphenols to our body in a complex way to keep it healthy, and in case of health conscious consuming, not only does it protect the health of our organism, it may also boost our mental/psychical characteristics, learning skills and shock absorbing capacity.";
    private static final String FLAVON_JOY_P1_ENG = "From children, through pregnant women and adults exposed to stronger oxidative stress, up to the older generation, anyone can consume it who craves for a tasty, sweet dietary supplement that has unique physiological effects at the same time.";
    private static final String FLAVON_JOY_P0_ENG = "Premium category Flavon Joy includes one of the ancient natural treasures, the fruit of cocoa tree due to which it has become a curio on the market of dietary supplements. Keeping the gel-consistency that is beneficial regarding bioavailability, we combined such fruit and vegetable ingredients and spices that have strong synergetic interactions, thus by enhancing each other’s effects. Therefore, it protects and pampers our body in all age group.";
    private static final String FLAVON_JOY_PNG = "flavon_green.png";
    private static final int FLAVON_JOY_CAN = 2424240;
    private static final int FLAVON_JOY_SPOON = 60606;

}
