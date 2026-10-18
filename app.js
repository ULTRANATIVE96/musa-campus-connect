/**
 * ============================================================================
 * MUSA CAMPUS CONNECT (MSC) — ZERO-DATA MESSAGING APPLICATION LOGIC
 * Portal: https://connect.msc.ac.za
 * ============================================================================
 */

// Application State
const CampusConnect = {
  activeScreen: 'chats', // 'chats', 'premium', 'profile', 'chatView'
  activeFilter: 'all',    // 'all', 'direct', 'group', 'channel'
  searchQuery: '',
  isPremium: false,
  activeChatId: null,

  // User Profile matching Photo 4
  user: {
    name: 'Hlayiseko Hlungwani',
    email: 'hlayisekohh7@gmail.com',
    initials: 'H',
    role: 'User'
  },

  // Suggested Campus Contacts for Direct Chat
  campusContacts: [
    { id: 'c1', name: 'Dr. P. Sithole (Lecturer - BIS301)', role: 'Lecturer' },
    { id: 'c2', name: 'Thabo Mokoena (22410452)', role: 'Student' },
    { id: 'c3', name: 'Lerato Khumalo (22411983)', role: 'Student' },
    { id: 'c4', name: 'Sipho Dlamini (22415629)', role: 'Student' },
    { id: 'c5', name: 'Naledi Molefe (22417834)', role: 'Student' },
    { id: 'c6', name: 'Kagiso Baloyi (22418291)', role: 'Student' }
  ],

  // In-Memory Conversations
  conversations: [
    {
      id: 1,
      type: 'channel',
      name: 'BIS301 Updates',
      description: 'Official course announcements and exam schedules',
      timestamp: '16:08',
      messages: [
        { sender: 'Dr. P. Sithole (Lecturer)', text: 'Welcome to BIS301. Please note that test 1 venue will be in ICT Lab 3 next Friday at 09:00.', time: '14:20', isMe: false },
        { sender: 'System', text: 'Zero-data verified: All text notices transmitted without mobile airtime charges.', time: '14:21', isMe: false },
        { sender: 'Hlayiseko Hlungwani', text: 'Thank you Dr. Sithole, will the revision slides be uploaded here?', time: '16:05', isMe: true },
        { sender: 'Dr. P. Sithole (Lecturer)', text: 'Yes, slides are uploaded and zero-rated for all students.', time: '16:08', isMe: false }
      ]
    },
    {
      id: 2,
      type: 'group',
      name: 'ICT Foundation Study Group',
      description: 'Peer revision for mathematics and programming assignments',
      timestamp: '15:45',
      messages: [
        { sender: 'Lerato Khumalo', text: 'Hey team, let’s review the variables table for Phase 1 tonight.', time: '15:30', isMe: false },
        { sender: 'Thabo Mokoena', text: 'Agreed! The zero-data messaging is saving my night data completely.', time: '15:40', isMe: false },
        { sender: 'Hlayiseko Hlungwani', text: 'Awesome, I compiled the Java core code with input validation loops.', time: '15:45', isMe: true }
      ]
    },
    {
      id: 3,
      type: 'direct',
      name: 'Thabo Mokoena',
      description: 'Informatics Classmate',
      timestamp: 'Yesterday',
      messages: [
        { sender: 'Thabo Mokoena', text: 'Hi Hlayiseko, did you get the lecture notes from Monday?', time: 'Yesterday', isMe: false },
        { sender: 'Hlayiseko Hlungwani', text: 'Yes, downloaded them on campus. I will send the summary now.', time: 'Yesterday', isMe: true }
      ]
    }
  ]
};

// ============================================================================
// INITIALIZATION
// ============================================================================
document.addEventListener('DOMContentLoaded', () => {
  setupNavigation();
  setupSearchAndFilters();
  setupNewConversationModal();
  setupChatThread();
  setupPremiumCheckout();
  setupProfileActions();
  
  // Render Initial View
  renderConversations();
  updateProfileCounters();
});

// ============================================================================
// NAVIGATION ENGINE
// ============================================================================
function setupNavigation() {
  const navChats = document.getElementById('navItemChats');
  const navPremium = document.getElementById('navItemPremium');
  const navProfile = document.getElementById('navItemProfile');

  if (navChats) navChats.addEventListener('click', () => navigateTo('chats'));
  if (navPremium) navPremium.addEventListener('click', () => navigateTo('premium'));
  if (navProfile) navProfile.addEventListener('click', () => navigateTo('profile'));

  // Header avatar goes to profile
  const headerAvatar = document.getElementById('headerAvatarBtn');
  if (headerAvatar) headerAvatar.addEventListener('click', () => navigateTo('profile'));

  // Back to chats buttons
  const backFromPrem = document.getElementById('btnBackToChatsFromPremium');
  const backFromProf = document.getElementById('btnBackToChatsFromProfile');
  const backFromThread = document.getElementById('btnBackToChatsFromThread');

  if (backFromPrem) backFromPrem.addEventListener('click', () => navigateTo('chats'));
  if (backFromProf) backFromProf.addEventListener('click', () => navigateTo('chats'));
  if (backFromThread) backFromThread.addEventListener('click', () => navigateTo('chats'));

  // Learn more link on banner
  const btnLearnMore = document.getElementById('btnPromoLearnMore');
  if (btnLearnMore) btnLearnMore.addEventListener('click', () => navigateTo('premium'));

  // Profile upsell banner
  const profUpgradeBanner = document.getElementById('profileUpgradeBanner');
  if (profUpgradeBanner) profUpgradeBanner.addEventListener('click', () => navigateTo('premium'));
}

function navigateTo(screenName) {
  CampusConnect.activeScreen = screenName;

  // Screen elements
  const screens = {
    chats: document.getElementById('screenChats'),
    premium: document.getElementById('screenPremium'),
    profile: document.getElementById('screenProfile'),
    chatView: document.getElementById('screenChatView')
  };

  // Nav item elements
  const navItems = {
    chats: document.getElementById('navItemChats'),
    premium: document.getElementById('navItemPremium'),
    profile: document.getElementById('navItemProfile')
  };

  // Hide all screens
  Object.values(screens).forEach(s => {
    if (s) s.classList.remove('active-screen');
  });

  // Reset nav highlights
  Object.values(navItems).forEach(n => {
    if (n) n.classList.remove('active');
  });

  // Show active screen
  if (screens[screenName]) {
    screens[screenName].classList.add('active-screen');
  }

  // Highlight bottom nav (unless in chat view)
  const bottomNav = document.getElementById('appBottomNav');
  if (screenName === 'chatView') {
    if (bottomNav) bottomNav.style.display = 'none';
  } else {
    if (bottomNav) bottomNav.style.display = 'flex';
    if (navItems[screenName]) {
      navItems[screenName].classList.add('active');
    }
  }

  if (screenName === 'profile') {
    updateProfileCounters();
  }
}

// ============================================================================
// CHATS RENDERING & FILTERING
// ============================================================================
function setupSearchAndFilters() {
  const searchInput = document.getElementById('chatSearchInput');
  const clearBtn = document.getElementById('btnClearSearch');

  if (searchInput) {
    searchInput.addEventListener('input', (e) => {
      CampusConnect.searchQuery = e.target.value.toLowerCase().trim();
      if (clearBtn) {
        clearBtn.style.display = CampusConnect.searchQuery.length > 0 ? 'block' : 'none';
      }
      renderConversations();
    });
  }

  if (clearBtn) {
    clearBtn.addEventListener('click', () => {
      if (searchInput) searchInput.value = '';
      CampusConnect.searchQuery = '';
      clearBtn.style.display = 'none';
      renderConversations();
    });
  }

  // Filter Pills (All, Direct, Groups, Channels)
  const pills = document.querySelectorAll('.cat-pill');
  pills.forEach(pill => {
    pill.addEventListener('click', () => {
      pills.forEach(p => p.classList.remove('active'));
      pill.classList.add('active');
      CampusConnect.activeFilter = pill.getAttribute('data-filter');
      renderConversations();
    });
  });

  // Dismiss promo banner
  const dismissBtn = document.getElementById('btnDismissPromo');
  const promoBanner = document.getElementById('premiumPromoBanner');
  if (dismissBtn && promoBanner) {
    dismissBtn.addEventListener('click', () => {
      promoBanner.style.display = 'none';
    });
  }

  // Empty state button
  const emptyStartBtn = document.getElementById('btnEmptyStateStart');
  if (emptyStartBtn) {
    emptyStartBtn.addEventListener('click', () => openNewConversationModal());
  }
}

function renderConversations() {
  const container = document.getElementById('conversationsContainer');
  const emptyState = document.getElementById('emptyStateContainer');
  if (!container || !emptyState) return;

  const filtered = CampusConnect.conversations.filter(c => {
    const matchesCategory = (CampusConnect.activeFilter === 'all') || (c.type === CampusConnect.activeFilter);
    const matchesSearch = !CampusConnect.searchQuery ||
      c.name.toLowerCase().includes(CampusConnect.searchQuery) ||
      c.description.toLowerCase().includes(CampusConnect.searchQuery);
    return matchesCategory && matchesSearch;
  });

  if (filtered.length === 0) {
    container.innerHTML = '';
    emptyState.style.display = 'flex';
  } else {
    emptyState.style.display = 'none';
    container.innerHTML = filtered.map(conv => {
      const typeClass = conv.type === 'channel' ? 'channel-type' : (conv.type === 'group' ? 'group-type' : 'direct-type');
      const icon = conv.type === 'channel' ? '📢' : (conv.type === 'group' ? '👥' : '💬');
      const lastMessage = conv.messages.length > 0 
        ? conv.messages[conv.messages.length - 1].text 
        : conv.description;

      return `
        <div class="conversation-row" onclick="openChatThread(${conv.id})">
          <div class="conv-avatar-box ${typeClass}">
            ${icon}
          </div>
          <div class="conv-meta">
            <div class="conv-meta-top">
              <span class="conv-title">${escapeHtml(conv.name)}</span>
              <span class="conv-timestamp">${conv.timestamp}</span>
            </div>
            <div class="conv-snippet">${escapeHtml(lastMessage)}</div>
          </div>
        </div>
      `;
    }).join('');
  }
}

// ============================================================================
// NEW CONVERSATION MODAL (Matching Photos 1, 2, 3)
// ============================================================================
let currentModalType = 'channel'; // default to channel as in photo 1

function setupNewConversationModal() {
  const openBtn = document.getElementById('btnOpenNewChatModal');
  const modal = document.getElementById('newConversationModal');
  const closeBtn = document.getElementById('btnCloseModal');
  const submitBtn = document.getElementById('btnSubmitNewConversation');
  const searchPeopleInput = document.getElementById('modalSearchPeopleInput');

  if (openBtn) openBtn.addEventListener('click', openNewConversationModal);
  if (closeBtn && modal) {
    closeBtn.addEventListener('click', () => modal.classList.remove('open'));
  }

  // Segmented Tabs: Direct | Group | Channel
  const segButtons = document.querySelectorAll('.modal-seg-btn');
  segButtons.forEach(btn => {
    btn.addEventListener('click', () => {
      segButtons.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      currentModalType = btn.getAttribute('data-type');
      updateModalFields();
    });
  });

  if (searchPeopleInput) {
    searchPeopleInput.addEventListener('input', (e) => {
      handleModalSearchPeople(e.target.value.trim().toLowerCase());
    });
  }

  if (submitBtn) {
    submitBtn.addEventListener('click', submitNewConversation);
  }
}

function openNewConversationModal() {
  const modal = document.getElementById('newConversationModal');
  if (!modal) return;

  // Reset inputs
  const nameInput = document.getElementById('modalInputName');
  const descInput = document.getElementById('modalInputDesc');
  const searchInput = document.getElementById('modalSearchPeopleInput');

  if (nameInput) nameInput.value = '';
  if (descInput) descInput.value = '';
  if (searchInput) searchInput.value = '';

  // Reset to Channel tab (as seen in Photo 1)
  const channelTab = document.querySelector('.modal-seg-btn[data-type="channel"]');
  if (channelTab) channelTab.click();

  modal.classList.add('open');
}

function updateModalFields() {
  const nameBlock = document.getElementById('modalNameFieldBlock');
  const descBlock = document.getElementById('modalDescFieldBlock');
  const nameInput = document.getElementById('modalInputName');
  const nameLabel = document.getElementById('modalNameLabel');
  const noUsersLabel = document.getElementById('modalNoUsersLabel');
  const suggestedList = document.getElementById('suggestedUsersList');

  if (currentModalType === 'direct') {
    // Photo 3: In Direct mode, Name and Description are hidden! Only search is shown.
    if (nameBlock) nameBlock.style.display = 'none';
    if (descBlock) descBlock.style.display = 'none';
    showSuggestedContacts();
  } else if (currentModalType === 'group') {
    // Photo 2: Group mode -> Name placeholder: "e.g. Study Group"
    if (nameBlock) nameBlock.style.display = 'block';
    if (descBlock) descBlock.style.display = 'block';
    if (nameLabel) nameLabel.textContent = 'Name';
    if (nameInput) nameInput.placeholder = 'e.g. Study Group';
    if (noUsersLabel) noUsersLabel.style.display = 'block';
    if (suggestedList) suggestedList.style.display = 'none';
  } else {
    // Photo 1: Channel mode -> Name placeholder: "e.g. BIS301 Updates"
    if (nameBlock) nameBlock.style.display = 'block';
    if (descBlock) descBlock.style.display = 'block';
    if (nameLabel) nameLabel.textContent = 'Name';
    if (nameInput) nameInput.placeholder = 'e.g. BIS301 Updates';
    if (noUsersLabel) noUsersLabel.style.display = 'block';
    if (suggestedList) suggestedList.style.display = 'none';
  }
}

function showSuggestedContacts() {
  const noUsersLabel = document.getElementById('modalNoUsersLabel');
  const suggestedList = document.getElementById('suggestedUsersList');
  if (!suggestedList) return;

  if (CampusConnect.campusContacts.length > 0) {
    if (noUsersLabel) noUsersLabel.style.display = 'none';
    suggestedList.style.display = 'flex';
    suggestedList.innerHTML = CampusConnect.campusContacts.map(c => `
      <div class="suggested-user-item" onclick="selectContactForDirect('${c.id}')">
        <div class="sug-user-avatar">${c.name.charAt(0)}</div>
        <div class="sug-user-name">${escapeHtml(c.name)}</div>
      </div>
    `).join('');
  } else {
    if (noUsersLabel) noUsersLabel.style.display = 'block';
    suggestedList.style.display = 'none';
  }
}

function handleModalSearchPeople(query) {
  const noUsersLabel = document.getElementById('modalNoUsersLabel');
  const suggestedList = document.getElementById('suggestedUsersList');
  if (!suggestedList || !noUsersLabel) return;

  if (!query) {
    if (currentModalType === 'direct') {
      showSuggestedContacts();
    } else {
      noUsersLabel.style.display = 'block';
      suggestedList.style.display = 'none';
    }
    return;
  }

  const matches = CampusConnect.campusContacts.filter(c => c.name.toLowerCase().includes(query));
  if (matches.length > 0) {
    noUsersLabel.style.display = 'none';
    suggestedList.style.display = 'flex';
    suggestedList.innerHTML = matches.map(c => `
      <div class="suggested-user-item" onclick="selectContactForDirect('${c.id}')">
        <div class="sug-user-avatar">${c.name.charAt(0)}</div>
        <div class="sug-user-name">${escapeHtml(c.name)}</div>
      </div>
    `).join('');
  } else {
    noUsersLabel.style.display = 'block';
    noUsersLabel.textContent = 'No users found';
    suggestedList.style.display = 'none';
  }
}

window.selectContactForDirect = function(contactId) {
  const contact = CampusConnect.campusContacts.find(c => c.id === contactId);
  if (!contact) return;

  // Check if conversation already exists
  let existing = CampusConnect.conversations.find(c => c.name === contact.name && c.type === 'direct');
  if (existing) {
    closeModal();
    openChatThread(existing.id);
    return;
  }

  const newChat = {
    id: Date.now(),
    type: 'direct',
    name: contact.name,
    description: `${contact.role} — Zero-data contact`,
    timestamp: 'Just now',
    messages: [
      { sender: 'System', text: `Encrypted direct chat started with ${contact.name}. Zero-data connection active.`, time: 'Just now', isMe: false }
    ]
  };

  CampusConnect.conversations.unshift(newChat);
  closeModal();
  renderConversations();
  updateProfileCounters();
  openChatThread(newChat.id);
};

function submitNewConversation() {
  const nameInput = document.getElementById('modalInputName');
  const descInput = document.getElementById('modalInputDesc');
  const searchInput = document.getElementById('modalSearchPeopleInput');

  let name = nameInput ? nameInput.value.trim() : '';
  const desc = descInput ? descInput.value.trim() : '';

  if (currentModalType === 'channel') {
    if (!name) name = 'BIS301 Updates';
  } else if (currentModalType === 'group') {
    if (!name) name = 'Study Group';
  } else {
    // Direct
    const searchQuery = searchInput ? searchInput.value.trim() : '';
    name = searchQuery || 'Thabo Mokoena';
  }

  const newConv = {
    id: Date.now(),
    type: currentModalType,
    name: name,
    description: desc || `${currentModalType.charAt(0).toUpperCase() + currentModalType.slice(1)} conversation on Campus Connect`,
    timestamp: 'Just now',
    messages: [
      { sender: 'System', text: `Conversation established under ${currentModalType} protocol. Mobile data usage: 0.00 MB.`, time: 'Just now', isMe: false }
    ]
  };

  CampusConnect.conversations.unshift(newConv);
  closeModal();
  renderConversations();
  updateProfileCounters();
  openChatThread(newConv.id);
}

function closeModal() {
  const modal = document.getElementById('newConversationModal');
  if (modal) modal.classList.remove('open');
}

// ============================================================================
// CHAT THREAD CONVERSATION
// ============================================================================
function setupChatThread() {
  const sendBtn = document.getElementById('btnSendMessage');
  const input = document.getElementById('activeMessageInput');
  const btnVoice = document.getElementById('btnMakeVoiceCall');
  const btnVideo = document.getElementById('btnMakeVideoCall');
  const btnAttach = document.getElementById('btnAttachFile');

  if (sendBtn && input) {
    sendBtn.addEventListener('click', handleSendMessage);
    input.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') handleSendMessage();
    });
  }

  if (btnVoice) {
    btnVoice.addEventListener('click', () => {
      if (!CampusConnect.isPremium) {
        if (confirm("Voice Calls are a Premium Feature (R20/mo).\nUnlock crystal-clear audio calls to any classmate or lecturer?")) {
          navigateTo('premium');
        }
      } else {
        alert("📞 Connecting voice call via zero-latency WebRTC gateway...");
      }
    });
  }

  if (btnVideo) {
    btnVideo.addEventListener('click', () => {
      if (!CampusConnect.isPremium) {
        if (confirm("Video Calls are a Premium Feature (R20/mo).\nUnlock face-to-face video consultations with lecturers?")) {
          navigateTo('premium');
        }
      } else {
        alert("📹 Initializing encrypted video consultation...");
      }
    });
  }

  if (btnAttach) {
    btnAttach.addEventListener('click', () => {
      if (!CampusConnect.isPremium) {
        if (confirm("PDF & Presentation File Sharing is a Premium Feature (R20/mo).\nUpgrade now to share documents?")) {
          navigateTo('premium');
        }
      } else {
        alert("📄 Select document or lecture slide to transmit data-free.");
      }
    });
  }
}

window.openChatThread = function(id) {
  const conv = CampusConnect.conversations.find(c => c.id === id);
  if (!conv) return;

  CampusConnect.activeChatId = id;
  const title = document.getElementById('chatThreadTitle');
  const status = document.getElementById('chatThreadStatus');

  if (title) title.textContent = conv.name;
  if (status) status.textContent = `Zero-data active • ${conv.type.toUpperCase()}`;

  renderChatMessages(conv);
  navigateTo('chatView');
};

function renderChatMessages(conv) {
  const stream = document.getElementById('chatMessagesStream');
  if (!stream) return;

  stream.innerHTML = conv.messages.map(m => `
    <div class="msg-bubble ${m.isMe ? 'outgoing' : 'incoming'}">
      ${!m.isMe ? `<div class="msg-sender-name">${escapeHtml(m.sender)}</div>` : ''}
      <div class="msg-text-body">${escapeHtml(m.text)}</div>
      <div class="msg-time-stamp">${m.time}</div>
    </div>
  `).join('');

  stream.scrollTop = stream.scrollHeight;
}

function handleSendMessage() {
  const input = document.getElementById('activeMessageInput');
  const text = input ? input.value.trim() : '';
  if (!text || !CampusConnect.activeChatId) return;

  const conv = CampusConnect.conversations.find(c => c.id === CampusConnect.activeChatId);
  if (!conv) return;

  // Add outgoing message
  conv.messages.push({
    sender: CampusConnect.user.name,
    text: text,
    time: formatCurrentTime(),
    isMe: true
  });
  conv.timestamp = 'Just now';

  input.value = '';
  renderChatMessages(conv);
  renderConversations();

  // Simulated peer or lecturer reply
  setTimeout(() => {
    let replySender = 'Peer (Online)';
    let replyText = 'Received loud and clear via zero-data connection!';

    if (conv.type === 'channel') {
      replySender = 'Dr. P. Sithole (Lecturer)';
      replyText = 'Acknowledgment: Noted. The update has been verified for the class records.';
    } else if (text.toLowerCase().includes('data') || text.toLowerCase().includes('bundle')) {
      replyText = 'Zero-rating is working 100%! My MTN airtime balance is still intact.';
    } else if (text.toLowerCase().includes('test') || text.toLowerCase().includes('exam')) {
      replyText = 'Remember to check Building 4 ICT Lab for the seat allocation.';
    }

    conv.messages.push({
      sender: replySender,
      text: replyText,
      time: formatCurrentTime(),
      isMe: false
    });

    renderChatMessages(conv);
    renderConversations();
  }, 900);
}

function formatCurrentTime() {
  const now = new Date();
  const h = String(now.getHours()).padStart(2, '0');
  const m = String(now.getMinutes()).padStart(2, '0');
  return `${h}:${m}`;
}

// ============================================================================
// PREMIUM CHECKOUT (R20/MONTH SIMULATION)
// ============================================================================
function setupPremiumCheckout() {
  const btnUpgrade = document.getElementById('btnTriggerUpgrade');
  const checkoutModal = document.getElementById('checkoutModal');
  const btnCloseCheckout = document.getElementById('btnCloseCheckout');
  const btnConfirmPayment = document.getElementById('btnConfirmPayment');

  if (btnUpgrade && checkoutModal) {
    btnUpgrade.addEventListener('click', () => {
      if (CampusConnect.isPremium) {
        if (confirm("You are currently subscribed to Campus Connect Premium.\nWould you like to downgrade to the Free Tier?")) {
          CampusConnect.isPremium = false;
          updatePremiumStatusUI();
          alert("Downgraded to Free Zero-Data tier.");
        }
        return;
      }
      checkoutModal.classList.add('open');
    });
  }

  if (btnCloseCheckout && checkoutModal) {
    btnCloseCheckout.addEventListener('click', () => checkoutModal.classList.remove('open'));
  }

  if (btnConfirmPayment && checkoutModal) {
    btnConfirmPayment.addEventListener('click', () => {
      const selectedPayMethod = document.querySelector('input[name="paymentOption"]:checked')?.value || 'airtime';
      CampusConnect.isPremium = true;
      checkoutModal.classList.remove('open');
      updatePremiumStatusUI();

      alert(`🎉 Welcome to Campus Connect Premium!\n\nPayment of R20.00 processed via ${selectedPayMethod.toUpperCase()}.\nVoice calls, video consultations, and unlimited PDF file sharing are now active.`);
    });
  }
}

function updatePremiumStatusUI() {
  const btnUpgrade = document.getElementById('btnTriggerUpgrade');
  const badge = document.getElementById('profileBadgePill');
  const promoCard = document.getElementById('premiumPromoBanner');

  if (btnUpgrade) {
    if (CampusConnect.isPremium) {
      btnUpgrade.textContent = '✓ Subscribed (Active)';
      btnUpgrade.style.background = '#10b981';
    } else {
      btnUpgrade.textContent = 'Upgrade to Premium';
      btnUpgrade.style.background = '#7c3aed';
    }
  }

  if (badge) {
    if (CampusConnect.isPremium) {
      badge.textContent = '👑 Premium Member';
      badge.classList.add('premium-tier');
    } else {
      badge.textContent = 'User';
      badge.classList.remove('premium-tier');
    }
  }

  if (promoCard && CampusConnect.isPremium) {
    promoCard.style.display = 'none';
  }
}

// ============================================================================
// PROFILE SCREEN LOGIC (Matching Photo 4)
// ============================================================================
function setupProfileActions() {
  const btnSignOut = document.getElementById('btnSignOut');
  const btnDelete = document.getElementById('btnDeleteAccount');

  if (btnSignOut) {
    btnSignOut.addEventListener('click', () => {
      alert("Session: Signed out from Campus Connect. Directing to login.");
    });
  }

  if (btnDelete) {
    btnDelete.addEventListener('click', () => {
      if (confirm("Are you sure you want to delete your Campus Connect account? (Simulation)")) {
        alert("Account deleted.");
      }
    });
  }
}

function updateProfileCounters() {
  const direct = CampusConnect.conversations.filter(c => c.type === 'direct').length;
  const groups = CampusConnect.conversations.filter(c => c.type === 'group').length;
  const channels = CampusConnect.conversations.filter(c => c.type === 'channel').length;

  const directEl = document.getElementById('profileDirectCount');
  const groupsEl = document.getElementById('profileGroupsCount');
  const channelsEl = document.getElementById('profileChannelsCount');

  if (directEl) directEl.textContent = direct;
  if (groupsEl) groupsEl.textContent = groups;
  if (channelsEl) channelsEl.textContent = channels;
}

// Helper: Escape HTML
function escapeHtml(text) {
  if (!text) return '';
  return text.replace(/[&<>"']/g, function(m) {
    switch (m) {
      case '&': return '&amp;';
      case '<': return '&lt;';
      case '>': return '&gt;';
      case '"': return '&quot;';
      default: return '&#039;';
    }
  });
}
