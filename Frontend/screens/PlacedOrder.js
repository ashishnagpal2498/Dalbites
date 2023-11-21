import React from "react";
import { View, Text, StyleSheet, Image } from "react-native";
import { FlatList, ScrollView } from "react-native-gesture-handler";

const restaurantData = {
    id: 1,
    name: "Starbucks",
    photo: require("../assets/images/placeholder.png"),
};

const orderItemsData = [
    {
        orderItemId: 1,
        name: "Burger",
        cost: 8,
        quantity: 2,
        photo: require("../assets/images/Placeholder_Food_Item.png"),
    },
    {
        orderItemId: 2,
        name: "Fries",
        cost: 5,
        quantity: 3,
        photo: require("../assets/images/Placeholder_Food_Item.png"),
    },
    {
        orderItemId: 3,
        name: "Pizza",
        cost: 11,
        quantity: 1,
        photo: require("../assets/images/Placeholder_Food_Item.png"),
    },
    {
        orderItemId: 4,
        name: "Coffee",
        cost: 2,
        quantity: 4,
        photo: require("../assets/images/Placeholder_Food_Item.png"),
    },
    {
        orderItemId: 5,
        name: "Cold Drink",
        cost: 3,
        quantity: 2,
        photo: require("../assets/images/Placeholder_Food_Item.png"),
    },
];

const orderData = {
    orderId: 1,
    date: "14-Nov-2023",
    time: "5:49pm",
    subTotal: 25,
    taxes: 5,
    grandTotal: 30,
    estimatedWaitTime: 15,
    orderStatus: "Accepted",
    photo: require("../assets/images/placeholder.png"),
};

const PlacedOrder = () => {
    return (
        <ScrollView style={styles.container}>
            <View style={styles.restaurantContainer}>
                <Image style={styles.restaurantImage} source={restaurantData.photo} />
                <View style={styles.restaurantNameContainer}>
                    <Text style={styles.restaurantName}>{restaurantData.name}</Text>
                </View>
            </View>

            <View style={styles.allOrdersContainer}>

                <View style={styles.HeaderContainer}>
                    <Text style={styles.orderHeaderText}>Your Order</Text>
                    <Text style={styles.orderHeaderText}>Order ID : {orderData.orderId}</Text>
                </View>

                <View style={styles.orderCardsContainer}>
                    {orderItemsData.map((orderItemData) => (
                        <View style={styles.orderCard} key={orderItemData.orderItemId}>
                            <View style={styles.itemImageContainer}>
                                <Image
                                style={styles.orderItemImage}
                                source={orderItemData.photo}
                                />
                            </View>
                            <View style={styles.orderDetails}>
                                <Text style={styles.orderItemNameText}>{orderItemData.name}</Text>
                                <Text style={styles.orderCardText}>Cost per item: $ {orderItemData.cost}</Text>
                                <Text style={styles.orderCardText}>Quantity: {orderItemData.quantity}</Text>
                                <Text style={styles.orderCardText}>Total Cost: $ {orderItemData.cost * orderItemData.quantity}</Text>
                            </View>
                        </View>
                    ))}
                </View>
            </View>

            <View style={styles.orderSummaryContainer}>

                <View style={styles.HeaderContainer}>
                    <Text style={styles.orderHeaderText}>Order Summary</Text>
                </View>

                <View style={styles.orderRow1}>
                    <Text style={styles.orderRow1Text}>Date: {orderData.date}</Text>
                    <Text style={styles.orderRow1Text}>Time: {orderData.time}</Text>
                </View>

                <Text style={styles.orderSummaryText}>Subtotal: $ {orderData.subTotal}</Text>
                <Text style={styles.orderSummaryText}>Taxes: $ {orderData.taxes}</Text>
                <Text style={styles.orderSummaryText}>Grand Total: $ {orderData.grandTotal}</Text>

                <View style={styles.orderRow2}>
                    <Text style={styles.orderRow2Text}>Status: {orderData.orderStatus}</Text>
                    <Text style={styles.orderRow2Text}>Estimated waiting time: {orderData.estimatedWaitTime} </Text>
                </View>
            </View>
    </ScrollView>
    );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "column",
    backgroundColor: "white",
  },
  restaurantContainer: {
    width: "95%",
    aspectRatio: 2.2,
    overflow: "hidden",
    alignSelf: "center",
    borderRadius: 10,
    borderWidth: 3,
    marginVertical: 5,
  },
  restaurantImage: {
    width: "100%",
    height: "100%",
  },
  restaurantNameContainer: {
    width: "100%",
    position: "absolute",
    bottom: 0,
    alignSelf: "center",
  },
  restaurantName: {
    fontSize: 30,
    fontWeight: "bold",
    paddingHorizontal: 10,
    paddingVertical: 5,
    color: "#EAB308",
    backgroundColor: "rgba(0, 0, 0, 0.7)",
    textAlign: "left",
  },
  allOrdersContainer: {
    flexDirection:"column",
    width:"93%",
    alignSelf: "center",
    alignItems:"center",
    justifyContent:"space-evenly",
    backgroundColor:'white'
  },
  HeaderContainer: {
    flexDirection:"row",
    justifyContent:"space-between",
    width:"100%",
    paddingHorizontal:5,
    borderBottomWidth:1,
  },
  orderHeaderText: {
    fontSize: 21,
    fontWeight: "bold",
    margin: 5,
  },
  orderCardsContainer: {
    flexDirection:"column",
    paddingVertical:5,
    width:"100%",
  },
  orderCard: {
    flexDirection: "row",
    width: "95%",
    alignSelf:"center",
    backgroundColor: "white",
    borderRadius: 10,
    elevation: 10,
    marginVertical: 5,
  },
  itemImageContainer:{
    flex: 1.2,
    width: "100%",
    aspectRatio: 1,
    overflow: "hidden",
    borderBottomLeftRadius:10,
    borderTopLeftRadius:10,
    padding:5,
  },
  orderItemImage: {
    width: "100%",
    height: "100%",
    borderRadius:10,
  },
  orderDetails: {
    flex: 3,
    justifyContent:"space-evenly",
    padding:5,
  },
  orderItemNameText:{
    fontSize: 20,
    fontWeight: "bold",
  },
  orderCardText: {
    fontSize: 15,
    fontWeight: "500",
  },
  orderSummaryContainer: {
    width:"93%",
    alignSelf:"center",
    paddingHorizontal:10,
  },
  orderRow1: {
    flexDirection:"row",
    justifyContent:"space-between",
    paddingVertical:5,
  },
  orderRow1Text: {
    fontSize: 15,
    fontWeight: "400",
    paddingHorizontal:5
  },
  orderSummaryText: {
    fontSize: 18,
    fontWeight: "600",
    paddingHorizontal:5
  },
  orderRow2: {
    borderWidth:1,
    marginBottom:15,
    marginTop:10,
    alignItems:"center",
    backgroundColor:"#EAB308"

  },
  orderRow2Text: {
    fontSize: 18,
    fontWeight: "600",
    paddingHorizontal:5
  },
});

export default PlacedOrder;
