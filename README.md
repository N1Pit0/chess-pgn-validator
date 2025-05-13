# ♟️ Chess Game Replay Viewer

---

## 🔧 Overview

This project builds upon the refactored chess game project to create a **Chess Game Replay Viewer**. The application parses chess games in PGN (Portable Game Notation) format from files and allows users to watch the games being replayed through a Java GUI. The focus of this project was on integrating a robust PGN parser, improving error handling, and ensuring modularity. Invalid moves or malformed inputs are detected and reported, and the replay is terminated gracefully when necessary.

---

## ⚠️ Initial Problems

Several challenges were identified during development:

- **PGN Parsing Complexity:**  
  Parsing PGN required handling multiple games from a single file, validating syntax, and converting moves into game states.

- **Error Handling:**  
  Malformed inputs needed to be handled without crashing the program.

- **Integration with Chess Logic:**  
  The refactored chess engine needed to support automated replays instead of interactive play.

- **Modularity and Maintainability:**  
  Adherence to SOLID principles was necessary to keep components loosely coupled and extensible.

---

## ✅ Implementation Highlights

### 1. PGN Parser

- **Syntax Checking:**  
  Validates PGN file format and reports syntax errors while continuing to parse other games.

- **Game Parsing:**  
  Converts PGN moves into actionable game states for replay.

- **Error Handling:**  
  Graceful termination of invalid or malformed games with clear error messages.

---

### 2. Replay Controller

- **Game Replay Logic:**  
  Replays parsed games move by move in the GUI.

- **Custom Exceptions for Invalid Moves:**  
  Uses the chess engine to detect invalid moves and terminates replays with detailed reports.

- **Integration with Refactored Project:**  
  Builds upon existing engine components to support non-interactive playback.

---

### 3. Error Reporting

- **Syntax Errors:**  
  Highlights invalid syntax with file, line, and description.

- **Invalid Moves:**  
  Clearly reports illegal moves encountered during replay.

- **Early Termination:**  
  Invalid games are halted gracefully without affecting the rest of the batch.

---

### 4. Adherence to SOLID Principles

- **SRP Enforcement:**
    - `PgnParser`: Handles parsing and syntax validation.
    - `ReplayController`: Manages replay flow and error management.
    - `ChessMove`: Maps PGN strings to game logic actions.

- **Modularity:**  
  Independent modules for parsing, logic, and UI enable easy maintenance and extension.

---

## 🧪 Unit Testing

Tests cover:

- **PGN Parser:**  
  Syntax validation, malformed input handling, and valid parsing.

- **Chess Model:**  
  Move validation, state transitions, and detection of invalid states.

- **Error Handling:**  
  Ensures the application doesn't crash under erroneous inputs.

---

## 🧠 Remaining Improvements

1. **ChessMove parse logic**  
   Unimplemented ChessMove parser which takes single chess move as string and makes an actual move based on info it extracted.

---

## 🚀 How to Run the Program

### Prerequisites

- **Java 17+**
- **Maven**

### Steps

**Clone the Repository:**
```bash
git clone <repository_url>
cd <repository_directory>
mvn clean install
java -jar target/chess-replay-viewer.jar <directory_with_pgn_files>
```

### 🏆 Conclusion
The Chess Game Replay Viewer offers a reliable and user-friendly way to visualize chess games written in PGN format. By reusing and extending the refactored chess engine, it delivers a modular, maintainable tool ready for future upgrades and features.
