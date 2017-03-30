/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.johannes.Clusters.TFIDF.Strategies;

import fi.johannes.Core.ArticleProcessor;
import fi.johannes.Utilities.Logging.CustomExceptions.ServiceNotReadyException;
import fi.johannes.Utilities.Logging.CustomExceptions.UnhandledServiceException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Johannes Sarpola <johannes.sarpola@gmail.com>
 */
public class WordNgramExtractorTest {

    public static FeatureExtractor featEx;

    public WordNgramExtractorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        featEx = new WordNgramExtractor();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of build method, of class WordNgramExtractor.
     */
    @Test
    public void testAddVocabulary() {
        System.out.println("addVocabulary");
        List<String> documents = new ArrayList<>();
        documents.add("Test");
        documents.add("Testss");
        WordNgramExtractor instance = new WordNgramExtractor();
        instance.build(documents);
        Assert.assertEquals(instance.isServiceReady(), true);
        Assert.assertEquals(instance.tfScoresCompressed.size(), documents.size());
        instance.clear();
    }

    /**
     * Test of processLineByAppend method, of class WordNgramExtractor.
     */
    @Test
    public void testProcessLineByAppend() throws Exception {
        System.out.println("processLineByAppend");
        List<String> ls = new ArrayList<>();
        ls.add("bi bi tri tri tri");
        ls.add("bi bi bi bi bi kol kol kol");
        ls.add("bi bi quad quad quad quad");
        String[] expect = {"tri tri", "bi bi bi", "quad quad"};

        int biasingSize = 1;
        WordNgramExtractor instance = new WordNgramExtractor();
        instance.build(ls);
        Assert.assertTrue(instance.isServiceReady());

        int i = 0;
        for (String str : ls) {
            String result = instance.processLineByAppend(str, biasingSize);
            System.out.println(result);
            Assert.assertEquals(str + " " + expect[i], result);
            i++;
        }

    }

    /**
     * Test of processLineByReplace method, of class WordNgramExtractor.
     */
    @Test
    public void testProcessLineByReplace() throws Exception {
        System.out.println("processLineByReplace");
        List<String> ls = new ArrayList<>();
        ls.add("bi bi tri tri tri");
        ls.add("bi bi bi bi bi kol kol kol");
        ls.add("bi bi quad quad quad quad");
        String[] expect = {"tri tri", "bi bi bi", "quad quad"};

        int biasingSize = 1;
        WordNgramExtractor instance = new WordNgramExtractor();
        instance.build(ls);
        Assert.assertTrue(instance.isServiceReady());

        int i = 0;
        for (String str : ls) {
            String result = instance.processLineByReplace(str, biasingSize);
            System.out.println(result);
            Assert.assertEquals(expect[i], result);
            i++;
        }
    }

    /**
     * Test of clear method, of class WordNgramExtractor.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        WordNgramExtractor instance = new WordNgramExtractor();
        List<String> ls = new ArrayList<>();
        ls.add("bi bi tri tri tri");
        ls.add("bi bi bi bi bi kol kol kol");
        ls.add("bi bi quad quad quad quad");

        instance.build(ls);
        instance.clear();
        Assert.assertEquals(true, instance.isServiceReady());
        Assert.assertEquals(true, instance.tfScoresCompressed.size() < 1);
        Assert.assertEquals(true, instance.idfScores.size() < 1 );
    }

    @Test
    public void testPerf() throws ServiceNotReadyException, UnhandledServiceException {
        System.out.println("wiki articles");
        String general = "Johannes Johannes Johannes Johannes James Hogun (died January 4, 1781) was one of five generals from North Carolina to serve with the Continental Army in the American Revolutionary War. Initially a major in the 7th North Carolina Regiment, Hogun advanced quickly in rank during 1776 to become the unit's commanding officer. He participated in the battles of Brandywine and Germantown in 1777. The Continental Congress promoted Hogun to brigadier general in 1779, although several congressmen and the North Carolina General Assembly wished to see Thomas Clark of North Carolina promoted instead.\n"
                + "Hogun was in command of North Carolina's line brigade during the Siege of Charleston in the spring of 1780, which ended in the surrender of all but one of North Carolina's regiments of regular infantry as well as more than 5,000 Patriot soldiers under Major General Benjamin Lincoln. Hogun was the highest-ranking officer from North Carolina to be captured and imprisoned after the surrender of Charleston, and despite being offered the opportunity to leave internment under a parole that was generally extended to other captured Continental officers, he remained in a British prisoner-of-war camp near Charleston. Hogun likely chose imprisonment in order to prevent the British Army from recruiting Continental soldiers for its campaign in the West Indies. He became ill and died in the prison on Haddrel's Point, a peninsula in Charleston's harbor, on January 4, 1781.";
        String earlylife = "Much of Hogun's early life remains unknown, due to his relative obscurity until the American Revolutionary War. He immigrated to North Carolina from Ireland, his place of birth, in 1751, and on October 3 that year he married Ruth Norfleet.[1] The couple had a son, Lemuel. Hogun made his home near the modern-day community of Hobgood in Halifax County.[2]\n"
                + "In 1774, Hogun became a member of the Halifax County Committee of Safety, which indicated his rise to prominence since arriving in the colony 23 years prior.[3] Between August, 1775, and November, 1776, Hogun represented Halifax County in the Third, Fourth, and Fifth North Carolina Provincial Congresses, and demonstrated an interest in military matters.[3] As a delegate, Hogun assisted in drafting the first Constitution of North Carolina.[1]";
        String initialCommand = "Hogun was named a major in the 7th North Carolina Regiment in April 1776,[2] and was given command of the unit on November 26, 1776.[1] Initially, the regiment had some difficulty organizing after several of the officers delayed their military work in order to take care of their personal affairs. Hogun was forced to reprimand his officers sharply, threatening them with the loss of their commissions. At the same time, currents of doubt ran through North Carolina, as Loyalists attempted to hinder enlistment of Patriots by spreading rumors about the imminent demise of the Patriot army in the north, and disease that was allegedly ravaging that force.[4]\n"
                + "While commanding his regiment, Hogun fought against the British Army in the battles of Brandywine and Germantown, and was present at Valley Forge in the winter of 1777–78.[1] In 1778, Hogun was given orders to assist in recruiting the so-called \"additional regiments\" requested by the Continental Congress from North Carolina, and afterwards was ordered to West Point with the first regiment so recruited.[5] After his arrival, and throughout the late autumn and winter of 1778–79, Hogun's regiment served on a work detail tasked with building up the fortifications at West Point. Hogun was not satisfied with this task, but his men lacked sufficient weapons to allow them to serve as a combat unit at that time. Approximately 400 muskets had to be requisitioned for the regiment to be fully armed.";
        String promotionAndPhiladelphia = "In early 1779, Major General Benedict Arnold, then Commandant of Philadelphia, Pennsylvania, requested that General George Washington send him an additional regiment of Continental soldiers to guard the Patriot stores in Philadelphia. Hogun was sent to Arnold with his newly recruited regiment, arriving on or before January 19, 1779.[5]\n"
                + "On January 9, 1779, while en route to Philadelphia, Hogun was promoted to brigadier general by the Continental Congress.[5] His promotion came about in part as a result of what Thomas Burke, a delegate to the Continental Congress from North Carolina, and a fellow Irishman, termed the \"distinguished intrepidity\" Hogun had exhibited at Germantown.[7] This caused some controversy, as the North Carolina General Assembly, which was customarily consulted for the promotion of generals from that state, had already nominated Thomas Clark and Jethro Sumner for promotion to the rank of brigadier general.[5] Sumner was promoted, but Clark was passed over in favor of Hogun, who received the support of nine of the thirteen states.[8] Hogun's surprising victory was due in large part Burke's lobbying efforts among his colleagues in the Continental Congress.[5] By political conventions governing such matters, Burke was bound by the vote of the North Carolina General Assembly to support the state legislature's recommendations of Clark and Sumner, but he worked to convince other Congressmen to vote for Hogun over Clark.[7] Hogun was appointed to succeed Arnold as Commandant of Philadelphia on March 19, 1779, serving until November 22 that year.";
        String charlestonCampaign = "In November 1779, Hogun took command of the North Carolina Brigade, composed of the 1st, 2nd, 3rd, and 4th North Carolina Regiments of the North Carolina Line. Through the winter of 1779–80, Hogun led the brigade of about 700 men[9] from Philadelphia to Charleston, South Carolina, where he was placed under the command of Major General Benjamin Lincoln.[10] The march was arduous, and Hogun's brigade endured one of the coldest, harshest winters in years.[11]\n"
                + "Hogun's command arrived at Charleston on March 13, 1780,[11] which according to Lincoln gave \"great spirit to the Town, and confidence to the Army.\"[12] The North Carolinians were immediately put to the task of defending the city, which was threatened with a siege by British General Henry Clinton in early March.[11] Shortly after Hogun's arrival, many of North Carolina's militia present in the city began to return home because their enlistment terms ended on or about March 24. The militiamen had only agreed to serve limited terms, and as they were not under Hogun's direct command he was powerless to stop them leaving.[13]\n"
                + "Charleston was principally located on a peninsula, and so Lincoln aligned his Continental units in defensive works that barricaded the \"neck\" of the peninsula, using a line of redoubts, redans, and batteries. These defensive works were connected by a parapet, and commanded from a concrete hornwork jutting out from the defensive line.[14] In front of the fortifications, the Patriot forces dug an 18-foot-wide moat, and between the moat and parapet they constructed a line of abatis to stall any British assault.[15] When the siege by the British Army began in earnest on April 1, Hogun and his men were positioned on the right of the Continental Army's lines, near the Cooper River.[16]\n"
                + "Hogun participated in a council of war on April 20, 1780. Several members of the South Carolina Privy Council, a part of the civilian government, threatened to block the Continental Army's attempts to withdraw from Charleston, if the council of war voted to do so.[17] Although the defending army had only eight to ten days worth of provisions,[18] Lincoln bowed to pressure from civil authorities and delayed evacuation. On April 26, another council of war at which Hogun was present determined that the British presence on all sides of the city prevented the army's escape.[19] For the next two weeks, the British and Patriot forces exchanged artillery and rifle fire at all times of day, and the British bombardment whittled down the American breastworks.[20]\n"
                + "On May 8, Lincoln called another council of war with all his army's general and field officers and ships' captains to discuss terms of surrender that had been proposed by Clinton.[21] Of the 61 officers in attendance at that council, 49, including Hogun, voted to offer terms of capitulation with the British commander.[22] When these were rejected, hostilities continued, and Lincoln called another council of war on May 11 to further discuss terms of capitulation. The council voted to present further terms to Clinton, which he accepted.[23] On May 12, 1780, Hogun was among the officers under Lincoln who formally surrendered to the British Army, along with approximately 5,000 Continental and militia soldiers.[10] The surrender led to the loss of all but one of the regiments of the North Carolina Line then in existence, depriving the state of all regular, non-militia soldiers.[10] As a brigadier general, Hogun held the highest rank of the approximately 814 Continental soldiers from North Carolina who capitulated at Charleston.";
        String imprisonomentAndDeath = "Rather than allowing himself to be paroled, Hogun requested he be taken prisoner, and was interned at the British prison camp at Haddrel's Point on Point Pleasant, located in what is now Mount Pleasant, South Carolina, across from Sullivan's Island.[25] Hogun's decision was based, in part, on his desire to stifle the recruiting efforts of the British, who sought to enlist captured Continental soldiers to serve in the British West Indies.[5] The British, however, held only the officers at Haddrel's Point, deciding to house the enlisted men in barracks in Charleston.[26]\n"
                + "Officers at Haddrel's Point were subjected to harsh treatment, barred from fishing to catch much-needed food, and threatened with deportation from South Carolina.[10] Approximately 3,300 Patriot soldiers were confined in prison camps around Charleston that were similar to the one at Haddrel's Point, and many were destined for cramped, unsanitary prison ships.[27] Because of the conditions, many Continental soldiers agreed to join Loyalist regiments, but Hogun and other officers set up courts martial in the camps and attempted to maintain a dignified military structure.[25] Hogun's health soon declined, and he died in the prison camp on January 4, 1781.[28] He was buried in an unmarked grave";
        String legacy = "On March 14, 1786, the North Carolina legislature granted Hogun's son, Lemuel, a 12,000-acre (4,900 ha; 19 sq mi) tract near modern-day Nashville, Tennessee, in recognition of his father's service.[30] The elder Hogun was one of twenty-two Patriot generals who perished during the American Revolutionary War, and one of twelve who died from disease or other non-combat causes.[31] In the early 20th century, North Carolina jurist and historian Walter Clark noted that while the careers of three of North Carolina's other generals—Brigadier Generals Francis Nash and James Moore, and Major General Robert Howe—were well known to contemporary historians, the story of Hogun's career as well as that of Jethro Sumner had been neglected.[32]\n"
                + "Hogun's personal papers appear to have been destroyed while in the possession of his descendants in Alabama during the American Civil War, leaving virtually no surviving correspondence that would shed further light on his life.[30] In 1954, the North Carolina Highway Historical Marker Program, a division of the North Carolina Department of Cultural Resources, erected a historical marker in Hogun's honor near his former home in Halifax County.";

        WordNgramExtractor instance = new WordNgramExtractor();

        List<String> ls = new ArrayList<>();
        ls.add(general);
        ls.add(earlylife);
        ls.add(initialCommand);
        ls.add(promotionAndPhiladelphia);
        ls.add(charlestonCampaign);
        ls.add(imprisonomentAndDeath);
        ls.add(legacy);
        ArticleProcessor art = new ArticleProcessor();
        art.getStates().setUseStanfordLemmatizer(true);
        art.getStates().setUseRemoveStopwords(false);
        List<String> prLs = new ArrayList<>();
        for (int i = 0; i < ls.size(); i++) {
            String s = art.processLineToString(ls.get(i));
            prLs.add(s);
        }
        int higestNgrams = 20;
        instance.build(prLs);
        for (String str : prLs) {
            String result = instance.processLineByReplace(str, higestNgrams);
            System.out.println(result);
        }
        System.out.println("end print");
    }

    @Test
    public void testPerf2() throws ServiceNotReadyException, UnhandledServiceException {
        System.out.println("wiki articles");
        String article1 = "James Hogun (died January 4, 1781) was an Irish-American military officer who was as one of five generals from North Carolina to serve with the Continental Army during the American Revolutionary War. Born in Ireland, Hogan migrated to North Carolina – then a British colony – in 1751. Settling in Halifax County, he raised a family and established himself as a prominent local figure. A member of the county's Committee of Safety, he represented it at the North Carolina Provincial Congress and helped to draft the first Constitution of North Carolina. Initially a major in the 7th North Carolina Regiment, Hogun advanced quickly in rank during 1776 to become the unit's commanding officer. He participated in the battles of Brandywine and Germantown in 1777. The Continental Congress promoted Hogun to brigadier general in 1779, although several congressmen and the North Carolina General Assembly wished to see Thomas Clark of North Carolina promoted instead. Hogun was in command of North Carolina's line brigade during the Siege of Charleston in the spring of 1780, which ended in the surrender of all but one of North Carolina's regiments of regular infantry as well as more than 5,000 Patriot soldiers under Major General Benjamin Lincoln. Hogun was the highest-ranking officer from North Carolina to be captured and imprisoned after the surrender of Charleston, and despite being offered the opportunity to leave internment under a parole that was generally extended to other captured Continental officers, he remained in a British prisoner-of-war camp near Charleston. Hogun likely chose imprisonment in order to prevent the British Army from recruiting Continental soldiers for its campaign in the West Indies. He became ill and died in the prison on Haddrel's Point, a peninsula in Charleston's harbor. Early life Much of Hogun's early life remains unknown, due to his relative obscurity until the American Revolutionary War. He immigrated to North Carolina from Ireland, his place of birth, in 1751, and on October 3 that year he married Ruth Norfleet.[1] The couple had a son, Lemuel. Hogun made his home near the modern-day community of Hobgood in Halifax County.[2] In 1774, Hogun became a member of the Halifax County Committee of Safety, which indicated his rise to prominence since arriving in the colony 23 years prior.[3] Between August, 1775, and November, 1776, Hogun represented Halifax County in the Third, Fourth, and Fifth North Carolina Provincial Congresses, and demonstrated an interest in military matters.[3] As a delegate, Hogun assisted in drafting the first Constitution of North Carolina.[1] American Revolutionary War Initial command Hogun was named a major in the 7th North Carolina Regiment in April 1776,[2] and was given command of the unit on November 26, 1776.[1] Initially, the regiment had some difficulty organizing after several of the officers delayed their military work in order to take care of their personal affairs. Hogun was forced to reprimand his officers sharply, threatening them with the loss of their commissions. At the same time, currents of doubt ran through North Carolina, as Loyalists attempted to hinder enlistment of Patriots by spreading rumors about the imminent demise of the Patriot army in the north, and disease that was allegedly ravaging that force.[4] While commanding his regiment, Hogun fought against the British Army in the battles of Brandywine and Germantown, and was present at Valley Forge in the winter of 1777–78.[1] In 1778, Hogun was given orders to assist in recruiting the so-called \"additional regiments\" requested by the Continental Congress from North Carolina, and afterwards was ordered to West Point with the first regiment so recruited.[5] After his arrival, and throughout the late autumn and winter of 1778–79, Hogun's regiment served on a work detail tasked with building up the fortifications at West Point. Hogun was not satisfied with this task, but his men lacked sufficient weapons to allow them to serve as a combat unit at that time. Approximately 400 muskets had to be requisitioned for the regiment to be fully armed.[6] Promotion and Philadelphia In early 1779, Major General Benedict Arnold, then Commandant of Philadelphia, Pennsylvania, requested that General George Washington send him an additional regiment of Continental soldiers to guard the Patriot stores in Philadelphia. Hogun was sent to Arnold with his newly recruited regiment, arriving on or before January 19, 1779.[5] On January 9, 1779, while en route to Philadelphia, Hogun was promoted to brigadier general by the Continental Congress.[5] His promotion came about in part as a result of what Thomas Burke, a delegate to the Continental Congress from North Carolina, and a fellow Irishman, termed the \"distinguished intrepidity\" Hogun had exhibited at Germantown.[7] This caused some controversy, as the North Carolina General Assembly, which was customarily consulted for the promotion of generals from that state, had already nominated Thomas Clark and Jethro Sumner for promotion to the rank of brigadier general.[5] Sumner was promoted, but Clark was passed over in favor of Hogun, who received the support of nine of the thirteen states.[8] Hogun's surprising victory was due in large part Burke's lobbying efforts among his colleagues in the Continental Congress.[5] By political conventions governing such matters, Burke was bound by the vote of the North Carolina General Assembly to support the state legislature's recommendations of Clark and Sumner, but he worked to convince other Congressmen to vote for Hogun over Clark.[7] Hogun was appointed to succeed Arnold as Commandant of Philadelphia on March 19, 1779, serving until November 22 that year.[5]";
        String article2 = "Finland's population is 5.5 million (2014), staying roughly on the same level with only about 9% increase in 24 years since the last economic depression in 1990.[8] The majority live in the southern regions.[9] The single largest group of foreigners living in Finland are Russians and Estonians, 36% of all of the foreigners (2014).[10] In terms of area, it is the eighth largest country in Europe and the most sparsely populated country in the European Union. Finland is a parliamentary republic with a central government based in the capital Helsinki, local governments in 317 municipalities,[11] and an autonomous region, the Åland Islands. Over 1.4 million people live in the Greater Helsinki metropolitan area, which produces a third of the country's GDP. Other large cities include Tampere, Turku, Oulu, Jyväskylä, Lahti, and Kuopio. From the late 12th century until 1809, Finland was part of Sweden, a legacy reflected in the prevalence of the Swedish language and its official status. In the spirit of the notion of Adolf Ivar Arwidsson (1791–1858), \"Swedes we are no-longer, Russians we do not want to become, let us therefore be Finns\", the Finnish national identity started to establish. However, Finland was still incorporated into the Russian Empire as the autonomous Grand Duchy of Finland, until the Russian Revolution of 1917 prompted the Finnish Declaration of Independence. This was followed by the Finnish Civil War in which the pro-Bolshevik Finnish Socialist Workers' Republic was defeated by the pro-conservative \"Whites\" with support from the German Empire. After a brief attempt to establish a kingdom, the country became a republic. In World War II, Finnish forces fought in three separate conflicts: the Winter War (1939–1940) and Continuation War (1941–1944) against the Soviet Union, and the Lapland War (1944–1945) against Nazi Germany. Finland joined the United Nations in 1955 and established an official policy of neutrality. It joined the Organisation for Economic Co-operation and Development (OECD) in 1969, the NATO Partnership for Peace on 1994,[12] the European Union in 1995, the Euro-Atlantic Partnership Council on 1997 [12] and finally the Eurozone at its inception in 1999. Finland was a relative latecomer to industrialisation, remaining a largely agrarian country until the 1950s. It rapidly developed an advanced economy while building an extensive Nordic-style welfare state, resulting in widespread prosperity and one of the highest per capita incomes in the world.[13] However, since 2012 Finnish GDP growth has been negative, with preceding peak of -8% in 2009.[14] Finland is a top performer in numerous metrics of national performance, including education, economic competitiveness, civil liberties, quality of life, and human development. In 2015, Finland is ranked first in the World Human Capital[15] and the Press Freedom Index, and as the most stable country in the world in the Failed States Index.[16][17][18][19][20] The country has a long legacy of social progressivism, in 1906, before gaining the independence, it became the second nation in the world to give the right to vote to all adult citizens and the first in the world to give full suffrage to all adult citizens.[21][22] About 73.9% of Finns were members of the Evangelical Lutheran Church of Finland in 2014; nevertheless, the Lutheran Church estimates that approximately only 2% of its members attend church services weekly.[23] EtymologyThe first known written appearance of the name Finland is thought to be on three rune-stones. Two were found in the Swedish province of Uppland and have the inscription finlonti (U 582). The third was found in Gotland, in the Baltic Sea. It has the inscription finlandi (G 319) and dates from the 13th century.[24] The name can be assumed to be related to the tribe name Finns, which is mentioned first known time AD 98 (disputed meaning). Suomi The name Suomi (Finnish for \"Finland\") has uncertain origins, but a candidate for a source is the Proto-Baltic word *źemē, meaning \"land\". In addition to the close relatives of Finnish (the Finnic languages), this name is also used in the Baltic languages Latvian and Lithuanian. Alternatively, the Indo-European word *gʰm-on \"man\" (cf. Gothic guma, Latin homo) has been suggested, being borrowed as *ćoma. The word originally referred only to the province of Finland Proper, and later to the northern coast of Gulf of Finland, with northern regions such as Ostrobothnia still sometimes being excluded until later. Earlier theories suggested derivation from suomaa (fen land) or suoniemi (fen cape), and parallels between saame (Sami, a Finno-Ugric people in Lapland), and Häme (a province in the inland) were drawn, but these theories are now considered outdated.[25] Concept Hakkapeliitta featured on a 1940 Finnish stamp\n"
                + "In the 12th and 13th centuries, the term \"Finland\" mostly referred to the area around Turku (Åbo), a region that later became known as Finland Proper, while the other parts of the country were called Tavastia and Karelia, but which could also sometimes be collectively referred to as \"Österland\" (compare Norrland). (Medieval politics concerned tribes such as the Finns, the Tavastians, and the Karelians more than geographical boundaries.) In the 15th century, \"Finland\" became a common name for the whole land area to the east of the Bothnian Sea, possibly even including Åland, when the archipelago was seen as belonging to Åbo (Turku). What the term actually refers to can vary between sources, additionally, the boundaries to the east and the north were not exact. A sort of establishment for Finland as a unity, if only in name, came when John III of Sweden called his duchy the \"grand duchy of Finland\" (about 1580), as a strategy to meet the claims of the Russian tsar. The term became part of the title of the King of Sweden but had little practical meaning. The Finnish land area had the same standing as the area to the west of the Bothnian Sea, and the Finnish part of the realm had the same representation in the parliament as the western part. In 1637, Queen Christina named Per Brahe the Younger as Governor General of Finland, Åland, and Ostrobothnia (other parts of Sweden had also had governor generals).The modern boundaries of Finland actually came into existence only after the end of Sweden-Finland. What was signed over to Russia in 1809 was not so much a \"Finland\" as six counties, Åland, and a little part of Västerbotten County. The boundary between the new Grand Duchy of Finland and the remaining part of Sweden could have been drawn along the river Kemijoki, which was the boundary between Västerbotten County and Österbotten County (Ostrobothnia) at the time—which the Swedish proposed in the peace negotiations—or along the river Kalix, thereby including the Finnish-speaking part of Meänmaa—which the Russians proposed. The boundary, which followed the Torne River and the Muonio River to the fells Saana and Halti in the northwest, was a compromise, which later became what the concept of Finland came to stand for—at least after the tsar Alexander I of Russia permitted the parts of Finland located to the east of the Kymi River, which were conquered by Russia in 1721 and 1743, called \"Old Finland\", to be administratively included in \"New Finland\" in 1812.";
        String article3 = "The United States of America (USA), commonly referred to as the United States (U.S.) or America, is a federal republic[19][20] composed of 50 states, a federal district, five major territories and various possessions.[fn 1][fn 2] The 48 contiguous states and Washington, D.C., are in central North America between Canada and Mexico. The state of Alaska is in the northwestern part of North America and the state of Hawaii is an archipelago in the mid-Pacific. The territories are scattered about the Pacific Ocean and the Caribbean Sea. At 3.8 million square miles (9.842 million km2)[18] and with over 320 million people, the country is the world's third or fourth-largest by total area[fn 3] and the third most populous. It is one of the world's most ethnically diverse and multicultural nations, the product of large-scale immigration from many countries.[26] The geography and climate of the United States are also extremely diverse, and the country is home to a wide variety of wildlife.[27] Paleo-Indians migrated from Eurasia to what is now the U.S. mainland at least 15,000 years ago,[28] with European colonization beginning in the 16th century. The United States emerged from 13 British colonies along the East Coast. Disputes between Great Britain and the colonies led to the American Revolution. On July 4, 1776, as the colonies were fighting Great Britain in the American Revolutionary War, delegates from the 13 colonies unanimously adopted the Declaration of Independence. The war ended in 1783 with recognition of the independence of the United States by the Kingdom of Great Britain, and was the first successful war of independence against a European colonial empire.[29] The country's constitution was adopted on September 17, 1787, and ratified by the states in 1788. The first ten amendments, collectively named the Bill of Rights, were ratified in 1791 and designed to guarantee many fundamental civil liberties. Driven by the doctrine of Manifest Destiny, the United States embarked on a vigorous expansion across North America throughout the 19th century.[30] This involved displacing American Indian tribes, acquiring new territories, and gradually admitting new states, until by 1848 the nation spanned the continent.[30] During the second half of the 19th century, the American Civil War ended legal slavery in the country.[31][32] By the end of that century, the United States extended into the Pacific Ocean,[33] and its economy, driven in large part by the Industrial Revolution, began to soar.[34] The Spanish–American War and World War I confirmed the country's status as a global military power. The United States emerged from World War II as a global superpower, the first country to develop nuclear weapons, the only country to use them in warfare, and a permanent member of the United Nations Security Council. The end of the Cold War and the dissolution of the Soviet Union in 1991 left the United States as the world's sole superpower.[35] The United States is a developed country and has the world's largest national economy by nominal and real GDP, benefiting from an abundance of natural resources and high worker productivity.[36] While the U.S. economy is considered post-industrial, the country continues to be one of the world's largest manufacturers.[37] Accounting for 34% of global military spending[38] and 23% of world GDP,[39] it is the world's foremost military and economic power, a prominent political and cultural force, and a leader in scientific research and technological innovations.[40] In 1507, the German cartographer Martin Waldseemüller produced a world map on which he named the lands of the Western Hemisphere \"America\" after the Italian explorer and cartographer Amerigo Vespucci (Latin: Americus Vespucius).[41] The first documentary evidence of the phrase \"United States of America\" is from a letter dated January 2, 1776, written by Stephen Moylan, Esq., George Washington's aide-de-camp and Muster-ConnectionMaster General of the Continental Army. Addressed to Lt. Col. Joseph Reed, Moylan expressed his wish to carry the \"full and ample powers of the United States of America\" to Spain to assist in the revolutionary war effort.[42] The first known publication of the phrase \"United States of America\" was in an anonymous essay in The Virginia Gazette newspaper in Williamsburg, Virginia, on April 6, 1776.[43][44] In June 1776, Thomas Jefferson wrote the phrase \"UNITED STATES OF AMERICA\" in all capitalized letters in the headline of his \"original Rough draught\" of the Declaration of Independence.[45][46] In the final Fourth of July version of the Declaration, the title was changed to read, \"The unanimous Declaration of the thirteen united States of America\".[47] In 1777 the Articles of Confederation announced, \"The Stile of this Confederacy shall be 'The United States of America'\".[48] The preamble of the Constitution states \"...establish this Constitution for the United States of America.\" The short form \"United States\" is also standard. Other common forms are the \"U.S.\", the \"USA\", and \"America\". Colloquial names are the \"U.S. of A.\" and, internationally, the \"States\". \"Columbia\", a name popular in poetry and songs of the late 1700s, derives its origin from Christopher Columbus; it appears in the name \"District of Columbia\".[49] In non-English languages, the name is frequently the translation of either the \"United States\" or \"United States of America\", and colloquially as \"America\". In addition, an abbreviation (e.g. USA) is sometimes used.[50] The phrase \"United States\" was originally plural, a description of a collection of independent states—e.g., \"the United States are\"—including in the Thirteenth Amendment to the United States Constitution, ratified in 1865. The singular form—e.g., \"the United States is\"— became popular after the end of the American Civil War. The singular form is now standard; the plural form is retained in the idiom \"these United States\".[51] The difference is more significant than usage; it is a difference between a collection of states and a unit.[52] A citizen of the United States is an \"American\". \"United States\", \"American\" and \"U.S.\" refer to the country adjectivally (\"American values\", \"U.S. forces\"). \"American\" rarely refers to subjects not connected with the United States.[53}";
        String article4 = "Java (Indonesian: Jawa; Javanese: ꦗꦮ) is an island of Indonesia. With a population of over 141 million (the island itself) or 145 million (the administrative region) as of 2015 Census released in December 2015,[1] Java is home to 56.7 percent of the Indonesian population, and is the most populous island on Earth. The Indonesian capital city, Jakarta, is located on western Java. Much of Indonesian history took place on Java. It was the center of powerful Hindu-Buddhist empires, the Islamic sultanates, and the core of the colonial Dutch East Indies. Java was also the center of the Indonesian struggle for independence during the 1930s and 1940s. Java dominates Indonesia politically, economically and culturally. Formed mostly as the result of volcanic eruptions, Java is the 13th largest island in the world and the fifth largest in Indonesia. A chain of volcanic mountains forms an east–west spine along the island. Three main languages are spoken on the island, with Javanese being the dominant; it is the native language of about 60 million people in Indonesia, most of whom live on Java. Most residents are bilingual, with Indonesian as their first or second language. While the majority of the people of Java are Muslim, Java has a diverse mixture of religious beliefs, ethnicities, and cultures. Java is divided into four provinces, West Java, Central Java, East Java, and Banten, and also two special regions, Jakarta and Yogyakarta. Etymology The origins of the name \"Java\" are not clear. One possibility is that the island was named after the jáwa-wut plant, which was said to be common in the island during the time, and that prior to Indianization the island had different names.[2] There are other possible sources: the word jaú and its variations mean \"beyond\" or \"distant\".[3] And, in Sanskrit yava means barley, a plant for which the island was famous.[3] \"Yawadvipa\" is mentioned in India's earliest epic, the Ramayana. Sugriva, the chief of Rama's army dispatched his men to Yawadvipa, the island of Java, in search of Sita.[4] It was hence referred to in Indian by the Sanskrit name \"yāvaka dvīpa\" (dvīpa = island). Java is mentioned in the ancient Tamil text Manimekalai that states that Java had a kingdom with a capital called Nagapuram.[5][6][7] Another source states that the \"Java\" word is derived from a Proto-Austronesian root word, meaning 'home'.[8] The great island of Iabadiu or Jabadiu was mentioned in Ptolemy's Geographia composed around 150 CE Roman Empire. Iabadiu is said to means \"barley island\", to be rich in gold, and have a silver town called Argyra at the west end. The name indicate Java,[9] and seems to be derived from Hindu name Java-dvipa (Yawadvipa). The natural environment of Java is tropical rainforest, with ecosystems ranging from coastal mangrove forests on the north coast, rocky coastal cliffs on the southern coast, and low-lying tropical forests to high altitude rainforests on the slopes of mountainous volcanic regions in the interior. The Javan environment and climate gradually alters from west to east; from wet and humid dense rainforest in western parts, to a dry savanna environment in the east, corresponding to the climate and rainfall in these regions. Originally Javan wildlife supported a rich biodiversity, where numbers of endemic species of flora and fauna flourished; such as the Javan rhinoceros,[14] Javan banteng, Javan warty pig, Javan hawk-eagle, Javan peafowl, Javan silvery gibbon, Javan lutung, Java mouse-deer, Javan rusa, and Javan leopard. With over 450 species of birds and 37 endemic species, Java is a birdwatcher's paradise.[15] There are about 130 freshwater fish species in Java.[16] Since ancient times, people have opened the rainforest, altered the ecosystem, shaped the landscapes and created rice paddy and terraces to support the growing population. Javan rice terraces have existed for more than a millennium, and had supported ancient agricultural kingdoms. The growing human population has put severe pressure on Java's wildlife, as rainforests were diminished and confined to highland slopes or isolated peninsulas. Some of Java's endemic species are now critically endangered, with some already extinct; Java used to have Javan tigers and Javan elephants, but both have been rendered extinct. Today, several national parks exist in Java that protect the remnants of its fragile wildlife, such as Ujung Kulon, Mount Halimun-Salak, Gede Pangrango, Baluran, Meru Betiri and Alas Purwo";
        WordNgramExtractor instance = new WordNgramExtractor();
        KeywordExtractor instance2 = new KeywordExtractor();
        List<String> ls = new ArrayList<>();
        ls.add(article1);
        ls.add(article2);
        ls.add(article3);
        ls.add(article4);
        ArticleProcessor art = new ArticleProcessor();
        ArticleProcessor art2 = new ArticleProcessor();
        art.getStates().setUseStanfordLemmatizer(true);
        art.getStates().setUseRemoveStopwords(false);
        art2.getStates().setUseStanfordLemmatizer(true);

        List<String> prLs = new ArrayList<>();
        List<String> prLs2 = new ArrayList<>();
        for (int i = 0; i < ls.size(); i++) {
            String s = art.processLineToString(ls.get(i));
            String s2 = art2.processLineToString(ls.get(i));
            prLs.add(s);
            prLs2.add(s2);
        }
        int bias = 40;
        instance.build(prLs);
        instance2.build(prLs2);
        for (String str : prLs) {
            String result = instance.processLineByReplace(str, bias);
            System.out.println(result);

        }
        for (String str : prLs2) {
            String result2 = instance2.processLineByReplace(str, bias);
            System.out.println(result2);
        }
        System.out.println("end print");
    }
}
