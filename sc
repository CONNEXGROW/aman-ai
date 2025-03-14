<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <title>JARVIS AM.0</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Share+Tech+Mono&display=swap');

    :root {
      --primary-color: #00f2fe;
      --secondary-color: #4facfe;
      --background-dark: #1a1a1a;
      --text-primary: #ffffff;
      --text-secondary: #a0a0a0;
      --glow-color: rgba(0, 242, 254, 0.5);
      --animation-timing: 0.3s;
      --message-max-width: 85%;
      --message-spacing: 1.5rem;
      --container-padding: 2rem;
    }

    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      -webkit-tap-highlight-color: transparent;
    }

    body {
      font-family: 'Share Tech Mono', monospace;
      background-color: var(--background-dark);
      color: var(--text-primary);
      line-height: 1.5;
      overflow: hidden;
      height: 100vh;
      width: 100vw;
    }

    .app-container {
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      padding: var(--container-padding);
      background: radial-gradient(circle at center, #1a1a1a 0%, #000000 100%);
    }

    .power-circle {
      position: absolute;
      width: 100vh;
      height: 100vh;
      border: 2px solid var(--primary-color);
      border-radius: 50%;
      opacity: 0.1;
      animation: pulse 4s infinite;
    }

    .chat-container {
      position: relative;
      width: 100%;
      max-width: 900px;
      height: calc(100vh - var(--container-padding) * 2 - 60px);
      background: rgba(26, 26, 26, 0.95);
      border: 1px solid var(--primary-color);
      border-radius: 16px;
      backdrop-filter: blur(10px);
      display: flex;
      flex-direction: column;
      overflow: hidden;
      box-shadow: 0 0 30px rgba(0, 242, 254, 0.1);
      display: none;
    }

    .chat-header {
      padding: 1.5rem;
      background: linear-gradient(180deg, rgba(0,242,254,0.1) 0%, rgba(0,242,254,0) 100%);
      border-bottom: 1px solid var(--primary-color);
    }

    .header-content {
      display: flex;
      align-items: center;
      gap: 1.5rem;
      margin-bottom: 1rem;
    }

    .jarvis-logo {
      position: relative;
      width: 60px;
      height: 60px;
      flex-shrink: 0;
    }

    .arc-reactor {
      position: relative;
      width: 100%;
      height: 100%;
      animation: float 3s ease-in-out infinite;
    }

    .reactor-core {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      width: 30px;
      height: 30px;
      background: var(--primary-color);
      border-radius: 50%;
      box-shadow: 0 0 20px var(--glow-color);
      animation: glow 2s ease-in-out infinite;
    }

    .reactor-ring {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      border: 2px solid var(--primary-color);
      border-radius: 50%;
      animation: spin 3s linear infinite;
    }

    .header-info h1 {
      font-size: clamp(1.5rem, 4vw, 2rem);
      color: var(--primary-color);
      text-shadow: 0 0 10px var(--glow-color);
      margin-bottom: 0.5rem;
    }

    .status-container {
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }

    .status-dot {
      width: 8px;
      height: 8px;
      background-color: var(--primary-color);
      border-radius: 50%;
      animation: pulse 2s infinite;
    }

    .system-stats {
      display: flex;
      gap: 2rem;
    }

    .stat {
      flex: 1;
    }

    .stat-label {
      font-size: 0.8rem;
      color: var(--text-secondary);
      margin-bottom: 0.25rem;
    }

    .stat-bar {
      height: 4px;
      background: rgba(255,255,255,0.1);
      border-radius: 2px;
      overflow: hidden;
    }

    .stat-fill {
      height: 100%;
      background: var(--primary-color);
      transition: width 1s ease;
    }

    .chat-box {
      flex: 1;
      padding: var(--message-spacing);
      overflow-y: auto;
      scroll-behavior: smooth;
      position: relative;
      display: flex;
      flex-direction: column;
      gap: 1.5rem;
    }

    .welcome-message {
      text-align: center;
      margin: 2rem 0;
      padding: 2rem;
      background: rgba(0, 242, 254, 0.05);
      border-radius: 12px;
      border: 1px solid var(--primary-color);
    }

    .welcome-message h2 {
      color: var(--primary-color);
      margin-bottom: 1rem;
      font-size: clamp(1.2rem, 3vw, 1.5rem);
    }

    .welcome-message p {
      color: var(--text-secondary);
    }

    .message-group {
      display: flex;
      flex-direction: column;
      gap: 0.25rem;
      width: 100%;
      padding: 0.5rem var(--message-spacing);
      position: relative;
    }

    .message {
      position: relative;
      padding: 1rem 1.25rem;
      border-radius: 12px;
      font-size: 1rem;
      line-height: 1.6;
      max-width: var(--message-max-width);
      word-wrap: break-word;
      white-space: pre-wrap;
      animation: fadeIn 0.3s ease-out;
      border: 1px solid var(--primary-color);
    }

    .user {
      background: rgba(0,242,254,0.1);
      margin-left: auto;
      border-top-right-radius: 4px;
      text-align: right;
    }

    .bot {
      background: rgba(255,255,255,0.05);
      margin-right: auto;
      border-top-left-radius: 4px;
      text-align: left;
    }

    .message::before {
      content: '';
      position: absolute;
      width: 0;
      height: 0;
      border: 8px solid transparent;
      top: -1px;
    }

    .user::before {
      right: -8px;
      border-left-color: var(--primary-color);
      border-top-color: var(--primary-color);
    }

    .bot::before {
      left: -8px;
      border-right-color: var(--primary-color);
      border-top-color: var(--primary-color);
    }

    .input-container {
      padding: 1.5rem;
      background: rgba(0,0,0,0.3);
      border-top: 1px solid var(--primary-color);
      display: flex;
      gap: 1rem;
    }

    .input-wrapper {
      position: relative;
      flex: 1;
    }

    #user-input {
      width: 100%;
      padding: 1rem;
      background: rgba(255,255,255,0.05);
      border: 1px solid var(--primary-color);
      border-radius: 8px;
      color: var(--text-primary);
      font-family: inherit;
      font-size: 1rem;
      outline: none;
      transition: all var(--animation-timing);
      resize: none;
      min-height: 50px;
      max-height: 150px;
    }

    #user-input:focus {
      box-shadow: 0 0 15px var(--glow-color);
    }

    .input-effects {
      position: absolute;
      inset: 0;
      pointer-events: none;
      border-radius: 8px;
      opacity: 0.5;
    }

    #send-button {
      position: relative;
      width: 50px;
      height: 50px;
      border: none;
      border-radius: 50%;
      background: var(--primary-color);
      color: var(--background-dark);
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all var(--animation-timing);
      overflow: hidden;
      flex-shrink: 0;
    }

    #send-button:hover {
      transform: scale(1.1);
    }

    #send-button:active {
      transform: scale(0.95);
    }

    #send-button svg {
      width: 24px;
      height: 24px;
      z-index: 1;
    }

    .button-glow {
      position: absolute;
      inset: 0;
      background: radial-gradient(circle at center, var(--primary-color) 0%, transparent 70%);
      opacity: 0;
      transition: opacity var(--animation-timing);
    }

    #send-button:hover .button-glow {
      opacity: 0.5;
    }

    .loading {
      width: 20px;
      height: 20px;
      border: 2px solid var(--background-dark);
      border-top: 2px solid transparent;
      border-radius: 50%;
      animation: spin 1s linear infinite;
    }

    .chat-box::-webkit-scrollbar {
      width: 6px;
    }

    .chat-box::-webkit-scrollbar-track {
      background: transparent;
    }

    .chat-box::-webkit-scrollbar-thumb {
      background-color: var(--primary-color);
      border-radius: 3px;
    }

    @keyframes pulse {
      0%, 100% { opacity: 0.6; }
      50% { opacity: 1; }
    }

    @keyframes float {
      0%, 100% { transform: translateY(0); }
      50% { transform: translateY(-10px); }
    }

    @keyframes spin {
      from { transform: rotate(0deg); }
      to { transform: rotate(360deg); }
    }

    @keyframes glow {
      0%, 100% { box-shadow: 0 0 20px var(--glow-color); }
      50% { box-shadow: 0 0 40px var(--glow-color); }
    }

    @keyframes fadeIn {
      from {
        opacity: 0;
        transform: translateY(10px);
      }
      to {
        opacity: 1;
        transform: translateY(0);
      }
    }
  </style>
</head>
<body>
  <div class="app-container">
    <div class="power-circle"></div>

    <!-- Chat Container -->
    <div class="chat-container" id="chat-container">
      <div class="chat-header">
        <div class="header-content">
          <div class="jarvis-logo">
            <div class="arc-reactor">
              <div class="reactor-core"></div>
              <div class="reactor-ring"></div>
            </div>
          </div>
          <div class="header-info">
            <h1>JARVIS AM.0</h1>
            <div class="status-container">
              <span class="status-dot"></span>
              <span class="status" id="chat-status">SYSTEM ONLINE</span>
            </div>
          </div>
        </div>
        <div class="system-stats">
          <div class="stat">
            <span class="stat-label">CPU</span>
            <div class="stat-bar">
              <div class="stat-fill" style="width: 75%"></div>
            </div>
          </div>
          <div class="stat">
            <span class="stat-label">MEMORY</span>
            <div class="stat-bar">
              <div class="stat-fill" style="width: 65%"></div>
            </div>
          </div>
        </div>
      </div>

      <div class="chat-box" id="chat-box">
        <div class="welcome-message">
          <h2>Welcome, I am JARVIS AM.0</h2>
          <p>How may I assist you today?</p>
        </div>
      </div>

      <div class="input-container">
        <div class="input-wrapper">
          <input
            type="text"
            id="user-input"
            placeholder="Enter command..."
            autocomplete="off"
            disabled
          >
          <div class="input-effects"></div>
        </div>
        <button id="send-button" aria-label="Send message" disabled>
          <div class="button-glow"></div>
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="22" y1="2" x2="11" y2="13"></line>
            <polygon points="22 2 15 22 11 13 2 9 22 2"></polygon>
          </svg>
        </button>
      </div>
    </div>
  </div>

  <script>
    document.addEventListener('DOMContentLoaded', () => {
      const chatContainer = document.getElementById('chat-container');
      const userInput = document.getElementById('user-input');
      const sendButton = document.getElementById('send-button');
      const chatBox = document.getElementById('chat-box');
      let isLoading = false;

      // Enable input and button
      userInput.disabled = false;
      sendButton.disabled = false;

      // Handle sending messages
      async function handleSendMessage() {
        const message = userInput.value.trim();
        if (!message || isLoading) return;

        setLoading(true);
        displayMessage(message, 'user');
        userInput.value = '';
        userInput.style.height = 'auto';

        try {
          // Simulate bot response
          setTimeout(() => {
            displayMessage("This is a bot response.", 'bot');
            setLoading(false);
          }, 1000);
        } catch (error) {
          console.error('Error:', error);
          displayMessage('I apologize, but I encountered an error processing your request. Please try again.', 'bot');
          setLoading(false);
        }
      }

      // Display message in chat
      function displayMessage(message, sender) {
        const messageGroup = document.createElement('div');
        messageGroup.classList.add('message-group');

        const messageElement = document.createElement('div');
        messageElement.classList.add('message', sender);
        messageElement.textContent = message;

        // Remove welcome message if it exists
        const welcomeMessage = chatBox.querySelector('.welcome-message');
        if (welcomeMessage && chatBox.children.length > 1) {
          welcomeMessage.remove();
        }

        messageGroup.appendChild(messageElement);
        chatBox.appendChild(messageGroup);

        // Smooth scroll to bottom
        chatBox.scrollTo({
          top: chatBox.scrollHeight,
          behavior: 'smooth'
        });
      }

      // Set loading state
      function setLoading(loading) {
        isLoading = loading;
        sendButton.disabled = loading;
        userInput.disabled = loading;

        if (loading) {
          sendButton.innerHTML = '<div class="loading"></div>';
        } else {
          sendButton.innerHTML = `
            <div class="button-glow"></div>
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <line x1="22" y1="2" x2="11" y2="13"></line>
              <polygon points="22 2 15 22 11 13 2 9 22 2"></polygon>
            </svg>
          `;
        }
      }

      // Event listeners
      sendButton.addEventListener('click', handleSendMessage);
      userInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
          e.preventDefault();
          handleSendMessage();
        }
      });

      // Show chat container
      chatContainer.style.display = 'block';
    });
  </script>
</body>
</html>
