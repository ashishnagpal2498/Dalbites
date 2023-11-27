# User Scenarios

## Project Summary
Dalbites is an application where the various restaurant owners within campus buildings can list their restaurants. After logging successfully into the application, owners can add the menu of food items they offer. Also, the owners handle the orders received from the customers.

The customers, after login, can apply the filter to get the restaurant of their choice, thereafter they can view the menu, and order the food of their taste. The customers have the facility to add multiple items to the cart, and then can proceed to checkout. They can also view their previous order history. Lastly, they can review the service they received.


## Features 1 : Authentication

* Every Dalhousie Student/Faculty who has a valid Dalhousie email address can Login as a User by entering their respective Unique IDs provided by Dalhousie University 
* The Email Ids are verified by OTP at the time of registeration
* To login user only has to enter their respective Unique IDs and their corresponding passwords.

* The owner of each and every food outlet in Dalhousie campus can register in this application using their personal email ID. 
* This Email ID is also verified at the time of Sign Up using OTP
* For Login the Restaurant owner can user his/her personal email ID.

## Features 2 : Setting Up Restaurant
* When a restaurant owner successfully logs in into his/her account for the first time, he his asked to Setup the Restaurant Profile.
* This incledes the Restaurant Name, Image, Description and Average prepation time.
* Next the owner has to setup the restaurant menu where he/she is supposed to add each and every item that they offer.
* Each Item must have Item name, Item Description, Item Cost, Item Image, and Preparation Time
* By default all these items are made available to the user, but incase the Restaurant is out of ingredients to prepare a particular item, the owner is able to make the item unavailable
* The owner is also allowed to edit or delete items from the menu

## Features 3 : Receive Orders
* When a user places an order the order is automatically reflected in orders section, where the owner can update the status of the order.
* The status can be "Accepted", "Declined", "In-Queue", "Preparing", "Pick-up" and "Picked-up"
* Each status update will send a notification to the user

## Features 4 : View Restaurants
* When a user successfully logs in into his/her account, they are able to see all the restaurant in Dalhousie campus.
* The user is able to filter these restaurant based on buildings
* If clicked on a restaurant card, the user is able to view all it food items and the restaurant review 

## Features 5 : Ordering 
* User is able to order multiple food item from a restaurant.
* The User is able to view all his to-be-ordered food item in cart, when he can edit his order and get a order summary at the same time
* The user is able to place order by card using a payment gateway
* After successfully placing the order, the user is able to see his order summary 

## Features 6 : Rating and Reviews
* Once a user has placed an order, the user can revist all their previous orders.
* Once ordered from a restaurant, the user is able to give rating and add a review to those restaurants.

[**Go back to README.md**](../README.md)
