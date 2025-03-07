package firstpackage;
import java.util.*;
import java.util.concurrent.*;

class Order {
    enum OrderType { BUY, SELL }
    
    OrderType orderType;
    String ticker;
    int quantity;
    double price;
    
    public Order(OrderType orderType, String ticker, int quantity, double price) {
        this.orderType = orderType;
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
    }
}

class StockOrderBook {
    private final Queue<Order> buyOrders = new ConcurrentLinkedQueue<>();
    private final Queue<Order> sellOrders = new ConcurrentLinkedQueue<>();
    private final Object lock = new Object();

    public void addOrder(Order order) {
        synchronized (lock) {
            if (order.orderType == Order.OrderType.BUY) {
                buyOrders.offer(order);
            } else {
                sellOrders.offer(order);
            }
            matchOrder();
        }
    }

    private void matchOrder() {
        synchronized (lock) {
            while (!buyOrders.isEmpty() && !sellOrders.isEmpty()) {
                Order buyOrder = buyOrders.peek();
                Order sellOrder = sellOrders.peek();

                if (buyOrder.price >= sellOrder.price) { 
                    int matchedQuantity = Math.min(buyOrder.quantity, sellOrder.quantity);
                    System.out.println("Matched " + matchedQuantity + " shares of " + buyOrder.ticker + " at $" + sellOrder.price);
                    
                    buyOrder.quantity -= matchedQuantity;
                    sellOrder.quantity -= matchedQuantity;
                    
                    if (buyOrder.quantity == 0) buyOrders.poll();
                    if (sellOrder.quantity == 0) sellOrders.poll();
                } else {
                    break;
                }
            }
        }
    }
}

class StockTradingEngine {
    private final StockOrderBook[] orderBooks;

    public StockTradingEngine(int numTickers) {
        orderBooks = new StockOrderBook[numTickers];
        for (int i = 0; i < numTickers; i++) {
            orderBooks[i] = new StockOrderBook();
        }
    }

    public void addOrder(Order.OrderType orderType, String ticker, int quantity, double price) {
        int tickerIndex = Math.abs(ticker.hashCode()) % orderBooks.length;
        orderBooks[tickerIndex].addOrder(new Order(orderType, ticker, quantity, price));
    }
}

public class StockTradingSimulation {

    public static void main(String[] args) {
        StockTradingEngine engine = new StockTradingEngine(1024);
        Random random = new Random();
        String[] tickers = new String[1024];
        for (int i = 0; i < 1024; i++) {
            tickers[i] = "STOCK" + i;
        }

        for (int i = 0; i < 500; i++) {
            String ticker = tickers[random.nextInt(tickers.length)];
            Order.OrderType orderType = random.nextBoolean() ? Order.OrderType.BUY : Order.OrderType.SELL;
            int quantity = random.nextInt(100) + 1;
            double price = 10 + (500 - 10) * random.nextDouble();

            engine.addOrder(orderType, ticker, quantity, price);
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
