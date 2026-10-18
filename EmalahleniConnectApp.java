/**
 * ============================================================================
 * MUSA CAMPUS CONNECT (MSC) — ZERO-DATA STUDENT MESSAGING PLATFORM
 * Faculty of Information and Communication Technology (ICT)
 * TUT eMalahleni Campus / South African Higher Education
 * ============================================================================
 * Full Java Web Application (Pure Java + HTML + CSS — Zero JS required)
 * Runs natively on Java via embedded HttpServer
 * Start via: `npm run dev` or `java EmalahleniConnectApp`
 * ============================================================================
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmalahleniConnectApp {

    private static final int PORT = 3000;

    // ========================================================================
    // APPLICATION STATE (Managed entirely in Java)
    // ========================================================================
    static class Message {
        String sender;
        String text;
        String time;
        boolean isMe;

        public Message(String sender, String text, String time, boolean isMe) {
            this.sender = sender;
            this.text = text;
            this.time = time;
            this.isMe = isMe;
        }
    }

    static class Conversation {
        int id;
        String type; // "direct", "group", "channel"
        String name;
        String description;
        String timestamp;
        boolean isVerified;
        int unreadCount;
        List<Message> messages = new ArrayList<>();

        public Conversation(int id, String type, String name, String description, String timestamp, boolean isVerified, int unreadCount) {
            this.id = id;
            this.type = type;
            this.name = name;
            this.description = description;
            this.timestamp = timestamp;
            this.isVerified = isVerified;
            this.unreadCount = unreadCount;
        }
    }

    // User Account Definition & User Registry
    static class UserAccount {
        String name;
        String email;
        String password;
        String role; // "admin" or "user"
        String department;
        String initials;

        public UserAccount(String name, String email, String password, String role, String department, String initials) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
            this.department = department;
            this.initials = initials;
        }
    }

    static final Map<String, UserAccount> userRegistry = new HashMap<>();

    private static void initUserRegistry() {
        if (!userRegistry.isEmpty()) return;
        // The permanent Manager / Administrator account (always kept and pre-authorized)
        UserAccount manager = new UserAccount(
            "Dr. P. Sithole",
            "admin@msc.ac.za",
            "admin123",
            "admin",
            "Faculty of ICT / Campus Administration",
            "A"
        );
        userRegistry.put("admin@msc.ac.za", manager);
        userRegistry.put("admin", manager);
    }

    // Authentication & Role-Based Access Control State
    static boolean isLoggedIn = false;
    static String currentUserRole = "user"; // "user" (Student) or "admin" (Campus Administrator)
    static String userName = "Student";
    static String userEmail = "";
    static String userInitials = "S";
    static boolean isPremium = false;
    static double totalReverseBilledMB = 18.42;

    // Conversations Repository
    static final List<Conversation> conversations = new ArrayList<>();
    static int activeChatId = 1;

    // ========================================================================
    // MAIN METHOD: Launches Embedded Java HTTP Web Server
    // ========================================================================
    public static void main(String[] args) throws IOException {
        initUserRegistry();
        initSampleData();

        int port = PORT;
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (java.net.BindException e) {
            port = 3001;
            server = HttpServer.create(new InetSocketAddress(port), 0);
        }
        
        // Route Handlers
        server.createContext("/", new MainAppHandler());
        server.createContext("/styles.css", new StaticCssHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/send-message", new SendMessageHandler());
        server.createContext("/new-conversation", new NewConversationHandler());
        server.createContext("/upgrade-premium", new UpgradePremiumHandler());
        server.createContext("/admin-broadcast", new AdminBroadcastHandler());
        server.createContext("/financial_model_data.csv", new CsvExportHandler());
        server.createContext("/signout", new SignoutHandler());
        
        server.setExecutor(null);
        server.start();

        System.out.println("==================================================================");
        System.out.println("   MUSA CAMPUS CONNECT (MSC) — ZERO-DATA MESSAGING (JAVA APP)     ");
        System.out.println("==================================================================");
        System.out.println(" Server successfully running on: http://localhost:" + port);
        System.out.println(" Portal URL: connect.msc.ac.za (Musa Campus Connect MSC)");
        System.out.println(" Technology Stack: Pure Java Core + HTML5 + CSS3 (Zero JS)");
        System.out.println(" Default Role: " + currentUserRole.toUpperCase() + " (User: " + userName + ")");
        System.out.println(" Press Ctrl + C to stop server.");
        System.out.println("==================================================================");

        try {
            if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.awt.Desktop.getDesktop().browse(new java.net.URI("http://localhost:" + port));
            }
        } catch (Exception ignored) {}
    }

    // ========================================================================
    // SAMPLE DATA INITIALIZATION (Musa Campus Connect - MSC)
    // ========================================================================
    private static void initSampleData() {
        if (!conversations.isEmpty()) return;

        // Channel 1: BIS301 eMalahleni Updates (Verified)
        Conversation bis301 = new Conversation(1, "channel", "BIS301 Updates", "Official course announcements and exam schedules", "16:08", true, 1);
        bis301.messages.add(new Message("Dr. P. Sithole (Lecturer)", "Welcome to BIS301 eMalahleni Campus. Please note Test 1 will take place in ICT Lab 3 next Friday at 09:00.", "14:20", false));
        bis301.messages.add(new Message("System", "MSC Zero-Data Gateway verified: Notices broadcasted without airtime charges.", "14:21", false));
        bis301.messages.add(new Message("Hlayiseko Hlungwani", "Thank you Dr. Sithole, are the revision slides uploaded for zero-rated download?", "16:05", true));
        bis301.messages.add(new Message("Dr. P. Sithole (Lecturer)", "Yes, lecture slides are uploaded and zero-rated for all students on MSC campus edge nodes.", "16:08", false));
        conversations.add(bis301);

        // Group 2: ICT Foundation Study Group
        Conversation ictGroup = new Conversation(2, "group", "ICT Foundation Study Group", "Peer study group for Java programming & algorithms", "15:45", false, 0);
        ictGroup.messages.add(new Message("Kagiso Baloyi", "Has everyone reviewed the Java HttpServer architecture for our final project?", "11:30", false));
        ictGroup.messages.add(new Message("Naledi Molefe", "Yes! The zero-data Reverse-Billing formula is working cleanly.", "12:15", false));
        ictGroup.messages.add(new Message("Hlayiseko Hlungwani", "Awesome, I compiled the Java core engine with pure HTML and responsive styling.", "15:45", true));
        conversations.add(ictGroup);

        // Direct 3: Thabo Mokoena (Classmate)
        Conversation thabo = new Conversation(3, "direct", "Thabo Mokoena (22410452)", "Fellow ICT student & Project Scribe", "Yesterday", false, 0);
        thabo.messages.add(new Message("Thabo Mokoena", "Hi Hlayiseko, did you get the financial model figures from the student guide?", "Yesterday 18:20", false));
        thabo.messages.add(new Message("Hlayiseko Hlungwani", "Yes, got them. Let me know if you need the break-even formulas checked.", "Yesterday 18:25", true));
        conversations.add(thabo);

        // Channel 4: Student Representative Council (SRC)
        Conversation src = new Conversation(4, "channel", "SRC Announcements eMalahleni", "Official campus notices, NSFAS updates & student affairs", "10:15", true, 0);
        src.messages.add(new Message("SRC President", "Reminder: NSFAS transport allowances disbursement starts this Friday at Building 2.", "10:15", false));
        conversations.add(src);

        // Group 5: TUT Campus Safety & Health Clinic
        Conversation clinic = new Conversation(5, "group", "TUT Health & Security Desk", "Emergency campus contacts and health clinic schedule", "08:30", true, 0);
        clinic.messages.add(new Message("Campus Security", "eMalahleni Campus perimeter gates are monitored 24/7. Emergency contact: 013 653 3100.", "08:30", false));
        conversations.add(clinic);
    }

    // ========================================================================
    // MAIN APP ROUTE HANDLER (Renders Dynamic HTML with RBAC)
    // ========================================================================
    static class MainAppHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            Map<String, String> params = parseQueryParams(query);

            String screen = params.getOrDefault("screen", "chats");
            String filter = params.getOrDefault("filter", "all");
            String search = params.getOrDefault("q", "").toLowerCase().trim();

            // If user is not logged in and not already requesting the login screen, force login screen
            if (!isLoggedIn && !screen.equals("login")) {
                screen = "login";
            }

            // ROLE-BASED ACCESS CONTROL: User does NOT have admin page!
            if ("admin".equalsIgnoreCase(screen) && !"admin".equalsIgnoreCase(currentUserRole)) {
                redirect(exchange, "/?screen=chats&error=unauthorized");
                return;
            }
            
            if (params.containsKey("chat")) {
                try {
                    activeChatId = Integer.parseInt(params.get("chat"));
                    screen = "chatView";
                    Conversation c = findConversation(activeChatId);
                    if (c != null) c.unreadCount = 0; // Mark as read
                } catch (NumberFormatException ignored) {}
            }

            String htmlResponse = renderHtmlPage(screen, filter, search, params);
            byte[] bytes = htmlResponse.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    // ========================================================================
    // ACTION HANDLER: Login & Role Authentication
    // ========================================================================
    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> postData = parseFormData(exchange);
                String role = postData.getOrDefault("role", "").toLowerCase().trim();
                String email = postData.getOrDefault("email", "").toLowerCase().trim();
                String password = postData.getOrDefault("password", "").trim();

                // 1-Click Campus Manager VIP Access (Kept pre-authorized)
                if ("admin".equals(role) || "admin@msc.ac.za".equals(email)) {
                    UserAccount manager = userRegistry.get("admin@msc.ac.za");
                    if (manager != null) {
                        currentUserRole = manager.role;
                        userName = manager.name + " (Campus Manager)";
                        userEmail = manager.email;
                        userInitials = manager.initials;
                        isLoggedIn = true;
                        redirect(exchange, "/?screen=admin");
                        return;
                    }
                }

                if (email.isEmpty()) {
                    redirect(exchange, "/?screen=login&tab=login&error=empty");
                    return;
                }

                // Check if account exists in registry
                UserAccount account = userRegistry.get(email);
                if (account == null) {
                    // ACCOUNT DOES NOT EXIST! BLOCK LOGIN AND REQUIRE REGISTRATION
                    redirect(exchange, "/?screen=login&tab=login&error=notfound&attempted=" + urlEncode(email));
                    return;
                }

                // Verify password
                if (!password.isEmpty() && !password.equals(account.password)) {
                    redirect(exchange, "/?screen=login&tab=login&error=badpass&attempted=" + urlEncode(email));
                    return;
                }

                // Successful login
                currentUserRole = account.role;
                userName = account.name + (account.role.equals("admin") ? " (Campus Manager)" : " (Student)");
                userEmail = account.email;
                userInitials = account.initials;
                isLoggedIn = true;

                if ("admin".equalsIgnoreCase(account.role)) {
                    redirect(exchange, "/?screen=admin");
                } else {
                    redirect(exchange, "/?screen=chats");
                }
            } else {
                redirect(exchange, "/?screen=login");
            }
        }
    }

    // ========================================================================
    // ACTION HANDLER: Register Student Account
    // ========================================================================
    static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> postData = parseFormData(exchange);
                String fullName = postData.getOrDefault("fullName", "").trim();
                String email = postData.getOrDefault("email", "").toLowerCase().trim();
                String department = postData.getOrDefault("department", "Faculty of ICT").trim();
                String password = postData.getOrDefault("password", "").trim();
                String confirmPass = postData.getOrDefault("confirmPassword", "").trim();

                if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    redirect(exchange, "/?screen=login&tab=register&error=missing&name=" + urlEncode(fullName) + "&attempted=" + urlEncode(email));
                    return;
                }

                if (!confirmPass.isEmpty() && !password.equals(confirmPass)) {
                    redirect(exchange, "/?screen=login&tab=register&error=mismatch&name=" + urlEncode(fullName) + "&attempted=" + urlEncode(email));
                    return;
                }

                if (userRegistry.containsKey(email)) {
                    redirect(exchange, "/?screen=login&tab=login&error=exists&attempted=" + urlEncode(email));
                    return;
                }

                // Derive initials
                String initials = "S";
                String[] parts = fullName.split("\\s+");
                if (parts.length >= 2 && !parts[0].isEmpty() && !parts[1].isEmpty()) {
                    initials = ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
                } else if (!fullName.isEmpty()) {
                    initials = ("" + fullName.charAt(0)).toUpperCase();
                }

                UserAccount newAcc = new UserAccount(fullName, email, password, "user", department, initials);
                userRegistry.put(email, newAcc);

                // Auto sign-in and redirect to chats with welcome notice
                currentUserRole = "user";
                userName = fullName + " (Student)";
                userEmail = email;
                userInitials = initials;
                isLoggedIn = true;

                redirect(exchange, "/?screen=chats&registered=true&user=" + urlEncode(fullName));
            } else {
                redirect(exchange, "/?screen=login&tab=register");
            }
        }
    }

    // ========================================================================
    // STATIC CSS HANDLER
    // ========================================================================
    static class StaticCssHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File cssFile = new File("styles.css");
            if (cssFile.exists()) {
                byte[] bytes = new byte[(int) cssFile.length()];
                try (FileInputStream fis = new FileInputStream(cssFile)) {
                    fis.read(bytes);
                }
                exchange.getResponseHeaders().set("Content-Type", "text/css; charset=UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        }
    }

    // ========================================================================
    // STATIC CSV EXPORT HANDLER
    // ========================================================================
    static class CsvExportHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            File csvFile = new File("financial_model_data.csv");
            if (csvFile.exists()) {
                byte[] bytes = new byte[(int) csvFile.length()];
                try (FileInputStream fis = new FileInputStream(csvFile)) {
                    fis.read(bytes);
                }
                exchange.getResponseHeaders().set("Content-Type", "text/csv; charset=UTF-8");
                exchange.getResponseHeaders().set("Content-Disposition", "attachment; filename=\"msc_financial_telemetry.csv\"");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        }
    }

    // ========================================================================
    // ACTION HANDLER: Send Message
    // ========================================================================
    static class SendMessageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> postData = parseFormData(exchange);
                int chatId = Integer.parseInt(postData.getOrDefault("chatId", "1"));
                String text = postData.getOrDefault("messageText", "").trim();

                if (!text.isEmpty()) {
                    Conversation target = findConversation(chatId);
                    if (target != null) {
                        String timeNow = new SimpleDateFormat("HH:mm").format(new Date());
                        target.messages.add(new Message(userName, text, timeNow, true));
                        target.timestamp = "Just now";
                        totalReverseBilledMB += 0.05;

                        // Contextual Simulated response
                        String replySender = target.type.equals("channel") ? "Dr. P. Sithole (Lecturer)" : "TUT Peer (Online)";
                        String replyText = target.type.equals("channel") 
                            ? "Acknowledgment: Message verified under Musa Campus Connect (MSC) zero-data gateway."
                            : "Received loud and clear via MSC zero-data tunnel!";
                        target.messages.add(new Message(replySender, replyText, timeNow, false));
                    }
                }
                redirect(exchange, "/?screen=chatView&chat=" + chatId);
            } else {
                redirect(exchange, "/");
            }
        }
    }

    // ========================================================================
    // ACTION HANDLER: New Conversation
    // ========================================================================
    static class NewConversationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> postData = parseFormData(exchange);
                String type = postData.getOrDefault("type", "channel").toLowerCase().trim();
                String name = postData.getOrDefault("name", "").trim();
                String desc = postData.getOrDefault("description", "").trim();

                if (name.isEmpty()) {
                    name = type.equals("channel") ? "BIS301 Updates" : (type.equals("group") ? "Study Group" : "Classmate");
                }
                if (desc.isEmpty()) {
                    desc = "Educational discussion under Musa Campus Connect (MSC) zero-data network.";
                }

                int newId = conversations.size() + 10;
                Conversation newConv = new Conversation(newId, type, name, desc, "Just now", false, 0);
                newConv.messages.add(new Message("System", "MSC zero-rated conversation initiated under " + type.toUpperCase() + " category.", "Just now", false));
                conversations.add(0, newConv);
                activeChatId = newId;

                redirect(exchange, "/?screen=chatView&chat=" + newId);
            } else {
                redirect(exchange, "/");
            }
        }
    }

    // ========================================================================
    // ACTION HANDLER: Admin Broadcast
    // ========================================================================
    static class AdminBroadcastHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Guard: Only admin can broadcast
            if (!"admin".equalsIgnoreCase(currentUserRole)) {
                redirect(exchange, "/?screen=chats&error=unauthorized");
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> postData = parseFormData(exchange);
                String title = postData.getOrDefault("broadcastTitle", "Campus Announcement").trim();
                String message = postData.getOrDefault("broadcastMessage", "").trim();

                if (!message.isEmpty()) {
                    String timeNow = new SimpleDateFormat("HH:mm").format(new Date());
                    String formattedNotice = "[OFFICIAL MSC NOTICE: " + title + "] " + message;
                    for (Conversation c : conversations) {
                        c.messages.add(new Message("MSC Admin Console", formattedNotice, timeNow, false));
                        c.timestamp = "Just now";
                        c.unreadCount++;
                    }
                    totalReverseBilledMB += 0.85;
                }
                redirect(exchange, "/?screen=admin&broadcast=success");
            } else {
                redirect(exchange, "/?screen=admin");
            }
        }
    }

    // ========================================================================
    // ACTION HANDLER: Upgrade to Premium
    // ========================================================================
    static class UpgradePremiumHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            isPremium = !isPremium;
            redirect(exchange, "/?screen=profile");
        }
    }

    // ========================================================================
    // ACTION HANDLER: Sign Out
    // ========================================================================
    static class SignoutHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            isLoggedIn = false;
            currentUserRole = "user";
            redirect(exchange, "/?screen=login");
        }
    }

    // ========================================================================
    // HTML TEMPLATE RENDERER (Musa Campus Connect - MSC)
    // Full-screen desktop & mobile, bottom dashboard on both, each element on own page
    // ========================================================================
    private static String renderHtmlPage(String screen, String filter, String search, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();

        long directCount = conversations.stream().filter(c -> c.type.equals("direct")).count();
        long groupCount = conversations.stream().filter(c -> c.type.equals("group")).count();
        long channelCount = conversations.stream().filter(c -> c.type.equals("channel")).count();

        Conversation currentChat = findConversation(activeChatId);
        if (currentChat == null && !conversations.isEmpty()) {
            currentChat = conversations.get(0);
            activeChatId = currentChat.id;
        }

        boolean isAdmin = "admin".equalsIgnoreCase(currentUserRole);

        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"en\">\n<head>\n");
        sb.append("<meta charset=\"UTF-8\">\n");
        sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, viewport-fit=cover\">\n");
        sb.append("<title>Musa Campus Connect (MSC) — Zero-Data Student Messaging</title>\n");
        sb.append("<link rel=\"icon\" href=\"data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>💬</text></svg>\">\n");
        sb.append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n");
        sb.append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n");
        sb.append("<link href=\"https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;500;600;700;800&family=Inter:wght@400;500;600;700&display=swap\" rel=\"stylesheet\">\n");
        sb.append("<link rel=\"stylesheet\" href=\"/styles.css\">\n");
        sb.append("</head>\n<body>\n");

        sb.append("<div class=\"app-viewport\">\n");

        // Top Simulated Desktop URL Bar (connect.msc.ac.za) - Only displayed inside portal
        if (!screen.equals("login")) {
            sb.append("  <div class=\"desktop-top-banner\">\n");
            sb.append("    <div class=\"desktop-banner-inner\">\n");
            sb.append("      <div class=\"desktop-url-chip\">\n");
            sb.append("        <span class=\"lock-icon\">🔒</span>\n");
            sb.append("        <span class=\"url-text\">connect.msc.ac.za (Musa Campus Connect MSC Gateway)</span>\n");
            sb.append("      </div>\n");
            sb.append("      <div class=\"desktop-banner-badge\">\n");
            sb.append("        <span class=\"pulse-dot\"></span>\n");
            sb.append("        <span>Zero-Data Protocol Active • " + (isAdmin ? "🛡️ Administrator Mode" : "👤 Student Mode") + " (" + String.format("%.2f", totalReverseBilledMB) + " MB Reverse-Billed)</span>\n");
            sb.append("      </div>\n");
            sb.append("    </div>\n");
            sb.append("  </div>\n");
        }

        // MASTER CONTAINER (Covers 100% of Available Screen Space)
        sb.append("  <div class=\"app-container\" id=\"appContainer\">\n");

        // ====================================================================
        // SCREEN 0: LOGIN & REGISTRATION (FULL SCREEN DUAL-PANEL MASTER)
        // ====================================================================
        sb.append("    <section class=\"app-screen " + (screen.equals("login") ? "active-screen login-screen-page" : "") + "\" id=\"screenLogin\" " + (screen.equals("login") ? "" : "style=\"display:none;\"") + ">\n");
        sb.append("      <div class=\"login-fullscreen-container\">\n");

        // LEFT HERO PANEL (DESKTOP SHOWCASE & ZERO-DATA MISSION)
        sb.append("        <div class=\"login-hero-panel\">\n");
        sb.append("          <div class=\"login-hero-top\">\n");
        sb.append("            <div class=\"hero-brand-pill\">\n");
        sb.append("              <span class=\"hero-brand-logo-icon\">💬</span>\n");
        sb.append("              <span class=\"hero-brand-name\">Musa Campus Connect</span>\n");
        sb.append("              <span class=\"hero-msc-badge\">MSC</span>\n");
        sb.append("            </div>\n");
        sb.append("            <h1 class=\"hero-main-title\">Connect & Collaborate.<br><span class=\"gradient-text\">Zero-Data Required.</span></h1>\n");
        sb.append("            <p class=\"hero-main-desc\">South Africa's premier reverse-billed higher-education messaging network. Real-time academic communications without mobile data costs.</p>\n");
        sb.append("            <div class=\"hero-features-list\">\n");
        sb.append("              <div class=\"hero-feature-item\">\n");
        sb.append("                <div class=\"hero-feat-icon\">📡</div>\n");
        sb.append("                <div>\n");
        sb.append("                  <div class=\"hero-feat-title\">Zero-Rated APN Connectivity</div>\n");
        sb.append("                  <div class=\"hero-feat-desc\">Free communication across MTN, Vodacom & Telkom with zero student airtime deduction.</div>\n");
        sb.append("                </div>\n");
        sb.append("              </div>\n");
        sb.append("              <div class=\"hero-feature-item\">\n");
        sb.append("                <div class=\"hero-feat-icon\">🎓</div>\n");
        sb.append("                <div>\n");
        sb.append("                  <div class=\"hero-feat-title\">Verified Student Registration</div>\n");
        sb.append("                  <div class=\"hero-feat-desc\">Register with your student number to access verified departmental channels and study circles.</div>\n");
        sb.append("                </div>\n");
        sb.append("              </div>\n");
        sb.append("              <div class=\"hero-feature-item\">\n");
        sb.append("                <div class=\"hero-feat-icon\">🛡️</div>\n");
        sb.append("                <div>\n");
        sb.append("                  <div class=\"hero-feat-title\">Campus Manager Administration</div>\n");
        sb.append("                  <div class=\"hero-feat-desc\">Dedicated management console for faculty announcements, APN allocations and emergency broadcasts.</div>\n");
        sb.append("                </div>\n");
        sb.append("              </div>\n");
        sb.append("            </div>\n");
        sb.append("          </div>\n");
        sb.append("          <div class=\"hero-bottom-stats-row\">\n");
        sb.append("            <div class=\"hero-stat-block\">\n");
        sb.append("              <span class=\"stat-num\">2,840+</span>\n");
        sb.append("              <span class=\"stat-sub\">Active Students</span>\n");
        sb.append("            </div>\n");
        sb.append("            <div class=\"hero-stat-block\">\n");
        sb.append("              <span class=\"stat-num\">428.6 GB</span>\n");
        sb.append("              <span class=\"stat-sub\">Zero-Data Saved</span>\n");
        sb.append("            </div>\n");
        sb.append("            <div class=\"hero-stat-block\">\n");
        sb.append("              <span class=\"stat-num\">99.98%</span>\n");
        sb.append("              <span class=\"stat-sub\">Telco APN Uptime</span>\n");
        sb.append("            </div>\n");
        sb.append("          </div>\n");
        sb.append("        </div>\n");

        // RIGHT AUTH PANEL (MANAGER VIP + REGISTER / LOGIN TABS)
        sb.append("        <div class=\"login-auth-panel\">\n");
        sb.append("          <div class=\"login-auth-content\">\n");

        String tab = params.getOrDefault("tab", "login");
        String err = params.getOrDefault("error", "");
        String attempted = params.getOrDefault("attempted", "");

        // Alert Messages
        if ("notfound".equalsIgnoreCase(err)) {
            sb.append("            <div class=\"auth-alert-banner alert-notfound\">\n");
            sb.append("              <div class=\"alert-icon-box\">❌</div>\n");
            sb.append("              <div class=\"alert-content-box\">\n");
            sb.append("                <div class=\"alert-headline\">Account Not Found!</div>\n");
            sb.append("                <div class=\"alert-message\">Details for <strong>" + escapeHtml(attempted) + "</strong> do not exist in the MSC registry. <strong>You must register an account first before signing in.</strong></div>\n");
            sb.append("                <a href=\"/?screen=login&tab=register&attempted=" + urlEncode(attempted) + "\" class=\"btn-alert-cta\">👉 Register an Account Now →</a>\n");
            sb.append("              </div>\n");
            sb.append("            </div>\n");
        } else if ("badpass".equalsIgnoreCase(err)) {
            sb.append("            <div class=\"auth-alert-banner alert-warning\">\n");
            sb.append("              <div class=\"alert-icon-box\">⚠️</div>\n");
            sb.append("              <div class=\"alert-content-box\">\n");
            sb.append("                <div class=\"alert-headline\">Incorrect Password</div>\n");
            sb.append("                <div class=\"alert-message\">The password for <strong>" + escapeHtml(attempted) + "</strong> is incorrect. Please try again.</div>\n");
            sb.append("              </div>\n");
            sb.append("            </div>\n");
        } else if ("exists".equalsIgnoreCase(err)) {
            sb.append("            <div class=\"auth-alert-banner alert-info\">\n");
            sb.append("              <div class=\"alert-icon-box\">ℹ️</div>\n");
            sb.append("              <div class=\"alert-content-box\">\n");
            sb.append("                <div class=\"alert-headline\">Account Already Exists</div>\n");
            sb.append("                <div class=\"alert-message\">An account for <strong>" + escapeHtml(attempted) + "</strong> is already registered. Please sign in below.</div>\n");
            sb.append("              </div>\n");
            sb.append("            </div>\n");
        } else if ("mismatch".equalsIgnoreCase(err)) {
            sb.append("            <div class=\"auth-alert-banner alert-warning\">\n");
            sb.append("              <div class=\"alert-icon-box\">⚠️</div>\n");
            sb.append("              <div class=\"alert-content-box\">\n");
            sb.append("                <div class=\"alert-headline\">Passwords Do Not Match</div>\n");
            sb.append("                <div class=\"alert-message\">Please ensure both password fields match exactly.</div>\n");
            sb.append("              </div>\n");
            sb.append("            </div>\n");
        } else if ("unauthorized".equalsIgnoreCase(err)) {
            sb.append("            <div class=\"auth-alert-banner alert-warning\">\n");
            sb.append("              <div class=\"alert-icon-box\">⛔</div>\n");
            sb.append("              <div class=\"alert-content-box\">\n");
            sb.append("                <div class=\"alert-headline\">Access Restricted</div>\n");
            sb.append("                <div class=\"alert-message\">Student user accounts do not have administrator rights. Log in as Campus Manager to access admin tools.</div>\n");
            sb.append("              </div>\n");
            sb.append("            </div>\n");
        }

        // PERMANENT CAMPUS MANAGER ACCOUNT CARD (KEPT THERE)
        sb.append("            <div class=\"manager-access-card\">\n");
        sb.append("              <div class=\"manager-card-top\">\n");
        sb.append("                <div class=\"manager-badge\"><span class=\"pulse-dot\"></span> Institutional Manager</div>\n");
        sb.append("                <span class=\"manager-tag\">Pre-Authorized Master Account</span>\n");
        sb.append("              </div>\n");
        sb.append("              <div class=\"manager-profile-row\">\n");
        sb.append("                <div class=\"manager-avatar-circle\">A</div>\n");
        sb.append("                <div class=\"manager-meta-box\">\n");
        sb.append("                  <h4 class=\"manager-title\">Dr. P. Sithole</h4>\n");
        sb.append("                  <p class=\"manager-subtext\">admin@msc.ac.za • Faculty Administration</p>\n");
        sb.append("                </div>\n");
        sb.append("              </div>\n");
        sb.append("              <form action=\"/login\" method=\"POST\">\n");
        sb.append("                <input type=\"hidden\" name=\"role\" value=\"admin\">\n");
        sb.append("                <input type=\"hidden\" name=\"email\" value=\"admin@msc.ac.za\">\n");
        sb.append("                <button type=\"submit\" class=\"btn-manager-action\">\n");
        sb.append("                  <span>🛡️ Sign In as Campus Manager</span>\n");
        sb.append("                  <span class=\"action-arrow\">→</span>\n");
        sb.append("                </button>\n");
        sb.append("              </form>\n");
        sb.append("            </div>\n");

        // STUDENT ACCESS PORTAL
        sb.append("            <div class=\"auth-divider-line\"><span>Student Access Portal</span></div>\n");

        // Tab Switcher
        sb.append("            <div class=\"auth-tabs-nav\">\n");
        sb.append("              <a href=\"/?screen=login&tab=login\" class=\"auth-tab-link " + (tab.equals("login") ? "active" : "") + "\">🔑 Sign In</a>\n");
        sb.append("              <a href=\"/?screen=login&tab=register\" class=\"auth-tab-link " + (tab.equals("register") ? "active" : "") + "\">✨ Register Account</a>\n");
        sb.append("            </div>\n");

        if (tab.equals("register")) {
            // REGISTER FORM
            sb.append("            <div class=\"auth-form-card\">\n");
            sb.append("              <h3 class=\"auth-form-title\">Register Student Account</h3>\n");
            sb.append("              <p class=\"auth-form-desc\">Create your student profile to start zero-data messaging with classmates and lecturers.</p>\n");
            sb.append("              <form action=\"/register\" method=\"POST\" class=\"auth-input-grid\">\n");
            sb.append("                <div class=\"form-input-group\">\n");
            sb.append("                  <label class=\"form-input-label\">Full Name</label>\n");
            sb.append("                  <input type=\"text\" name=\"fullName\" class=\"form-input-field\" placeholder=\"e.g. Hlayiseko Hlungwani\" required>\n");
            sb.append("                </div>\n");
            sb.append("                <div class=\"form-input-group\">\n");
            sb.append("                  <label class=\"form-input-label\">Student Number / Campus Email</label>\n");
            sb.append("                  <input type=\"email\" name=\"email\" class=\"form-input-field\" placeholder=\"e.g. 22409812@msc.ac.za or student@msc.ac.za\" value=\"" + escapeHtml(attempted) + "\" required>\n");
            sb.append("                </div>\n");
            sb.append("                <div class=\"form-input-group\">\n");
            sb.append("                  <label class=\"form-input-label\">Faculty & Department</label>\n");
            sb.append("                  <select name=\"department\" class=\"form-input-field\">\n");
            sb.append("                    <option value=\"Faculty of ICT — BIS301\">Faculty of ICT — BIS301</option>\n");
            sb.append("                    <option value=\"Faculty of ICT — Software Development\">Faculty of ICT — Software Development</option>\n");
            sb.append("                    <option value=\"Faculty of ICT — IT Support & Networks\">Faculty of ICT — IT Support & Networks</option>\n");
            sb.append("                    <option value=\"Faculty of Engineering\">Faculty of Engineering</option>\n");
            sb.append("                    <option value=\"Faculty of Management Sciences\">Faculty of Management Sciences</option>\n");
            sb.append("                  </select>\n");
            sb.append("                </div>\n");
            sb.append("                <div class=\"form-input-row-2col\">\n");
            sb.append("                  <div class=\"form-input-group\">\n");
            sb.append("                    <label class=\"form-input-label\">Create Password</label>\n");
            sb.append("                    <input type=\"password\" name=\"password\" class=\"form-input-field\" placeholder=\"••••••••\" required>\n");
            sb.append("                  </div>\n");
            sb.append("                  <div class=\"form-input-group\">\n");
            sb.append("                    <label class=\"form-input-label\">Confirm Password</label>\n");
            sb.append("                    <input type=\"password\" name=\"confirmPassword\" class=\"form-input-field\" placeholder=\"••••••••\" required>\n");
            sb.append("                  </div>\n");
            sb.append("                </div>\n");
            sb.append("                <button type=\"submit\" class=\"btn-auth-primary\">✨ Register Student Account →</button>\n");
            sb.append("              </form>\n");
            sb.append("              <div class=\"auth-switch-row\">Already have an account? <a href=\"/?screen=login&tab=login\" class=\"auth-switch-link\">Sign in here</a></div>\n");
            sb.append("            </div>\n");
        } else {
            // SIGN IN FORM
            sb.append("            <div class=\"auth-form-card\">\n");
            sb.append("              <h3 class=\"auth-form-title\">Sign In to Your Account</h3>\n");
            sb.append("              <p class=\"auth-form-desc\">Enter your registered student credentials. <em>If you don't have an account, you must register first.</em></p>\n");
            sb.append("              <form action=\"/login\" method=\"POST\" class=\"auth-input-grid\">\n");
            sb.append("                <div class=\"form-input-group\">\n");
            sb.append("                  <label class=\"form-input-label\">Student No. or Campus Email</label>\n");
            sb.append("                  <input type=\"email\" name=\"email\" class=\"form-input-field\" placeholder=\"student@msc.ac.za\" value=\"" + escapeHtml(attempted) + "\" required>\n");
            sb.append("                </div>\n");
            sb.append("                <div class=\"form-input-group\">\n");
            sb.append("                  <label class=\"form-input-label\">Password</label>\n");
            sb.append("                  <input type=\"password\" name=\"password\" class=\"form-input-field\" placeholder=\"••••••••\" required>\n");
            sb.append("                </div>\n");
            sb.append("                <button type=\"submit\" class=\"btn-auth-primary\">Sign In to MSC Portal →</button>\n");
            sb.append("              </form>\n");
            sb.append("              <div class=\"auth-need-register-box\">\n");
            sb.append("                <span>Don't have an account yet?</span>\n");
            sb.append("                <a href=\"/?screen=login&tab=register\" class=\"btn-register-secondary\">Register New Student Account →</a>\n");
            sb.append("              </div>\n");
            sb.append("            </div>\n");
        }

        sb.append("            <p class=\"auth-security-footnote\">🔒 Zero-Rated Telco APN Gateway • Faculty of ICT • Zero student airtime deducted</p>\n");
        sb.append("          </div>\n"); // end login-auth-content
        sb.append("        </div>\n");   // end login-auth-panel
        sb.append("      </div>\n");     // end login-fullscreen-container
        sb.append("    </section>\n");   // end screenLogin // end screenLogin

        // ====================================================================
        // SCREEN 1: CHATS (ITS OWN DEDICATED FULL-SCREEN PAGE)
        // ====================================================================
        sb.append("    <section class=\"app-screen " + (screen.equals("chats") ? "active-screen" : "") + "\" id=\"screenChats\">\n");
        
        sb.append("      <header class=\"app-header\">\n");
        sb.append("        <div class=\"header-main-row\">\n");
        sb.append("          <div class=\"brand-identity-wrap\">\n");
        sb.append("            <div class=\"brand-logo-icon\">💬</div>\n");
        sb.append("            <div class=\"header-brand\">\n");
        sb.append("              <h1 class=\"brand-title\">Musa Campus Connect</h1>\n");
        sb.append("              <div class=\"brand-tagline-row\">\n");
        sb.append("                <span class=\"brand-tagline\">Zero-data messaging</span>\n");
        sb.append("                <span class=\"msc-badge\">MSC</span>\n");
        if (isAdmin) {
            sb.append("                <span class=\"persona-role-badge badge-admin\" style=\"margin-left:4px;\">Admin</span>\n");
        }
        sb.append("              </div>\n");
        sb.append("            </div>\n");
        sb.append("          </div>\n");
        sb.append("          <div class=\"header-actions\">\n");
        // ADMIN BUTTON: ONLY VISIBLE IF ADMIN
        if (isAdmin) {
            sb.append("            <a href=\"/?screen=admin\" class=\"admin-quick-btn\" title=\"Admin Dashboard\">🛡️ Admin</a>\n");
        }
        sb.append("            <a href=\"/?screen=profile\" class=\"user-avatar-btn\" title=\"View Profile (" + escapeHtml(userName) + ")\">" + userInitials + "</a>\n");
        sb.append("            <a href=\"#new-chat-modal\" class=\"icon-btn-new-chat\" title=\"New Conversation\">💬⁺</a>\n");
        sb.append("          </div>\n");
        sb.append("        </div>\n");

        // Search Form (Pure HTML)
        sb.append("        <form method=\"GET\" action=\"/\" class=\"search-bar-wrap\">\n");
        sb.append("          <span class=\"search-icon\">🔍</span>\n");
        sb.append("          <input type=\"text\" name=\"q\" value=\"" + escapeHtml(search) + "\" placeholder=\"Search conversations...\">\n");
        sb.append("          <input type=\"hidden\" name=\"screen\" value=\"chats\">\n");
        sb.append("          <input type=\"hidden\" name=\"filter\" value=\"" + filter + "\">\n");
        sb.append("        </form>\n");

        // Filter Pills
        sb.append("        <div class=\"category-pills-row\">\n");
        sb.append("          <a href=\"/?screen=chats&filter=all\" class=\"cat-pill " + (filter.equals("all") ? "active" : "") + "\">All</a>\n");
        sb.append("          <a href=\"/?screen=chats&filter=direct\" class=\"cat-pill " + (filter.equals("direct") ? "active" : "") + "\">Direct</a>\n");
        sb.append("          <a href=\"/?screen=chats&filter=group\" class=\"cat-pill " + (filter.equals("group") ? "active" : "") + "\">Groups</a>\n");
        sb.append("          <a href=\"/?screen=chats&filter=channel\" class=\"cat-pill " + (filter.equals("channel") ? "active" : "") + "\">Channels</a>\n");
        sb.append("        </div>\n");
        sb.append("      </header>\n");

        sb.append("      <div class=\"screen-scroll-body\">\n");

        // Unauthorized alert banner if user attempted to access admin page
        if (params.containsKey("error") && "unauthorized".equals(params.get("error"))) {
            sb.append("        <div class=\"unauthorized-toast-banner\">\n");
            sb.append("          <span>⛔</span>\n");
            sb.append("          <div>\n");
            sb.append("            <strong>Access Denied:</strong> The Admin Console is restricted to Campus Administrators. Log in as Dr. P. Sithole to access admin tools.\n");
            sb.append("          </div>\n");
            sb.append("        </div>\n");
        }

        // Go Premium Card
        if (!isPremium) {
            sb.append("        <div class=\"premium-promo-card\">\n");
            sb.append("          <a href=\"/?screen=chats\" class=\"promo-close-btn\">✕</a>\n");
            sb.append("          <div class=\"promo-header\"><span>✨</span><span class=\"promo-title\">Go Premium</span></div>\n");
            sb.append("          <p class=\"promo-description\">Unlock audio calls, video calls & file sharing for just R20/month</p>\n");
            sb.append("          <div class=\"promo-perks-row\"><span>📞 Calls</span><span>📹 Video</span><span>📄 Files</span></div>\n");
            sb.append("          <a href=\"/?screen=premium\" class=\"promo-learn-more\">Learn more →</a>\n");
            sb.append("        </div>\n");
        }

        // Conversations List
        sb.append("        <div class=\"conversations-list\">\n");
        int countRendered = 0;
        for (Conversation c : conversations) {
            boolean matchesCat = filter.equals("all") || c.type.equals(filter);
            boolean matchesSearch = search.isEmpty() || c.name.toLowerCase().contains(search) || c.description.toLowerCase().contains(search);
            if (matchesCat && matchesSearch) {
                countRendered++;
                String typeClass = c.type.equals("channel") ? "channel-type" : (c.type.equals("group") ? "group-type" : "direct-type");
                String icon = c.type.equals("channel") ? "📢" : (c.type.equals("group") ? "👥" : "💬");
                String lastMsg = c.messages.isEmpty() ? c.description : c.messages.get(c.messages.size() - 1).text;

                sb.append("          <a href=\"/?screen=chatView&chat=" + c.id + "\" class=\"conversation-row " + (c.id == activeChatId ? "selected-desktop-chat" : "") + "\">\n");
                sb.append("            <div class=\"conv-avatar-box " + typeClass + "\">" + icon + "<span class=\"conv-online-dot\"></span></div>\n");
                sb.append("            <div class=\"conv-meta\">\n");
                sb.append("              <div class=\"conv-meta-top\">\n");
                sb.append("                <div class=\"conv-title-wrap\">\n");
                sb.append("                  <span class=\"conv-title\">" + escapeHtml(c.name) + "</span>\n");
                if (c.isVerified) {
                    sb.append("                  <span class=\"verified-check-badge\" title=\"Verified MSC Official Channel\">✓</span>\n");
                }
                sb.append("                </div>\n");
                sb.append("                <span class=\"conv-timestamp\">" + c.timestamp + "</span>\n");
                sb.append("              </div>\n");
                sb.append("              <div class=\"conv-snippet-row\">\n");
                sb.append("                <div class=\"conv-snippet\">" + escapeHtml(lastMsg) + "</div>\n");
                if (c.unreadCount > 0) {
                    sb.append("                <span class=\"unread-badge\">" + c.unreadCount + "</span>\n");
                }
                sb.append("              </div>\n");
                sb.append("            </div>\n");
                sb.append("          </a>\n");
            }
        }

        if (countRendered == 0) {
            sb.append("          <div class=\"empty-state-card\">\n");
            sb.append("            <div class=\"empty-state-icon-circle\">💬</div>\n");
            sb.append("            <h3 class=\"empty-state-title\">No conversations yet</h3>\n");
            sb.append("            <p class=\"empty-state-text\">Start a chat to connect with classmates and lecturers in eMalahleni</p>\n");
            sb.append("            <a href=\"#new-chat-modal\" class=\"empty-state-action-btn\">Start Conversation</a>\n");
            sb.append("          </div>\n");
        }
        sb.append("        </div>\n"); // end conversations-list
        sb.append("      </div>\n");   // end screen-scroll-body
        sb.append("    </section>\n"); // end screenChats

        // ====================================================================
        // SCREEN 2: ACTIVE CHAT SPACE (ITS OWN DEDICATED FULL-SCREEN PAGE)
        // ====================================================================
        sb.append("    <section class=\"app-screen " + (screen.equals("chatView") ? "active-screen" : "") + "\" id=\"screenChatView\">\n");
        if (currentChat != null) {
            sb.append("      <header class=\"active-chat-header\">\n");
            sb.append("        <a href=\"/?screen=chats\" class=\"back-link-btn-icon\" title=\"Back to chats\">←</a>\n");
            sb.append("        <div class=\"chat-header-avatar\">" + (currentChat.type.equals("channel") ? "📢" : (currentChat.type.equals("group") ? "👥" : "💬")) + "</div>\n");
            sb.append("        <div class=\"chat-header-user-info\">\n");
            sb.append("          <h3 class=\"chat-header-name\">\n");
            sb.append("            " + escapeHtml(currentChat.name) + "\n");
            if (currentChat.isVerified) {
                sb.append("            <span class=\"verified-check-badge\" title=\"Verified MSC Channel\">✓</span>\n");
            }
            sb.append("          </h3>\n");
            sb.append("          <span class=\"chat-header-status\">\n");
            sb.append("            <span class=\"pulse-dot\"></span> Zero-data encrypted • MSC Gateway Node (eMalahleni)\n");
            sb.append("          </span>\n");
            sb.append("        </div>\n");
            sb.append("        <div class=\"chat-header-call-actions\">\n");
            sb.append("          <a href=\"/?screen=premium\" class=\"call-action-btn\" title=\"Voice Call (Premium)\">📞</a>\n");
            sb.append("          <a href=\"/?screen=premium\" class=\"call-action-btn\" title=\"Video Call (Premium)\">📹</a>\n");
            sb.append("        </div>\n");
            sb.append("      </header>\n");

            // Messages Stream
            sb.append("      <div class=\"chat-messages-container\">\n");
            sb.append("        <div class=\"chat-date-divider\">Today • TUT eMalahleni Campus</div>\n");
            for (Message m : currentChat.messages) {
                sb.append("        <div class=\"msg-bubble " + (m.isMe ? "outgoing" : "incoming") + "\">\n");
                if (!m.isMe) {
                    sb.append("          <div class=\"msg-sender-name\">" + escapeHtml(m.sender) + "</div>\n");
                }
                sb.append("          <div class=\"msg-text-body\">" + escapeHtml(m.text) + "</div>\n");
                sb.append("          <div class=\"msg-time-stamp\">\n");
                sb.append("            " + m.time + "\n");
                if (m.isMe) {
                    sb.append("            <span class=\"double-tick\">✓✓</span>\n");
                }
                sb.append("          </div>\n");
                sb.append("        </div>\n");
            }
            sb.append("      </div>\n");

            // Input Composer Bar
            sb.append("      <form action=\"/send-message\" method=\"POST\" class=\"chat-composer-bar\">\n");
            sb.append("        <input type=\"hidden\" name=\"chatId\" value=\"" + currentChat.id + "\">\n");
            sb.append("        <a href=\"/?screen=premium\" class=\"composer-icon-btn\" title=\"Attach PDF (Premium)\">📎</a>\n");
            sb.append("        <input type=\"text\" name=\"messageText\" placeholder=\"Type zero-data message...\" required autocomplete=\"off\">\n");
            sb.append("        <button type=\"submit\" class=\"composer-send-btn\" title=\"Send\">➤</button>\n");
            sb.append("      </form>\n");
        }
        sb.append("    </section>\n");

        // ====================================================================
        // SCREEN 3: ADMIN PORTAL (COVERS ENTIRE SCREEN EDGE-TO-EDGE - ADMIN ONLY)
        // ====================================================================
        if (isAdmin) {
            sb.append("    <section class=\"app-screen " + (screen.equals("admin") ? "active-screen" : "") + "\" id=\"screenAdmin\">\n");
            sb.append("      <header class=\"admin-top-header\">\n");
            sb.append("        <div class=\"admin-header-title-box\">\n");
            sb.append("          <a href=\"/?screen=chats\" class=\"back-link-btn-icon\" title=\"Back to Chats\">←</a>\n");
            sb.append("          <div>\n");
            sb.append("            <h2 class=\"admin-portal-title\">Musa Campus Connect (MSC) — Admin Console</h2>\n");
            sb.append("            <div class=\"admin-portal-subbar\">\n");
            sb.append("              <span class=\"status-pill-green\"><span class=\"pulse-dot\"></span> Gateway APN Online</span>\n");
            sb.append("              <span class=\"admin-subtext\">TUT eMalahleni Central Node • Reverse-Billing Telemetry</span>\n");
            sb.append("            </div>\n");
            sb.append("          </div>\n");
            sb.append("        </div>\n");
            sb.append("        <div class=\"admin-header-cta-group\">\n");
            sb.append("          <a href=\"/financial_model_data.csv\" download class=\"btn-export-csv\">⬇ Export Telemetry CSV</a>\n");
            sb.append("          <a href=\"/?screen=chats\" class=\"btn-admin-chats\">💬 Student View</a>\n");
            sb.append("        </div>\n");
            sb.append("      </header>\n");

            sb.append("      <div class=\"screen-scroll-body admin-scroll-body\">\n");

            // Broadcast success toast if sent
            if (params.containsKey("broadcast")) {
                sb.append("        <div class=\"admin-alert-banner\">\n");
                sb.append("          <span class=\"alert-icon\">📢</span>\n");
                sb.append("          <div>\n");
                sb.append("            <strong>Broadcast Transmitted:</strong> Announcement has been successfully broadcast to all student channels and group chats with zero-data reverse billing.\n");
                sb.append("          </div>\n");
                sb.append("        </div>\n");
            }

            // 4 KPI Metric Cards
            sb.append("        <div class=\"admin-kpi-grid\">\n");

            sb.append("          <div class=\"kpi-metric-card\">\n");
            sb.append("            <div class=\"kpi-icon-bubble\">👥</div>\n");
            sb.append("            <div class=\"kpi-value-text\">2,840</div>\n");
            sb.append("            <div class=\"kpi-label-text\">Active Students (eMalahleni)</div>\n");
            sb.append("            <div class=\"kpi-trend-pill positive\">↑ +14.2% this month</div>\n");
            sb.append("          </div>\n");

            sb.append("          <div class=\"kpi-metric-card\">\n");
            sb.append("            <div class=\"kpi-icon-bubble\">📶</div>\n");
            sb.append("            <div class=\"kpi-value-text\">428.60 GB</div>\n");
            sb.append("            <div class=\"kpi-label-text\">Reverse-Billed Bandwidth</div>\n");
            sb.append("            <div class=\"kpi-trend-pill telco\">MTN / Vodacom / Telkom</div>\n");
            sb.append("          </div>\n");

            sb.append("          <div class=\"kpi-metric-card\">\n");
            sb.append("            <div class=\"kpi-icon-bubble\">💰</div>\n");
            sb.append("            <div class=\"kpi-value-text\">R118,500</div>\n");
            sb.append("            <div class=\"kpi-label-text\">Monthly Operating Revenue</div>\n");
            sb.append("            <div class=\"kpi-trend-pill positive\">Break-Even Surpassed</div>\n");
            sb.append("          </div>\n");

            sb.append("          <div class=\"kpi-metric-card\">\n");
            sb.append("            <div class=\"kpi-icon-bubble\">⚡</div>\n");
            sb.append("            <div class=\"kpi-value-text\">99.98% / 14ms</div>\n");
            sb.append("            <div class=\"kpi-label-text\">Gateway Cluster Latency</div>\n");
            sb.append("            <div class=\"kpi-trend-pill positive\">High Availability SLA</div>\n");
            sb.append("          </div>\n");

            sb.append("        </div>\n"); // end admin-kpi-grid

            // 2-Column Section: Broadcast Composer & Telco Reverse-Billing APN Breakdown
            sb.append("        <div class=\"admin-dual-grid\">\n");

            // Column 1: Broadcast Composer
            sb.append("          <div class=\"admin-card-panel\">\n");
            sb.append("            <div class=\"panel-header-row\">\n");
            sb.append("              <h3 class=\"panel-title\">📢 Campus Announcement Broadcast</h3>\n");
            sb.append("              <span class=\"panel-tag\">Zero-Rated Push</span>\n");
            sb.append("            </div>\n");
            sb.append("            <p class=\"panel-desc\">Transmit an official MSC alert directly into all student groups and channels without airtime cost.</p>\n");

            sb.append("            <form action=\"/admin-broadcast\" method=\"POST\" class=\"admin-broadcast-form\">\n");
            sb.append("              <div class=\"admin-form-group\">\n");
            sb.append("                <label class=\"admin-label\">Announcement Title</label>\n");
            sb.append("                <input type=\"text\" name=\"broadcastTitle\" class=\"admin-input\" placeholder=\"e.g. BIS301 Test 1 Venue Update / Loadshedding Notice\" required>\n");
            sb.append("              </div>\n");
            sb.append("              <div class=\"admin-form-group\">\n");
            sb.append("                <label class=\"admin-label\">Broadcast Message Content</label>\n");
            sb.append("                <textarea name=\"broadcastMessage\" class=\"admin-textarea\" rows=\"4\" placeholder=\"Write official university notice to broadcast to all students...\" required></textarea>\n");
            sb.append("              </div>\n");
            sb.append("              <button type=\"submit\" class=\"btn-transmit-broadcast\">🚀 Transmit Zero-Rated Broadcast</button>\n");
            sb.append("            </form>\n");
            sb.append("          </div>\n");

            // Column 2: Telco Reverse-Billing Breakdown
            sb.append("          <div class=\"admin-card-panel\">\n");
            sb.append("            <div class=\"panel-header-row\">\n");
            sb.append("              <h3 class=\"panel-title\">📡 Telco Reverse-Billing APN Interconnect</h3>\n");
            sb.append("              <span class=\"panel-tag\">Live APN</span>\n");
            sb.append("            </div>\n");
            sb.append("            <p class=\"panel-desc\">Real-time data packets zero-rated and reverse-billed to MSC institutional sponsor accounts.</p>\n");

            sb.append("            <div class=\"telco-apn-list\">\n");

            sb.append("              <div class=\"telco-apn-item\">\n");
            sb.append("                <div class=\"telco-badge mtn\">MTN</div>\n");
            sb.append("                <div class=\"telco-info\">\n");
            sb.append("                  <div class=\"telco-name-row\"><span class=\"telco-name\">MTN South Africa APN</span><span class=\"telco-status active\">ACTIVE</span></div>\n");
            sb.append("                  <div class=\"telco-sub\">APN: <code>msc.mtn.co.za</code> • 2,140 Connections • Rate: R0.10/MB</div>\n");
            sb.append("                </div>\n");
            sb.append("                <div class=\"telco-numbers\"><span class=\"telco-gb\">198.4 GB</span><span class=\"telco-cost\">R19,840.00</span></div>\n");
            sb.append("              </div>\n");

            sb.append("              <div class=\"telco-apn-item\">\n");
            sb.append("                <div class=\"telco-badge vodacom\">VOD</div>\n");
            sb.append("                <div class=\"telco-info\">\n");
            sb.append("                  <div class=\"telco-name-row\"><span class=\"telco-name\">Vodacom SA APN</span><span class=\"telco-status active\">ACTIVE</span></div>\n");
            sb.append("                  <div class=\"telco-sub\">APN: <code>msc.vodacom.co.za</code> • 1,620 Connections • Rate: R0.10/MB</div>\n");
            sb.append("                </div>\n");
            sb.append("                <div class=\"telco-numbers\"><span class=\"telco-gb\">154.2 GB</span><span class=\"telco-cost\">R15,420.00</span></div>\n");
            sb.append("              </div>\n");

            sb.append("              <div class=\"telco-apn-item\">\n");
            sb.append("                <div class=\"telco-badge telkom\">TEL</div>\n");
            sb.append("                <div class=\"telco-info\">\n");
            sb.append("                  <div class=\"telco-name-row\"><span class=\"telco-name\">Telkom Mobile APN</span><span class=\"telco-status active\">ACTIVE</span></div>\n");
            sb.append("                  <div class=\"telco-sub\">APN: <code>msc.telkom.co.za</code> • 940 Connections • Rate: R0.10/MB</div>\n");
            sb.append("                </div>\n");
            sb.append("                <div class=\"telco-numbers\"><span class=\"telco-gb\">76.0 GB</span><span class=\"telco-cost\">R7,600.00</span></div>\n");
            sb.append("              </div>\n");

            sb.append("            </div>\n");
            sb.append("            <div class=\"telco-summary-foot\">Total Telco Reverse-Billing: <strong>428.6 GB / R42,860.00</strong></div>\n");
            sb.append("          </div>\n");

            sb.append("        </div>\n"); // end admin-dual-grid

            // Section 3: Financial Sustainability & Cash Flow Matrix
            sb.append("        <div class=\"admin-card-panel full-width-panel\">\n");
            sb.append("          <div class=\"panel-header-row\">\n");
            sb.append("            <h3 class=\"panel-title\">📊 12-Month Financial Model & Cash Flow Sustainability</h3>\n");
            sb.append("            <span class=\"panel-tag badge-success\">Break-Even Surpassed (Month 4)</span>\n");
            sb.append("          </div>\n");
            sb.append("          <p class=\"panel-desc\">Mathematical modeling of MSC student adoption, R20/month premium upgrades, and reverse-billing bandwidth sustainability.</p>\n");

            sb.append("          <div class=\"admin-table-wrap\">\n");
            sb.append("            <table class=\"admin-table\">\n");
            sb.append("              <thead>\n");
            sb.append("                <tr>\n");
            sb.append("                  <th>Period</th>\n");
            sb.append("                  <th>Total Students</th>\n");
            sb.append("                  <th>Premium Users</th>\n");
            sb.append("                  <th>Conversion %</th>\n");
            sb.append("                  <th>Gross Inflow</th>\n");
            sb.append("                  <th>Telco Bandwidth</th>\n");
            sb.append("                  <th>Cloud & Hosting</th>\n");
            sb.append("                  <th>Net Operating Surplus</th>\n");
            sb.append("                  <th>Cumulative Surplus</th>\n");
            sb.append("                </tr>\n");
            sb.append("              </thead>\n");
            sb.append("              <tbody>\n");
            sb.append("                <tr><td>Month 1</td><td>1,000</td><td>50</td><td>5.0%</td><td>R1,000.00</td><td>R875.00</td><td>R1,350.00</td><td class=\"text-danger\">-R1,225.00</td><td class=\"text-danger\">-R1,225.00</td></tr>\n");
            sb.append("                <tr><td>Month 2</td><td>1,500</td><td>105</td><td>7.0%</td><td>R2,100.00</td><td>R1,312.50</td><td>R1,350.00</td><td class=\"text-danger\">-R562.50</td><td class=\"text-danger\">-R1,787.50</td></tr>\n");
            sb.append("                <tr><td>Month 3</td><td>2,200</td><td>176</td><td>8.0%</td><td>R3,520.00</td><td>R1,925.00</td><td>R1,450.00</td><td class=\"text-success\">+R145.00</td><td class=\"text-danger\">-R1,642.50</td></tr>\n");
            sb.append("                <tr class=\"highlight-row\"><td><strong>Month 4 (Break-Even)</strong></td><td>3,000</td><td>270</td><td>9.0%</td><td>R5,400.00</td><td>R2,625.00</td><td>R1,550.00</td><td class=\"text-success\"><strong>+R1,225.00</strong></td><td class=\"text-danger\">-R417.50</td></tr>\n");
            sb.append("                <tr><td>Month 6</td><td>4,600</td><td>506</td><td>11.0%</td><td>R10,120.00</td><td>R4,025.00</td><td>R1,850.00</td><td class=\"text-success\">+R4,245.00</td><td class=\"text-success\">+R6,352.50</td></tr>\n");
            sb.append("                <tr><td>Month 12</td><td>10,000</td><td>1,700</td><td>17.0%</td><td>R34,000.00</td><td>R8,750.00</td><td>R2,850.00</td><td class=\"text-success\">+R22,400.00</td><td class=\"text-success\"><strong>+R88,865.00</strong></td></tr>\n");
            sb.append("              </tbody>\n");
            sb.append("            </table>\n");
            sb.append("          </div>\n");
            sb.append("        </div>\n");

            // Section 4: Channel & Student Moderation Directory
            sb.append("        <div class=\"admin-card-panel full-width-panel\">\n");
            sb.append("          <div class=\"panel-header-row\">\n");
            sb.append("            <h3 class=\"panel-title\">🏛️ Active Channel Nodes & Student Moderation Directory</h3>\n");
            sb.append("            <span class=\"panel-tag\">" + conversations.size() + " Total Nodes</span>\n");
            sb.append("          </div>\n");

            sb.append("          <div class=\"admin-table-wrap\">\n");
            sb.append("            <table class=\"admin-table\">\n");
            sb.append("              <thead>\n");
            sb.append("                <tr>\n");
            sb.append("                  <th>ID</th>\n");
            sb.append("                  <th>Channel / Conversation</th>\n");
            sb.append("                  <th>Type</th>\n");
            sb.append("                  <th>Verification</th>\n");
            sb.append("                  <th>Message Traffic</th>\n");
            sb.append("                  <th>Zero-Data APN</th>\n");
            sb.append("                  <th>Action</th>\n");
            sb.append("                </tr>\n");
            sb.append("              </thead>\n");
            sb.append("              <tbody>\n");
            for (Conversation c : conversations) {
                String typeBadge = c.type.equals("channel") ? "badge-channel" : (c.type.equals("group") ? "badge-group" : "badge-direct");
                sb.append("                <tr>\n");
                sb.append("                  <td>#" + c.id + "</td>\n");
                sb.append("                  <td><strong>" + escapeHtml(c.name) + "</strong><br><small style=\"color: var(--text-muted);\">" + escapeHtml(c.description) + "</small></td>\n");
                sb.append("                  <td><span class=\"table-type-pill " + typeBadge + "\">" + c.type.toUpperCase() + "</span></td>\n");
                sb.append("                  <td>" + (c.isVerified ? "<span class=\"verified-check-badge\">✓ Verified Official</span>" : "<span style=\"color:#94a3b8;\">Standard</span>") + "</td>\n");
                sb.append("                  <td>" + c.messages.size() + " messages</td>\n");
                sb.append("                  <td><span class=\"status-pill-green\"><span class=\"pulse-dot\"></span> Reverse-Billed</span></td>\n");
                sb.append("                  <td><a href=\"/?screen=chatView&chat=" + c.id + "\" class=\"table-action-link\">Open Space →</a></td>\n");
                sb.append("                </tr>\n");
            }
            sb.append("              </tbody>\n");
            sb.append("            </table>\n");
            sb.append("          </div>\n");
            sb.append("        </div>\n");

            sb.append("      </div>\n"); // end admin-scroll-body
            sb.append("    </section>\n"); // end screenAdmin
        }

        // ====================================================================
        // SCREEN 4: PREMIUM UPGRADE (ITS OWN DEDICATED FULL-SCREEN PAGE)
        // ====================================================================
        sb.append("    <section class=\"app-screen " + (screen.equals("premium") ? "active-screen" : "") + "\" id=\"screenPremium\">\n");
        sb.append("      <div class=\"sub-screen-header\">\n");
        sb.append("        <a href=\"/?screen=chats\" class=\"back-link-btn\">← Back to chats</a>\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"screen-scroll-body premium-body-padding\">\n");
        sb.append("        <div class=\"premium-hero-badge\">👑</div>\n");
        sb.append("        <h2 class=\"premium-main-heading\">Musa Campus Connect Premium</h2>\n");
        sb.append("        <p class=\"premium-main-subheading\">Unlock the full experience for your campus life • Powered by MSC</p>\n");

        sb.append("        <div class=\"premium-plan-card\">\n");
        sb.append("          <div class=\"plan-card-header\">\n");
        sb.append("            <div class=\"plan-badge-label\"><span>✨</span><span>Premium</span></div>\n");
        sb.append("            <div class=\"plan-price-block\"><span class=\"price-currency-val\">R20</span><span class=\"price-period\">/month</span></div>\n");
        sb.append("          </div>\n");

        sb.append("          <div class=\"premium-features-list\">\n");
        sb.append("            <div class=\"prem-feat-row\"><div class=\"feat-icon-bubble\">📞</div><div class=\"feat-text-block\"><h4 class=\"feat-title\">Voice Calls</h4><p class=\"feat-desc\">Crystal-clear audio calls to any user</p></div></div>\n");
        sb.append("            <div class=\"prem-feat-row\"><div class=\"feat-icon-bubble\">📹</div><div class=\"feat-text-block\"><h4 class=\"feat-title\">Video Calls</h4><p class=\"feat-desc\">Face-to-face with lecturers and classmates</p></div></div>\n");
        sb.append("            <div class=\"prem-feat-row\"><div class=\"feat-icon-bubble\">📄</div><div class=\"feat-text-block\"><h4 class=\"feat-title\">File Sharing</h4><p class=\"feat-desc\">Share documents, PDFs, and presentations</p></div></div>\n");
        sb.append("            <div class=\"prem-feat-row\"><div class=\"feat-icon-bubble\">⚡</div><div class=\"feat-text-block\"><h4 class=\"feat-title\">Priority Support</h4><p class=\"feat-desc\">Direct 24/7 technical assistance via MSC SLA</p></div></div>\n");
        sb.append("          </div>\n");

        sb.append("          <form action=\"/upgrade-premium\" method=\"POST\">\n");
        if (isPremium) {
            sb.append("            <button type=\"submit\" class=\"btn-upgrade-purple\" style=\"background: #10b981;\">✓ Subscribed (Click to Downgrade)</button>\n");
        } else {
            sb.append("            <button type=\"submit\" class=\"btn-upgrade-purple\">Upgrade to Premium (R20/mo)</button>\n");
        }
        sb.append("          </form>\n");
        sb.append("          <p class=\"plan-terms-subtext\">Cancel anytime • 7-day free trial • NSFAS & Airtime billing supported</p>\n");
        sb.append("        </div>\n");
        sb.append("      </div>\n");
        sb.append("    </section>\n");

        // ====================================================================
        // SCREEN 5: PROFILE (ITS OWN DEDICATED FULL-SCREEN PAGE)
        // ====================================================================
        sb.append("    <section class=\"app-screen " + (screen.equals("profile") ? "active-screen" : "") + "\" id=\"screenProfile\">\n");
        sb.append("      <div class=\"sub-screen-header\">\n");
        sb.append("        <a href=\"/?screen=chats\" class=\"back-link-btn\">← Back to chats</a>\n");
        sb.append("      </div>\n");
        sb.append("      <div class=\"screen-scroll-body profile-body-padding\">\n");
        sb.append("        <div class=\"profile-card\">\n");
        sb.append("          <div class=\"profile-avatar-circle\">" + userInitials + "</div>\n");
        sb.append("          <h2 class=\"profile-user-name\">" + escapeHtml(userName) + "</h2>\n");
        sb.append("          <p class=\"profile-user-email\">" + escapeHtml(userEmail) + "</p>\n");
        if (isAdmin) {
            sb.append("          <div class=\"profile-badge-pill\" style=\"background:#ecfdf5; color:#065f46; border:1px solid #a7f3d0;\">🛡️ Campus Administrator</div>\n");
        } else {
            sb.append("          <div class=\"profile-badge-pill " + (isPremium ? "premium-tier" : "") + "\">" + (isPremium ? "👑 Premium Member" : "👤 Student (No Admin Access)") + "</div>\n");
        }
        sb.append("        </div>\n");

        // 3 Counters: Direct, Groups, Channels
        sb.append("        <div class=\"profile-stats-row\">\n");
        sb.append("          <div class=\"stat-count-box\">\n");
        sb.append("            <span class=\"stat-box-icon\">💬</span>\n");
        sb.append("            <span class=\"stat-box-number\">" + directCount + "</span>\n");
        sb.append("            <span class=\"stat-box-label\">Direct</span>\n");
        sb.append("          </div>\n");
        sb.append("          <div class=\"stat-count-box\">\n");
        sb.append("            <span class=\"stat-box-icon\">👥</span>\n");
        sb.append("            <span class=\"stat-box-number\">" + groupCount + "</span>\n");
        sb.append("            <span class=\"stat-box-label\">Groups</span>\n");
        sb.append("          </div>\n");
        sb.append("          <div class=\"stat-count-box\">\n");
        sb.append("            <span class=\"stat-box-icon\">📢</span>\n");
        sb.append("            <span class=\"stat-box-number\">" + channelCount + "</span>\n");
        sb.append("            <span class=\"stat-box-label\">Channels</span>\n");
        sb.append("          </div>\n");
        sb.append("        </div>\n");

        // Fast Switch Account Form
        sb.append("        <div style=\"margin-bottom: 14px;\">\n");
        if (isAdmin) {
            sb.append("          <form action=\"/login\" method=\"POST\">\n");
            sb.append("            <input type=\"hidden\" name=\"role\" value=\"user\">\n");
            sb.append("            <button type=\"submit\" class=\"profile-switch-btn\">👤 Switch to Student Account (Hlayiseko)</button>\n");
            sb.append("          </form>\n");
        } else {
            sb.append("          <form action=\"/login\" method=\"POST\">\n");
            sb.append("            <input type=\"hidden\" name=\"role\" value=\"admin\">\n");
            sb.append("            <button type=\"submit\" class=\"profile-switch-btn\">🛡️ Switch to Administrator Account (Dr. P. Sithole)</button>\n");
            sb.append("          </form>\n");
        }
        sb.append("        </div>\n");

        // Profile Upsell Card
        sb.append("        <a href=\"/?screen=premium\" class=\"profile-upsell-card\">\n");
        sb.append("          <div class=\"upsell-crown-bubble\">👑</div>\n");
        sb.append("          <div class=\"upsell-text-content\">\n");
        sb.append("            <h4 class=\"upsell-title\">Upgrade to Premium</h4>\n");
        sb.append("            <p class=\"upsell-desc\">Calls, video & file sharing — R20/mo</p>\n");
        sb.append("          </div>\n");
        sb.append("        </a>\n");        sb.append("        <a href=\"/signout\" class=\"profile-signout-btn\">↪ Sign Out & Choose Persona</a>\n");
        sb.append("      </div>\n");
        sb.append("    </section>\n");

        // ====================================================================
        // BOTTOM DASHBOARD (PRESERVED ON BOTH DESKTOP AND MOBILE)
        // NOTE: USER DOES NOT HAVE ADMIN PAGE!
        // ====================================================================
        if (!screen.equals("login")) {
            sb.append("    <nav class=\"app-bottom-nav\">\n");
            sb.append("      <a href=\"/?screen=chats\" class=\"bottom-nav-item " + (screen.equals("chats") || screen.equals("chatView") ? "active" : "") + "\">\n");
            sb.append("        <span class=\"nav-icon\">💬</span><span class=\"nav-label\">Chats</span>\n");
            sb.append("      </a>\n");

            // USER DOES NOT HAVE ADMIN PAGE: Only render Admin if role is 'admin'
            if (isAdmin) {
                sb.append("      <a href=\"/?screen=admin\" class=\"bottom-nav-item " + (screen.equals("admin") ? "active" : "") + "\">\n");
                sb.append("        <span class=\"nav-icon\">🛡️</span><span class=\"nav-label\">Admin</span>\n");
                sb.append("      </a>\n");
            }

            sb.append("      <a href=\"/?screen=premium\" class=\"bottom-nav-item " + (screen.equals("premium") ? "active" : "") + "\">\n");
            sb.append("        <span class=\"nav-icon\">👑</span><span class=\"nav-label\">Premium</span>\n");
            sb.append("      </a>\n");
            sb.append("      <a href=\"/?screen=profile\" class=\"bottom-nav-item " + (screen.equals("profile") ? "active" : "") + "\">\n");
            sb.append("        <span class=\"nav-icon\">👤</span><span class=\"nav-label\">Profile</span>\n");
            sb.append("      </a>\n");
            sb.append("    </nav>\n");
        }

        // ====================================================================
        // MODAL: NEW CONVERSATION (CSS :target Modal)
        // ====================================================================
        sb.append("    <div class=\"modal-backdrop\" id=\"new-chat-modal\">\n");
        sb.append("      <div class=\"modal-sheet\">\n");
        sb.append("        <div class=\"modal-sheet-header\">\n");
        sb.append("          <h3 class=\"modal-sheet-title\">New Conversation</h3>\n");
        sb.append("          <a href=\"#\" class=\"modal-close-icon\">✕</a>\n");
        sb.append("        </div>\n");

        sb.append("        <form action=\"/new-conversation\" method=\"POST\">\n");
        sb.append("          <div class=\"modal-segmented-tabs\">\n");
        sb.append("            <label class=\"modal-seg-btn\"><input type=\"radio\" name=\"type\" value=\"direct\" style=\"display:none;\"> 👤 Direct</label>\n");
        sb.append("            <label class=\"modal-seg-btn\"><input type=\"radio\" name=\"type\" value=\"group\" style=\"display:none;\"> 👥 Group</label>\n");
        sb.append("            <label class=\"modal-seg-btn active\"><input type=\"radio\" name=\"type\" value=\"channel\" checked style=\"display:none;\"> 📢 Channel</label>\n");
        sb.append("          </div>\n");

        sb.append("          <div class=\"modal-field-block\">\n");
        sb.append("            <label class=\"modal-field-label\">Name</label>\n");
        sb.append("            <input type=\"text\" name=\"name\" class=\"modal-text-input\" placeholder=\"e.g. BIS301 Updates / Study Group\" required>\n");
        sb.append("          </div>\n");

        sb.append("          <div class=\"modal-field-block\">\n");
        sb.append("            <label class=\"modal-field-label\">Description (optional)</label>\n");
        sb.append("            <input type=\"text\" name=\"description\" class=\"modal-text-input\" placeholder=\"What's this about?\">\n");
        sb.append("          </div>\n");

        sb.append("          <div class=\"modal-field-block\">\n");
        sb.append("            <div class=\"modal-search-box\">\n");
        sb.append("              <span>🔍</span>\n");
        sb.append("              <input type=\"text\" placeholder=\"Search people in MSC directory...\">\n");
        sb.append("            </div>\n");
        sb.append("          </div>\n");

        sb.append("          <button type=\"submit\" class=\"btn-start-conversation-purple\">Start Conversation</button>\n");
        sb.append("        </form>\n");
        sb.append("      </div>\n");
        sb.append("    </div>\n");

        sb.append("  </div>\n"); // end app-container
        sb.append("</div>\n");   // end app-viewport

        sb.append("</body>\n</html>");
        return sb.toString();
    }

    // ========================================================================
    // HELPER METHODS
    // ========================================================================
    private static Conversation findConversation(int id) {
        for (Conversation c : conversations) {
            if (c.id == id) return c;
        }
        return null;
    }

    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) return params;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if (idx > 0) {
                String key = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                String val = URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8);
                params.put(key, val);
            }
        }
        return params;
    }

    private static Map<String, String> parseFormData(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            body.append(line);
        }
        return parseQueryParams(body.toString());
    }

    private static void redirect(HttpExchange exchange, String location) throws IOException {
        exchange.getResponseHeaders().set("Location", location);
        exchange.sendResponseHeaders(302, -1);
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }

    private static String urlEncode(String text) {
        if (text == null) return "";
        return java.net.URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}
