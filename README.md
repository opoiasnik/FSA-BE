# Property Rental Application

This application will allow users to search for apartments or houses available for rent or sale. Property owners will be able to publish property listings with descriptions, photos, structured address information and price details.

Users will be able to browse available properties, filter them by address, price or property type and view detailed information about each listing, including selected property features such as area, number of rooms, floor, furnishing and parking availability. If a user is interested in a property, they will be able to contact the owner directly through the platform or request a property viewing.

Property owners will also be able to manage their listings by creating, editing or removing property offers, updating their price and address information and managing property characteristics.

The goal of the application is to simplify the process of finding and offering accommodation through a centralized online platform.

# Zber požiadaviek

- **RQ01** Používateľ sa môže zaregistrovať do systému pomocou e-mailu.
- **RQ02** Systém by mal podporovať registráciu a autorizáciu používateľov s rozlíšením rolí (používateľ, vlastník).
- **RQ03** Používateľ by mal mať možnosť vyhľadávať nehnuteľnosti dostupné na prenájom alebo predaj.
- **RQ04** Používateľ by mal mať možnosť filtrovať nehnuteľnosti podľa adresy, ceny alebo typu nehnuteľnosti.
- **RQ05** Používateľ by mal mať možnosť zobraziť detail vybranej nehnuteľnosti.
- **RQ06** Detail nehnuteľnosti by mal obsahovať popis, fotografie, adresu, cenu a základné vlastnosti nehnuteľnosti.
- **RQ07** Používateľ by mal mať možnosť kontaktovať vlastníka nehnuteľnosti v prípade záujmu.
- **RQ08** Používateľ by mal mať možnosť ukladať nehnuteľnosti medzi obľúbené.
- **RQ09** Vlastník by mal mať možnosť vytvárať nové inzeráty nehnuteľností.
- **RQ10** Vlastník by mal mať možnosť upravovať existujúce inzeráty.
- **RQ11** Vlastník by mal mať možnosť odstrániť inzerát, ktorý už nie je aktuálny.
- **RQ12** Vlastník by mal mať možnosť spravovať základné informácie o svojej nehnuteľnosti.
- **RQ13** Systém by mal používateľovi zobrazovať zoznam dostupných nehnuteľností.
- **RQ14** Systém by mal rozlišovať medzi ponukami na prenájom a ponukami na predaj.
- **RQ15** Používateľ by mal mať možnosť prezerať fotografie priradené k inzerátu.
- **RQ16** Používateľ by mal mať možnosť zobraziť kontaktné údaje vlastníka.
- **RQ17** Vlastník by mal mať možnosť pridať k inzerátu viacero fotografií.
- **RQ18** Systém by mal umožniť evidenciu stavu inzerátu (aktívny, neaktívny).
- **RQ19** Používateľ by mal mať možnosť prezerať iba aktívne inzeráty.
- **RQ20** Používateľ by mal mať možnosť požiadať o obhliadku nehnuteľnosti.
- **RQ21** Vlastník by mal mať možnosť schváliť alebo zamietnuť žiadosť o obhliadku.
- **RQ22** Systém by mal evidovať stav žiadosti o obhliadku (pending, approved, rejected, cancelled).
- **RQ23** Inzerát by mal obsahovať štruktúrovanú adresu (ulica, mesto, PSČ, krajina).
- **RQ24** Cena nehnuteľnosti by mala byť evidovaná ako suma a mena.
- **RQ25** Inzerát by mal obsahovať základné vlastnosti nehnuteľnosti, napríklad rozlohu, počet izieb, poschodie, informáciu o zariadení a parkovaní.
- **RQ26** Cieľom systému je zjednodušiť proces hľadania a ponúkania ubytovania prostredníctvom online platformy.

# Slovník pojmov

| Pojem | Anglický názov | Definícia |
|-------|-----------------|-----------|
| **Používateľ** | User | Osoba, ktorá používa systém na vyhľadávanie nehnuteľností alebo kontaktovanie vlastníkov. |
| **Vlastník** | Owner | Používateľ s oprávnením vytvárať, upravovať a odstraňovať inzeráty nehnuteľností. |
| **Nehnuteľnosť** | Property | Objekt určený na prenájom alebo predaj, napríklad byt, dom alebo izba. |
| **Inzerát** | Listing | Ponuka nehnuteľnosti zverejnená v systéme s detailnými informáciami. |
| **Prenájom** | Rental | Forma ponuky, pri ktorej je nehnuteľnosť dostupná na dočasné užívanie za poplatok. |
| **Predaj** | Sale | Forma ponuky, pri ktorej je nehnuteľnosť určená na odkúpenie. |
| **Adresa** | Address | Štruktúrované umiestnenie nehnuteľnosti obsahujúce ulicu, mesto, PSČ a krajinu. |
| **Cena** | Price | Finančná hodnota nehnuteľnosti evidovaná ako suma a mena. |
| **Typ nehnuteľnosti** | Property Type | Kategória nehnuteľnosti, napríklad byt, dom alebo izba. |
| **Vlastnosti nehnuteľnosti** | Property Features | Súbor charakteristík nehnuteľnosti, napríklad rozloha, počet izieb, poschodie, zariadenie a parkovanie. |
| **Popis** | Description | Textová informácia o vlastnostiach a stave nehnuteľnosti. |
| **Fotografia** | Photo | Obrázok priradený k inzerátu, ktorý zobrazuje nehnuteľnosť. |
| **Obľúbené** | Favorites | Zoznam inzerátov, ktoré si používateľ uložil na neskoršie zobrazenie. |
| **Konverzácia** | Conversation | Komunikačné vlákno medzi používateľom a vlastníkom týkajúce sa konkrétneho inzerátu. |
| **Správa** | Message | Jednotlivá textová správa odoslaná v rámci konverzácie. |
| **Žiadosť o obhliadku** | Viewing Request | Požiadavka používateľa na obhliadku konkrétnej nehnuteľnosti v navrhnutom termíne. |
| **Stav žiadosti o obhliadku** | Viewing Status | Informácia o tom, či je žiadosť o obhliadku čakajúca, schválená, zamietnutá alebo zrušená. |
| **Filter** | Filter | Mechanizmus na obmedzenie výsledkov vyhľadávania podľa vybraných kritérií. |
| **Detail inzerátu** | Listing Detail | Podrobný pohľad na vybranú nehnuteľnosť vrátane ceny, adresy, popisu, fotografie a kontaktu. |
| **Stav inzerátu** | Listing Status | Informácia o tom, či je inzerát aktívny alebo neaktívny. |
| **Registrácia** | Registration | Proces vytvorenia nového používateľského účtu v systéme. |
| **Autorizácia** | Authorization | Proces overenia a prihlásenia používateľa do systému. |
| **Test Case** | Test Case | Špecifikácia testovacieho scenára, ktorý definuje vstupné dáta, očakávaný výsledok a kroky potrebné na otestovanie funkcionality systému. |

# Prípady použitia

- **UC-01** Registrácia používateľa
- **UC-02** Prihlásenie používateľa
- **UC-03** Vyhľadávanie nehnuteľností
- **UC-04** Filtrovanie nehnuteľností
- **UC-05** Zobrazenie detailu nehnuteľnosti
- **UC-06** Kontaktovanie vlastníka nehnuteľnosti
- **UC-07** Uloženie inzerátu medzi obľúbené
- **UC-08** Vytvorenie nového inzerátu
- **UC-09** Úprava existujúceho inzerátu
- **UC-10** Odstránenie inzerátu
- **UC-11** Pridanie fotografií k inzerátu
- **UC-12** Zmena stavu inzerátu
- **UC-13** Vytvorenie žiadosti o obhliadku
- **UC-14** Spracovanie žiadosti o obhliadku
- **UC-15** Zobrazenie vlastností nehnuteľnosti

## UC-03 Vyhľadávanie nehnuteľností

**Účel**  
Vyhľadať dostupné nehnuteľnosti určené na prenájom alebo predaj.

**Používateľ**  
Používateľ

**Vstupné podmienky**  
Systém obsahuje aspoň jeden aktívny inzerát.

**Výstup**  
Používateľ vidí zoznam nehnuteľností, ktoré zodpovedajú vyhľadávaniu.

**Postup**

1. Používateľ otvorí hlavnú stránku alebo sekciu nehnuteľností.  
   Systém zobrazí dostupné aktívne inzeráty.

2. Používateľ zadá hľadaný výraz.  
   Hľadaný výraz môže byť napríklad názov mesta, ulice alebo typ nehnuteľnosti.

3. Používateľ potvrdí vyhľadávanie.

4. Systém spracuje zadaný výraz.

5. Systém vyhľadá zodpovedajúce aktívne inzeráty.

6. Systém zobrazí zoznam nájdených nehnuteľností.

**Alternatívny scenár**

2a. Používateľ nezadá žiadny hľadaný výraz.  
Systém zobrazí všetky aktívne inzeráty.

5a. Systém nenájde žiadne zodpovedajúce nehnuteľnosti.  
Systém zobrazí informáciu, že neboli nájdené žiadne výsledky.

3a. Používateľ zruší vyhľadávanie.  
Systém ponechá aktuálny zoznam inzerátov bez zmeny.

## UC-06 Kontaktovanie vlastníka nehnuteľnosti

**Účel**  
Odoslať správu vlastníkovi vybranej nehnuteľnosti v prípade záujmu.

**Používateľ**  
Používateľ

**Vstupné podmienky**  
Používateľ je prihlásený do systému.  
Vybraná nehnuteľnosť existuje a má aktívny inzerát.

**Výstup**  
Vlastník dostane správu od používateľa týkajúcu sa vybranej nehnuteľnosti.

**Postup**

1. Používateľ otvorí detail vybranej nehnuteľnosti.  
   Systém zobrazí detail inzerátu vrátane základných informácií a možnosti kontaktovať vlastníka.

2. Používateľ zvolí možnosť „Kontaktovať vlastníka“.

3. Systém zobrazí formulár na odoslanie správy.

4. Používateľ zadá text správy.

5. Používateľ odošle správu.

6. Systém overí vyplnenie povinných údajov.

7. Systém vytvorí alebo použije existujúcu konverzáciu pre daný inzerát.

8. Systém uloží správu do systému.

9. Systém sprístupní správu vlastníkovi v rámci konverzácie.

10. Systém zobrazí potvrdenie o úspešnom odoslaní správy.

**Alternatívny scenár**

2a. Používateľ nie je prihlásený.  
Systém vyzve používateľa na prihlásenie.

6a. Používateľ nevyplní text správy.  
Systém zobrazí chybové hlásenie a správu neodošle.

5a. Používateľ zruší odoslanie správy.  
Systém zavrie formulár a používateľ zostáva na detaile nehnuteľnosti.

## UC-08 Vytvorenie nového inzerátu

**Účel**  
Vytvoriť nový inzerát nehnuteľnosti určený na prenájom alebo predaj.

**Používateľ**  
Vlastník

**Vstupné podmienky**  
Vlastník je prihlásený do systému.

**Výstup**  
V systéme pribudne nový inzerát nehnuteľnosti.

**Postup**

1. Vlastník otvorí sekciu správy inzerátov.  
   Systém zobrazí zoznam jeho existujúcich inzerátov a možnosť vytvoriť nový inzerát.

2. Vlastník zvolí možnosť „Pridať inzerát“.

3. Systém zobrazí formulár na zadanie údajov o nehnuteľnosti.

4. Vlastník zadá názov inzerátu.

5. Vlastník zadá popis nehnuteľnosti.

6. Vlastník zadá adresu nehnuteľnosti.

7. Vlastník zadá cenu vrátane meny.

8. Vlastník vyberie typ nehnuteľnosti.

9. Vlastník zadá vlastnosti nehnuteľnosti, napríklad rozlohu, počet izieb, poschodie, zariadenie a parkovanie.

10. Vlastník zvolí, či ide o prenájom alebo predaj.

11. Vlastník môže pridať fotografie nehnuteľnosti.

12. Vlastník potvrdí vytvorenie inzerátu.

13. Systém overí vyplnenie povinných údajov.

14. Systém uloží nový inzerát do databázy.

15. Systém zobrazí vytvorený inzerát vlastníkovi.

**Alternatívny scenár**

11a. Vlastník nevloží fotografie.  
Systém vytvorí inzerát aj bez fotografií, ak sú ostatné povinné údaje vyplnené.

13a. Niektorý povinný údaj nie je vyplnený.  
Systém zobrazí chybové hlásenie a neuloží inzerát.

12a. Vlastník zruší vytváranie inzerátu.  
Systém nevytvorí nový inzerát a vlastník zostáva v sekcii správy inzerátov.

## UC-09 Úprava existujúceho inzerátu

**Účel**  
Upraviť údaje existujúceho inzerátu.

**Používateľ**  
Vlastník

**Vstupné podmienky**  
Vlastník je prihlásený do systému.  
Inzerát existuje a patrí prihlásenému vlastníkovi.

**Výstup**  
Údaje inzerátu sú v systéme aktualizované.

**Postup**

1. Vlastník otvorí sekciu správy inzerátov.  
   Systém zobrazí zoznam jeho inzerátov.

2. Vlastník vyberie konkrétny inzerát.

3. Vlastník zvolí možnosť „Upraviť inzerát“.

4. Systém zobrazí formulár s aktuálnymi údajmi inzerátu.

5. Vlastník upraví požadované údaje.

6. Vlastník potvrdí zmeny.

7. Systém overí správnosť údajov.

8. Systém uloží zmeny do databázy.

9. Systém zobrazí aktualizovaný detail inzerátu.

**Alternatívny scenár**

7a. Niektorý povinný údaj nie je vyplnený správne.  
Systém zobrazí chybové hlásenie a zmeny neuloží.

6a. Vlastník zruší úpravu inzerátu.  
Systém neuloží žiadne zmeny a vlastník zostáva na detaile inzerátu.

## UC-07 Uloženie inzerátu medzi obľúbené

**Účel**  
Uložiť vybraný inzerát medzi obľúbené pre neskoršie zobrazenie.

**Používateľ**  
Používateľ

**Vstupné podmienky**  
Používateľ je prihlásený do systému.  
Inzerát existuje a je aktívny.

**Výstup**  
Vybraný inzerát je uložený v zozname obľúbených používateľa.

**Postup**

1. Používateľ otvorí detail inzerátu.  
   Systém zobrazí detail nehnuteľnosti a možnosť uložiť ju medzi obľúbené.

2. Používateľ zvolí možnosť „Pridať medzi obľúbené“.

3. Systém overí, či už inzerát nie je uložený medzi obľúbenými.

4. Systém uloží inzerát do zoznamu obľúbených.

5. Systém zobrazí potvrdenie o úspešnom uložení.

**Alternatívny scenár**

2a. Používateľ nie je prihlásený.  
Systém vyzve používateľa na prihlásenie.

3a. Inzerát už je uložený medzi obľúbenými.  
Systém zobrazí informáciu, že inzerát je už v zozname obľúbených.

## UC-13 Vytvorenie žiadosti o obhliadku

**Účel**  
Vytvoriť žiadosť o obhliadku vybranej nehnuteľnosti.

**Používateľ**  
Používateľ

**Vstupné podmienky**  
Používateľ je prihlásený do systému.  
Vybraný inzerát existuje a je aktívny.

**Výstup**  
V systéme pribudne nová žiadosť o obhliadku so stavom pending.

**Postup**

1. Používateľ otvorí detail vybranej nehnuteľnosti.  
   Systém zobrazí detail inzerátu a možnosť požiadať o obhliadku.

2. Používateľ zvolí možnosť „Požiadať o obhliadku“.

3. Systém zobrazí formulár žiadosti o obhliadku.

4. Používateľ zadá navrhovaný termín obhliadky.

5. Používateľ môže pridať poznámku k žiadosti.

6. Používateľ odošle žiadosť.

7. Systém overí vyplnenie povinných údajov.

8. Systém uloží žiadosť o obhliadku so stavom pending.

9. Systém sprístupní žiadosť vlastníkovi nehnuteľnosti.

10. Systém zobrazí potvrdenie o úspešnom odoslaní žiadosti.

**Alternatívny scenár**

2a. Používateľ nie je prihlásený.  
Systém vyzve používateľa na prihlásenie.

7a. Používateľ nezadá termín obhliadky.  
Systém zobrazí chybové hlásenie a žiadosť neuloží.

6a. Používateľ zruší odoslanie žiadosti.  
Systém zavrie formulár a používateľ zostáva na detaile nehnuteľnosti.