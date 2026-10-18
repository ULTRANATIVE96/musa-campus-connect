/**
 * ============================================================================
 * FACULTY OF INFORMATION AND COMMUNICATION TECHNOLOGY (ICT)
 * First Year and Foundation Unit
 * Joint Group Assignment (JGA) 2026 — Theme: Digital Entrepreneurship
 * ============================================================================
 * Project Title : Campus Connect — Zero-Data Messaging & Subscription Platform
 * Lead Student  : Hlayiseko Hlungwani (Email: hlayisekohh7@gmail.com)
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CampusConnectApp {

    // ========================================================================
    // SYSTEM PARAMETERS (Fixed values determined during Phase 1 Design)
    // ========================================================================
    private static final String APP_NAME = "Campus Connect";
    private static final String TAGLINE = "Zero-data messaging for South African Students";
    private static final double PREMIUM_SUBSCRIPTION_FEE = 20.00; // R20.00 per month
    private static final double TELCO_REVERSE_BILLING_RATE_PER_MB = 0.035; // R0.035 / MB negotiated rate
    private static final double FIXED_CLOUD_HOSTING_MONTHLY = 850.00; // Monthly server & gateway cost (ZAR)
    private static final double FREE_DATA_ALLOWANCE_MB_PER_USER = 120.0; // Monthly text data allowance per user


    // ========================================================================
    // SYSTEM VARIABLES (Dynamic runtime state)
    // ========================================================================
    private static String currentUserName = "Hlayiseko Hlungwani";
    private static String currentUserEmail = "hlayisekohh7@gmail.com";
    private static boolean isPremiumUser = false;
    private static int directChatCount = 0;
    private static int groupChatCount = 0;
    private static int channelCount = 0;

    // In-memory data structures
    private static final List<Conversation> conversations = new ArrayList<>();
    private static final List<String> systemAuditLogs = new ArrayList<>();

    // Sample campus adoption cohorts (number of active students across 5 campus faculties)
    private static final double[] CAMPUS_FACULTY_ADOPTION = { 1250.0, 840.0, 2100.0, 620.0, 1490.0 };

    // ========================================================================
    // DATA MODEL: Conversation (Direct, Group, Channel)
    // ========================================================================
    static class Conversation {
        String type; // "Direct", "Group", or "Channel"
        String name;
        String description;
        List<String> messages;
        Date createdAt;

        public Conversation(String type, String name, String description) {
            this.type = type;
            this.name = name;
            this.description = description;
            this.messages = new ArrayList<>();
            this.createdAt = new Date();
        }

        public void addMessage(String sender, String text) {
            this.messages.add("[" + sender + "]: " + text);
        }
    }

    // ========================================================================
    // MAIN ENTRY POINT
    // ========================================================================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeSeedData();

        printHeaderBanner();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = readIntegerInput(scanner, "Please select an option (1 - 7): ", 1, 7);

            System.out.println();
            switch (choice) {
                case 1:
                    handleViewProfile(scanner);
                    break;
                case 2:
                    handleListConversations();
                    break;
                case 3:
                    handleCreateNewConversation(scanner);
                    break;
                case 4:
                    handleSendAndReadMessages(scanner);
                    break;
                case 5:
                    handleSubscriptionUpgrade(scanner);
                    break;
                case 6:
                    handleFinancialAndStatisticalAnalysis(scanner);
                    break;
                case 7:
                    running = false;
                    System.out.println("============================================================");
                    System.out.println(" Thank you for using " + APP_NAME + "!");
                    System.out.println(" Empowering South African students through zero-data access.");
                    System.out.println(" Exiting system safely. Goodbye!");
                    System.out.println("============================================================");
                    break;
                default:
                    System.out.println("Unexpected selection. Returning to main menu.");
            }
            System.out.println();
        }

        scanner.close();
    }

    // ========================================================================
    // INITIALIZATION & SEED DATA
    // ========================================================================
    private static void initializeSeedData() {
        // Initial sample channel based on photo placeholder "e.g. BIS301 Updates"
        Conversation bis301 = new Conversation("Channel", "BIS301 Updates", "Official course announcements and exam schedules");
        bis301.addMessage("Dr. Sithole (Lecturer)", "Welcome everyone to BIS301. Please note test 1 is scheduled for next Friday.");
        bis301.addMessage("System", "Zero-rated document uploaded: Lecture_Notes_Week1.pdf");
        conversations.add(bis301);
        channelCount++;

        // Initial sample study group based on photo placeholder "e.g. Study Group"
        Conversation studyGroup = new Conversation("Group", "ICT Foundation Study Group", "Peer revision for mathematics and programming");
        studyGroup.addMessage("Hlayiseko Hlungwani", "Hi team, let's review the IPO table for Phase 1 tonight.");
        studyGroup.addMessage("Lerato", "Agreed! The zero-data mode is saving my night bundle.");
        conversations.add(studyGroup);
        groupChatCount++;

        systemAuditLogs.add("System booted successfully. Default zero-data proxy connection established.");
    }

    // ========================================================================
    // UI DISPLAY METHODS
    // ========================================================================
    private static void printHeaderBanner() {
        System.out.println("************************************************************");
        System.out.println("*                     CAMPUS CONNECT                       *");
        System.out.println("*            Zero-Data Student Messaging Prototype          *");
        System.out.println("*          Theme: Digital Entrepreneurship (JGA 2026)      *");
        System.out.println("************************************************************");
        System.out.println(" Active User : " + currentUserName + " (" + currentUserEmail + ")");
        System.out.println(" Status      : " + (isPremiumUser ? "[PREMIUM SUBSCRIBER]" : "[STANDARD ZERO-DATA USER]"));
        System.out.println(" Pricing     : Free text chatting | R" + String.format("%.2f", PREMIUM_SUBSCRIPTION_FEE) + "/mo for Audio, Video & Files");
        System.out.println("************************************************************");
    }

    private static void displayMainMenu() {
        System.out.println("------------------------- MAIN MENU ------------------------");
        System.out.println(" 1. View User Profile & Membership Status");
        System.out.println(" 2. View All Conversations (Direct, Groups, Channels)");
        System.out.println(" 3. Create New Conversation (Direct, Group, or Channel)");
        System.out.println(" 4. Open a Conversation & Send Message");
        System.out.println(" 5. Campus Connect Premium Subscription (R20/mo)");
        System.out.println(" 6. Mathematical & Financial Business Model Analysis");
        System.out.println(" 7. Exit Program");
        System.out.println("------------------------------------------------------------");
    }

    // ========================================================================
    // MENU HANDLERS
    // ========================================================================

    /**
     * Option 1: Displays the exact user details matching the WhatsApp screenshots
     */
    private static void handleViewProfile(Scanner scanner) {
        System.out.println("============================================================");
        System.out.println("                     USER PROFILE                           ");
        System.out.println("============================================================");
        System.out.println(" Avatar Initials : [ H ]");
        System.out.println(" Full Name       : " + currentUserName);
        System.out.println(" Email Address   : " + currentUserEmail);
        System.out.println(" Account Tier    : " + (isPremiumUser ? "PREMIUM VIP (R20/month active)" : "Standard User (Free Zero-Data)"));
        System.out.println("------------------------------------------------------------");
        System.out.println(" Total Direct Chats : " + directChatCount);
        System.out.println(" Total Groups       : " + groupChatCount);
        System.out.println(" Total Channels     : " + channelCount);
        System.out.println("------------------------------------------------------------");
        System.out.println(" Reverse-Billing Network Status: MTN / Vodacom Zero-Rating Active");
        System.out.println(" Mobile Data Used By Student   : 0.00 MB (Reverse-billed to sponsor)");
        System.out.println("============================================================");

        System.out.print("Press Enter to return to main menu...");
        scanner.nextLine();
    }

    /**
     * Option 2: Lists all conversations filtered by category
     */
    private static void handleListConversations() {
        System.out.println("============================================================");
        System.out.println("                 CONVERSATIONS LIST                         ");
        System.out.println("============================================================");
        if (conversations.isEmpty()) {
            System.out.println(" No conversations found. Start a chat to connect!");
            return;
        }

        for (int i = 0; i < conversations.size(); i++) {
            Conversation c = conversations.get(i);
            System.out.printf(" [%d] [%-7s] %s\n", (i + 1), c.type.toUpperCase(), c.name);
            System.out.println("     Description : " + c.description);
            System.out.println("     Messages    : " + c.messages.size() + " messages exchanged");
            System.out.println("     Created     : " + c.createdAt);
            System.out.println("     -------------------------------------------------------");
        }
    }

    /**
     * Option 3: Create conversation matching the modal in the screenshots
     */
    private static void handleCreateNewConversation(Scanner scanner) {
        System.out.println("============================================================");
        System.out.println("               NEW CONVERSATION CREATOR                     ");
        System.out.println("============================================================");
        System.out.println(" Select Conversation Category:");
        System.out.println("  1. Direct Chat (Classmate or Lecturer)");
        System.out.println("  2. Group Chat (Study Group or Project Team)");
        System.out.println("  3. Channel (Course Announcements e.g. BIS301)");

        int catChoice = readIntegerInput(scanner, "Enter selection (1 - 3): ", 1, 3);
        String type;
        String namePrompt;
        String defaultExample;

        if (catChoice == 1) {
            type = "Direct";
            namePrompt = "Enter recipient's full name or student number: ";
            defaultExample = "Thabo Mokoena (22108945)";
        } else if (catChoice == 2) {
            type = "Group";
            namePrompt = "Enter group name (e.g., Study Group): ";
            defaultExample = "Software Engineering Team Alpha";
        } else {
            type = "Channel";
            namePrompt = "Enter channel name (e.g., BIS301 Updates): ";
            defaultExample = "ICT Department Broadcaster";
        }

        System.out.print(namePrompt + "[Default: " + defaultExample + "]: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = defaultExample;
        }

        System.out.print("Enter description / purpose (optional): ");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) {
            description = "Educational discussion and collaborative communication.";
        }

        Conversation newConv = new Conversation(type, name, description);
        newConv.addMessage("System", "Conversation initiated under " + APP_NAME + " zero-data network.");
        conversations.add(newConv);

        if (type.equals("Direct")) directChatCount++;
        else if (type.equals("Group")) groupChatCount++;
        else channelCount++;

        System.out.println(">> Success! " + type + " '" + name + "' created successfully!");
        systemAuditLogs.add("New " + type + " created: " + name);
    }

    /**
     * Option 4: Send & Read messages
     */
    private static void handleSendAndReadMessages(Scanner scanner) {
        if (conversations.isEmpty()) {
            System.out.println("No conversations available. Please create one first.");
            return;
        }

        handleListConversations();
        int convIndex = readIntegerInput(scanner, "Select conversation number to open: ", 1, conversations.size()) - 1;
        Conversation target = conversations.get(convIndex);

        System.out.println("============================================================");
        System.out.println(" CHAT ROOM: " + target.name + " (" + target.type + ")");
        System.out.println(" " + target.description);
        System.out.println("============================================================");

        for (String msg : target.messages) {
            System.out.println(" " + msg);
        }
        System.out.println("------------------------------------------------------------");

        System.out.print("Type your message (or press Enter to exit chat): ");
        String userMsg = scanner.nextLine().trim();
        if (!userMsg.isEmpty()) {
            target.addMessage(currentUserName, userMsg);
            System.out.println(">> Message transmitted via zero-data protocol!");

            // Simulated peer response
            if (target.type.equalsIgnoreCase("Channel")) {
                target.addMessage("Campus Bot", "Acknowledgment: Announcement noted for campus records.");
            } else {
                target.addMessage("Study Partner (Online)", "Thanks for the message! Zero-data communication is working great.");
            }
        }
    }

    /**
     * Option 5: Handles the R20/month Premium subscription upsell
     */
    private static void handleSubscriptionUpgrade(Scanner scanner) {
        System.out.println("============================================================");
        System.out.println("             CAMPUS CONNECT PREMIUM UPGRADE                 ");
        System.out.println("     Unlock the full experience for your campus life        ");
        System.out.println("============================================================");
        System.out.println(" Current Status: " + (isPremiumUser ? "ACTIVE PREMIUM SUBSCRIBER" : "FREE ZERO-DATA USER"));
        System.out.println(" Premium Price : R" + String.format("%.2f", PREMIUM_SUBSCRIPTION_FEE) + " / month (7-Day Free Trial)");
        System.out.println();
        System.out.println(" Premium Features Included:");
        System.out.println("  [+] Voice Calls   : Crystal-clear audio calls to any user");
        System.out.println("  [+] Video Calls   : Face-to-face with lecturers and classmates");
        System.out.println("  [+] File Sharing  : Share documents, PDFs, and presentations");
        System.out.println("  [+] Priority Help : Priority technical support when you need it");
        System.out.println("============================================================");

        if (isPremiumUser) {
            System.out.println("You are already enjoying Campus Connect Premium!");
            System.out.print("Would you like to downgrade to Free Tier? (y/n): ");
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.startsWith("y")) {
                isPremiumUser = false;
                System.out.println(">> Subscription cancelled. You are now on the Free Zero-Data tier.");
            }
            return;
        }

        System.out.print("Would you like to subscribe to Premium for R20/month? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.startsWith("y")) {
            System.out.println("\nSelect Payment Method:");
            System.out.println(" 1. Student Airtime / Direct Carrier Billing");
            System.out.println(" 2. Campus Bursary Voucher / NSFAS Wallet");
            System.out.println(" 3. Ozow / Instant EFT / Debit Card");
            int payMethod = readIntegerInput(scanner, "Choose payment option (1 - 3): ", 1, 3);

            isPremiumUser = true;
            System.out.println("\n>> Payment approved! Welcome to Campus Connect Premium!");
            System.out.println(">> Audio calls, video calls, and unlimited PDF file sharing unlocked.");
            systemAuditLogs.add("User " + currentUserName + " upgraded to Premium via method #" + payMethod);
        } else {
            System.out.println(">> Upgrade cancelled. You can continue using Free Zero-Data messaging!");
        }
    }

    /**
     * Option 6: Phase 1 & Phase 3 Mathematical and Financial Computations
     * Covers:
     * - Variables & Parameters
     * - Elementary Statistics (Mean, Median, Range, Std Dev, Percentages)
     * - Projected Cash Flow & Cost-Benefit Analysis
     */
    private static void handleFinancialAndStatisticalAnalysis(Scanner scanner) {
        System.out.println("============================================================");
        System.out.println("        MATHEMATICAL COMPUTATIONS & FINANCIAL MODEL         ");
        System.out.println("           JGA 2026 Phase 1 & Phase 3 Evaluation            ");
        System.out.println("============================================================");
        System.out.println("Choose an analytical module:");
        System.out.println(" 1. Elementary Statistics of Campus Faculty Cohorts");
        System.out.println(" 2. Financial Projections & Cash Flow (12-Month Forecast)");
        System.out.println(" 3. Cost-Benefit & Break-Even Analysis");
        System.out.println(" 4. Variables & Parameters Table Reference");

        int choice = readIntegerInput(scanner, "Select module (1 - 4): ", 1, 4);
        System.out.println();

        switch (choice) {
            case 1:
                computeElementaryStatistics();
                break;
            case 2:
                computeFinancialProjections(scanner);
                break;
            case 3:
                computeBreakEvenAnalysis();
                break;
            case 4:
                displayVariablesAndParameters();
                break;
        }

        System.out.print("\nPress Enter to return to main menu...");
        scanner.nextLine();
    }

    // ========================================================================
    // MATHEMATICAL LOGIC: ELEMENTARY STATISTICS (Step 15)
    // ========================================================================
    private static void computeElementaryStatistics() {
        System.out.println("------------------------------------------------------------");
        System.out.println(" 1. ELEMENTARY STATISTICS: CAMPUS STUDENT COHORT ADOPTION  ");
        System.out.println("------------------------------------------------------------");
        System.out.println("Data points (Faculties: ICT, Management, Humanities, Science, Eng):");
        System.out.println(" " + Arrays.toString(CAMPUS_FACULTY_ADOPTION));

        int n = CAMPUS_FACULTY_ADOPTION.length;
        double sum = 0;
        double min = CAMPUS_FACULTY_ADOPTION[0];
        double max = CAMPUS_FACULTY_ADOPTION[0];

        for (double val : CAMPUS_FACULTY_ADOPTION) {
            sum += val;
            if (val < min) min = val;
            if (val > max) max = val;
        }

        // Mean
        double mean = sum / n;

        // Median
        double[] sorted = CAMPUS_FACULTY_ADOPTION.clone();
        Arrays.sort(sorted);
        double median;
        if (n % 2 == 0) {
            median = (sorted[n / 2 - 1] + sorted[n / 2]) / 2.0;
        } else {
            median = sorted[n / 2];
        }

        // Range
        double range = max - min;

        // Standard Deviation
        double varianceSum = 0;
        for (double val : sorted) {
            varianceSum += Math.pow(val - mean, 2);
        }
        double standardDeviation = Math.sqrt(varianceSum / n);

        System.out.printf(" Total Sample Population (N) : %d faculty departments\n", n);
        System.out.printf(" Total Targeted Students     : %.0f students\n", sum);
        System.out.printf(" Arithmetic Mean (Average)   : %.2f students per faculty\n", mean);
        System.out.printf(" Median Value                : %.2f students\n", median);
        System.out.printf(" Minimum Faculty Adoption    : %.0f students\n", min);
        System.out.printf(" Maximum Faculty Adoption    : %.0f students\n", max);
        System.out.printf(" Data Range (Max - Min)      : %.2f students\n", range);
        System.out.printf(" Standard Deviation          : %.2f\n", standardDeviation);
        System.out.println(" Interpretation: A standard deviation of " + String.format("%.2f", standardDeviation)
                + " demonstrates solid variance between large faculties (Science/ICT) and smaller faculties.");
    }

    // ========================================================================
    // MATHEMATICAL LOGIC: FINANCIAL PROJECTIONS (Step 5 & Step 15)
    // ========================================================================
    private static void computeFinancialProjections(Scanner scanner) {
        System.out.println("------------------------------------------------------------");
        System.out.println(" 2. FINANCIAL CASH FLOW MODEL (12 MONTH ESTIMATION)         ");
        System.out.println("------------------------------------------------------------");

        System.out.print("Enter expected total active student base [Default: 5000]: ");
        String baseInput = scanner.nextLine().trim();
        int totalStudents = baseInput.isEmpty() ? 5000 : Integer.parseInt(baseInput);

        System.out.print("Enter estimated Premium conversion percentage (e.g. 15 for 15%) [Default: 12%]: ");
        String convInput = scanner.nextLine().trim();
        double convRate = convInput.isEmpty() ? 0.12 : (Double.parseDouble(convInput) / 100.0);

        int premiumUsers = (int) Math.round(totalStudents * convRate);
        int freeUsers = totalStudents - premiumUsers;

        // Revenue calculations
        double monthlySubscriptionRev = premiumUsers * PREMIUM_SUBSCRIPTION_FEE;
        double annualSubscriptionRev = monthlySubscriptionRev * 12.0;

        // Telco reverse-billing bandwidth expense
        // Average active text user consumes approx 25 MB/month on lightweight messaging
        double avgTextConsumptionMB = 25.0;
        double monthlyReverseBillingCost = totalStudents * avgTextConsumptionMB * TELCO_REVERSE_BILLING_RATE_PER_MB;
        double monthlyTotalCosts = monthlyReverseBillingCost + FIXED_CLOUD_HOSTING_MONTHLY;
        double monthlyNetProfit = monthlySubscriptionRev - monthlyTotalCosts;
        double annualNetProfit = monthlyNetProfit * 12.0;
        double profitMarginPercent = (monthlySubscriptionRev > 0) ? (monthlyNetProfit / monthlySubscriptionRev) * 100.0 : 0.0;

        System.out.println("\n--- PROJECTION SUMMARY ---");
        System.out.printf(" Total Active Students        : %d users\n", totalStudents);
        System.out.printf(" Free Zero-Data Users         : %d users (%.1f%%)\n", freeUsers, (1.0 - convRate) * 100.0);
        System.out.printf(" Premium Paying Users         : %d users (%.1f%%)\n", premiumUsers, convRate * 100.0);
        System.out.println("------------------------------------------------------------");
        System.out.printf(" Monthly Gross Revenue (ZAR)  : R %,10.2f\n", monthlySubscriptionRev);
        System.out.printf("  - Reverse-Billing Telco Cost: R %,10.2f (%.1f MB/student @ R%.3f/MB)\n",
                monthlyReverseBillingCost, avgTextConsumptionMB, TELCO_REVERSE_BILLING_RATE_PER_MB);
        System.out.printf("  - Cloud Hosting & Gateway   : R %,10.2f\n", FIXED_CLOUD_HOSTING_MONTHLY);
        System.out.printf(" Monthly Total Operating Cost : R %,10.2f\n", monthlyTotalCosts);
        System.out.printf(" Monthly Net Profit / (Loss)  : R %,10.2f\n", monthlyNetProfit);
        System.out.printf(" Annualized Net Profit (ZAR)  : R %,10.2f\n", annualNetProfit);
        System.out.printf(" Profit Margin Percentage     : %.2f%%\n", profitMarginPercent);
        System.out.println("------------------------------------------------------------");
    }

    // ========================================================================
    // MATHEMATICAL LOGIC: BREAK-EVEN COMPUTATION
    // ========================================================================
    private static void computeBreakEvenAnalysis() {
        System.out.println("------------------------------------------------------------");
        System.out.println(" 3. COST-BENEFIT & BREAK-EVEN ANALYSIS                      ");
        System.out.println("------------------------------------------------------------");

        // Break Even = Fixed Costs / (Selling Price per Unit - Variable Cost per Unit)
        // Fixed cost = Cloud hosting R850.00
        // Variable cost per subscriber = Telco reverse billing R0.875
        double variableCostPerUser = 25.0 * TELCO_REVERSE_BILLING_RATE_PER_MB; // ~R0.875
        double contributionMargin = PREMIUM_SUBSCRIPTION_FEE - variableCostPerUser; // R20 - R0.875 = R19.125
        double breakEvenUsers = FIXED_CLOUD_HOSTING_MONTHLY / contributionMargin;

        System.out.printf(" Monthly Fixed Infrastructure Costs : R %.2f\n", FIXED_CLOUD_HOSTING_MONTHLY);
        System.out.printf(" Selling Price per Premium Sub      : R %.2f / month\n", PREMIUM_SUBSCRIPTION_FEE);
        System.out.printf(" Variable Bandwidth Cost per User   : R %.3f / month\n", variableCostPerUser);
        System.out.printf(" Contribution Margin per Subscriber : R %.3f\n", contributionMargin);
        System.out.printf(" Break-Even Quantity                : %.0f paying subscribers\n", Math.ceil(breakEvenUsers));
        System.out.println(" Conclusion: Campus Connect reaches monthly profitability with as few as "
                + (int) Math.ceil(breakEvenUsers) + " paying premium students.");
    }

    // ========================================================================
    // VARIABLES & PARAMETERS TABLE (Phase 1 Step 5)
    // ========================================================================
    private static void displayVariablesAndParameters() {
        System.out.println("------------------------------------------------------------");
        System.out.println(" 4. VARIABLES & PARAMETERS TABLE (Phase 1 Step 5 Deliverable)");
        System.out.println("------------------------------------------------------------");
        System.out.printf(" %-30s | %-25s\n", "IDENTIFIED VARIABLES (Changes)", "IDENTIFIED PARAMETERS (Fixed)");
        System.out.println("------------------------------------------------------------");
        System.out.printf(" %-30s | %-25s\n", "totalRegisteredStudents", "PREMIUM_FEE = R20.00/mo");
        System.out.printf(" %-30s | %-25s\n", "premiumConversionRate", "TELCO_RATE = R0.035/MB");
        System.out.printf(" %-30s | %-25s\n", "monthlyDataUsageMB", "SERVER_HOSTING = R850.00/mo");
        System.out.printf(" %-30s | %-25s\n", "activeConversationsCount", "FREE_ALLOWANCE = 120 MB");
        System.out.printf(" %-30s | %-25s\n", "monthlyNetProfit", "CURRENCY = South African Rand (ZAR)");
        System.out.println("------------------------------------------------------------");
    }

// ========================================================================
    // ROBUST INPUT VALIDATION HELPER (Prevents crashes on bad input)
    // ========================================================================
    /**
     * Rejects numbers outside bounds and non-numeric letters without crashing.
     * Complies with Step 13 & Step 17 requirement.
     */
    private static int readIntegerInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();

            if (raw.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number between " + min + " and " + max + ".");
                continue;
            }

            try {
                int value = Integer.parseInt(raw);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Out of bounds. Please enter a valid number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid format. Please enter numbers only (letters and symbols are not permitted).");
            }
        }
    }
}
