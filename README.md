# GUI-Based Snake Game 

## 🐍 Overview

The **GUI-Based Snake Game** is an interactive desktop game developed using **Java Swing**, **AWT**, and **Object-Oriented Programming (OOP)** concepts. The project provides a modern graphical user interface with multiple gameplay features such as levels, obstacles, golden apples, sound effects, score tracking, pause/resume functionality, and high-score management.

The objective of the game is to control the snake, collect apples to increase the score, advance through levels, and avoid collisions with obstacles, walls, and the snake's own body.

---

## ✨ Features

* 🎮 Smooth snake movement using arrow keys
* 🍎 Random red apple generation
* ⭐ Golden Apple bonus system (+5 score)
* 🚧 Obstacle-based gameplay
* 📈 Real-time score tracking
* 🏆 High-score saving using file handling
* 🎯 Multi-level progression system
* ⏸️ Pause and Resume functionality
* 🔊 Sound effects for eating apples and Game Over
* 💀 Game Over popup screen
* 🎉 Level Complete popup screen
* 🚀 Start Screen with instructions
* 🖥️ Java Swing based GUI
* ⚡ Increasing difficulty as levels progress

---

## 🛠️ Technologies Used

* Java
* Java Swing
* AWT (Abstract Window Toolkit)
* Object-Oriented Programming (OOP)
* Event Handling
* Java Timer
* File Handling
* Audio System API

---

## 📂 Project Structure

```bash
SnakeGame/
│
├── src/
│   ├── GamePanel.java
│   ├── GameFrame.java
│   └── SnakeGame.java
│
├── assets/
│   ├── eat.wav
│   └── gameover.wav
│
├── highscore.txt
└── README.md
```

---

## ⚙️ How the Game Works

1. The player starts the game from the welcome screen.
2. The snake moves continuously in the selected direction.
3. Red apples increase the score by 1 point.
4. Golden apples provide bonus points and additional growth.
5. After collecting enough apples, the player advances to the next level.
6. Obstacles appear on the game board, increasing difficulty.
7. The game ends if the snake collides with:

   * Walls
   * Obstacles
   * Its own body
8. The highest score achieved is saved and displayed.

---

## ▶️ Installation & Execution

### Step 1: Clone the Repository

```bash
git clone https://github.com/AKHILESH-create/gui-based-snake-game.git
```

### Step 2: Open the Project

Open the project in:

* IntelliJ IDEA
* Eclipse
* VS Code

### Step 3: Run the Game

Execute:

```bash
SnakeGame.java
```

or run the generated JAR file.

---

## 🎯 Controls

| Key   | Action                            |
| ----- | --------------------------------- |
| ↑     | Move Up                           |
| ↓     | Move Down                         |
| ←     | Move Left                         |
| →     | Move Right                        |
| Enter | Start Game / Next Level / Restart |
| P     | Pause / Resume                    |
| ESC   | Exit Game                         |

---

## 📸 Screenshots

```bash
/screenshots/startscreen.png
/screenshots/gameplay.png
/screenshots/levelcomplete.png
/screenshots/gameover.png
```

---

## 📚 Concepts Used

* Java Swing GUI Development
* Event Handling
* Collision Detection
* Game Loop Logic
* File Handling
* Audio Integration
* Object-Oriented Programming
* Data Structures using Arrays
* State Management

---

## 🚀 Future Enhancements

* Player Name System
* Leaderboard Support
* Multiple Difficulty Modes
* Additional Power-Ups
* Advanced Animations
* Database Integration
* Multiplayer Mode

---

## 🎓 Learning Outcomes

This project helped in understanding:

* Java Swing Application Development
* Event-Driven Programming
* Game Development Fundamentals
* Collision Detection Techniques
* File Handling and Data Persistence
* GUI Design and User Experience
* Debugging and Testing

---

## 👨‍💻 Author

**Akhilesh Kumar**

Developed as a Java GUI project to learn game development, object-oriented programming, and user interaction handling.

---

## 📄 License

This project is open-source and intended for educational and learning purposes.
