# FACULTY OF INFORMATION AND COMMUNICATION TECHNOLOGY (ICT)
## Joint Group Assignment (JGA) 2026 — Theme: Digital Entrepreneurship
### Final Investigative Report & Prototype Portfolio

---

### Cover Page Information
- **Project Topic:** Campus Connect — A Sustainable Zero-Data Messaging and Subscription Platform for South African Tertiary Institutions
- **Lead Researcher & Developer:** Hlayiseko Hlungwani (Student No: 22409817, Email: hlayisekohh7@gmail.com)
- **Group Members & Roles:**
  1. **Hlayiseko Hlungwani** (Project Leader, Lead Software Architect & Full-Stack Developer)
  2. **Thabo Mokoena** (Student No: 22410452 — Scribe, System Analyst & Flowchart Designer)
  3. **Lerato Khumalo** (Student No: 22411983 — Financial Analyst & Mathematics Modeler)
  4. **Sipho Dlamini** (Student No: 22415629 — UX/UI Designer & Mobile Interface Engineer)
  5. **Naledi Molefe** (Student No: 22417834 — Research Specialist & Case Study Analyst)
  6. **Kagiso Baloyi** (Student No: 22418291 — Quality Assurance, Input Validation & Security)
  7. **Zanele Ndlovu** (Student No: 22419045 — Cloud Infrastructure & Network Reverse-Billing Specialist)
  8. **Musa Sithole** (Student No: 22420318 — Technical Documentation & Presentation Coordinator)
- **Subject Name & Code:** Foundations of Information and Communication Technology (ICT101 / BIS301)
- **Qualification Code & Group:** Diploma in ICT / B.Com Informatics (Group 4B)
- **Lecturer Surname:** Dr. Sithole / Prof. Van Der Merwe
- **Submission Date:** October 2026
- **Live Platform URL:** `https://connect.msc.ac.za` (Musa Campus Connect MSC — Local mirror: `index.html`)

---

## 1. Table of Contents
1. [Aim and Objectives](#2-aim-and-objectives)
2. [Problem Statement](#3-problem-statement)
3. [Introduction](#4-introduction)
4. [Findings](#5-findings)
   - 4.1 Field Research & Five Case Studies (MoyaApp, WhatsApp, Telegram, Vodacom e-School, biNu)
   - 4.2 Prototype Architecture & Functional Description (Campus Connect)
5. [Methods and Tools Used](#6-methods-and-tools-used)
6. [Results & Mathematical Analysis](#7-results--mathematical-analysis)
   - 6.1 Variables and Parameters Definition
   - 6.2 Elementary Statistics (Mean, Median, Range, Standard Deviation)
   - 6.3 12-Month Financial Projections, Cash Flow & Cost-Benefit Break-Even
7. [Recommendations](#8-recommendations)
   - 7.1 Fostering Innovation and Youth Employment in South Africa
   - 7.2 Scalable Architectural Integrations
8. [Conclusion](#9-conclusion)
9. [Reference List (Chicago Author-Date)](#10-reference-list-chicago-author-date)
10. [Appendices](#11-appendices)
    - Appendix A: Java Prototype Source Code (`CampusConnectApp.java`)
    - Appendix B: Pseudocode for Main Features
    - Appendix C: System Flowchart Specifications
    - Appendix D: IPO (Input-Processing-Output) Tables & Error Handling
    - Appendix E: Group Meeting Minutes & Evidence of Teamwork

---

## 2. Aim and Objectives

### 2.1 Project Aim
To design, model, and deploy a viable digital entrepreneurship prototype called **Campus Connect** that eliminates mobile data cost barriers for South African tertiary students through reverse-billed zero-data messaging, while generating sustainable commercial revenue via a R20/month premium subscription tier offering voice/video calls, academic document sharing, and priority support.

### 2.2 Measurable Objectives
1. **Mathematical & Economical Modeling:** Formulate mathematical equations and conduct elementary statistical evaluations (mean, median, range, standard deviation) across 5 campus cohorts, establishing a break-even threshold within 12 months.
2. **Java Core Engine Implementation:** Develop an object-oriented Java console prototype featuring strict input validation, procedural loops, menu navigation, and simulated telco reverse-billing bandwidth computations without runtime exceptions.
3. **Interactive Responsive Web Application:** Build and deploy a zero-latency, mobile-first web application reflecting the Campus Connect mobile user interface with interactive direct messaging, group collaboration, academic channels (e.g., BIS301 Updates), user profile management, and payment checkout flows.
4. **Comparative Market Investigation:** Critically examine five existing digital communication and zero-rated platforms in South Africa, identifying key structural shortcomings to define Campus Connect's competitive value proposition.
5. **National Development & Policy Alignment:** Propose concrete digital entrepreneurship recommendations detailing how student-built micro-subscription services foster youth employment and alleviate South Africa's "data apartheid."

---

## 3. Problem Statement
In South Africa, access to high-speed internet remains profoundly unequal—a phenomenon often described as "digital divide" or "data apartheid." While tertiary institutions increasingly migrate course communications, lecture timetables, and assignment notifications to digital channels, students from historically disadvantaged backgrounds face prohibitive mobile data costs. 

In 2026, mobile data in South Africa continues to trade at exorbitant out-of-bundle rates (averaging between R65 and R85 per gigabyte), while university Wi-Fi is strictly constrained to campus boundaries. Over 72% of South African university students live in off-campus accommodation or commute long distances daily. When students run out of data bundles at mid-month, they are abruptly cut off from critical academic notices (such as class changes or test venues), peer study group discussions, and direct communication with lecturers. 

Existing solutions fail to bridge this divide: mainstream instant messaging applications (such as WhatsApp and Telegram) consume mobile data for basic status checks and messages, leaving students stranded when their balance reaches R0.00. While educational sites are occasionally zero-rated, they are static web portals that lack instant peer-to-peer collaboration, instant messaging channels, and dynamic group discussion tools. 

There is an urgent requirement for a dedicated student platform that guarantees **100% data-free text messaging and channel announcements for all students at zero cost**, funded sustainably through a low-cost, high-value premium tier (R20/month) that unlocks audio calls, video calls, and rich document sharing.

---

## 4. Introduction
Digital entrepreneurship serves as a catalyst for sustainable community development and social mobility across the African continent. The Joint Group Assignment (JGA) 2026 presents an opportunity to harness information and communication technologies (ICT) to address structural developmental challenges in our immediate community.

Tertiary education in South Africa is the primary pathway out of intergenerational poverty. However, the efficacy of student learning hinges upon continuous, frictionless communication between learners, peers, and faculty members. **Campus Connect** is conceived as a digital entrepreneurship venture directly tackling Sustainable Development Goal 4 (Quality Education) and Goal 9 (Industry, Innovation, and Infrastructure). 

By leveraging telecommunication **reverse-billing proxies** (where the hosting organization absorbs cellular packet costs directly with MTN, Vodacom, Telkom, and Cell C at bulk enterprise wholesale rates of ~R0.035/MB), Campus Connect enables any student with an active SIM card to send and receive text messages, register for study groups, and follow module updates even with a R0.00 airtime and data balance. 

To ensure commercial longevity without relying indefinitely on philanthropic grants, Campus Connect adopts a freemium business model: 
- **Free Zero-Data Tier:** Guaranteed zero-data access for direct messages, study groups, module broadcast channels, and text announcements.
- **Campus Connect Premium Tier (R20/month):** An affordable micro-subscription tailored to student budgets that unlocks WebRTC audio/video consultations with lecturers, high-speed PDF/document sharing, and priority server routing.

---

## 5. Findings

### 5.1 Field Research: Five Case Studies of Real-World Digital Businesses

| Company / Platform | Country / Target | What They Do Well (Strengths) | What They Do Badly (Shortcomings) | Relevance & Gap for Campus Connect |
| :--- | :--- | :--- | :--- | :--- |
| **1. MoyaApp** *(biNu Africa)* | South Africa (Mass Market) | • Over 6 million monthly active users.<br>• Data-free messaging and news on MTN, Vodacom, Telkom, Cell C.<br>• Integrated prepaid financial services (MoyaPay). | • Cluttered with programmatic banner advertisements.<br>• Not structured for academic workflows or university module channels.<br>• Media/files require costly data vouchers. | Demonstrates that reverse-billing models work at scale in SA. Campus Connect provides a clean, education-focused environment with academic channel hierarchy without spammy ads. |
| **2. WhatsApp** *(Meta Platforms)* | Global (Ubiquitous) | • Deep market penetration across all demographics.<br>• End-to-end encryption.<br>• Familiar, intuitive user interface. | • Completely data-dependent; ceases working when balance reaches R0.00.<br>• Large video/voice file downloads rapidly exhaust monthly student data allowances. | Serves as the benchmark for UI familiarity. Campus Connect replicates WhatsApp's instant responsiveness while offering zero-rated IP routing. |
| **3. Telegram** *(Telegram FZ-LLC)* | Global (Tech Enthusiasts) | • Powerful broadcast channels and bot automation.<br>• Handles up to 200,000 members per supergroup.<br>• Fast cloud storage synchronization. | • No telco zero-rating agreements in South Africa.<br>• High baseline data overhead for background syncing.<br>• Overwhelming feature set for basic student queries. | Inspires the "Channels" architecture (e.g., BIS301 Updates). Campus Connect strips away unnecessary overhead and zero-rates channel broadcasts. |
| **4. Vodacom e-School** | South Africa (K-12 Students) | • Completely zero-rated for Vodacom subscribers.<br>• Structured curriculum-aligned content.<br>• Backed by a major national telecommunications provider. | • Carrier locked: only functions on Vodacom SIM cards.<br>• Static, one-way content consumption with zero peer-to-peer discussion or chat forums.<br>• Geared toward primary/secondary schools, not tertiary students. | Highlights the fatal flaw of single-network lock-in. Campus Connect is network-agnostic, integrating reverse-billing across all four major SA mobile carriers. |
| **5. Canvas / Moodle Mobile** | Global (Higher Education) | • Native integration with university grading, submission portals, and learning management systems (LMS). | • Heavy web applications that consume substantial data to refresh dashboards.<br>• Cumbersome and sluggish for rapid daily communications between classmates.<br>• Frequent session timeouts. | Campus Connect acts as the rapid, lightweight conversational front-end that complements institutional LMS systems. |

### 5.2 Prototype Architecture & Functional Description
The Campus Connect prototype consists of three tightly coupled modules:
1. **Interactive Mobile Web Client (`index.html`):** A high-fidelity mobile application matching the project screenshots:
   - **Chats Tab:** Filterable view (`All`, `Direct`, `Groups`, `Channels`), live search bar, dismissible "Go Premium" announcement banner, and zero-state placeholder.
   - **New Conversation Modal:** Dynamic multi-category creator supporting 1-on-1 Direct Chats, Study Groups ("e.g. Study Group"), and Academic Channels ("e.g. BIS301 Updates").
   - **Premium Upsell Portal:** Clear presentation of the R20/month tier featuring Voice Calls, Video Calls, File Sharing, and Priority Support with simulated instant checkout (NSFAS, Airtime, Ozow).
   - **Profile Dashboard:** User avatar "H", registered name (Hlayiseko Hlungwani), email (`hlayisekohh7@gmail.com`), metric counters for active Direct, Group, and Channel links, and account settings.
2. **Core Java Business Engine (`CampusConnectApp.java`):** A command-line system demonstrating procedural loops, defensive input validation (preventing crashes on illegal characters), object-oriented conversation storage, and mathematical modules for financial reporting.
3. **Analytical & Statistical Engine:** Embedded Chart.js dashboards computing monthly cash flows, subscriber conversion ratios, and descriptive statistics across campus faculties.

---

## 6. Methods and Tools Used

### 6.1 Programming Languages & Frameworks
- **Java (JDK 20):** Used to construct the primary algorithmic prototype `CampusConnectApp.java`. Java's strict static typing and object-oriented paradigms ensured memory safety, structured encapsulation, and robust mathematical computing.
- **HTML5 & Vanilla CSS3:** Employed for the interactive web prototype. Followed modern design principles (glassmorphism, CSS custom properties, responsive flexbox/grid, and mobile viewport emulation) without external bloatware.
- **JavaScript (ES6+):** Implemented client-side state management, search filtration, conversational event listeners, and dynamic UI transitions.

### 6.2 Data Modeling & Computing Tools
- **Microsoft Excel / CSV (`financial_model_data.csv`):** Utilized to build 12-month financial models, compute statistical variance, and evaluate cost-benefit ratios.
- **Chart.js (v4.4.0):** Leveraged for rendering real-time responsive data visualizations (multi-axis line graphs, stacked bar charts, and doughnut distribution charts).

### 6.3 Telecommunications & Cloud Infrastructure
- **Reverse-Billing Data APIs:** Integration with South African Mobile Network Operators (Vodacom, MTN, Telkom, Cell C) utilizing APN (Access Point Name) reverse-billing proxies. HTTP packet headers for text conversations are whitelisted and billed directly to the Campus Connect enterprise account.
- **WebRTC (Web Real-Time Communication):** Targeted architecture for the R20/month Premium tier enabling low-latency, peer-to-peer audio and video transmission.

---

## 7. Results & Mathematical Analysis

### 7.1 Variables and Parameters Definition (Phase 1 Step 5 Deliverable)

| Variable (Dynamic / Changing State) | Description & Units |
| :--- | :--- |
| $N_{total}$ | Total registered active student user base (students) |
| $c$ | Premium conversion rate percentage (%) |
| $N_{prem}$ | Number of paying premium subscribers ($N_{prem} = N_{total} \times c$) |
| $N_{free}$ | Number of free zero-data users ($N_{free} = N_{total} - N_{prem}$) |
| $D_{usage}$ | Average monthly zero-data text consumption per user (~25 MB) |
| $R_{gross}$ | Monthly gross subscription revenue (ZAR) |
| $C_{bandwidth}$ | Monthly reverse-billing telco expense ($N_{total} \times D_{usage} \times r_{telco}$) |
| $P_{net}$ | Monthly net profit or loss ($R_{gross} - C_{total}$) |

| Parameter (Fixed Constants) | Description & Value |
| :--- | :--- |
| $S_{fee}$ | Premium monthly subscription fee = **R20.00 / month** |
| $r_{telco}$ | Negotiated wholesale reverse-billing rate = **R0.035 / MB** |
| $C_{cloud}$ | Fixed monthly cloud hosting & database overhead = **R850.00 / month** |
| $D_{allowance}$ | Hard data allowance cap for text per user = **120 MB / month** |
| $T_{trial}$ | Free trial duration = **7 days** |

### 7.2 Elementary Statistics (Phase 3 Step 15 Deliverable)
To determine campus adoption variance, student enrollment numbers across five major university faculties were sampled:
- **Faculty A (Faculty of ICT):** 1,250 students
- **Faculty B (Faculty of Management Sciences):** 840 students
- **Faculty C (Faculty of Science):** 2,100 students
- **Faculty D (Faculty of Humanities):** 620 students
- **Faculty E (Faculty of Engineering & Built Environment):** 1,490 students

**Statistical Computations:**
1. **Sample Size ($n$):** 5 faculties
2. **Total Population ($ \sum x_i $):** $1,250 + 840 + 2,100 + 620 + 1,490 = \mathbf{6,300 \text{ students}}$
3. **Arithmetic Mean ($\bar{x}$):**
   $$\bar{x} = \frac{\sum x_i}{n} = \frac{6,300}{5} = \mathbf{1,260.00 \text{ students per faculty}}$$
4. **Median:**
   - Ordered array: $[620, 840, \mathbf{1250}, 1490, 2100]$
   - Median ($Q_2$): $\mathbf{1,250.00 \text{ students}}$
5. **Range:**
   $$\text{Range} = x_{max} - x_{min} = 2,100 - 620 = \mathbf{1,480.00 \text{ students}}$$
6. **Variance ($s^2$) & Standard Deviation ($s$):**
   $$s^2 = \frac{\sum (x_i - \bar{x})^2}{n} = \frac{(1250-1260)^2 + (840-1260)^2 + (2100-1260)^2 + (620-1260)^2 + (1490-1260)^2}{5} = 268,920$$
   $$s = \sqrt{268,920} \approx \mathbf{518.57 \text{ students}}$$
   *Interpretation:* A standard deviation of 518.57 students reflects healthy, predictable variation between specialized faculties (Humanities) and technical megadepartments (Science and ICT).

### 7.3 Financial Projections, Cash Flow & Cost-Benefit Break-Even
Using the financial model recorded in `financial_model_data.csv`:
- In **Month 1**, with 1,000 active students and a conservative 5.0% premium conversion (50 paying subscribers), gross revenue is R1,000. Operating costs (R875 reverse-billing + R850 hosting + R500 support = R2,225) result in an initial launch loss of -R1,225.
- By **Month 3**, student adoption reaches 2,200 users with an 8% conversion rate (176 paying subscribers = R3,520 revenue). Total costs are R3,375, yielding the first positive monthly net profit of **+R145.00**.
- By **Month 12**, scaling to 10,000 active students and a 17% conversion rate (1,700 paying subscribers), gross monthly revenue reaches **R34,000.00** against operating expenses of R11,600.00, generating a monthly net profit of **R22,400.00** (65.8% profit margin) and a cumulative Year 1 profit of **R88,865.00**.

**Break-Even Mathematical Formula:**
$$\text{Break-Even Subscribers} (Q_{BE}) = \frac{\text{Fixed Costs}}{\text{Price per Unit} - \text{Variable Cost per Unit}}$$
- Fixed Costs ($C_{cloud}$) = R850.00 / month
- Selling Price ($S_{fee}$) = R20.00 / month
- Variable Cost per Subscriber ($25\text{ MB} \times \text{R0.035}$) = R0.875
- Contribution Margin = $\text{R20.00} - \text{R0.875} = \mathbf{\text{R19.125}}$
$$Q_{BE} = \frac{\text{R850.00}}{\text{R19.125}} \approx \mathbf{44.45} \implies \mathbf{45 \text{ paying subscribers}}$$
*Conclusion:* The venture achieves operational break-even with just **45 paying subscribers**, demonstrating exceptional financial resilience and low capital barrier to entry.

---

## 8. Recommendations

### 8.1 Fostering Innovation and Youth Employment in South Africa
1. **University-Incubated Telco Partnerships:** South African universities (such as TUT, UP, UJ, and Wits) should partner directly with the Department of Communications and Digital Technologies (DCDT) to provide student software ventures with institutional reverse-billing APN allocations. Providing student startups with free enterprise reverse-billing credits for their first 12 months eliminates the primary financial barrier to launching zero-rated services.
2. **Student Micro-Enterprise & Peer Campus Ambassadors:** Campus Connect can create direct on-campus employment for 15 to 25 undergraduate students per university as "Campus Brand Champions" and technical community moderators. Students can earn a 20% recurring commission (R4/month per active subscriber) on every student they onboard to the Premium tier, fostering grassroots entrepreneurship.
3. **Integration with NSFAS Digital Learning Allowances:** The National Student Financial Aid Scheme (NSFAS) should permit students to allocate a micro-stipend of R20/month from their digital learning allowances to access accredited academic communications, voice consultations, and study repositories.

### 8.2 Scalable Architectural Integrations
1. **AI-Powered Academic FAQ Chatbot:** Integrate an automated Llama/Gemini API assistant directly into academic channels (e.g., BIS301 Updates). When students query exam timetables, assignment rubrics, or lecture venues, the AI bot answers instantly using natural language without expending human tutor time.
2. **South African Mobile Micropayment Gateways:** Integrate localized payment endpoints such as Ozow Instant EFT, Capitec Pay, Vodacom VodaPay, and MTN MoMo, allowing students without traditional credit cards to subscribe seamlessly via prepaid airtime or digital wallets.

---

## 9. Conclusion
The **Campus Connect** project successfully demonstrates how digital entrepreneurship can bridge critical socioeconomic divides in South African higher education. By combining zero-data text communications with an affordable R20/month premium subscription tier, the platform delivers guaranteed communication equity for disadvantaged students while establishing a financially self-sustaining commercial model. 

The software prototype meets all requirements specified in the JGA 2026 Student Guide:
- A fully tested, robust Java core application (`CampusConnectApp.java`) with input validation and loop architecture;
- An interactive, mobile-optimized web application (`index.html`) faithfully recreating the user screens for Chats, Channels, Premium, and Profile;
- Comprehensive mathematical modeling including elementary statistics (mean: 1,260, median: 1,250, range: 1,480, std dev: 518.57) and cost-benefit break-even analysis ($Q_{BE} = 45$ users).

Through institutional backing, reverse-billing agreements, and student-centric design, Campus Connect exemplifies how technology can foster sustainable community development across South Africa.

---

## 10. Reference List (Chicago Author-Date)

1. biNu Africa. 2026. "MoyaApp: Data-Free Messaging and Digital Services for Africa." biNu Digital Technologies. Accessed October 14, 2026. https://www.moya.app.
2. Independent Communications Authority of South Africa (ICASA). 2025. *Bi-Annual State of the ICT Sector in South Africa Report: Mobile Data Pricing and Consumer Access*. Pretoria: ICASA Publishing.
3. Statistics South Africa (Stats SA). 2025. *General Household Survey: Information and Communication Technology Disparities in Tertiary Education*. Statistical Release P0318. Pretoria: Stats SA.
4. Telegram FZ-LLC. 2026. "Telegram API and Channel Broadcast Specifications." Accessed October 12, 2026. https://core.telegram.org.
5. Vodacom Group. 2026. "Vodacom e-School: Zero-Rated Education for South African Learners." Vodacom Corporate Social Responsibility. Accessed October 10, 2026. https://www.vodacom.co.za/vodacom/services/vodacom-e-school.
6. WhatsApp LLC. 2026. "WhatsApp Business Platform Architecture and Bandwidth Optimization." Meta Platforms. Accessed October 11, 2026. https://www.whatsapp.com/business.

---

## 11. Appendices

### Appendix A: Java Prototype Source Code
*(Full source code is contained in the project repository file: `CampusConnectApp.java`)*

### Appendix B: Pseudocode for Main Features
```text
BEGIN CampusConnect_Session
    DISPLAY Welcome Banner and Active User Credentials
    INITIALIZE Default Channel "BIS301 Updates" and Group "ICT Study Group"
    
    WHILE User_Running IS TRUE DO
        DISPLAY Main Menu (1: Profile, 2: List, 3: Create, 4: Chat, 5: Premium, 6: Math, 7: Exit)
        INPUT User_Choice WITH Input_Validation(1, 7)
        
        IF User_Choice EQUALS 1 THEN
            CALL Display_Profile(User_Name, User_Email, Account_Tier, Chat_Counts)
        ELSE IF User_Choice EQUALS 2 THEN
            CALL Display_All_Conversations(Conversations_List)
        ELSE IF User_Choice EQUALS 3 THEN
            PROMPT Conversation_Type ("Direct", "Group", "Channel")
            PROMPT Conversation_Name
            PROMPT Conversation_Description
            CREATE New_Conversation_Object
            APPEND to Conversations_List
            INCREMENT Matching_Counter
            DISPLAY "Success: Created!"
        ELSE IF User_Choice EQUALS 4 THEN
            PROMPT Select_Conversation_Index
            DISPLAY Message_History
            PROMPT User_Message_Text
            APPEND User_Message_Text to Conversation
            GENERATE Automated_ZeroData_Response()
        ELSE IF User_Choice EQUALS 5 THEN
            DISPLAY Premium_Features (Voice, Video, Files, Priority)
            IF User_Tier IS Free THEN
                PROMPT "Subscribe for R20/month? (y/n)"
                IF Response IS "y" THEN
                    SET User_Tier = Premium
                    DISPLAY "Upgrade Successful!"
                END IF
            ELSE
                PROMPT "Cancel subscription? (y/n)"
            END IF
        ELSE IF User_Choice EQUALS 6 THEN
            CALL Calculate_Elementary_Statistics(Mean, Median, Range, StdDev)
            CALL Compute_12Month_Financial_CashFlow()
            CALL Compute_BreakEven_Analysis()
        ELSE IF User_Choice EQUALS 7 THEN
            SET User_Running = FALSE
            DISPLAY "System Exiting Cleanly. Goodbye!"
        END IF
    END WHILE
END CampusConnect_Session
```

### Appendix C: Flowchart Specifications
```
 [Start App]
      |
 [Initialize Default Channels & User "Hlayiseko Hlungwani"]
      |
 [Display Main Menu Options (1 - 7)] <---------------------------+
      |                                                           |
 [Read User Integer Input]                                        |
      |                                                           |
   / Is Input Valid (1-7)? \ -- No --> [Print Polite Error Msg] --+
      \        Yes          /
      |
 [Route to Selected Handler]:
   +--> Option 1: View Profile & Zero-Rating Bandwidth Status --->+
   +--> Option 2: List Filtered Conversations -------------------->+
   +--> Option 3: Create Direct/Group/Channel -------------------->+
   +--> Option 4: Send & Receive Zero-Data Chat Messages --------->+
   +--> Option 5: Subscribe to Premium (R20/mo) ------------------>+
   +--> Option 6: Execute Mathematical & Statistical Modeling ---->+
   +--> Option 7: Terminate Process Safely ---------------------> [Exit]
```

### Appendix D: IPO (Input – Processing – Output) Tables

| Menu Option / Feature | Input (Given to System) | Processing (Algorithm / Transformation) | Output (Result / Display) | Error Case & Handling |
| :--- | :--- | :--- | :--- | :--- |
| **1. View User Profile** | User selects Option 1. | Retrieves static/session profile object, counts active chats, verifies tier. | Renders Profile Card: Avatar "H", Name, Email, Direct/Group/Channel totals. | Profile not initialized $\rightarrow$ Fallback to default user `Hlayiseko Hlungwani`. |
| **2. List Conversations** | User selects Option 2. | Loops through `conversations` collection, formats type tag, timestamp, and message counts. | Formatted numbered list of all active chats. | No chats in list $\rightarrow$ Outputs friendly empty notice: "No conversations yet." |
| **3. Create Conversation** | Type (1-3), Name string, optional description string. | Validates non-empty name, instantiates `Conversation` object, increments category count. | "Success! Channel 'BIS301 Updates' created." | Empty name $\rightarrow$ Assigns contextual default based on type. |
| **4. Send & Read Chat** | Target chat index, text message string. | Appends message with user timestamp, triggers simulated zero-data peer response. | Updated message stream displaying sent and received messages. | Out of range index $\rightarrow$ Re-prompts politely without crashing. |
| **5. Upgrade to Premium** | Upgrade confirmation ('y'/'n'), payment method (1-3). | Sets `isPremiumUser = true`, activates voice/video permissions, logs audit event. | Confirmation receipt: "Welcome to Campus Connect Premium (R20/mo active)." | Invalid payment selection $\rightarrow$ Re-prompts 1-3. |
| **6. Statistical Analysis** | Faculty cohort array, student base input. | Calculates $\sum x$, Mean $\bar{x}$, Median $Q_2$, Range ($x_{max}-x_{min}$), and Standard Deviation $s$. | Comprehensive statistical dashboard with analytical interpretations. | Non-numeric student base $\rightarrow$ Defaults safely to 5,000 students. |

### Appendix E: Group Meeting Minutes & Evidence of Teamwork
- **Meeting 1 (28 May 2026):** Project Kick-off. Formed 8-member group from ICT timetable main group. Appointed Hlayiseko Hlungwani as Project Leader and Thabo Mokoena as Scribe. Evaluated three business concepts; unanimously selected "Campus Connect: Zero-Data Student Messaging".
- **Meeting 2 (12 June 2026):** Phase 1 Problem Domain & Variables Identification. Established parameters ($S_{fee} = \text{R20}$, $r_{telco} = \text{R0.035/MB}$) and variables ($N_{total}, c, P_{net}$). Designed draft IPO tables and system flowcharts.
- **Meeting 3 (26 June 2026):** Field Research Synthesis. Each member presented 1 researched competitor (MoyaApp, WhatsApp, Telegram, Vodacom e-School, biNu). Saved citations in Chicago Author-Date.
- **Meeting 4 (18 July 2026):** Java Architecture Review. Hlayiseko Hlungwani demonstrated initial working Java code. Implemented input validation loops to prevent crash bugs.
- **Meeting 5 (15 August 2026):** Phase 3 Web Interface & Math Modeling. Reviewed screenshots of mobile client (`connect.msc.ac.za` — Musa Campus Connect MSC). Integrated visualizations for 12-month cash flows and break-even calculations.
- **Meeting 6 (18 September 2026):** Final Polish & Presentation Rehearsal. Assigned 2-minute elevator pitch roles to all 8 group members. Finalized report formatting in accordance with Step 20.
