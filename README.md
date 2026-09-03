# Restaurant Automation System

A Java Swing application designed to help restaurant waiters manage tables and customer orders.

## Features

* Waiter login system
* 30 restaurant tables
* View assigned tables
* Table status tracking: Open, Occupied, and Dirty
* Menu organized by categories
* Add menu items to customer orders
* Kitchen order queue
* Waiter information loaded from a text file

## Technologies Used

* Java
* Java Swing
* Git/GitHub

## Project Structure

The project includes classes for:

* `RestaurantApp` – Main application and initialization
* `Table` – Stores table number, status, and orders
* `Waiter` – Stores waiter information and assigned tables
* `MenuItem` – Stores menu category and item information
* `LoginFrame` – Handles waiter login
* `FloorFrame` – Displays assigned tables and their status
* `TableFrame` – Displays table details
* `MenuFrame` – Allows waiters to select and add menu items

## How It Works

When the application starts, waiter information is loaded from `waiters.txt`, restaurant tables are initialized, and the menu is created.

After logging in, a waiter can view their assigned tables, select a table, choose menu items, and add them to the customer's order. Added orders are also placed into a kitchen queue.

## What I Learned

Through this project, I practiced Java programming, object-oriented programming, GUI development with Swing, working with files, collections such as lists and queues, and basic system design.

## Note

This project was created as an academic project to practice software development and object-oriented programming.
