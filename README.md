# Stock Trading Engine

## Overview

This project implements a real-time stock trading engine that matches buy and sell orders efficiently while ensuring thread safety and avoiding dictionary-based data structures. It supports up to 1,024 different stock tickers and processes transactions in O(n) time complexity.

## Technologies Used

- Java

- Multithreading (synchronization for thread safety)

- Lock-free data structures (`ConcurrentLinkedQueue`)

## Installation & Usage

### Prerequisites

- Java Development Kit (JDK) 8 or later

### Running the Code

1. Clone the repository:
```bash
git clone https://github.com/sevamahapat/stock-trading-engine.git
cd stock-trading-engine
```

2. Compile the Java files:
```bash
javac StockTradingSimulation.java
```

3. Run the simulation:
``` bash
java StockTradingSimulation
```

## Code Structure

- `Order.java` - Defines the order structure (buy/sell orders).

- `StockOrderBook.java` - Manages buy and sell orders and performs order matching.

- `StockTradingEngine.java` - Manages multiple stock order books.

- `StockTradingSimulation.java` - Simulates real-time stock transactions.

## Example Output
```
Matched 12 shares of STOCK844 at $34.8439261427889
Matched 88 shares of STOCK756 at $55.707648982449804
Matched 14 shares of STOCK393 at $138.33237202713082
Matched 63 shares of STOCK56 at $272.3437387116104
Matched 55 shares of STOCK300 at $50.01630368101819
Matched 36 shares of STOCK251 at $118.62374532406682
Matched 28 shares of STOCK32 at $29.962932227631214
```
