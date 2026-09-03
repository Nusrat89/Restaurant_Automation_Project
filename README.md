# Restaurant Automation System

A Java Swing project I built to help restaurant waiters manage tables and customer orders.

## Features

* Waiter login
* 30 restaurant tables
* View assigned tables
* Track table status: Open, Occupied, and Dirty
* Browse menu items by category
* Add items to customer orders
* Add orders to a kitchen queue
* Load waiter information from a text file

## Technologies Used

* Java
* Java Swing
* Git/GitHub

## Project Structure

The project includes several classes:

* `RestaurantApp` – Main application
* `Table` – Stores table information and orders
* `Waiter` – Stores waiter information and assigned tables
* `MenuItem` – Stores menu items and categories
* `LoginFrame` – Handles waiter login
* `FloorFrame` – Shows assigned tables
* `TableFrame` – Shows table details
* `MenuFrame` – Lets waiters select menu items

## How It Works

When the program starts, it loads waiter information from `waiters.txt`, creates the restaurant tables, and loads the menu.

After logging in, a waiter can view their assigned tables, select a table, choose menu items, and add them to an order. The order is also added to the kitchen queue.

## What I Learned

This project helped me practice Java, object-oriented programming, Java Swing, file handling, lists and queues, and basic software design.

## Academic Project

This was created as a student project to practice software development and object-oriented programming.
